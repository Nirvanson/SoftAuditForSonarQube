package plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import plugin.analyser.FileNormalizer;
import plugin.analyser.MetricCalculator;
import plugin.analyser.ModelAnalyser;
import plugin.analyser.ModelBuilder;
import plugin.analyser.ModelDetailExpander;
import plugin.analyser.ModelStructureExpander;
import plugin.model.JavaFileContent;
import plugin.model.WordInFile;
import plugin.util.AnalyzeException;
import plugin.util.SoftAuditLogger;
import plugin.util.ParsingException;

/**
 * Analyses project-files in search for relevant information.
 *
 * @author Jan Rucks
 * @version 0.1
 */
public class SoftAuditSensor implements Sensor {

    /** The file system object for the project being analysed. */
    private final FileSystem fileSystem;
    private static final Logger LOGGER = Loggers.get(SoftAuditSensor.class);
    
    /**
     * Constructor for test-runs.
     *
     * @param fileSystem the project file system
     */
    public SoftAuditSensor(String filename) {
        this.fileSystem = null;
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("soft-audit-plugin.properties");) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        SoftAuditLogger.getLogger(properties.getProperty("logfilepath"), properties.getProperty("logfiletimestampformat"), 
                filename, Integer.valueOf(properties.getProperty("loglevel")));
    }

    /**
     * Constructor that sets the file system object for the project being analysed.
     *
     * @param fileSystem the project file system
     */
    public SoftAuditSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Determines whether the sensor should run or not for the given project.
     *
     * @param project - the project being analysed
     * @return true if java used in project
     */
    public boolean shouldExecuteOnProject(Project project) {
        if (fileSystem.languages().contains("java")) {
            return true;
        }
        return false;
    }

    /**
     * Do analysation.
     *
     * @param project - the project being analysed
     * @param sensorContext - the sensor context
     */
    public void analyse(Project project, SensorContext sensorContext) {
    	Settings properties = sensorContext.settings();
    	SoftAuditLogger.getLogger(properties.getString("logfilepath"), properties.getString("logfiletimestampformat"), 
                properties.getString("logfilename"), Integer.valueOf(properties.getInt("loglevel")));
        // get measures from files
        Map<Metric<?>, Double> measures = doAnalyse(fileSystem.files(fileSystem.predicates().hasLanguage("java")));
        measures.put(SoftAuditMetrics.OMS, Double.valueOf(properties.getDouble("optimalModuleSize")));
        // save measures
        for (Metric<?> measure : measures.keySet()) {
            sensorContext.saveMeasure(new Measure<Integer>(measure, measures.get(measure), 0));
        }
        // calculate metrics
        LOGGER.info("Calculate metrics");
        Map<Metric<?>, Double> metrics = MetricCalculator.calculate(measures);
        SoftAuditLogger.getLogger().printMetrics(metrics);
        // save metrics
        for (Metric<?> metric : metrics.keySet()) {
            if (metrics.get(metric) > 1) {
                sensorContext.saveMeasure(new Measure<Integer>(metric, 1.0, 3));
            } else {
                sensorContext.saveMeasure(new Measure<Integer>(metric, metrics.get(metric), 3));
            }
        }
        LOGGER.info("SoftAuditSensor finished");
        SoftAuditLogger.getLogger().close();
    }

    /**
     * Start analyzing.
     *
     * @return map with results for measures
     */
    public Map<Metric<?>, Double> doAnalyse(Iterable<File> files) {
    	Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        // add all measures from metrics list (metrics with keys like base_xyz)
        for (Metric<?> metric : new SoftAuditMetrics().getMetrics()) {
            if (metric.getKey().startsWith("base")) {
                result.put(metric, 0d);
            }
        }
        ModelAnalyser analyser = new ModelAnalyser();
        Map<List<WordInFile>, List<JavaFileContent>> models = new HashMap<List<WordInFile>, List<JavaFileContent>>();
        LOGGER.info("Parse java files and build model");
        for (File file : files) {
            // try parsing file
            List<JavaFileContent> fileModel = null;
            try {
                // step 0 - read file
            	SoftAuditLogger.getLogger().printFile(file);
            } catch (IOException exceptionInStepZero) {
                // file not readable, skip file completely
                exceptionInStepZero.printStackTrace();
                continue;
            }
            List<WordInFile> wordList = null;
            try {
                // step 1 - do file normalization
                wordList = FileNormalizer.doFileNormalization(file);
            } catch (ParsingException exceptionInStepOne) {
                // file normalization failed. skip file completely
                exceptionInStepOne.printStackTrace();
                continue;
            }
            try {
                // step 2 - build basic model
                fileModel = ModelBuilder.parseBasicModel(wordList);
            } catch (ParsingException exceptionInStepTwo) {
                // Building basic model failed. skip file completely
                exceptionInStepTwo.printStackTrace();
                continue;
            }
            boolean structureParsed = false;
            try {
                // step 3 - refine model by statement structure
                fileModel = ModelStructureExpander.parseStatementStructure(fileModel);
                structureParsed = true;
            } catch (ParsingException exceptionInStepThree) {
                // Refining Model with structural statements failed. skip detail parsing and do basic analysis
                exceptionInStepThree.printStackTrace();
            }
            if (structureParsed) {
                try {
                    // step 4 - parse model details
                    fileModel = ModelDetailExpander.parseModelDetails(fileModel);
                } catch (ParsingException exceptionInStepFour) {
                    // Refining Model with details failed. Do medium analysis
                    exceptionInStepFour.printStackTrace();
                }
            }
            // step 5 - if at least a basic model could be parsed analyze model for available measures
            if (fileModel != null) {
                models.put(wordList, fileModel);
            }
        }
        for (List<WordInFile> file : models.keySet()) {
            analyser.collectDeclaredMethods(models.get(file));
        }
        LOGGER.info("Count measures");
        for (List<WordInFile> file : models.keySet()) {
            try {
                Map<Metric<?>, Double> partialResult = analyser.doFileModelAnalysis(models.get(file), file);
                for (Metric<?> metric : partialResult.keySet()) {
                    result.put(metric, result.get(metric) + partialResult.get(metric));
                }
            } catch (AnalyzeException exceptionInStepFive) {
                // Analyzing model failed - ignore file
                exceptionInStepFive.printStackTrace();
            }
        }
        // step 6 - put non-additive measures to resultmap
        result.put(SoftAuditMetrics.SRC, analyser.getScannedSourceFiles());
        result.put(SoftAuditMetrics.DTY, analyser.getNumberOfDataTypes());
        result.put(SoftAuditMetrics.STY, analyser.getNumberOfStatementTypes());
        SoftAuditLogger.getLogger().printCumulatedMeasures(result);
        return result;
    }

    /**
     * Returns the name of the sensor as it will be used in logs during analysis.
     *
     * @return the name of the sensor
     */
    public String toString() {
        return "SoftAuditSensor";
    }
}

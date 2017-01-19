package plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import plugin.analyser.ModelAnalyser;
import plugin.analyser.ModelBuilder;
import plugin.analyser.ModelDetailExpander;
import plugin.analyser.ModelStructureExpander;
import plugin.model.AnalyseTriple;
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
        String timestamp = (new SimpleDateFormat(properties.getProperty("logfiletimestampformat"))).format(new Date());
        try {
            SoftAuditLogger.getLogger(properties.getProperty("logfilepath") + timestamp + filename, Integer.valueOf(properties.getProperty("loglevel")));
        } catch (NumberFormatException e) {
            LOGGER.error("Property 'loglevel' is invalid!", e);
        } catch (IOException e) {
            LOGGER.error("Initializing SoftAudit-Logger failed!", e);
        }
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
    	String timestamp = (new SimpleDateFormat(properties.getString("logfiletimestampformat"))).format(new Date());
    	String filename = properties.getString("logfilepath") + timestamp + properties.getString("logfilename");
    	try {
            SoftAuditLogger.getLogger(filename, Integer.valueOf(properties.getInt("loglevel")));
        } catch (IOException e) {
            LOGGER.error("Initializing SoftAudit-Logger failed!", e);
        }
    	properties.appendProperty("currentlogfile", filename);
        // get measures from files
        Map<Metric<?>, Double> measures = doAnalyse(fileSystem.files(fileSystem.predicates().hasLanguage("java")));
        measures.put(SoftAuditMetrics.OMS, Double.valueOf(properties.getDouble("optimalModuleSize")));
        // save measures
        for (Metric<?> measure : measures.keySet()) {
            sensorContext.saveMeasure(new Measure<Integer>(measure, measures.get(measure), 0));
        }
        LOGGER.info("--- SoftAuditSensor finished");
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
        List<AnalyseTriple<File, List<WordInFile>, List<JavaFileContent>>> models = new ArrayList<AnalyseTriple<File, List<WordInFile>, List<JavaFileContent>>>();
        LOGGER.info("--- Parse java files and build filemodels.");
        for (File file : files) {
            // try parsing file
            List<JavaFileContent> fileModel = null;
            List<WordInFile> wordList = null;
            LOGGER.info("Parse file " + file.getName());
            try {
                // step 1 - do file normalization
                wordList = FileNormalizer.doFileNormalization(file);
            } catch (ParsingException e) {
                // file normalization failed. skip file completely
                LOGGER.error("Normalizing file to word-list failed!", e);
                continue;
            }
            try {
                // step 2 - build basic model
                fileModel = ModelBuilder.parseBasicModel(wordList);
            } catch (ParsingException e) {
                // Building basic model failed. skip file completely
                LOGGER.error("Building basic model out of word-list failed!", e);
                continue;
            }
            boolean structureParsed = false;
            try {
                // step 3 - refine model by statement structure
                fileModel = ModelStructureExpander.parseStatementStructure(fileModel);
                structureParsed = true;
            } catch (ParsingException e) {
                // Refining Model with structural statements failed. skip detail parsing and do basic analysis
                LOGGER.error("Extending basemodel with structural statements failed!", e);
            }
            if (structureParsed) {
                try {
                    // step 4 - parse model details
                    fileModel = ModelDetailExpander.parseModelDetails(fileModel);
                } catch (ParsingException e) {
                    // Refining Model with details failed. Do medium analysis
                    LOGGER.error("Completing structured model with detail information failed!", e);
                }
            }
            models.add(new AnalyseTriple<File, List<WordInFile>, List<JavaFileContent>>(file, wordList, fileModel));
        }
        LOGGER.info("--- Count measures in parsed models");
        LOGGER.info("Collecting declared methods.");
        try  {
            for (AnalyseTriple<File, List<WordInFile>, List<JavaFileContent>> parsedFile : models) {
                if (parsedFile.getModel()!=null) {
                    analyser.collectDeclaredMethods(parsedFile.getModel());
                }
            }
        } catch (Exception e) {
            // Refining Model with details failed. Do medium analysis
            LOGGER.error("Collecting declared methods in models failed!", e);
        }
        for (AnalyseTriple<File, List<WordInFile>, List<JavaFileContent>> parsedFile : models) {
            LOGGER.info("Measure file " + parsedFile.getFile().getName());
            try {
                Map<Metric<?>, Double> partialResult = analyser.doFileModelAnalysis(parsedFile.getModel(), parsedFile.getWordList());
                SoftAuditLogger.getLogger().printAnalysedFile(parsedFile, partialResult);
                for (Metric<?> metric : partialResult.keySet()) {
                    result.put(metric, result.get(metric) + partialResult.get(metric));
                }
            } catch (AnalyzeException e) {
                LOGGER.error("Analyzing FileModel failed!", e);
            } catch (IOException e) {
                LOGGER.error("Logging parsed file failed!", e);
            }
        }
        // step 6 - put non-additive measures to resultmap
        LOGGER.info("Add non-additive measures");
        result.put(SoftAuditMetrics.SRC, analyser.getScannedSourceFiles());
        result.put(SoftAuditMetrics.DTY, analyser.getNumberOfDataTypes());
        result.put(SoftAuditMetrics.STY, analyser.getNumberOfStatementTypes());
        SoftAuditLogger.getLogger().printCumulatedMeasures(result);
        SoftAuditLogger.getLogger().close();
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

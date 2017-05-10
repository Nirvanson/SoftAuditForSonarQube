package plugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extendj.ExtensionMain;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import plugin.analyzer.FileNormalizer;
import plugin.analyzer.MeasureExtractor;
import plugin.analyzer.ModelBuilder;
import plugin.analyzer.ModelDetailExpander;
import plugin.analyzer.ModelStructureExpander;
import plugin.model.AnalyzeTriple;
import plugin.model.JavaFileContent;
import plugin.model.WordInFile;
import plugin.util.AnalyzeException;
import plugin.util.SoftAuditLogger;
import plugin.util.ParsingException;

/**
 * Analyzing source-files for retrieving measures needed for metric-calculation.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class SoftAuditSensor implements Sensor {

    /** Console-logger. */
    private static final Logger LOGGER = Loggers.get(SoftAuditSensor.class);
    /** The file system object for the project being analyzed. */
    private final FileSystem fileSystem;

    /**
     * Constructor for test-runs.
     *
     * @param filename - for the log-file-writer
     */
    protected SoftAuditSensor(String filename) {
    	this.fileSystem = null;
        try {
            SoftAuditLogger.getLogger(filename, 5);
        } catch (IOException e) {
            LOGGER.error("Initializing SoftAudit-Logger failed!", e);
        }
    }

    /**
     * Constructor that sets the file system object for the project being analyzed.
     *
     * @param fileSystem - the project file system
     */
    public SoftAuditSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Determines whether the sensor should run or not for the given project. Only Java is supported.
     *
     * @param project - the project being analyzed
     * @return true if java used in project
     */
    public boolean shouldExecuteOnProject(Project project) {
        if (fileSystem.languages().contains("java")) {
            return true;
        }
        return false;
    }

    /**
     * Start analyzing.
     *
     * @param project - the project being analyzed
     * @param sensorContext - the sensor context
     */
    public void analyse(Project project, SensorContext sensorContext) {
        LOGGER.info("--- SoftAuditSensor started");
        // initialization with properties values
        Double optimalModuleSize = null;
        try {
            Settings properties = sensorContext.settings();
            String timestamp = (new SimpleDateFormat(properties.getString("logfiletimestampformat")))
                    .format(new Date());
            String filename = properties.getString("logfilepath") + timestamp + properties.getString("logfilename");
            SoftAuditLogger.getLogger(filename, Integer.valueOf(properties.getInt("loglevel")));
            properties.appendProperty("currentlogfile", filename);
            optimalModuleSize = Double.valueOf(properties.getDouble("optimalModuleSize"));
        } catch (Exception e) {
            // initialization failed. Use default for optimalModuleSize and skip file-logging
            LOGGER.warn("Initializing SoftAudit-Logger failed!");
            optimalModuleSize = 200.0;
        }
        
        // do analyze
        Map<Metric<?>, Double> measures = doAnalyze(fileSystem.files(fileSystem.predicates().hasLanguage("java")),
                optimalModuleSize);
        try {
            SoftAuditLogger.getLogger().printCumulatedMeasures(measures);
            SoftAuditLogger.getLogger().close();
        } catch (IOException e) {
            LOGGER.warn("Logging measures failed!");
        }

        // save measures
        for (Metric<?> measure : measures.keySet()) {
            sensorContext.saveMeasure(new Measure<Integer>(measure, measures.get(measure), 0));
        }
        LOGGER.info("--- SoftAuditSensor finished");
    }

    /**
     * Starts the single analyze-steps and collects the retrieved measures.
     *
     * @param files - java source files to be analyzed
     * @param omsvalue - fixed value for optimalModuleSize (from properties)
     * @return map with results for measures
     */
    protected Map<Metric<?>, Double> doAnalyze(Iterable<File> files, double omsvalue) {
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        // add all measures from metrics list (metrics with keys like base_xyz) with start-value 0
        for (Metric<?> metric : new SoftAuditMetrics().getMetrics()) {
            if (metric.getKey().startsWith("base")) {
                result.put(metric, 0d);
            }
        }
        // add optimalModuleSize from properties
        result.put(SoftAuditMetrics.OMS, omsvalue);

        // Parse
        List<AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>>> models = new ArrayList<AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>>>();
        LOGGER.info("--- Parse java files and build filemodels.");
        for (File file : files) {
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
            models.add(new AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>>(file, wordList, fileModel));
        }

        // Analyze
        LOGGER.info("--- Count measures in parsed models");
        MeasureExtractor analyzer = new MeasureExtractor();
        try {
            // collect all declared methods (for scanning after foreign function calls)
            LOGGER.info("Collecting declared methods.");
            for (AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>> parsedFile : models) {
                if (parsedFile.getModel() != null) {
                    analyzer.collectDeclaredMethods(parsedFile.getModel());
                }
            }
        } catch (Exception e) {
            // Collecting Methods in Models failed - maybe invalid results
            LOGGER.error("Collecting declared methods in models failed!", e);
        }
        for (AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>> parsedFile : models) {
            // do measurement in filemodel and wordlist
            LOGGER.info("Measure file " + parsedFile.getFile().getName());
            try {
                Map<Metric<?>, Double> partialResult = analyzer.doFileModelAnalysis(parsedFile.getModel(),
                        parsedFile.getWordList());
                SoftAuditLogger.getLogger().printAnalysedFile(parsedFile, partialResult);
                for (Metric<?> metric : partialResult.keySet()) {
                    result.put(metric, result.get(metric) + partialResult.get(metric));
                }
            } catch (AnalyzeException e) {
                LOGGER.error("Analyzing FileModel failed!", e);
            } catch (IOException e) {
                LOGGER.warn("Logging parsed file failed!");
            }
        }

        // Put non-additive measures to result-map
        LOGGER.info("Add global measures");
        result.put(SoftAuditMetrics.SRC, analyzer.getScannedSourceFiles());
        result.put(SoftAuditMetrics.DTY, analyzer.getNumberOfDataTypes());
        result.put(SoftAuditMetrics.STY, analyzer.getNumberOfStatementTypes());

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

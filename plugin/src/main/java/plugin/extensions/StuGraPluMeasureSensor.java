package plugin.extensions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extendj.MeasureExtractor;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import plugin.definitions.StuGraPluConfigurableValues;
import plugin.definitions.StuGraPluMeasures;
import plugin.services.LogFileWriter;

/**
 * Analyzing source-files for retrieving measures needed for metric-calculation.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class StuGraPluMeasureSensor implements Sensor {

    /** Console-logger. */
    private static final Logger LOGGER = Loggers.get(StuGraPluMeasureSensor.class);
    /** The file system object for the project being analyzed. */
    private final FileSystem fileSystem;

    /**
     * Constructor for test-runs.
     *
     * @param filename - for the log-file-writer
     * @throws IOException
     */
    protected StuGraPluMeasureSensor(String logFileName) throws IOException {
        this.fileSystem = null;
        LogFileWriter.getLogger(logFileName, LogFileWriter.FULL_LOG);
    }

    /**
     * Constructor that sets the file system object for the project being analyzed.
     *
     * @param fileSystem - the project file system
     */
    public StuGraPluMeasureSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Determines whether the sensor should run or not for the given project. Only Java is
     * supported.
     *
     * @param project - the project being analyzed
     * @return true if java used in project
     */
    @Override
    public boolean shouldExecuteOnProject(Project project) {
        if (fileSystem.languages().contains("java")) {
            return true;
        }
        return false;
    }

    /**
     * Do project analysis.
     *
     * @param project - the project being analyzed
     * @param sensorContext - the sensor context
     */
    @Override
    public void analyse(Project project, SensorContext sensorContext) {
        LOGGER.info("--- StuGraPlu-Sensor started");
        Map<Metric<?>, Double> configurableValues = initializeWithProperties(sensorContext);
        String[] fileNames = getFileList();
        Map<Metric<?>, Double> measures = extractMeasures(fileNames);
        measures.putAll(configurableValues);

        try {
            LogFileWriter.getLogger().printMeasures(measures);
            LogFileWriter.getLogger().close();
        } catch (IOException ex) {
            LOGGER.warn("Logging measures failed!");
        }

        for (Metric<?> measure : measures.keySet()) {
            sensorContext.saveMeasure(new Measure<Integer>(measure, measures.get(measure), 0));
        }
        LOGGER.info("--- StuGraPlu-Sensor finished");
    }

    /**
     * Initializes log-file-writer and configurable values from properties file.
     * 
     * @param sensorContext
     * @return map of configurable values
     */
    private Map<Metric<?>, Double> initializeWithProperties(SensorContext sensorContext) {
        Map<Metric<?>, Double> configurableValues = new HashMap<Metric<?>, Double>();
        try {
            Settings properties = sensorContext.settings();
            String timestamp = (new SimpleDateFormat(properties.getString("logfiletimestampformat")))
                    .format(new Date());
            String filename = properties.getString("logfilepath") + timestamp + properties.getString("logfilename");
            LogFileWriter.getLogger(filename, Integer.valueOf(properties.getInt("loglevel")));
            properties.appendProperty("currentlogfile", filename);
            configurableValues.put(StuGraPluConfigurableValues.OMS,
                    Double.valueOf(properties.getDouble("optimalModuleSize")));
            LOGGER.info("Adding configurable values finished");
        } catch (Exception ex) {
            LOGGER.warn("Initializing with properties-file failed!");
            LOGGER.warn("Default values will be used and no logfiles can be written.");
            configurableValues.put(StuGraPluConfigurableValues.OMS, StuGraPluConfigurableValues.OMS_DEFAULT);
        }
        return configurableValues;
    }

    /**
     * Extracts filename-array from project.
     * 
     * @param project - to analyze
     * @return list of filenames
     */
    private String[] getFileList() {
        Iterable<File> files = fileSystem.files(fileSystem.predicates().hasLanguage("java"));
        List<String> fileNames = new ArrayList<String>();
        for (File file : files) {
            fileNames.add(file.getAbsolutePath());
        }
        String[] nameArray = new String[fileNames.size()];
        nameArray = fileNames.toArray(nameArray);

        try {
            LogFileWriter.getLogger().printFileList(nameArray);
        } catch (IOException ex) {
            LOGGER.warn("Logging filenames failed!");
        }
        return nameArray;
    }

    /**
     * Starts the extendJ-node-collector and counts the retrieved nodes for each measures.
     *
     * @param files - source files to measure
     * @return map with results
     */
    @SuppressWarnings("rawtypes")
    protected Map<Metric<?>, Double> extractMeasures(String[] fileNames) {
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        List<Metric> measures = (new StuGraPluMeasures()).getMetrics();

        Map<String, Collection<String>> collectedNodes = new MeasureExtractor().extractNodes(fileNames);
        try {
            LogFileWriter.getLogger().printCollectedNodes(collectedNodes);
        } catch (IOException ex) {
            LOGGER.warn("Logging collected nodes failed!");
        }

        for (Metric measure : measures) {
            result.put(measure, (double) collectedNodes.get(measure.getName()).size());
        }

        LOGGER.info("Extracting measures with ExtendJ finished");
        return result;
    }

    /**
     * Returns the name of the sensor as it will be used in logs during analysis.
     *
     * @return the name of the sensor
     */
    public String toString() {
        return "StuGraPluSensor";
    }
}

package plugin.worker;

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

import plugin.definition.StuGraPluConfigurableValues;
import plugin.definition.StuGraPluMeasures;

/**
 * Analyzing source-files for retrieving measures needed for metric-calculation.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class StuGraPluSensor implements Sensor {

    /** Console-logger. */
    private static final Logger LOGGER = Loggers.get(StuGraPluSensor.class);
    /** The file system object for the project being analyzed. */
    private final FileSystem fileSystem;

    /**
     * Constructor for test-runs.
     *
     * @param filename - for the log-file-writer
     */
    protected StuGraPluSensor(String filename) {
        this.fileSystem = null;
        try {
            LogFileWriter.getLogger(filename, 5);
        } catch (IOException e) {
            LOGGER.error("Initializing LogFileWriter failed!", e);
        }
    }

    /**
     * Constructor that sets the file system object for the project being analyzed.
     *
     * @param fileSystem - the project file system
     */
    public StuGraPluSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Determines whether the sensor should run or not for the given project. Only Java is
     * supported.
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
        LOGGER.info("--- StuGraPlu-Sensor started");
        // initialization with properties values
        Double optimalModuleSize = null;
        try {
            Settings properties = sensorContext.settings();
            String timestamp = (new SimpleDateFormat(properties.getString("logfiletimestampformat")))
                    .format(new Date());
            String filename = properties.getString("logfilepath") + timestamp + properties.getString("logfilename");
            LogFileWriter.getLogger(filename, Integer.valueOf(properties.getInt("loglevel")));
            properties.appendProperty("currentlogfile", filename);
            optimalModuleSize = Double.valueOf(properties.getDouble("optimalModuleSize"));
        } catch (Exception e) {
            // initialization failed. Use default for optimalModuleSize and skip file-logging
            LOGGER.warn(
                    "Initializing with properties-file failed! - Default values will be used and no logfiles can be written.");
            optimalModuleSize = 200.0;
        }

        // parse and measure source files with extendJ
        Map<Metric<?>, Double> measures = extractMeasures(
                fileSystem.files(fileSystem.predicates().hasLanguage("java")));

        // Add configurable values
        measures.put(StuGraPluConfigurableValues.OMS, optimalModuleSize);

        try {
            LogFileWriter.getLogger().printMeasures(measures);
            LogFileWriter.getLogger().close();
        } catch (IOException e) {
            LOGGER.warn("Logging measures failed!");
        }

        // save measures
        for (Metric<?> measure : measures.keySet()) {
            sensorContext.saveMeasure(new Measure<Integer>(measure, measures.get(measure), 0));
        }
        LOGGER.info("--- StuGraPlu-Sensor finished");
    }

    /**
     * Starts the extendJ-node-collector and counts the retrieved nodes for each measures.
     *
     * @param files - source files to measure
     * @return map with results
     */
    @SuppressWarnings("rawtypes")
    protected Map<Metric<?>, Double> extractMeasures(Iterable<File> files) {
        // list file names in array
        List<File> input = new ArrayList<File>();
        for (File file : files) {
            input.add(file);
        }
        String[] filenames = new String[input.size()];
        for (int i = 0; i < input.size(); i++) {
            filenames[i] = input.get(i).getAbsolutePath();
        }
        try {
            LogFileWriter.getLogger().printFileList(input);
        } catch (IOException e) {
            LOGGER.warn("Logging filenames failed!");
        }

        // initialize resultMap
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        List<Metric> measures = (new StuGraPluMeasures()).getMetrics();

        // run extendJ-measure-extractor
        long startTime = System.currentTimeMillis();
        Map<String, Collection<String>> collectedNodes = new MeasureExtractor().extractNodes(filenames);
        long extendJRunningTime = startTime - System.currentTimeMillis();
        LOGGER.info("Running ExtendJ-Node-Collector took " + extendJRunningTime + " milliseconds");
        try {
            LogFileWriter.getLogger().printCollectedNodes(collectedNodes);
        } catch (IOException e) {
            LOGGER.warn("Logging collected nodes failed!");
        }

        // count collected elements
        for (Metric measure : measures) {
            result.put(measure, (double) collectedNodes.get(measure.getName()).size());
        }

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

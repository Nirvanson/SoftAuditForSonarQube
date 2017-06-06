package plugin.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.sonar.api.measures.Metric;

/**
 * Prints all steps to log-file.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class LogFileWriter {
    /** The singleton instance. */
    private static LogFileWriter logger;

    /** Headline for step 1: printing names of measured files. */
    private final String FILES = "--- Analyzed Files:";
    /** Headline for step 2: printing nodes collected by extendJ. */
    private final String NODES = "--- Collected nodes:";
    /** Headline for step 3: printing accumulated measures on project-level. */
    private final String MEASURES = "--- Measured values:";
    /** Headline for step 4: printing accumulated measures on project-level. */
    private final String SONARMEASURES = "--- Measures provided by SonarQube:";
    /** Headline for step 5: printing calculated metrics on project level. */
    private final String METRICS = "--- Calculated Metrics:";

    /** The log-level that determines which steps are printed. */
    private final int loglevel;
    /** The writer. */
    private PrintWriter writer;

    /**
     * The Constructor, initializing writer.
     * 
     * @param filename - full name of the log-file with path
     * @param loglevel
     * @throws IOException - if file creation fails
     */
    private LogFileWriter(String filename, int loglevel) throws IOException {
        this.loglevel = loglevel;
        writer = new PrintWriter(new FileWriter(filename, true));
    }

    /**
     * Get singleton instance, used for initialization.
     * 
     * @param filename
     *        - full name of the log-file with path
     * @param loglevel
     * @throws IOException
     *         - if file creation fails
     */
    public static LogFileWriter getLogger(String filename, int loglevel) throws IOException {
        if (logger == null) {
            logger = new LogFileWriter(filename, loglevel);
        }
        return logger;
    }

    /**
     * Get singleton instance, used after initialization.
     * 
     * @param filename - full name of the log-file with path
     * @param loglevel
     * @throws IOException - if file creation fails
     */
    public static LogFileWriter getLogger() throws IOException {
        if (logger == null) {
            throw new IOException("Logger is not initialized!");
        }
        return logger;
    }

    /**
     * Print out file list.
     * 
     * @param files - list of analyzed files
     */
    public void printFileList(List<File> files) {
        if (loglevel > 3) {
            writer.println(FILES);
            for (File file : files) {
                writer.println(file.getName());
            }
        }
    }

    /**
     * Print out by extendJ collected nodes.
     * 
     * @param collectedNodes - node map
     */
    public void printCollectedNodes(Map<String, Collection<String>> collectedNodes) {
        if (loglevel > 2) {
            writer.println(NODES);
            for (String nodetype : collectedNodes.keySet()) {
                writer.println(nodetype + ":");
                for (String node : collectedNodes.get(nodetype)) {
                    writer.println(node);
                }
            }
        }
    }

    /**
     * Print out retrieved measures on project level.
     * 
     * @param measures - accumulated measures
     */
    public void printMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 1) {
            writer.println(MEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    /**
     * Print measures provided by SonarQube.
     * 
     * @param measures - measures provided by sonarqube
     */
    public void printSonarMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 1) {
            writer.println(SONARMEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    /**
     * Print calculated metrics.
     * 
     * @param metrics - calculated metrics
     */
    public void printMetrics(Map<Metric<?>, Double> metrics) {
        if (loglevel > 0) {
            writer.println(METRICS);
            for (Metric<?> metric : metrics.keySet()) {
                writer.println(metric.getName() + ": " + metrics.get(metric));
            }
        }
    }

    /**
     * Close writer and set singleton instance to null.
     */
    public void close() {
        if (logger != null) {
            writer.close();
        }
        logger = null;
    }
}

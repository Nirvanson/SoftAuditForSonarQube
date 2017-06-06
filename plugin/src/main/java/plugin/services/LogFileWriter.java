package plugin.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
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

    /** Log-Level 0 - no log-file */
    public static final int NO_LOG = 0;
    /** Log-Level 1 - only results in log (metrics, index) */
    public static final int ONLY_RESULT = 1;
    /** Log-Level 2 - results and measures in log */
    public static final int RESULTS_AND_MEASURES = 2;
    /** Log-Level 3 - all steps in log (nodes, measures, results) */
    public static final int ALL_STEPS = 3;
    /** Log-Level 4 - full log (All steps and files) */
    public static final int FULL_LOG = 4;

    /** Headline for step 1: printing names of measured files. */
    private static final String FILES = "--- Analyzed Files:";
    /** Headline for step 2: printing nodes collected by extendJ. */
    private static final String NODES = "--- Collected nodes:";
    /** Headline for step 3: printing accumulated measures on project-level. */
    private static final String MEASURES = "--- Measured values:";
    /** Headline for step 4: printing accumulated measures on project-level. */
    private static final String SONARMEASURES = "--- Measures provided by SonarQube:";
    /** Headline for step 5: printing calculated metrics on project level. */
    private static final String METRICS = "--- Calculated Metrics:";

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
     * @param filename - full name of the log-file with path
     * @param loglevel - to establish
     * @return logFileWriter-instance
     * @throws IOException - if file creation fails
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
    public void printFileList(String[] files) {
        if (loglevel == FULL_LOG) {
            writer.println(FILES);
            for (int i = 0; i < files.length; i++) {
                writer.println(files[i]);
            }
        }
    }

    /**
     * Print out by extendJ collected nodes.
     * 
     * @param collectedNodes - node map
     */
    public void printCollectedNodes(Map<String, Collection<String>> collectedNodes) {
        if (loglevel >= ALL_STEPS) {
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
        if (loglevel >= RESULTS_AND_MEASURES) {
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
        if (loglevel >= RESULTS_AND_MEASURES) {
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
        if (loglevel >= ONLY_RESULT) {
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

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
 * Prints all steps of parsing, analyzing and calculating to log-file.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class LogFileWriter {
    /** The singleton instance. */
    private static LogFileWriter logger;

    /** Headline for step 1: printing source-file-content. */
    private final String FILE = "--- FILENAME --- Analyzed Filecontent:";
    /** Headline for step 2: printing extracted word-list. */
    private final String WORDLIST = "--- FILENAME --- Extracted wordlist:";
    /** Headline for step 3: printing generated file-model. */
    private final String FILEMODEL = "--- FILENAME --- Generated Model:";
    /** Headline for step 4: printing measured values for file. */
    private final String FILEMEASURES = "--- FILENAME --- Measured Values:";
    /** Headline for step 5: printing accumulated measures on project-level. */
    private final String MEASURES = "--- Project-level - Measured values:";
    /** Headline for step 6: printing accumulated measures on project-level. */
    private final String SONARMEASURES = "--- Project-level - Measures provided by SonarQube:";
    /** Headline for step 7: printing calculated metrics on project level. */
    private final String METRICS = "--- Project-level - Calculated Metrics:";

    /** The log-level that determines which steps are printed. */
    private final int loglevel;
    /** The writer. */
    private PrintWriter writer;

    /**
     * The Constructor, initializing writer.
     * 
     * @param filename
     *            - full name of the log-file with path
     * @param loglevel
     * @throws IOException
     *             - if file creation fails
     */
    private LogFileWriter(String filename, int loglevel) throws IOException {
        this.loglevel = loglevel;
        writer = new PrintWriter(new FileWriter(filename, true));
    }

    /**
     * Get singleton instance, used for initialization.
     * 
     * @param filename
     *            - full name of the log-file with path
     * @param loglevel
     * @throws IOException
     *             - if file creation fails
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
     * @param filename
     *            - full name of the log-file with path
     * @param loglevel
     * @throws IOException
     *             - if file creation fails
     */
    public static LogFileWriter getLogger() throws IOException {
        if (logger == null) {
            throw new IOException("Logger is not initialized!");
        }
        return logger;
    }

    public void printFileList(List<File> files) {
        // TODO
    }

    public void printCollectedNodes(Map<String, Collection<String>> collectedNodes) {
        // TODO
    }

    /**
     * Print out retrieved measures on project level.
     * 
     * @param measures
     *            - accumulated measures
     */
    public void printMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 0) {
            writer.println(MEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    /**
     * Print step 6 on project level.
     * 
     * @param measures
     *            - measures provided by sonarqube
     */
    public void printSonarMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 0) {
            writer.println(SONARMEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    /**
     * Print step 7 on project level.
     * 
     * @param metrics
     *            - calculated metrics
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

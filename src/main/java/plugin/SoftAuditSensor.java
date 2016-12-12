package plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

import plugin.analyser.JavaFileAnalyzer;

/**
 * Analyses project-files in search for relevant information.
 *
 * @author Jan Rucks
 * @version 0.1
 */
public class SoftAuditSensor implements Sensor {

    /** The file system object for the project being analysed. */
    private final FileSystem fileSystem;
    /** The logger object for the sensor. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor that sets the file system object for the
     * project being analysed.
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
        	log.info("Java as language detected. Do analysation!");
        	return true;
        }
        return false;
    }

    /**
     * Do analysation.
     *
     * @param project       - the project being analysed
     * @param sensorContext - the sensor context
     */
    public void analyse(Project project, SensorContext sensorContext) {
    	// extract files
        Iterable<File> files = fileSystem.files(fileSystem.predicates().hasLanguage("java"));
        // initalise analyzer 
        JavaFileAnalyzer analyzer = new JavaFileAnalyzer(files);
        // analyse
        Map<Metric<Integer>, Double> measures = new HashMap<Metric<Integer>, Double>();
        measures = analyzer.analyze();
        // save
        saveMeasures(sensorContext, measures); 
        log.info("Analysation Done!");
    }

    /**
     * Saves measures corresponding to main project information.
     *
     * @param sensorContext - the sensor context
     * @param measures      - measured values as HashMap
     */
    private void saveMeasures(SensorContext sensorContext,  Map<Metric<Integer>, Double> measures) {
    	// save measures
        sensorContext.saveMeasure(new Measure<Integer>(SoftAuditMetrics.RET, measures.get(SoftAuditMetrics.RET)));
        sensorContext.saveMeasure(new Measure<Float>(SoftAuditMetrics.EXA, measures.get(SoftAuditMetrics.RET)*2));
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

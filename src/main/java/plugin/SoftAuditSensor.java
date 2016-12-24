package plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
        // measure
        Map<Metric<Integer>, Double> measures = new HashMap<Metric<Integer>, Double>();
        measures = analyzer.analyze();
        // save measures
        for (Metric<Integer> metric: measures.keySet()) {
    		sensorContext.saveMeasure(new Measure<Integer>(metric, measures.get(metric)));
    	}
        // compute and save metrics
        sensorContext.saveMeasure(new Measure<Float>(SoftAuditMetrics.COC, (measures.get(SoftAuditMetrics.IFS) + 
        		measures.get(SoftAuditMetrics.LOP) + measures.get(SoftAuditMetrics.SWI) + measures.get(SoftAuditMetrics.CAS)) / (measures.get(SoftAuditMetrics.MET) * 4)));
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

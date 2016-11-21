package plugin;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

import plugin.analyser.AnalyzerException;
import plugin.analyser.EclipseAnalyzer;
import plugin.model.ProjectInfo;

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
     * @param project the project being analysed
     * @return always true
     */
    public boolean shouldExecuteOnProject(Project project) {
        // TODO: only java projects
        return true;
    }

    /**
     * Do analysation.
     *
     * @param project       the project being analysed
     * @param sensorContext the sensor context
     */
    public void analyse(Project project, SensorContext sensorContext) {
    	//TODO: switch to SoftAudit analyzer
        File rootDir = fileSystem.baseDir();

        log.info("Analysing project root in search for IDE Metadata files: " + rootDir.getAbsolutePath());

        EclipseAnalyzer analyzer = new EclipseAnalyzer(rootDir);
        ProjectInfo projectInfo;

        try {
        	
            projectInfo = analyzer.analyze();

            log.info("Analysis done");
            log.debug("this is what we've found: " + projectInfo);

            saveMeasures(sensorContext, projectInfo);

        } catch (AnalyzerException ae) {
            log.error("Error while running EclipseAnalyzer", ae);
        }
    }

    /**
     * Saves measures corresponding to main project information.
     *
     * @param sensorContext the sensor context
     * @param projectInfo   the project information bean
     */
    private void saveMeasures(SensorContext sensorContext, ProjectInfo projectInfo) {
    	//TODO: switch to SoftAudit Measures
        log.debug("Start saving measures.");
        Measure<String> measure = new Measure<String>(SoftAuditMetrics.IDE_PRO_NAME, projectInfo.getProjectName());
        sensorContext.saveMeasure(measure);

        log.debug("Finished saving measures.");
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

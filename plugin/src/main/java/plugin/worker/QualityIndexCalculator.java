package plugin.worker;

import org.sonar.api.batch.Phase;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.issue.Issue;
import org.sonar.api.issue.ProjectIssues;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

/**
 * Calculating code quality index out of tracked issues.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@Phase(name = Phase.Name.POST)
public class QualityIndexCalculator implements PostJob {

    /** Console-logger. */
    private final Logger LOGGER = Loggers.get(QualityIndexCalculator.class);
    /** Issues found by SonarQube. */
    private ProjectIssues issues;

    /**
     * Constructor, sets issuelist.
     * 
     * @param issues
     */
    public QualityIndexCalculator(ProjectIssues issues) {
        this.issues = issues;
    }

    /**
     * Run PostJob to calculate the code-quality-index from detected issues.
     *
     * @param project - the project being analyzed
     * @param sensor - SensorContext
     */
    @Override
    public void executeOn(Project project, SensorContext sensor) {
        LOGGER.info("--- QualityIndexCalculator started for " + project.getName() + " / " + sensor.toString());
        for (Issue issue : issues.issues()) {
            LOGGER.info("--- new issue:");
            LOGGER.info("key: " + issue.key());
            LOGGER.info("message: " + issue.message());
            LOGGER.info("severity: " + issue.severity());
            LOGGER.info("status: " + issue.status());
        }
    }
}

package plugin.worker;

import java.util.ArrayList;
import java.util.List;

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
    private static final Logger LOGGER = Loggers.get(QualityIndexCalculator.class);
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
     * @param context - SensorContext
     */
    @Override
    public void executeOn(Project project, SensorContext context) {
        LOGGER.info("--- QualityIndexCalculator started for " + project.getName() + " / " + context.toString());
        List<String> issueKeys = new ArrayList<String>();
        for (Issue issue : issues.issues()) {
            issueKeys.add(issue.key());
        }
        LOGGER.info(issueKeys.size() + " issues in context.");
        LOGGER.info("--- QualityIndexCalculator finished.");
    }
}

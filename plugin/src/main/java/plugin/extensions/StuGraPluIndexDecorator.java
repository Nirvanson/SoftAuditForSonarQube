package plugin.extensions;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.ProjectIssues;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import plugin.definitions.StuGraPluIndexValues;
import plugin.model.IndicatorThreshold;
import plugin.services.IndexDataCollector;

/**
 * Calculating metrics out of measured values from StuGraPluSensor and some CoreMetrics.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@DependsUpon(DecoratorBarriers.ISSUES_TRACKED)
public class StuGraPluIndexDecorator implements Decorator {

    /** Console-logger. */
    private static final Logger LOGGER = Loggers.get(StuGraPluIndexDecorator.class);
    /** Context of the decorator. */
    private ProjectIssues issues;

    public StuGraPluIndexDecorator(ProjectIssues issues) {
        this.issues = issues;
    }

    /**
     * Determines whether the decorator should run or not for the given project.
     *
     * @param project - the project being analyzed
     * @return true
     */
    @Override
    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }

    /**
     * Do metric-calculations.
     *
     * @param resource - the resource for which the calculation should be done
     * @param context - the decorator context
     */
    @SuppressWarnings("deprecation")
    @Override
    public void decorate(Resource resource, DecoratorContext context) {
        if (!Scopes.isProject(resource)) {
            return;
        }
        Project project = context.getProject();
        LOGGER.info("--- QualityIndexCalculator");
        Settings properties = project.getSettings();
        double linesOfCode = MeasureUtils.getValue(context.getMeasure(CoreMetrics.LINES), 0.0);
        if (properties.getString("indexmode") == null || properties.getString("indexmode").equals("calculate")) {
            LOGGER.info("Calculate SWP-Code-Quality-Index based on former data");
            calculateIndex(properties, linesOfCode, context);
        } else if (properties.getString("indexmode").equals("datacollect")) {
            LOGGER.info("Collect data for later Index-Calculation");
            performDataCollection(properties, project.getName(), linesOfCode);
        } else {
            LOGGER.warn("Unknown Index-Mode in properties. No action will be performed!");
        }
        LOGGER.info("--- QualityIndexCalculator finished.");
    }

    private void performDataCollection(Settings properties, String projectName, double linesOfCode) {
        IndexDataCollector dataCollector = null;
        try {
            dataCollector = new IndexDataCollector(properties);
        } catch (ClassNotFoundException ex) {
            LOGGER.error("JDBC-Driver not found. No Data collection possible!");
        } catch (SQLException ex) {
            LOGGER.error("Connecting to Index-Database failed. No Data collection possible!");
        } catch (FileNotFoundException ex) {
            LOGGER.error("Database-initialization-sql-file not found!");
        }
        if (dataCollector != null) {
            try {
                dataCollector.collectRuleViolationsForIndex(issues, projectName, linesOfCode);
            } catch (SQLException ex) {
                LOGGER.error("Collecting Rule-Violations for Index database failed!");
                ex.printStackTrace();
            }
            try {
                dataCollector.closeConnection();
            } catch (SQLException ex) {
                LOGGER.error("Closing Index-Database-Connection failed!");
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void calculateIndex(Settings properties, double linesOfCode, DecoratorContext context) {
        IndexDataCollector dataCollector = null;
        try {
            dataCollector = new IndexDataCollector(properties);
        } catch (ClassNotFoundException ex) {
            LOGGER.error("JDBC-Driver not found. No Index calculation possible!");
        } catch (SQLException ex) {
            LOGGER.error("Connecting to Index-Database failed. No Index calculation possible!");
        } catch (FileNotFoundException ex) {
            LOGGER.error("Database not initialized. No Index calculation possible!");
        }
        if (dataCollector != null) {
            Double reachedLevel = 5D;
            Map<Integer, Double> violations = null;
            List<IndicatorThreshold> thresholds = null;
            List<Metric> indicatorValues = (new StuGraPluIndexValues()).getMetrics();
            try {
                violations = dataCollector.countViolationsGroupedByIndicator(issues, linesOfCode);
                thresholds = dataCollector.getIndicatorThresholds();
            } catch (SQLException ex) {
                LOGGER.error("Collecting Rule-Violations or Thresholds for project failed!");
                ex.printStackTrace();
            }
            for (IndicatorThreshold indicator : thresholds) {
                Integer indicatorLevel = indicator.calculateLevel(violations.get(indicator.getId()));
                reachedLevel = Math.min(reachedLevel, indicatorLevel);
                String metricText = "Level " + indicatorLevel + " - "
                        + indicator.getPositioning(violations.get(indicator.getId()));
                saveIndicatorValue(indicator.getName(), metricText, indicatorValues, context);
            }
            context.saveMeasure(new Measure<Integer>(StuGraPluIndexValues.CQI, reachedLevel, 1));
        }
    }

    @SuppressWarnings("rawtypes")
    private void saveIndicatorValue(String name, String metricText, List<Metric> indicatorValues,
            DecoratorContext context) {
        Metric metricToSave = null;
        int i = 0;
        while (metricToSave == null) {
            if (indicatorValues.get(i).getName().equals(name)) {
                metricToSave = indicatorValues.get(i);
            } else {
                i++;
            }
        }
        context.saveMeasure(new Measure<String>(metricToSave, metricText));
    }
}

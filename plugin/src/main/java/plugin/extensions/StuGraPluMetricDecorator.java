package plugin.extensions;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import plugin.definitions.StuGraPluConfigurableValues;
import plugin.definitions.StuGraPluMeasures;
import plugin.definitions.StuGraPluMetrics;
import plugin.services.LogFileWriter;
import plugin.services.MetricCalculator;
import plugin.services.MetricDataCollector;

/**
 * Calculating metrics out of measured values from StuGraPluSensor and some CoreMetrics.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@DependsUpon(DecoratorBarriers.END_OF_TIME_MACHINE)
public class StuGraPluMetricDecorator implements Decorator {

    /** Console-logger. */
    private static final Logger LOGGER = Loggers.get(StuGraPluMetricDecorator.class);
    /** Context of the decorator. */
    private DecoratorContext context;

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
    @Override
    public void decorate(Resource resource, DecoratorContext context) {
        if (!Scopes.isProject(resource)) {
            return;
        }
        LOGGER.info("--- StuGraPluMetricDecorator started");
        this.context = context;
        LOGGER.info("Retrieve SonarQube-provided measures");
        Map<Metric<?>, Double> sonarQubeMeasures = getSonarQubeMeasures();
        LOGGER.info("Retrieve StuGraPlu-provided measures");
        Map<Metric<?>, Double> measures = getStuGraPluMeasures();
        measures.putAll(sonarQubeMeasures);
        LOGGER.info("Calculate metrics");
        MetricCalculator calculator = new MetricCalculator(measures);
        calculator.calculateMetrics();
        LOGGER.info("Save metrics");
        saveMetricResults(calculator);
        LOGGER.info("Log results");
        appendLogFile(sonarQubeMeasures, calculator.getCalculations());
        Map<Metric<?>, Double> allValues = new HashMap<Metric<?>, Double>();
        allValues.putAll(measures);
        allValues.putAll(calculator.getCalculations());
        allValues.put(CoreMetrics.NCLOC, MeasureUtils.getValue(context.getMeasure(CoreMetrics.NCLOC), 0.0));
        saveValuesForStatistics(allValues);
        LOGGER.info("--- StuGraPluMetricDecorator finished");
    }

    @SuppressWarnings("rawtypes")
    private Map<Metric<?>, Double> getStuGraPluMeasures() {
        Map<Metric<?>, Double> measures = new HashMap<Metric<?>, Double>();
        for (Metric measure : (new StuGraPluMeasures()).getMetrics()) {
            measures.put(measure, getValue(measure));
        }
        for (Metric configValue : (new StuGraPluConfigurableValues()).getMetrics()) {
            measures.put(configValue, getValue(configValue));
        }
        return measures;
    }

    private Map<Metric<?>, Double> getSonarQubeMeasures() {
        Map<Metric<?>, Double> sonarQubeMeasures = new HashMap<Metric<?>, Double>();
        sonarQubeMeasures.put(CoreMetrics.BLOCKER_VIOLATIONS, getValue(CoreMetrics.BLOCKER_VIOLATIONS));
        sonarQubeMeasures.put(CoreMetrics.CRITICAL_VIOLATIONS, getValue(CoreMetrics.CRITICAL_VIOLATIONS));
        sonarQubeMeasures.put(CoreMetrics.MAJOR_VIOLATIONS, getValue(CoreMetrics.MAJOR_VIOLATIONS));
        sonarQubeMeasures.put(CoreMetrics.MINOR_VIOLATIONS, getValue(CoreMetrics.MINOR_VIOLATIONS));
        sonarQubeMeasures.put(CoreMetrics.INFO_VIOLATIONS, getValue(CoreMetrics.INFO_VIOLATIONS));
        return sonarQubeMeasures;
    }

    private void saveMetricResults(MetricCalculator calculator) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        df.applyPattern("0.000");
        Map<Metric<?>, Double> normalizedResult = calculator.getNormalizedResult();
        for (Metric<?> metric : normalizedResult.keySet()) {
            context.saveMeasure(new Measure<String>(metric, df.format(normalizedResult.get(metric))));
        }
        context.saveMeasure(new Measure<Integer>(StuGraPluMetrics.OBP, calculator.getObpCalculation()));
    }

    @SuppressWarnings("deprecation")
    private void appendLogFile(Map<Metric<?>, Double> sonarQubeProvidedMeasures, Map<Metric<?>, Double> calculations) {
        Settings properties = context.getProject().getSettings();
        try {
            LogFileWriter.getLogger(properties.getString("currentlogfile"), properties.getInt("loglevel"));
            LogFileWriter.getLogger().printSonarMeasures(sonarQubeProvidedMeasures);
            LogFileWriter.getLogger().printMetrics(calculations);
            properties.removeProperty("currentlogfile");
            LogFileWriter.getLogger().close();
        } catch (Exception e) {
            LOGGER.warn("Initializing SoftAudit-Logger with log from sensor failed!");
        }
    }

    @SuppressWarnings("deprecation")
    private void saveValuesForStatistics(Map<Metric<?>, Double> values) {
        Settings properties = context.getProject().getSettings();
        if (properties.getBoolean("collectmetricdata")) {
            try {
                MetricDataCollector dataCollector = new MetricDataCollector(properties);
                dataCollector.saveValues(context.getProject().getName(), properties.getString("collectmetrictag"),
                        values);
                dataCollector.closeConnection();
            } catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
                LOGGER.warn("Data collection failed! Maybe parsing failed in Sensor");
            }
        }
    }

    private double getValue(Metric<?> metric) {
        return MeasureUtils.getValue(context.getMeasure(metric), 0.0);
    }
}

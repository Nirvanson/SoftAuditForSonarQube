package plugin.worker;

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

import plugin.definition.StuGraPluConfigurableValues;
import plugin.definition.StuGraPluMeasures;
import plugin.definition.StuGraPluMetrics;

/**
 * Calculating metrics out of measured values from StuGraPluSensor and some CoreMetrics.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@DependsUpon(DecoratorBarriers.ISSUES_TRACKED)
public class MetricCalculator implements Decorator {

    /** Console-logger. */
    private final Logger LOGGER = Loggers.get(MetricCalculator.class);
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
        // only execute on project level
        if (!Scopes.isProject(resource)) {
            return;
        }
        LOGGER.info("--- MetricCalculator started");
        this.context = context;

        // TODO: rework
        // safe deficiency-measures from CoreMetrics
        LOGGER.info("Retrieve SonarQube-provided measures");
        Map<Metric<?>, Double> sonarQubeProvidedMeasures = new HashMap<Metric<?>, Double>();
        sonarQubeProvidedMeasures.put(CoreMetrics.BLOCKER_VIOLATIONS, getValue(CoreMetrics.BLOCKER_VIOLATIONS));
        sonarQubeProvidedMeasures.put(CoreMetrics.CRITICAL_VIOLATIONS, getValue(CoreMetrics.CRITICAL_VIOLATIONS));
        sonarQubeProvidedMeasures.put(CoreMetrics.MAJOR_VIOLATIONS, getValue(CoreMetrics.MAJOR_VIOLATIONS));
        sonarQubeProvidedMeasures.put(CoreMetrics.MINOR_VIOLATIONS, getValue(CoreMetrics.MINOR_VIOLATIONS));
        // compute number of secure statements determined by security deficiencies
        sonarQubeProvidedMeasures.put(StuGraPluMetrics.SST,
                getValue(StuGraPluMeasures.STA) - getValue(CoreMetrics.BLOCKER_VIOLATIONS));
        for (Metric<?> metric : sonarQubeProvidedMeasures.keySet()) {
            context.saveMeasure(metric, sonarQubeProvidedMeasures.get(metric));
        }
        // calculate metrics
        LOGGER.info("Calculate metrics");
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        Map<Metric<?>, Double> resultForLogger = new HashMap<Metric<?>, Double>();
        // calculate all metrics which only depend on measures and save result for logger
        resultForLogger.put(StuGraPluMetrics.DCO,
                ((getValue(StuGraPluMeasures.PRE) * 2) + (getValue(StuGraPluMeasures.NPR) * 1.25)
                        + (getValue(StuGraPluMeasures.MPA) * 0.5))
                        / (getValue(StuGraPluMeasures.STA) + getValue(StuGraPluMeasures.VRE)));
        resultForLogger.put(StuGraPluMetrics.DFC,
                1 - ((getValue(StuGraPluMeasures.VAR) * 2) / getValue(StuGraPluMeasures.VRE)));
        resultForLogger.put(StuGraPluMetrics.ICO, getValue(StuGraPluMeasures.FMC) / getValue(StuGraPluMeasures.MEC));
        resultForLogger.put(StuGraPluMetrics.CFC, (getValue(StuGraPluMeasures.BRA) - (getValue(StuGraPluMeasures.IFS)
                + getValue(StuGraPluMeasures.SWS) + getValue(StuGraPluMeasures.LOS) + getValue(StuGraPluMeasures.RES)))
                / getValue(StuGraPluMeasures.BRA));
        resultForLogger.put(StuGraPluMetrics.COC,
                (getValue(StuGraPluMeasures.IFS) + getValue(StuGraPluMeasures.SWS)
                        + getValue(StuGraPluMeasures.SWC) + getValue(StuGraPluMeasures.LOS) + 1)
                        / (getValue(StuGraPluMeasures.PUM) * 4));
        resultForLogger.put(StuGraPluMetrics.BRC,
                ((getValue(StuGraPluMeasures.FMC) * 2) + (getValue(StuGraPluMeasures.RES) * 2)
                        + getValue(StuGraPluMeasures.MEC) - getValue(StuGraPluMeasures.FMC))
                        / (getValue(StuGraPluMeasures.STA) * 2));
        resultForLogger.put(StuGraPluMetrics.LCM, ((getValue(StuGraPluMeasures.STY) / getValue(StuGraPluMeasures.STA))
                + (getValue(StuGraPluMeasures.DTY)
                        / (getValue(StuGraPluMeasures.VAR) + getValue(StuGraPluMeasures.NUL))))
                / 2);
        resultForLogger.put(StuGraPluMetrics.MOD,
                ((((getValue(StuGraPluMeasures.CLA) * 4) + (getValue(StuGraPluMeasures.PUM) * 2))
                        / ((getValue(StuGraPluMeasures.IMP) * 4) + getValue(StuGraPluMeasures.GVA)))
                        + (1 - (getValue(StuGraPluMeasures.FMC)
                                / (getValue(StuGraPluMeasures.MEC) + getValue(StuGraPluMeasures.PUM))))
                        + ((getValue(StuGraPluMeasures.STA) / getValue(StuGraPluMeasures.SOF))
                                / getValue(StuGraPluConfigurableValues.OMS)))
                        / 3);
        resultForLogger.put(StuGraPluMetrics.TST,
                ((1 - ((getValue(StuGraPluMeasures.BRA) * 2) / getValue(StuGraPluMeasures.STA)))
                        + (1 - ((getValue(StuGraPluMeasures.PRE) * 2) / getValue(StuGraPluMeasures.VRE)))) / 2);
        resultForLogger.put(StuGraPluMetrics.REU, getValue(StuGraPluMeasures.RUM) / getValue(StuGraPluMeasures.PUM));
        resultForLogger.put(StuGraPluMetrics.SEC,
                (getValue(StuGraPluMetrics.SST) / 1.2) / getValue(StuGraPluMeasures.STA));
        resultForLogger.put(StuGraPluMetrics.FLE,
                1 - ((getValue(StuGraPluMeasures.STL) + getValue(StuGraPluMeasures.NUL))
                        / getValue(StuGraPluMeasures.STA)));
        resultForLogger.put(StuGraPluMetrics.COF,
                1 - (((getValue(CoreMetrics.BLOCKER_VIOLATIONS) * 2) + (getValue(CoreMetrics.CRITICAL_VIOLATIONS) * 1.5)
                        + getValue(CoreMetrics.MAJOR_VIOLATIONS) + (getValue(CoreMetrics.MINOR_VIOLATIONS) * 0.5))
                        / getValue(StuGraPluMeasures.STA)));
        // add all results normalized to SonarQubeResult
        for (Metric<?> metric : resultForLogger.keySet()) {
            result.put(metric, normalizeResult(resultForLogger.get(metric)));
        }
        // calculate all metrics which depend on other metrics (use normalized value) and add to
        // both results
        resultForLogger.put(StuGraPluMetrics.ACM,
                (result.get(StuGraPluMetrics.DCO) + result.get(StuGraPluMetrics.DFC)
                        + result.get(StuGraPluMetrics.ICO) + result.get(StuGraPluMetrics.CFC)
                        + result.get(StuGraPluMetrics.COC) + result.get(StuGraPluMetrics.BRC)
                        + result.get(StuGraPluMetrics.LCM)) / 7);
        result.put(StuGraPluMetrics.ACM, resultForLogger.get(StuGraPluMetrics.ACM));
        resultForLogger.put(StuGraPluMetrics.MAM,
                ((1 - result.get(StuGraPluMetrics.ACM))
                        + ((result.get(StuGraPluMetrics.MOD) + result.get(StuGraPluMetrics.SEC)
                                + result.get(StuGraPluMetrics.TST) + result.get(StuGraPluMetrics.REU)
                                + result.get(StuGraPluMetrics.FLE) + (result.get(StuGraPluMetrics.COF) / 2)) / 6))
                        / 2);
        result.put(StuGraPluMetrics.MAM, resultForLogger.get(StuGraPluMetrics.MAM));
        resultForLogger.put(StuGraPluMetrics.AQM,
                (result.get(StuGraPluMetrics.MOD) + result.get(StuGraPluMetrics.TST)
                        + result.get(StuGraPluMetrics.REU) + result.get(StuGraPluMetrics.SEC)
                        + result.get(StuGraPluMetrics.FLE) + result.get(StuGraPluMetrics.COF)
                        + result.get(StuGraPluMetrics.MAM)) / 7);
        result.put(StuGraPluMetrics.AQM, resultForLogger.get(StuGraPluMetrics.AQM));
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        df.applyPattern("0.000");
        for (Metric<?> metric : result.keySet()) {
            context.saveMeasure(new Measure<String>(metric, df.format(result.get(metric))));
        }
        // calculate Object points (no normalization) and add to both results
        resultForLogger.put(StuGraPluMetrics.OBP,
                (getValue(StuGraPluMeasures.CLA) * 4) + (getValue(StuGraPluMeasures.PUM) * 3)
                        + (getValue(StuGraPluMeasures.INT) * 2) + getValue(StuGraPluMeasures.GVA));
        context.saveMeasure(new Measure<Integer>(StuGraPluMetrics.OBP, resultForLogger.get(StuGraPluMetrics.OBP)));
        // do SoftAuditLogging - no better way for getting properties-access found
        @SuppressWarnings("deprecation")
        Settings properties = context.getProject().getSettings();
        try {
            LogFileWriter.getLogger(properties.getString("currentlogfile"), properties.getInt("loglevel"));
            LogFileWriter.getLogger().printSonarMeasures(sonarQubeProvidedMeasures);
            LogFileWriter.getLogger().printMetrics(resultForLogger);
            properties.removeProperty("currentlogfile");
            LogFileWriter.getLogger().close();
        } catch (Exception e) {
            LOGGER.warn("Initializing SoftAudit-Logger with log from sensor failed!");
        }
        LOGGER.info("--- SoftAuditDecorator finished");
    }

    /**
     * Normalize metric result. Cut off when under 0 or greater then 1.
     *
     * @param d - the metric result
     * @return normalized result
     */
    private double normalizeResult(double d) {
        if (d > 1) {
            return 1.0;
        }
        if (d < 0) {
            return 0.0;
        }
        return d;
    }

    /**
     * Retrieve measure from SoftAuditSensor.
     *
     * @param metric - the measure to retrieve
     * @return measured value
     */
    private double getValue(Metric<?> metric) {
        return MeasureUtils.getValue(context.getMeasure(metric), 0.0);
    }
}

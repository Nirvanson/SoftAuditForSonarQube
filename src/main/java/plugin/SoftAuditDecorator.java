package plugin;

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

import plugin.util.SoftAuditLogger;

/**
 * Calculating metrics out of measured values from SoftAuditSensor and some CoreMetrics.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@DependsUpon(DecoratorBarriers.ISSUES_TRACKED)
public class SoftAuditDecorator implements Decorator {
    
    /** Console-logger. */
    private final Logger LOGGER = Loggers.get(SoftAuditDecorator.class);
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
        LOGGER.info("--- SoftAuditDecorator started");
        this.context = context;
        // safe deficiency-measures from CoreMetrics
        LOGGER.info("Retrieve SonarQube-provided measures");
        Map<Metric<?>, Double> sonarQubeProvidedMeasures = new HashMap<Metric<?>, Double>();
        sonarQubeProvidedMeasures.put(SoftAuditMetrics.SED, getValue(CoreMetrics.BLOCKER_VIOLATIONS));
        sonarQubeProvidedMeasures.put(SoftAuditMetrics.MAD, getValue(CoreMetrics.CRITICAL_VIOLATIONS));
        sonarQubeProvidedMeasures.put(SoftAuditMetrics.MED, getValue(CoreMetrics.MAJOR_VIOLATIONS));
        sonarQubeProvidedMeasures.put(SoftAuditMetrics.MID, getValue(CoreMetrics.MINOR_VIOLATIONS));
        // compute number of secure statements determined by security deficiencies
        sonarQubeProvidedMeasures.put(SoftAuditMetrics.SST,
                getValue(SoftAuditMetrics.STM) - getValue(CoreMetrics.BLOCKER_VIOLATIONS));
        for (Metric<?> metric: sonarQubeProvidedMeasures.keySet()) {
            context.saveMeasure(metric, sonarQubeProvidedMeasures.get(metric));
        }
        // calculate metrics
        LOGGER.info("Calculate metrics");
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        Map<Metric<?>, Double> resultForLogger = new HashMap<Metric<?>, Double>();
        // calculate all metrics which only depend on measures and save result for logger
        resultForLogger.put(SoftAuditMetrics.DCO,
                ((getValue(SoftAuditMetrics.PRE) * 2) + (getValue(SoftAuditMetrics.ARG) * 1.25)
                        + (getValue(SoftAuditMetrics.PAR) * 0.5))
                        / (getValue(SoftAuditMetrics.STM) + getValue(SoftAuditMetrics.REF)));
        resultForLogger.put(SoftAuditMetrics.DFC,
                1 - ((getValue(SoftAuditMetrics.VAR) * 2) / getValue(SoftAuditMetrics.REF)));
        resultForLogger.put(SoftAuditMetrics.ICO, getValue(SoftAuditMetrics.FFC) / getValue(SoftAuditMetrics.FUC));
        resultForLogger.put(SoftAuditMetrics.CFC, (getValue(SoftAuditMetrics.BRA) - (getValue(SoftAuditMetrics.IFS)
                + getValue(SoftAuditMetrics.SWI) + getValue(SoftAuditMetrics.LOP) + getValue(SoftAuditMetrics.RET)))
                / getValue(SoftAuditMetrics.BRA));
        resultForLogger.put(SoftAuditMetrics.COC,
                (getValue(SoftAuditMetrics.IFS) + getValue(SoftAuditMetrics.SWI)
                        + getValue(SoftAuditMetrics.CAS) + getValue(SoftAuditMetrics.LOP) + 1)
                        / (getValue(SoftAuditMetrics.MET) * 4));
        resultForLogger.put(SoftAuditMetrics.BRC,
                ((getValue(SoftAuditMetrics.FFC) * 2) + (getValue(SoftAuditMetrics.RET) * 2)
                        + getValue(SoftAuditMetrics.FUC) - getValue(SoftAuditMetrics.FFC)) / (getValue(SoftAuditMetrics.STM) * 2));
        resultForLogger.put(SoftAuditMetrics.LCM, ((getValue(SoftAuditMetrics.STY) / getValue(SoftAuditMetrics.STM))
                + (getValue(SoftAuditMetrics.DTY) / (getValue(SoftAuditMetrics.VAR) + getValue(SoftAuditMetrics.CON))))
                / 2);
        resultForLogger.put(SoftAuditMetrics.MOD,
                ((((getValue(SoftAuditMetrics.CLA) * 4) + (getValue(SoftAuditMetrics.MET) * 2))
                        / ((getValue(SoftAuditMetrics.IMP) * 4) + getValue(SoftAuditMetrics.GVA)))
                        + (1 - (getValue(SoftAuditMetrics.FFC)
                                / (getValue(SoftAuditMetrics.FUC) + getValue(SoftAuditMetrics.MET))))
                        + ((getValue(SoftAuditMetrics.STM) / getValue(SoftAuditMetrics.SRC))
                                / getValue(SoftAuditMetrics.OMS)))
                        / 3);
        resultForLogger.put(SoftAuditMetrics.TST,
                ((1 - ((getValue(SoftAuditMetrics.BRA) * 2) / getValue(SoftAuditMetrics.STM)))
                        + (1 - ((getValue(SoftAuditMetrics.PRE) * 2) / getValue(SoftAuditMetrics.REF)))) / 2);
        resultForLogger.put(SoftAuditMetrics.REU, getValue(SoftAuditMetrics.RUM) / getValue(SoftAuditMetrics.MET));
        resultForLogger.put(SoftAuditMetrics.SEC,
                (getValue(SoftAuditMetrics.SST) / 1.2) / getValue(SoftAuditMetrics.STM));
        resultForLogger.put(SoftAuditMetrics.FLE,
                1 - ((getValue(SoftAuditMetrics.LIT) + getValue(SoftAuditMetrics.CON))
                        / getValue(SoftAuditMetrics.STM)));
        resultForLogger.put(SoftAuditMetrics.COF,
                1 - (((getValue(SoftAuditMetrics.SED) * 2) + (getValue(SoftAuditMetrics.MAD) * 1.5)
                        + getValue(SoftAuditMetrics.MED) + (getValue(SoftAuditMetrics.MID) * 0.5))
                        / getValue(SoftAuditMetrics.STM)));
        // add all results normalized to SonarQubeResult
        for (Metric<?> metric: resultForLogger.keySet()) {
            result.put(metric, normalizeResult(resultForLogger.get(metric)));
        }
        // calculate all metrics which depend on other metrics (use normalized value) and add to both results
        resultForLogger.put(SoftAuditMetrics.ACM,
                (result.get(SoftAuditMetrics.DCO) + result.get(SoftAuditMetrics.DFC)
                        + result.get(SoftAuditMetrics.ICO) + result.get(SoftAuditMetrics.CFC)
                        + result.get(SoftAuditMetrics.COC) + result.get(SoftAuditMetrics.BRC)
                        + result.get(SoftAuditMetrics.LCM)) / 7);
        result.put(SoftAuditMetrics.ACM, resultForLogger.get(SoftAuditMetrics.ACM));
        resultForLogger.put(SoftAuditMetrics.MAM,
                ((1 - result.get(SoftAuditMetrics.ACM))
                        + ((result.get(SoftAuditMetrics.MOD) + result.get(SoftAuditMetrics.SEC)
                                + result.get(SoftAuditMetrics.TST) + result.get(SoftAuditMetrics.REU)
                                + result.get(SoftAuditMetrics.FLE) + (result.get(SoftAuditMetrics.COF) / 2)) / 6))
                        / 2);
        result.put(SoftAuditMetrics.MAM,  resultForLogger.get(SoftAuditMetrics.MAM));
        resultForLogger.put(SoftAuditMetrics.AQM,
                (result.get(SoftAuditMetrics.MOD) + result.get(SoftAuditMetrics.TST)
                        + result.get(SoftAuditMetrics.REU) + result.get(SoftAuditMetrics.SEC)
                        + result.get(SoftAuditMetrics.FLE) + result.get(SoftAuditMetrics.COF)
                        + result.get(SoftAuditMetrics.MAM)) / 7);
        result.put(SoftAuditMetrics.AQM, resultForLogger.get(SoftAuditMetrics.AQM));
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        df.applyPattern("0.000");
        for (Metric<?> metric : result.keySet()) {
            context.saveMeasure(new Measure<String>(metric, df.format(result.get(metric))));
        }
        // calculate Object points (no normalization) and add to both results
        resultForLogger.put(SoftAuditMetrics.OBP,
                (getValue(SoftAuditMetrics.CLA) * 4) + (getValue(SoftAuditMetrics.MET) * 3)
                + (getValue(SoftAuditMetrics.INT) * 2) + getValue(SoftAuditMetrics.GVA));
        context.saveMeasure(new Measure<Integer>(SoftAuditMetrics.OBP, resultForLogger.get(SoftAuditMetrics.OBP)));
        // do SoftAuditLogging - no better way for getting properties-access found
        @SuppressWarnings("deprecation")
        Settings properties = context.getProject().getSettings();
        try {
            SoftAuditLogger.getLogger(properties.getString("currentlogfile"), properties.getInt("loglevel"));
            SoftAuditLogger.getLogger().printSonarMeasures(sonarQubeProvidedMeasures);
            SoftAuditLogger.getLogger().printMetrics(resultForLogger);
            properties.removeProperty("currentlogfile");
            SoftAuditLogger.getLogger().close();
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

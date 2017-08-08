package plugin.services;

import java.util.HashMap;
import java.util.Map;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import plugin.definitions.StuGraPluConfigurableValues;
import plugin.definitions.StuGraPluMeasures;
import plugin.definitions.StuGraPluMetrics;

public class MetricCalculator {

    private final Map<Metric<?>, Double> measures;
    private Map<Metric<?>, Double> calculations;
    private Map<Metric<?>, Double> normalizedResult;

    /**
     * Constructer with map of measures.
     * 
     * @param measures
     */
    public MetricCalculator(Map<Metric<?>, Double> measures) {
        this.measures = measures;
        this.calculations = new HashMap<Metric<?>, Double>();
        this.normalizedResult = new HashMap<Metric<?>, Double>();
    }

    /**
     * calculate all metrics in correct order with normalization.
     */
    public void calculateMetrics() {
        calculateMeasureDependingMetrics();
        for (Metric<?> metric : calculations.keySet()) {
            normalizedResult.put(metric, normalizeResult(calculations.get(metric)));
        }
        calculateMetricDependingMetrics();
    }

    /**
     * Retrieves unnormalized calculation results.
     * 
     * @return calculations map
     */
    public Map<Metric<?>, Double> getCalculations() {
        return this.calculations;
    }

    /**
     * Retrieves normalized calculation results.
     * 
     * @return result map
     */
    public Map<Metric<?>, Double> getNormalizedResult() {
        return this.normalizedResult;
    }

    /**
     * Retrieves obp-result.
     * 
     * @return obp-result from calculations map
     */
    public Double getObpCalculation() {
        return this.calculations.get(StuGraPluMetrics.OBP);
    }

    private void calculateMeasureDependingMetrics() {
        calculations.put(StuGraPluMetrics.DCO, calculateDataComplexity());
        calculations.put(StuGraPluMetrics.DFC, calculateDataFlowComplexity());
        calculations.put(StuGraPluMetrics.ICO, calculateInterfaceComplexity());
        calculations.put(StuGraPluMetrics.CFC, calculateControlFlowComplexity());
        calculations.put(StuGraPluMetrics.COC, calculateConditionalComplexity());
        calculations.put(StuGraPluMetrics.BRC, calculateBranchingComplexity());
        calculations.put(StuGraPluMetrics.LCO, calculateLanguageComplexity());
        calculations.put(StuGraPluMetrics.MOD, calculateModularity());
        calculations.put(StuGraPluMetrics.TST, calculateTestability());
        calculations.put(StuGraPluMetrics.REU, calculateReusability());
        calculations.put(StuGraPluMetrics.SEC, calculateSecurity());
        calculations.put(StuGraPluMetrics.FLE, calculateFlexibility());
        calculations.put(StuGraPluMetrics.COF, calculateConformity());
    }

    private void calculateMetricDependingMetrics() {
        Double acmCalculation = calculateAverageComplexity(normalizedResult);
        calculations.put(StuGraPluMetrics.ACO, acmCalculation);
        normalizedResult.put(StuGraPluMetrics.ACO, acmCalculation);
        Double mamCalculation = calculateMaintainability(normalizedResult);
        calculations.put(StuGraPluMetrics.MAI, mamCalculation);
        normalizedResult.put(StuGraPluMetrics.MAI, mamCalculation);
        Double aqmCalculation = calculateAverageQuality(normalizedResult);
        calculations.put(StuGraPluMetrics.AQU, aqmCalculation);
        normalizedResult.put(StuGraPluMetrics.AQU, aqmCalculation);
        Double obpCalculation = calculateObjectPoints(measures);
        calculations.put(StuGraPluMetrics.OBP, obpCalculation);
    }

    private double normalizeResult(double calculation) {
        if (calculation > 1) {
            return 1.0;
        }
        if (calculation < 0) {
            return 0.0;
        }
        return calculation;
    }

    private Double calculateConformity() {
        return 1 - (((measures.get(CoreMetrics.BLOCKER_VIOLATIONS) * 2)
                + (measures.get(CoreMetrics.CRITICAL_VIOLATIONS) * 1.5)
                + measures.get(CoreMetrics.MAJOR_VIOLATIONS)
                + (measures.get(CoreMetrics.MINOR_VIOLATIONS) * 0.5))
                / measures.get(StuGraPluMeasures.STA));
    }

    private Double calculateFlexibility() {
        return 1 - ((measures.get(StuGraPluMeasures.STL) + measures.get(StuGraPluMeasures.NUL))
                / measures.get(StuGraPluMeasures.STA));
    }

    private Double calculateSecurity() {
        return ((measures.get(StuGraPluMeasures.STA) - measures.get(StuGraPluMeasures.VUS)) / 1.2)
                / measures.get(StuGraPluMeasures.STA);
    }

    private Double calculateReusability() {
        return measures.get(StuGraPluMeasures.RUM) / measures.get(StuGraPluMeasures.PUM);
    }

    private Double calculateTestability() {
        return ((1 - ((measures.get(StuGraPluMeasures.BRA) * 2) / measures.get(StuGraPluMeasures.STA)))
                + (1 - ((measures.get(StuGraPluMeasures.PRE) * 2) / measures.get(StuGraPluMeasures.VRE)))) / 2;
    }

    private Double calculateModularity() {
        return ((((measures.get(StuGraPluMeasures.CLA) * 4) + (measures.get(StuGraPluMeasures.PUM) * 2))
                / ((measures.get(StuGraPluMeasures.IMP) * 4) + measures.get(StuGraPluMeasures.GVA)))
                + (1 - (measures.get(StuGraPluMeasures.FMC)
                        / (measures.get(StuGraPluMeasures.MEC) + measures.get(StuGraPluMeasures.PUM))))
                + ((measures.get(StuGraPluMeasures.STA) / measures.get(StuGraPluMeasures.SOF))
                        / measures.get(StuGraPluConfigurableValues.OMS)))
                / 3;
    }

    private Double calculateLanguageComplexity() {
        return ((measures.get(StuGraPluMeasures.STY) / measures.get(StuGraPluMeasures.STA))
                + (measures.get(StuGraPluMeasures.DTY)
                        / (measures.get(StuGraPluMeasures.VAR) + measures.get(StuGraPluMeasures.NUL))))
                / 2;
    }

    private Double calculateBranchingComplexity() {
        return ((measures.get(StuGraPluMeasures.FMC) * 2) + (measures.get(StuGraPluMeasures.RES) * 2)
                + measures.get(StuGraPluMeasures.MEC) - measures.get(StuGraPluMeasures.FMC))
                / (measures.get(StuGraPluMeasures.STA) * 2);
    }

    private Double calculateConditionalComplexity() {
        return (measures.get(StuGraPluMeasures.IFS) + measures.get(StuGraPluMeasures.SWS)
                + measures.get(StuGraPluMeasures.SWC) + measures.get(StuGraPluMeasures.LOS) + 1)
                / (measures.get(StuGraPluMeasures.PUM) * 4);
    }

    private Double calculateControlFlowComplexity() {
        return (measures.get(StuGraPluMeasures.BRA) - (measures.get(StuGraPluMeasures.IFS)
                + measures.get(StuGraPluMeasures.SWS) + measures.get(StuGraPluMeasures.LOS)
                + measures.get(StuGraPluMeasures.RES)))
                / measures.get(StuGraPluMeasures.BRA);
    }

    private Double calculateInterfaceComplexity() {
        return measures.get(StuGraPluMeasures.FMC) / measures.get(StuGraPluMeasures.MEC);
    }

    private Double calculateDataFlowComplexity() {
        return 1 - ((measures.get(StuGraPluMeasures.VAR) * 2) / measures.get(StuGraPluMeasures.VRE));
    }

    private Double calculateDataComplexity() {
        return ((measures.get(StuGraPluMeasures.PRE) * 2) + (measures.get(StuGraPluMeasures.NPR) * 1.25)
                + (measures.get(StuGraPluMeasures.MPA) * 0.5))
                / (measures.get(StuGraPluMeasures.STA) + measures.get(StuGraPluMeasures.VRE));
    }

    private Double calculateAverageComplexity(Map<Metric<?>, Double> previousMetrics) {
        return (previousMetrics.get(StuGraPluMetrics.DCO) + previousMetrics.get(StuGraPluMetrics.DFC)
                + previousMetrics.get(StuGraPluMetrics.ICO) + previousMetrics.get(StuGraPluMetrics.CFC)
                + previousMetrics.get(StuGraPluMetrics.COC) + previousMetrics.get(StuGraPluMetrics.BRC)
                + previousMetrics.get(StuGraPluMetrics.LCO)) / 7;
    }

    private Double calculateMaintainability(Map<Metric<?>, Double> previousMetrics) {
        return ((1 - previousMetrics.get(StuGraPluMetrics.ACO))
                + ((previousMetrics.get(StuGraPluMetrics.MOD) + previousMetrics.get(StuGraPluMetrics.SEC)
                        + previousMetrics.get(StuGraPluMetrics.TST) + previousMetrics.get(StuGraPluMetrics.REU)
                        + previousMetrics.get(StuGraPluMetrics.FLE) + (previousMetrics.get(StuGraPluMetrics.COF) / 2))
                        / 6))
                / 2;
    }

    private Double calculateAverageQuality(Map<Metric<?>, Double> previousMetrics) {
        return (previousMetrics.get(StuGraPluMetrics.MOD) + previousMetrics.get(StuGraPluMetrics.TST)
                + previousMetrics.get(StuGraPluMetrics.REU) + previousMetrics.get(StuGraPluMetrics.SEC)
                + previousMetrics.get(StuGraPluMetrics.FLE) + previousMetrics.get(StuGraPluMetrics.COF)
                + previousMetrics.get(StuGraPluMetrics.MAI)) / 7;
    }

    private Double calculateObjectPoints(Map<Metric<?>, Double> measures) {
        return (measures.get(StuGraPluMeasures.CLA) * 4) + (measures.get(StuGraPluMeasures.PUM) * 3)
                + (measures.get(StuGraPluMeasures.INT) * 2) + measures.get(StuGraPluMeasures.GVA);
    }
}

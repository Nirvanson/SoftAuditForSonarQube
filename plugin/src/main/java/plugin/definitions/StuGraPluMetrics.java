package plugin.definitions;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * PlugIn Metric definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class StuGraPluMetrics implements Metrics {
    /**
     * Number of Secure Statements = STM - SED.
     */
    public static final Metric<Integer> SST = new Metric.Builder("sst", "Secure Statements", Metric.ValueType.INT)
            .setDescription("Number of secure statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Object Points = (CLA * 4) + (MET * 3) + (INT * 2) + GVA
     */
    public static final Metric<Integer> OBP = new Metric.Builder("obp", "Object Points", Metric.ValueType.INT)
            .setDescription("Computed object points.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Data Complexity (Based on Chapin) = ((PRE * 2) + (ARG * 1,25) + (PAR * 0,5)) / (STM + REF)
     */
    public static final Metric<String> DCO = new Metric.Builder("dco", "Data Complexity",
            Metric.ValueType.STRING).setDescription("Computed data complexity (Chapin).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Data Flow Complexity (Based on Elshof) = 1 – ((VAR * 2) / REF)
     */
    public static final Metric<String> DFC = new Metric.Builder("dfc", "Data Flow Complexity",
            Metric.ValueType.STRING).setDescription("Computed data flow complexity (Elshof).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Control Flow Complexity (Based on McCabe) = (BRA – (IFS + SWI + LOP + RET)) / BRA
     */
    public static final Metric<String> CFC = new Metric.Builder("cfc", "Control Flow Complexity",
            Metric.ValueType.STRING).setDescription("Computed control flow complexity (McCabe).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Conditional Complexity (Based on McClure) = (IFS + SWI + CAS + LOP + 1) / (MET * 4)
     */
    public static final Metric<String> COC = new Metric.Builder("coc", "Conditional Complexity",
            Metric.ValueType.STRING).setDescription("Computed conditional complexity (McClure).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Testability = ((1 – ((BRA * 2) / STM)) + (1 – ((PRE * 2) / REF))) / 2
     */
    public static final Metric<String> TST = new Metric.Builder("tst", "Testability", Metric.ValueType.STRING)
            .setDescription("Computed testability.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Flexibility = 1 – ((LIT + CON) / STM)
     */
    public static final Metric<String> FLE = new Metric.Builder("fle", "Flexibility", Metric.ValueType.STRING)
            .setDescription("Computed flexibility.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Conformity = 1 – (((SED * 2) + (MAD * 1,5) + MED + (MID * 0,5)) / STM)
     */
    public static final Metric<String> COF = new Metric.Builder("cof", "Conformity", Metric.ValueType.STRING)
            .setDescription("Computed conformity.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Interface Complexity (Based on Henry) = FFC / FUC
     */
    public static final Metric<String> ICO = new Metric.Builder("ico", "Interface Complexity",
            Metric.ValueType.STRING).setDescription("Computed interface complexity (Henry).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Branching Complexity (Based on Sneed) = ((FFC * 2) + (RET * 2) + FUC - FFC) / (STM * 2)
     */
    public static final Metric<String> BRC = new Metric.Builder("brc", "Branching Complexity",
            Metric.ValueType.STRING).setDescription("Computed branching complexity (Sneed).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Language Complexity (Based on Halstead) = ((STY / STM) + (DTY / (VAR + CON))) / * 2
     */
    public static final Metric<String> LCM = new Metric.Builder("lcm", "Language Complexity",
            Metric.ValueType.STRING).setDescription("Computed language complexity (Halstead).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Average Complexity Medium Level = (DCO + DFC + CFC + COC + ICO + BRC + LCM) / 7
     */
    public static final Metric<String> ACM = new Metric.Builder("acm", "Average Complexity",
            Metric.ValueType.STRING).setDescription("Computed average complexity.").setDirection(Metric.DIRECTION_WORST)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Modularity = ((((CLA * 4) + (MET * 2)) / ((IMP * 4) + GVA))
     * + (1 – (FFC / (FUC + MET))) + ((STM / SRC) / OMS)) / 3
     */
    public static final Metric<String> MOD = new Metric.Builder("mod", "Modularity", Metric.ValueType.STRING)
            .setDescription("Computed modularity.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Reusability = RUM / MET
     */
    public static final Metric<String> REU = new Metric.Builder("reu", "Reuseability", Metric.ValueType.STRING)
            .setDescription("Computed reusability.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Security = (SST / 1,2) / STM
     */
    public static final Metric<String> SEC = new Metric.Builder("sec", "Security", Metric.ValueType.STRING)
            .setDescription("Computed security.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Maintainability = ((1 – ACM) + ((MOD + TST + REU + SEC + FLE + (COF / 2)) / 6)) / 2
     */
    public static final Metric<String> MAM = new Metric.Builder("mam", "Maintainability",
            Metric.ValueType.STRING).setDescription("Computed maintainability.").setDirection(Metric.DIRECTION_BETTER)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Average Quality = (MOD + TST + REU + SEC + FLE + COF + MAB) / 7
     */
    public static final Metric<String> AQM = new Metric.Builder("aqm", "Average Quality",
            Metric.ValueType.STRING).setDescription("Computed average quality.").setDirection(Metric.DIRECTION_BETTER)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    /**
     * Default constructor.
     */
    public StuGraPluMetrics() {
        super();
    }

    /**
     * Returns the metrics of the PlugIn.
     *
     * @return list of metrics
     */
    @SuppressWarnings("rawtypes")
    public List<Metric> getMetrics() {
        return Arrays.asList(SST, OBP, DCO, DFC, CFC, COC, TST, FLE, COF, ICO, BRC, LCM, ACM, MOD, REU, SEC, MAM, AQM);
    }
}

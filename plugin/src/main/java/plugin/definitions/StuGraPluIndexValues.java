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
public class StuGraPluIndexValues implements Metrics {
    /**
     * Duplicated Code.
     */
    public static final Metric<String> DUC = new Metric.Builder("duc", "Duplicated Code",
            Metric.ValueType.STRING).setDescription("Duplicated Code Indicator - Blocker")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Magic Numbers.
     */
    public static final Metric<String> MAN = new Metric.Builder("man", "Magic Numbers",
            Metric.ValueType.STRING).setDescription("Magic Numbers Indicator - Blocker")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Data Encapsulation.
     */
    public static final Metric<String> DAE = new Metric.Builder("dae", "Data Encapsulation",
            Metric.ValueType.STRING).setDescription("Data Encapsulation - Blocker")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Overbooked File.
     */
    public static final Metric<String> OVF = new Metric.Builder("ovf", "Overbooked File",
            Metric.ValueType.STRING).setDescription("Overbooked File - Critical")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Insufficient Tests.
     */
    public static final Metric<String> ITE = new Metric.Builder("ite", "Insufficient Tests", Metric.ValueType.STRING)
            .setDescription("Insufficient Tests - Critical").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Informal Documentation.
     */
    public static final Metric<String> IDO = new Metric.Builder("ido", "Informal Documentation",
            Metric.ValueType.STRING)
                    .setDescription("Informal Documentation - Critical").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false)
                    .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * To High Coupling.
     */
    public static final Metric<String> THC = new Metric.Builder("thc", "To High Coupling", Metric.ValueType.STRING)
            .setDescription("To High Coupling - Critical").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * God Construct.
     */
    public static final Metric<String> GOC = new Metric.Builder("goc", "God Construct",
            Metric.ValueType.STRING).setDescription("God Construct - Critical")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Wrong Naming.
     */
    public static final Metric<String> WRN = new Metric.Builder("wrn", "Wrong Naming",
            Metric.ValueType.STRING).setDescription("Wrong Naming - Critical")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * To Complex.
     */
    public static final Metric<String> TCO = new Metric.Builder("tco", "To Complex",
            Metric.ValueType.STRING).setDescription("To Complex - Major")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Confusion Danger.
     */
    public static final Metric<String> COD = new Metric.Builder("cod", "Confusion Danger",
            Metric.ValueType.STRING).setDescription("Confusion Danger - Major").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Wrong Formatting.
     */
    public static final Metric<String> WRF = new Metric.Builder("wrf", "Wrong Formatting", Metric.ValueType.STRING)
            .setDescription("Wrong Formatting - Major").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Object Placebo.
     */
    public static final Metric<String> OPL = new Metric.Builder("opl", "Object Placebo", Metric.ValueType.STRING)
            .setDescription("Object Placebo - Minor").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Wrong Comparison.
     */
    public static final Metric<String> WRC = new Metric.Builder("wrc", "Wrong Comparison", Metric.ValueType.STRING)
            .setDescription("Wrong Comparison - Minor").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Risk Code.
     */
    public static final Metric<String> RIC = new Metric.Builder("ric", "Risk Code",
            Metric.ValueType.STRING).setDescription("Risk Code - Minor").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Wrong Imports.
     */
    public static final Metric<String> WRI = new Metric.Builder("wri", "Wrong Imports",
            Metric.ValueType.STRING).setDescription("Wrong Imports - Minor").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Wrong Declaration.
     */
    public static final Metric<String> WRD = new Metric.Builder("wrd", "Wrong Declaration",
            Metric.ValueType.STRING).setDescription("Wrong Declaration - Minor").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Dead Code.
     */
    public static final Metric<String> DEC = new Metric.Builder("dec", "Dead Code",
            Metric.ValueType.STRING).setDescription("Dead Code - Minor").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Unfinished Code.
     */
    public static final Metric<String> UNC = new Metric.Builder("unc", "Unfinished Code",
            Metric.ValueType.STRING).setDescription("Unfinished Code - Info").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Code Quality Index.
     */
    public static final Metric<Integer> CQI = new Metric.Builder("cqi", "Code Quality Index",
            Metric.ValueType.STRING).setDescription("Code Quality Index Benchmark Level")
                    .setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    /**
     * Default constructor.
     */
    public StuGraPluIndexValues() {
        super();
    }

    /**
     * Returns the metrics of the PlugIn.
     *
     * @return list of metrics
     */
    @SuppressWarnings("rawtypes")
    public List<Metric> getMetrics() {
        return Arrays.asList(DUC, MAN, DAE, OVF, ITE, IDO, THC, GOC, WRN, TCO, COD, WRF, OPL, WRC, RIC, WRI, WRD, DEC,
                UNC, CQI);
    }
}

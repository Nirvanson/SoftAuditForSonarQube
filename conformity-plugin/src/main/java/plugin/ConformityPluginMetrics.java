package plugin;

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
public class ConformityPluginMetrics implements Metrics {
    
    /**
     * Object Points = (Classes * 4) + (Methods * 3) + (Interfaces * 2) + Global-Variables
     */
    public static final Metric<Integer> OBP = new Metric.Builder("obp", "Object Points", Metric.ValueType.INT)
            .setDescription("Computed object points.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    
    /**
     * Conformity = 1 – (((Blocker-Violations * 2) + (Critical-Violations * 1,5) + Major-Violations + (Minor-Violations * 0,5)) / Statements)
     */
    public static final Metric<String> COF = new Metric.Builder("cof", "Conformity", Metric.ValueType.STRING)
            .setDescription("Computed conformity.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    

    /**
     * Default constructor.
     */
    public ConformityPluginMetrics() {
        super();
    }

    /**
     * Returns the metrics of the PlugIn.
     *
     * @return list of metrics
     */
    @SuppressWarnings("rawtypes")
    public List<Metric> getMetrics() {
        return Arrays.asList(OBP, COF);
    }
}

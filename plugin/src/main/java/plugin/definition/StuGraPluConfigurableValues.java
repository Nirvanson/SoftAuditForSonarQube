package plugin.definition;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * PlugIn Configurable-Value definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class StuGraPluConfigurableValues implements Metrics {
    /**
     * Optimal Module Size, default 200.
     */
    public static final Metric<Integer> OMS = new Metric.Builder("oms", "Optimal-Module-Size",
            Metric.ValueType.INT).setDescription("Optimal module size.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    /**
     * Default constructor.
     */
    public StuGraPluConfigurableValues() {
        super();
    }

    /**
     * Returns the configurable values of the PlugIn.
     *
     * @return list of configurable values
     */
    @SuppressWarnings("rawtypes")
    public List<Metric> getMetrics() {
        return Arrays.asList(OMS);
    }
}

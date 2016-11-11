package jnru.measures;

import static java.util.Arrays.asList;

import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public class SoftAuditMetrics implements Metrics {

  public static final Metric<Integer> PREDICATE_COUNT = new Metric.Builder("predicate_count", "Predicate Count", Metric.ValueType.INT)
    .setDescription("Number of predicates.")
    .setDirection(Metric.DIRECTION_NONE)
    .setQualitative(false)
    .setDomain(CoreMetrics.DOMAIN_GENERAL)
    .create();

  @Override
  public List<Metric> getMetrics() {
    return asList(PREDICATE_COUNT);
  }
}

package plugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
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

@DependsUpon(DecoratorBarriers.ISSUES_TRACKED)
public class SoftAuditDecorator implements Decorator {

	private DecoratorContext context;
	DecimalFormat df = new DecimalFormat("0.000");

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	@Override
	public void decorate(Resource resource, DecoratorContext context) {
		Logger LOGGER = Loggers.get(SoftAuditDecorator.class);
		// only execute on project level
		if (!Scopes.isProject(resource)) {
			return;
		}
		this.context = context;
		// safe deficiencie-measures from coremetrics
		LOGGER.info("Retrieve SonarQube-provided measures");
		context.saveMeasure(SoftAuditMetrics.SED, getValue(CoreMetrics.BLOCKER_VIOLATIONS));
		context.saveMeasure(SoftAuditMetrics.MAD, getValue(CoreMetrics.CRITICAL_VIOLATIONS));
		context.saveMeasure(SoftAuditMetrics.MED, getValue(CoreMetrics.MAJOR_VIOLATIONS));
		context.saveMeasure(SoftAuditMetrics.MID, getValue(CoreMetrics.MINOR_VIOLATIONS));
		// compute number of secure statements determined by security deficiencies
		context.saveMeasure(SoftAuditMetrics.SST,
				getValue(SoftAuditMetrics.STM) - getValue(CoreMetrics.BLOCKER_VIOLATIONS));
		// calculate metrics ( *100 because of lacking precision in sonarqube...)
		Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
		LOGGER.info("Calculate metrics");
		context.saveMeasure(new Measure<Integer>(SoftAuditMetrics.OBP,
				(getValue(SoftAuditMetrics.CLA) * 4) + (getValue(SoftAuditMetrics.MET) * 3)
						+ (getValue(SoftAuditMetrics.INT) * 2) + getValue(SoftAuditMetrics.VAR)));
		result.put(SoftAuditMetrics.DCO,
		        checkMetric(((getValue(SoftAuditMetrics.PRE) * 2) + getValue(SoftAuditMetrics.ARG)
						+ (getValue(SoftAuditMetrics.PAR) * 0.5))
						/ (getValue(SoftAuditMetrics.STM) + getValue(SoftAuditMetrics.REF))));
		result.put(SoftAuditMetrics.DFC,
		        checkMetric(1 - ((getValue(SoftAuditMetrics.VAR) * 2) / getValue(SoftAuditMetrics.REF))));
		result.put(SoftAuditMetrics.ICO, checkMetric(getValue(SoftAuditMetrics.FFC) / getValue(SoftAuditMetrics.FUC)));
		result.put(SoftAuditMetrics.CFC, checkMetric((getValue(SoftAuditMetrics.BRA) - (getValue(SoftAuditMetrics.IFS)
				+ getValue(SoftAuditMetrics.SWI) + getValue(SoftAuditMetrics.LOP) + getValue(SoftAuditMetrics.RET)))
				/ getValue(SoftAuditMetrics.BRA)));
		result.put(SoftAuditMetrics.COC,
		        checkMetric((getValue(SoftAuditMetrics.IFS) + getValue(SoftAuditMetrics.SWI)
						+ getValue(SoftAuditMetrics.CAS) + getValue(SoftAuditMetrics.LOP) + 1)
						/ (getValue(SoftAuditMetrics.MET) * 4)));
		result.put(SoftAuditMetrics.BRC,
		        checkMetric(((getValue(SoftAuditMetrics.FFC) * 2) + (getValue(SoftAuditMetrics.RET) * 2)
						+ getValue(SoftAuditMetrics.FUC)) / getValue(SoftAuditMetrics.STM)));
		result.put(SoftAuditMetrics.LCM, checkMetric(((getValue(SoftAuditMetrics.STY) / getValue(SoftAuditMetrics.STM))
				+ (getValue(SoftAuditMetrics.DTY) / (getValue(SoftAuditMetrics.VAR) + getValue(SoftAuditMetrics.CON))))
				/ 2));
		result.put(SoftAuditMetrics.ACM,
				checkMetric((result.get(SoftAuditMetrics.DCO) + result.get(SoftAuditMetrics.DFC)
						+ result.get(SoftAuditMetrics.ICO) + result.get(SoftAuditMetrics.CFC)
						+ result.get(SoftAuditMetrics.COC) + result.get(SoftAuditMetrics.BRC)
						+ result.get(SoftAuditMetrics.LCM)) / 7));
		result.put(SoftAuditMetrics.MOD,
		        checkMetric(((((getValue(SoftAuditMetrics.CLA) * 4) + (getValue(SoftAuditMetrics.MET) * 2))
						/ ((getValue(SoftAuditMetrics.IMP) * 4) + getValue(SoftAuditMetrics.VAR)))
						+ (1 - (getValue(SoftAuditMetrics.FFC)
								/ (getValue(SoftAuditMetrics.FUC) + getValue(SoftAuditMetrics.MET))))
						+ ((getValue(SoftAuditMetrics.STM) / getValue(SoftAuditMetrics.SRC))
								/ getValue(SoftAuditMetrics.OMS)))
						/ 3));
		result.put(SoftAuditMetrics.TST,
		        checkMetric(((1 - ((getValue(SoftAuditMetrics.BRA) * 2) / getValue(SoftAuditMetrics.STM)))
						+ (1 - ((getValue(SoftAuditMetrics.PRE) * 2) / getValue(SoftAuditMetrics.REF)))) / 2));
		result.put(SoftAuditMetrics.REU, checkMetric(getValue(SoftAuditMetrics.RUM) / getValue(SoftAuditMetrics.MET)));
		result.put(SoftAuditMetrics.SEC,
		        checkMetric((getValue(SoftAuditMetrics.SST) / 1.2) / getValue(SoftAuditMetrics.STM)));
		result.put(SoftAuditMetrics.FLE,
		        checkMetric(1 - ((getValue(SoftAuditMetrics.LIT) + getValue(SoftAuditMetrics.CON))
						/ getValue(SoftAuditMetrics.STM))));
		result.put(SoftAuditMetrics.COF,
		        checkMetric(1 - (((getValue(SoftAuditMetrics.SED) * 2) + (getValue(SoftAuditMetrics.MAD) * 1.5)
						+ getValue(SoftAuditMetrics.MED) + (getValue(SoftAuditMetrics.MID) * 0.5))
						/ getValue(SoftAuditMetrics.STM))));
		result.put(SoftAuditMetrics.MAM,
				checkMetric(((1 - result.get(SoftAuditMetrics.ACM))
						+ ((result.get(SoftAuditMetrics.MOD) + result.get(SoftAuditMetrics.SEC)
								+ result.get(SoftAuditMetrics.TST) + result.get(SoftAuditMetrics.REU)
								+ result.get(SoftAuditMetrics.FLE) + (result.get(SoftAuditMetrics.COF) / 2)) / 6))
						/ 2));
		result.put(SoftAuditMetrics.AQM,
				checkMetric((result.get(SoftAuditMetrics.MOD) + result.get(SoftAuditMetrics.TST)
						+ result.get(SoftAuditMetrics.REU) + result.get(SoftAuditMetrics.SEC)
						+ result.get(SoftAuditMetrics.FLE) + result.get(SoftAuditMetrics.COF)
						+ result.get(SoftAuditMetrics.MAM)) / 7));
		SoftAuditLogger.getLogger().printMetrics(result);
		// save metrics
		for (Metric<?> metric : result.keySet()) {
			context.saveMeasure(new Measure<String>(metric, df.format(result.get(metric))));
		}
	}

	private double checkMetric(double d) {
		if (d > 1) {
			return 1.0;
		}
		if (d<0) {
			return 0.0;
		}
		return d;
	}

	private double getValue(Metric<?> metric) {
		return MeasureUtils.getValue(context.getMeasure(metric), 0.0);
	}
}

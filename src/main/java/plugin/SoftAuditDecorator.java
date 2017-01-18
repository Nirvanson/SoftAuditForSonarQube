package plugin;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@DependsUpon(DecoratorBarriers.ISSUES_TRACKED)
public class SoftAuditDecorator implements Decorator {

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	@Override
	public void decorate(Resource resource, DecoratorContext context) {
		Logger LOGGER = Loggers.get(SoftAuditDecorator.class);
		LOGGER.info("--- resourcescope: " + resource.getScope());
		Measure<?> defis1 = context.getMeasure(CoreMetrics.BLOCKER_VIOLATIONS);
		Measure<?> defis2 = context.getMeasure(CoreMetrics.CRITICAL_VIOLATIONS);
		Measure<?> defis3 = context.getMeasure(CoreMetrics.INFO_VIOLATIONS);
		Measure<?> defis4 = context.getMeasure(CoreMetrics.MAJOR_VIOLATIONS);
		Measure<?> defis5 = context.getMeasure(CoreMetrics.MINOR_VIOLATIONS);
		Measure<?> defis6 = context.getMeasure(CoreMetrics.VIOLATIONS);
		Measure<?> statements = context.getMeasure(SoftAuditMetrics.STM);
		Measure<?> linesofcode = context.getMeasure(CoreMetrics.NCLOC);
		LOGGER.info("blocker: " + String.valueOf(MeasureUtils.getValue(defis1, 0.0)));
		LOGGER.info("critical: " + String.valueOf(MeasureUtils.getValue(defis2, 0.0)));
		LOGGER.info("info: " + String.valueOf(MeasureUtils.getValue(defis3, 0.0)));
		LOGGER.info("major: " + String.valueOf(MeasureUtils.getValue(defis4, 0.0)));
		LOGGER.info("minor: " + String.valueOf(MeasureUtils.getValue(defis5, 0.0)));
		LOGGER.info("all?: " + String.valueOf(MeasureUtils.getValue(defis6, 0.0)));
		LOGGER.info("linesofcode: " + String.valueOf(MeasureUtils.getValue(linesofcode, 0.0)));
		LOGGER.info("statements: " + String.valueOf(MeasureUtils.getValue(statements, 0.0)));
	}

}

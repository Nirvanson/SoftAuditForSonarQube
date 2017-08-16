package plugin;

import java.text.DecimalFormat;
import java.util.Locale;

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
import org.sonar.api.utils.log.Loggers;

/**
 * Calculating metrics out of measured values from StuGraPluSensor and some CoreMetrics.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@DependsUpon(DecoratorBarriers.END_OF_TIME_MACHINE)
public class ConformityDecorator implements Decorator {

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
        if (!Scopes.isProject(resource)) {
            return;
        }
        this.context = context;
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        df.applyPattern("0.000");
        context.saveMeasure(new Measure<String>(ConformityPluginMetrics.COF, df.format(calculateConformity())));
        context.saveMeasure(new Measure<Integer>(ConformityPluginMetrics.OBP, calculateObjectPoints()));
        Loggers.get(ConformityDecorator.class).info("Calculating Conformity and Object-Ponts finished");
    }
    
    private Double calculateConformity() {
        return 1 - (((getValue(CoreMetrics.BLOCKER_VIOLATIONS) * 2)
                + (getValue(CoreMetrics.CRITICAL_VIOLATIONS) * 1.5)
                + getValue(CoreMetrics.MAJOR_VIOLATIONS)
                + (getValue(CoreMetrics.MINOR_VIOLATIONS) * 0.5))
                / getValue(CoreMetrics.STATEMENTS));
    }
    
    @SuppressWarnings("deprecation")
    private Double calculateObjectPoints() {
        return (getValue(CoreMetrics.CLASSES) * 4) + (getValue(CoreMetrics.FUNCTIONS) * 3) + (getValue(CoreMetrics.ACCESSORS) * 2);
    }
    
    private double getValue(Metric<?> metric) {
        return MeasureUtils.getValue(context.getMeasure(metric), 0.0);
    }
}

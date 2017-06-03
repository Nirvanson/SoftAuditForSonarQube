package plugin.definition;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

import plugin.worker.StuGraPluDecorator;
import plugin.worker.StuGraPluSensor;

/**
 * Plugin definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class StudentGradingPlugin extends SonarPlugin {

    /**
     * Default constructor.
     */
    public StudentGradingPlugin() {
        super();
    }

    /**
     * Defines the PlugIn extensions: metrics, sensor, decorator and widget.
     *
     * @return the list of extensions for this PlugIn
     */
    public List<Class<? extends Extension>> getExtensions() {
        return Arrays.asList(
                StuGraPluMeasures.class,
                StuGraPluConfigurableValues.class,
                StuGraPluMetrics.class,
                StuGraPluSensor.class,
                StuGraPluWidget.class,
                StuGraPluDecorator.class);
    }
}

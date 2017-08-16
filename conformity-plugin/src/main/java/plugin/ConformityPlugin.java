package plugin;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

/**
 * Plugin definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class ConformityPlugin extends SonarPlugin {

    /**
     * Default constructor.
     */
    public ConformityPlugin() {
        super();
    }

    /**
     * Defines the PlugIn extensions: metrics, sensor, decorator and widget.
     *
     * @return the list of extensions for this PlugIn
     */
    public List<Class<? extends Extension>> getExtensions() {
        return Arrays.asList(
                ConformityPluginMetrics.class,
                ConformityDecorator.class,
                ConformityWidget.class);
    }
}

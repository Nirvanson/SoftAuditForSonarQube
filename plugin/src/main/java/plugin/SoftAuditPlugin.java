package plugin;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

/**
 * Soft-Audit-Plugin definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class SoftAuditPlugin extends SonarPlugin {

    /**
     * Default constructor.
     */
    public SoftAuditPlugin() {
        super();
    }

    /**
     * Defines the plugin extensions: metrics, sensor, decorator and widget.
     *
     * @return the list of extensions for this plugin
     */
    public List<Class<? extends Extension>> getExtensions() {
        return Arrays.asList(
                SoftAuditMetrics.class,
                SoftAuditSensor.class,
                SoftAuditWidget.class,
                SoftAuditDecorator.class);
    }
}

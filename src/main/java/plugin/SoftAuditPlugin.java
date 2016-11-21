package plugin;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

/**
 * SoftAudit plugin definition
 *
 * @author Jan Rucks
 * @version 0.1
 */
public class SoftAuditPlugin extends SonarPlugin {

    /**
     * Default constructor.
     */
    public SoftAuditPlugin() {
        super();
    }

    /**
     * Defines the plugin extensions: metrics, sensor and dashboard widget.
     *
     * @return the list of extensions for this plugin
     */
    public List<Class<? extends Extension>> getExtensions() {
        return Arrays.asList(
        		SoftAuditMetrics.class,
        		SoftAuditSensor.class,
        		SoftAuditWidget.class);
    }
}

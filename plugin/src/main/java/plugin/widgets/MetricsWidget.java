package plugin.widgets;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * PlugIn widget definition for calculated metrics.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@UserRole(UserRole.USER)
@Description("Shows Quality / Complexity Metrics.")
public class MetricsWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public MetricsWidget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "stugraplu-metrics";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "Calculated-Metrics";
    }

    /**
     * Returns the path to the widget Ruby file.
     *
     * @return the path to the widget Ruby file
     */
    @Override
    protected String getTemplatePath() {
        return "metrics_widget.html.erb";
    }
}

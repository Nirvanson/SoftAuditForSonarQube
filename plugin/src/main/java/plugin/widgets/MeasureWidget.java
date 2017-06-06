package plugin.widgets;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * PlugIn widget definition for listing measured values.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@UserRole(UserRole.USER)
@Description("Shows all measured values extracted by StuGraPluSensor.")
public class MeasureWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public MeasureWidget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "stugraplu-measure";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "StuGraPlu-Measures";
    }

    /**
     * Returns the path to the widget Ruby file.
     *
     * @return the path to the widget Ruby file
     */
    @Override
    protected String getTemplatePath() {
        return "/measure_widget.html.erb";
    }
}

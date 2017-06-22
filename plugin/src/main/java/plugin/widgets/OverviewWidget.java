package plugin.widgets;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * PlugIn widget definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@UserRole(UserRole.USER)
@Description("Shows overview of StuGraPlu features.")
public class OverviewWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public OverviewWidget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "stugraplu-overview";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "Student-Grading-PlugIn-Overview";
    }

    /**
     * Returns the path to the widget Ruby file.
     *
     * @return the path to the widget Ruby file
     */
    @Override
    protected String getTemplatePath() {
        return "/widgets/overview_widget.html.erb";
    }
}

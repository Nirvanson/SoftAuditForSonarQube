package plugin.definition;

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
@Description("Offers Quality / Complexity Metrics and other values for grading student-projects.")
public class StuGraPluWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public StuGraPluWidget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "stugraplu";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "Student-Grading-PlugIn";
    }

    /**
     * Returns the path to the widget Ruby file.
     *
     * @return the path to the widget Ruby file
     */
    @Override
    protected String getTemplatePath() {
        return "/plugin/stugraplu_widget.html.erb";
    }
}

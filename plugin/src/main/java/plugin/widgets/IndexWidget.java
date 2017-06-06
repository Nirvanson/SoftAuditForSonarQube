package plugin.widgets;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * PlugIn widget definition for code-quality-index.
 *
 * @author Jan Rucks
 * @version 1.0
 */
@UserRole(UserRole.USER)
@Description("Shows SWP-Quality-Index values.")
public class IndexWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public IndexWidget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "stugraplu-index";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "SWP-Code-Quality-Index";
    }

    /**
     * Returns the path to the widget Ruby file.
     *
     * @return the path to the widget Ruby file
     */
    @Override
    protected String getTemplatePath() {
        return "/index_widget.html.erb";
    }
}

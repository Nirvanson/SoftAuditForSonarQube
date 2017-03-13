package plugin.model;

import java.util.List;

/**
 * Super-class for all Model components.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public abstract class JavaFileContent {
    /** Inner content of the component. */
    private List<JavaFileContent> content;

    /**
     * Constructor with inner content.
     *
     * @param content
     */
    public JavaFileContent(List<JavaFileContent> content) {
        this.content = content;
    }

    /**
     * Get inner content.
     * 
     * @return content
     */
    public List<JavaFileContent> getContent() {
        return this.content;
    }

    /**
     * Set inner Content.
     * 
     * @param content
     */
    public void setContent(List<JavaFileContent> content) {
        this.content = content;
    }
}

package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

/**
 * Model for a java variable declaration (also parameters).
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaVariable extends JavaFileContent {
    /** Identifier of the variable. */
    private final String name;
    /** Data-type of the variable. */
    private final List<WordInFile> type;

    /**
     * Constructor with all informations.
     * 
     * @param name - variable identifier
     * @param type - data-type
     */
    public JavaVariable(String name, List<WordInFile> type) {
        super(null);
        this.name = name;
        this.type = type;
    }

    /**
     * Get variable identifier.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get variable data type.
     * 
     * @return type
     */
    public List<WordInFile> getType() {
        return type;
    }

}

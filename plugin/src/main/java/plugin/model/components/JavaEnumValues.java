package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

/**
 * Model for enum-value-list.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaEnumValues extends JavaFileContent {
    /** Values as list of word-lists (each value can contain more than one word). */
    private List<List<WordInFile>> values;

    /**
     * Constructor with value-list.
     * 
     * @param values
     */
    public JavaEnumValues(List<List<WordInFile>> values) {
        super(null);
        this.values = values;
    }

    /**
     * Get value list.
     * 
     * @return values
     */
    public List<List<WordInFile>> getValues() {
        return values;
    }

    /**
     * Set value list.
     * 
     * @param values
     */
    public void setValues(List<List<WordInFile>> values) {
        this.values = values;
    }
}

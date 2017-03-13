package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.KeyWord;
import plugin.model.WordInFile;

/**
 * Model for class / enum / interface declarations.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaClass extends JavaFileContent {
    /** Class name. */
    private final String name;
    /** Class modifiers. */
    private final List<WordInFile> modifiers;
    /** Class type (enum, class, interface, annotation). */
    private final KeyWord type;
    /** Extended classes. */
    private List<String> extending;
    /** Implemented interfaces. */
    private List<String> implementing;

    /**
     * Constructor with all informations.
     * 
     * @param name - class name
     * @param content - class body as content-list
     * @param type - enum / interface / class / annotation
     * @param modifiers - public, static, ...
     * @param extending - list of extended classes
     * @param implementing - list of implemented interfaces
     */
    public JavaClass(String name, List<JavaFileContent> content, KeyWord type, List<WordInFile> modifiers,
            List<String> extending, List<String> implementing) {
        super(content);
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.extending = extending;
        this.implementing = implementing;
    }

    /**
     * Get List of modifiers (public, static, abstract, ...).
     * 
     * @return modifiers
     */
    public List<WordInFile> getModifiers() {
        return modifiers;
    }

    /**
     * Get type (class, enum, interface, annotation).
     * 
     * @return type
     */
    public KeyWord getType() {
        return type;
    }

    /**
     * Get list of extended classes.
     * 
     * @return extended-classes
     */
    public List<String> getExtending() {
        return extending;
    }

    /**
     * Get list of implemented interfaces.
     * 
     * @return implemented-interfaces
     */
    public List<String> getImplementing() {
        return implementing;
    }

    /**
     * Get name of the class.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
}

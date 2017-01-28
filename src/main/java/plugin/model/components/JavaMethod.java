package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

/**
 * Model for methods.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaMethod extends JavaFileContent {
    /** Name of the method. */
    private final String name;
    /** List of modifiers (public, static, ...). */
    private final List<WordInFile> modifiers;
    /** List of parameters. */
    private final List<JavaVariable> parameters;
    /** Return-type. */
    private final List<WordInFile> returntype;

    /**
     * Constructor with all informations.
     * 
     * @param name - method-name
     * @param returntype - return-type
     * @param modifiers - list of modifiers
     * @param parameters - list of parameters
     * @param body - method-content
     */
    public JavaMethod(String name, List<WordInFile> returntype, List<WordInFile> modifiers,
            List<JavaVariable> parameters, List<JavaFileContent> body) {
        super(body);
        this.returntype = returntype;
        this.modifiers = modifiers;
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Get parameters as list of variables.
     * 
     * @return parameters
     */
    public List<JavaVariable> getParameters() {
        return parameters;
    }

    /**
     * Get method name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get modifiers as word-list.
     * 
     * @return modifiers
     */
    public List<WordInFile> getModifiers() {
        return modifiers;
    }

    /**
     * Get return type as word-list (some types like generics have more than one word).
     * 
     * @return return-type
     */
    public List<WordInFile> getReturntype() {
        return returntype;
    }
}

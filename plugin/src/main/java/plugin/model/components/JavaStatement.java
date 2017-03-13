package plugin.model.components;

import java.util.ArrayList;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;

/**
 * Model for java statements.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaStatement extends JavaFileContent {
    /** The statement-text in single line statments without content. */
    private List<WordInFile> statementText;
    /** Statement-type (like return, import, if, ...). */
    private StatementType type;
    /** List of in statement declared variables (not in content). */
    private List<JavaVariable> declaredVariables;
    /** List of in statement referenced variables (not in content). */
    private List<JavaVariable> referencedVariables;
    /** List of in statement called methods (not in content). */
    private List<WordInFile> calledMethods;

    /**
     * Constructor with statement-type. Sets all default-values.
     * 
     * @param type - statement-type
     */
    public JavaStatement(StatementType type) {
        super(null);
        this.type = type;
        this.statementText = null;
        this.declaredVariables = new ArrayList<JavaVariable>();
        this.referencedVariables = new ArrayList<JavaVariable>();
        this.calledMethods = new ArrayList<WordInFile>();
    }

    /**
     * Get statement text.
     * 
     * @return statementText
     */
    public List<WordInFile> getStatementText() {
        return statementText;
    }

    /**
     * Set statement-text.
     * 
     * @param statementText
     */
    public void setStatementText(List<WordInFile> statementText) {
        this.statementText = statementText;
    }

    /**
     * Get statement-type.
     * 
     * @return statementType
     */
    public StatementType getType() {
        return type;
    }

    /**
     * Set statement-type.
     * 
     * @param type
     */
    public void setStatementType(StatementType type) {
        this.type = type;
    }

    /**
     * Get list of declared variables.
     * 
     * @return declaredVariables
     */
    public List<JavaVariable> getDeclaredVariables() {
        return declaredVariables;
    }

    /**
     * Set declared variables.
     * 
     * @param declaredVariables
     */
    public void setDeclaredVariables(List<JavaVariable> declaredVariables) {
        this.declaredVariables = declaredVariables;
    }

    /**
     * Get list of referenced variables.
     * 
     * @return referencedVariables
     */
    public List<JavaVariable> getReferencedVariables() {
        return referencedVariables;
    }

    /**
     * Set referenced variables.
     * 
     * @param referencedVariables
     */
    public void setReferencedVariables(List<JavaVariable> referencedVariables) {
        this.referencedVariables = referencedVariables;
    }

    /**
     * Get list of called methods.
     * 
     * @return calledMethods
     */
    public List<WordInFile> getCalledMethods() {
        return calledMethods;
    }

    /**
     * Set called methods.
     * 
     * @param calledMethods
     */
    public void setCalledMethods(List<WordInFile> calledMethods) {
        this.calledMethods = calledMethods;
    }
}

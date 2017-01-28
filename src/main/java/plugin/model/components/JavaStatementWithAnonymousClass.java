package plugin.model.components;

import java.util.List;

import plugin.model.StatementType;
import plugin.model.WordInFile;

/**
 * Model for a java statements that contains an anonymous class.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaStatementWithAnonymousClass extends JavaStatement {
    /** Statement-text before class declaration. */
    private final List<WordInFile> statementBeforeClass;
    /** Statement-text after class declaration. */
    private List<WordInFile> statementAfterClass;
    /** Interface implemented by anonymous class. */
    private final List<WordInFile> classType;

    /**
     * Constructor with statement-text before class and class-type.
     * 
     * @param statementBeforeClass
     * @param classType
     */
    public JavaStatementWithAnonymousClass(List<WordInFile> statementBeforeClass, List<WordInFile> classType) {
        super(StatementType.UNSPECIFIED);
        this.statementBeforeClass = statementBeforeClass;
        this.classType = classType;
    }

    /**
     * Get statement-text before class declaration.
     * 
     * @return statementBeforeClass
     */
    public List<WordInFile> getStatementBeforeClass() {
        return statementBeforeClass;
    }

    /**
     * Get statement-text after class declaration.
     * 
     * @return statementAfterClass
     */
    public List<WordInFile> getStatementAfterClass() {
        return statementAfterClass;
    }

    /**
     * Set statement-text after class declaration.
     * 
     * @param statementAfterClass
     */
    public void setStatementAfterClass(List<WordInFile> statementAfterClass) {
        this.statementAfterClass = statementAfterClass;
    }

    /**
     * Get interface implemented by anonymous class.
     * 
     * @return classType
     */
    public List<WordInFile> getClassType() {
        return classType;
    }
}

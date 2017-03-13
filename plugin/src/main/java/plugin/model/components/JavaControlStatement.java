package plugin.model.components;

import java.util.List;
import java.util.Map;

import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;

/**
 * Model for Control-Statement like loops, if, try, switch, return and some other.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class JavaControlStatement extends JavaStatement {
    // content of JavaFileContent is the block inside ifs, fors, whiles, trys, cases, and blocks
    // or the list of cases in switches

    /**
     * Condition in while and if, termination in for, declaration in enhanced for, switch-variable in switch and value
     * of switch-variable in case.
     */
    private List<WordInFile> condition;
    /** Only set for standard for loops. */
    private List<WordInFile> initialization;
    /** Only set for standard for loops. */
    private List<WordInFile> increment;
    /** Else block in if and finally block in try. */
    private List<JavaFileContent> othercontent;
    /** Resources-block in try with resources. */
    private List<JavaFileContent> resources;
    /** List of catched Exceptions with catch-blocks. */
    private Map<List<WordInFile>, List<JavaFileContent>> catchedExceptions;
    /** Indicates for ifs, whiles, for-loops and cases if content is in braces. */
    private boolean contentInBlock;
    /** Indicates if else-block of an if-statement is in braces. */
    private boolean otherContentInBlock;

    /**
     * Constructor with statement-type. Sets all default-values.
     * 
     * @param type - statement-type
     */
    public JavaControlStatement(StatementType type) {
        super(type);
        this.condition = null;
        this.initialization = null;
        this.increment = null;
        this.othercontent = null;
        this.catchedExceptions = null;
        this.resources = null;
        this.contentInBlock = false;
        this.otherContentInBlock = false;
    }

    /**
     * Get condition as word-list.
     * 
     * @return condition
     */
    public List<WordInFile> getCondition() {
        return condition;
    }

    /**
     * Set Condition.
     * 
     * @param condition
     */
    public void setCondition(List<WordInFile> condition) {
        this.condition = condition;
    }

    /**
     * Get initialization as word-list.
     * 
     * @return initialization
     */
    public List<WordInFile> getInitialization() {
        return initialization;
    }

    /**
     * Set Initialization.
     * 
     * @param initialization
     */
    public void setInitialization(List<WordInFile> initialization) {
        this.initialization = initialization;
    }

    /**
     * Get increment as word-list.
     * 
     * @return increment
     */
    public List<WordInFile> getIncrement() {
        return increment;
    }

    /**
     * Set increment.
     * 
     * @param increment
     */
    public void setIncrement(List<WordInFile> increment) {
        this.increment = increment;
    }

    /**
     * Get second content block.
     * 
     * @return otherContent
     */
    public List<JavaFileContent> getOthercontent() {
        return othercontent;
    }

    /**
     * Set second content block.
     * 
     * @param elsecontent
     */
    public void setOthercontent(List<JavaFileContent> elsecontent) {
        this.othercontent = elsecontent;
    }

    /**
     * Get resources-block.
     * 
     * @return resources
     */
    public List<JavaFileContent> getResources() {
        return resources;
    }

    /**
     * Set resources-block.
     * 
     * @param resources
     */
    public void setResources(List<JavaFileContent> resources) {
        this.resources = resources;
    }

    /**
     * Get catched exceptions with their content-blocks.
     * 
     * @return catchedExceptions
     */
    public Map<List<WordInFile>, List<JavaFileContent>> getCatchedExceptions() {
        return catchedExceptions;
    }

    /**
     * Set catched Exceptions.
     * 
     * @param catchedExceptions
     */
    public void setCatchedExceptions(Map<List<WordInFile>, List<JavaFileContent>> catchedExceptions) {
        this.catchedExceptions = catchedExceptions;
    }

    /**
     * Check if content is in braces.
     * 
     * @return true if content is in braces.
     */
    public boolean isContentInBlock() {
        return contentInBlock;
    }

    /**
     * Set if content is in braces.
     * 
     * @param contentInBlock
     */
    public void setContentInBlock(boolean contentInBlock) {
        this.contentInBlock = contentInBlock;
    }

    /**
     * Check if second content is in braces.
     * 
     * @return true if second content is in braces
     */
    public boolean isOtherContentInBlock() {
        return otherContentInBlock;
    }

    /**
     * Set if second content is in braces.
     * 
     * @param otherContentInBlock
     */
    public void setOtherContentInBlock(boolean otherContentInBlock) {
        this.otherContentInBlock = otherContentInBlock;
    }
}

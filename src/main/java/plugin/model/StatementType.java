package plugin.model;

/**
 * Statement-types.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public enum StatementType {
    // control statements
    BREAK,
    CONTINUE,
    ASSERT,
    THROW,
    TRY,
    IF,
    FOR,
    WHILE,
    DOWHILE,
    RETURN,
    BLOCK,
    SWITCH,
    CASE,
    SYNCHRONIZED,
    // other statements
    ANNOTATION,
    PACKAGE,
    IMPORT,
    ASSIGNMENT,
    VARDECLARATION,
    UNSPECIFIED,
    INCREMENT,
    DECREMENT,
    METHODCALL,
    // statementtypes for counting, not to be set as statementtype on statement in model
    ELSE,
    FINALLY,
    CATCH,
    METHODDECLARATION, 
    CLASSDECLARATION, 
    INTERFACEDECLARATION, 
    ENUMDECLARATION,
    TRYWITHRESOURCES,
    ENHANCEDFOR
}

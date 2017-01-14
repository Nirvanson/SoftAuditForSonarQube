package plugin.analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import plugin.model.JavaFileContent;
import plugin.model.KeyWord;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.model.components.JavaVariable;
import plugin.util.ParsingException;

/**
 * Refines fully structured code model (by ModelStructureExpander) with details: splits remaining word-lists to
 * statements and performs extraction of variable declarations, variable references and method calls.
 * 
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelDetailExpander {

    /**
     * Recoursive scanning model for remaining word-lists and split them to statements.
     * 
     * @param contents - structured model of the component-body for refinement
     * @throws ParsingException
     * @return refined model
     */
    public static List<JavaFileContent> parseRemainingWordListsToStatements(List<JavaFileContent> contents)
            throws ParsingException {
        try {
            if (contents == null) {
                // ignore null-content. No error!
                return null;
            }
            List<JavaFileContent> result = new ArrayList<JavaFileContent>();
            for (JavaFileContent content : contents) {
                if (content instanceof JavaClass || content instanceof JavaMethod
                        || content instanceof JavaControlStatement
                        || content instanceof JavaStatementWithAnonymousClass) {
                    // search in body of methods, classes, controlstatements
                    content.setContent(parseRemainingWordListsToStatements(content.getContent()));
                }
                if (content instanceof JavaControlStatement) {
                    // search in additional contents of control statements
                    JavaControlStatement ctrlstm = (JavaControlStatement) content;
                    ctrlstm.setOthercontent(parseRemainingWordListsToStatements(ctrlstm.getOthercontent()));
                    ctrlstm.setResources(parseRemainingWordListsToStatements(ctrlstm.getResources()));
                    if (ctrlstm.getCatchedExceptions() != null) {
                        for (List<WordInFile> exception : ctrlstm.getCatchedExceptions().keySet()) {
                            ctrlstm.getCatchedExceptions().put(exception,
                                    parseRemainingWordListsToStatements(ctrlstm.getCatchedExceptions().get(exception)));
                        }
                    }
                    result.add(ctrlstm);
                } else if (!(content instanceof WordList)) {
                    // ignore already parsed components without content
                    result.add(content);
                } else {
                    // parse found wordlist
                    result.addAll(parseWordListToStatements(((WordList) content).getWordlist()));
                }
            }
            return result;
        } catch (Exception e) {
            throw new ParsingException("Splitting remaining word-lists failed.");
        }
    }

    /**
     * Recoursive scanning model for parsing declared variables and method calls.
     * 
     * @param contents - model of the component-body for parsing
     * @throws ParsingException
     */
    public static void parseDeclarationsAndCalls(List<JavaFileContent> contents) throws ParsingException {
        try {
            for (JavaFileContent content : contents) {
                if (content instanceof JavaClass || content instanceof JavaMethod) {
                    // parse content
                    parseDeclarationsAndCalls(content.getContent());
                } else if (content instanceof JavaStatementWithAnonymousClass) {
                    // parse content of anonymous class
                    // build together statement with placeholder for class and scan
                    JavaStatementWithAnonymousClass theStatement = (JavaStatementWithAnonymousClass) content;
                    parseDeclarationsAndCalls(theStatement.getContent());
                    List<WordInFile> textToScan = new ArrayList<WordInFile>();
                    textToScan.addAll(theStatement.getStatementBeforeClass());
                    textToScan.add(new WordInFile(null, KeyWord.NEW));
                    textToScan.addAll(theStatement.getClassType());
                    textToScan.addAll(theStatement.getStatementAfterClass());
                    parseDeclarationsAndCallsInStatement(theStatement, textToScan);
                } else if (content instanceof JavaControlStatement) {
                    JavaControlStatement theStatement = (JavaControlStatement) content;
                    switch (theStatement.getType()) {
                    case SWITCH:
                    case WHILE:
                    case SYNCHRONIZED:
                    case CASE:
                        // parse content block and scan condition
                        parseDeclarationsAndCallsInStatement(theStatement, theStatement.getCondition());
                        parseDeclarationsAndCalls(theStatement.getContent());
                        break;
                    case FOR:
                        // parse content-block and scan initialization, termination and increment / declaration
                        parseDeclarationsAndCalls(theStatement.getContent());
                        parseDeclarationsAndCallsInStatement(theStatement, theStatement.getCondition());
                        if (theStatement.getInitialization() != null) {
                            parseDeclarationsAndCallsInStatement(theStatement, theStatement.getInitialization());
                            parseDeclarationsAndCallsInStatement(theStatement, theStatement.getIncrement());
                        }
                        break;
                    case IF:
                        // parse if-block and else-block and scan text of condition
                        parseDeclarationsAndCalls(theStatement.getContent());
                        parseDeclarationsAndCallsInStatement(theStatement, theStatement.getCondition());
                        if (theStatement.getOthercontent() != null) {
                            parseDeclarationsAndCalls(theStatement.getOthercontent());
                        }
                        break;
                    case TRY:
                        // parse resource/try/catch/finally-blocks and scan text of catched exceptions
                        parseDeclarationsAndCalls(theStatement.getContent());
                        if (theStatement.getOthercontent() != null) {
                            parseDeclarationsAndCalls(theStatement.getOthercontent());
                        }
                        if (theStatement.getResources() != null) {
                            parseDeclarationsAndCalls(theStatement.getResources());
                        }
                        if (theStatement.getCatchedExceptions() != null) {
                            Map<List<WordInFile>, List<JavaFileContent>> resultMap = new HashMap<List<WordInFile>, List<JavaFileContent>>();
                            for (List<WordInFile> exception : theStatement.getCatchedExceptions().keySet()) {
                                List<JavaFileContent> exceptioncontent = theStatement.getCatchedExceptions()
                                        .get(exception);
                                parseDeclarationsAndCalls(exceptioncontent);
                                resultMap.put(parseDeclarationsAndCallsInStatement(theStatement, exception),
                                        exceptioncontent);
                            }
                            theStatement.setCatchedExceptions(resultMap);
                        }
                        break;
                    case BLOCK:
                        // parse block-content
                        parseDeclarationsAndCalls(theStatement.getContent());
                        break;
                    case RETURN:
                    case ASSERT:
                    case THROW:
                        // scan statement-text for returns, assertions and throws
                        parseDeclarationsAndCallsInStatement(theStatement, theStatement.getStatementText());
                        break;
                    case BREAK:
                    case CONTINUE:
                        // these statements can't declare/reference variables or call methods
                        theStatement.setDeclaredVariables(null);
                        theStatement.setReferencedVariables(null);
                        theStatement.setCalledMethods(null);
                        break;
                    default:
                        throw new ParsingException("Unknown model-component");
                    }
                } else if (content instanceof JavaStatement) {
                    JavaStatement theStatement = (JavaStatement) content;
                    switch (theStatement.getType()) {
                    case ANNOTATION:
                    case IMPORT:
                    case PACKAGE:
                        // these statements can't declare/reference variables or call methods
                        theStatement.setDeclaredVariables(null);
                        theStatement.setReferencedVariables(null);
                        theStatement.setCalledMethods(null);
                        break;
                    case UNSPECIFIED:
                        // some other statement. scan statement-text
                        parseDeclarationsAndCallsInStatement(theStatement, theStatement.getStatementText());
                        break;
                    default:
                        throw new ParsingException("Unknown Model-Component");
                    }
                }
            }
        } catch (Exception e) {
            throw new ParsingException("Parsing variable declarations and method calls failed.");
        }
    }

    /**
     * Recoursive scanning model for parsing referenced variables.
     * 
     * @param contents - model of the component-body for parsing
     * @param declaredVariables - list of declared variables
     * @throws ParsingException
     */
    public static void parseReferences(List<JavaFileContent> contents, Set<String> declaredVariables)
            throws ParsingException {
        try {
            if (declaredVariables == null || declaredVariables.isEmpty()) {
                // skip this step if no variables declared. No Error!
                return;
            }
            for (JavaFileContent content : contents) {
                if (content instanceof JavaClass || content instanceof JavaMethod) {
                    // parse content
                    parseReferences(content.getContent(), declaredVariables);
                } else if (content instanceof JavaStatementWithAnonymousClass) {
                    // parse content of anonymous class
                    // build together statement with placeholder for class and scan
                    JavaStatementWithAnonymousClass theStatement = (JavaStatementWithAnonymousClass) content;
                    parseReferences(theStatement.getContent(), declaredVariables);
                    List<WordInFile> textToScan = new ArrayList<WordInFile>();
                    textToScan.addAll(theStatement.getStatementBeforeClass());
                    textToScan.add(new WordInFile(null, KeyWord.NEW));
                    textToScan.addAll(theStatement.getClassType());
                    textToScan.addAll(theStatement.getStatementAfterClass());
                    parseReferencesInStatement(theStatement, textToScan, declaredVariables);
                } else if (content instanceof JavaControlStatement) {
                    JavaControlStatement theStatement = (JavaControlStatement) content;
                    switch (theStatement.getType()) {
                    case SWITCH:
                    case WHILE:
                    case SYNCHRONIZED:
                    case CASE:
                        // parse content block
                        // scan condition
                        parseReferencesInStatement(theStatement, theStatement.getCondition(), declaredVariables);
                        parseReferences(theStatement.getContent(), declaredVariables);
                        break;
                    case FOR:
                        // parse content-block
                        // scan initialization, termination and increment or enhanced declaration
                        parseReferencesInStatement(theStatement, theStatement.getCondition(), declaredVariables);
                        parseReferences(theStatement.getContent(), declaredVariables);
                        if (theStatement.getInitialization() != null) {
                            parseReferencesInStatement(theStatement, theStatement.getInitialization(),
                                    declaredVariables);
                            parseReferencesInStatement(theStatement, theStatement.getIncrement(), declaredVariables);
                        }
                        break;
                    case IF:
                        // parse (if available) if-block and else-block
                        // scan text of condition
                        parseReferencesInStatement(theStatement, theStatement.getCondition(), declaredVariables);
                        parseReferences(theStatement.getContent(), declaredVariables);
                        if (theStatement.getOthercontent() != null) {
                            parseReferences(theStatement.getOthercontent(), declaredVariables);
                        }
                        break;
                    case TRY:
                        // parse (if available) resource-block, try-block, catch-blocks and finally block
                        // scan text of catched exceptions
                        parseReferences(theStatement.getContent(), declaredVariables);
                        if (theStatement.getOthercontent() != null) {
                            parseReferences(theStatement.getOthercontent(), declaredVariables);
                        }
                        if (theStatement.getResources() != null) {
                            parseReferences(theStatement.getResources(), declaredVariables);
                        }
                        if (theStatement.getCatchedExceptions() != null) {
                            for (List<WordInFile> exception : theStatement.getCatchedExceptions().keySet()) {
                                parseReferences(theStatement.getCatchedExceptions().get(exception), declaredVariables);
                            }
                        }
                        break;
                    case BLOCK:
                        // parse block-content
                        parseReferences(theStatement.getContent(), declaredVariables);
                        break;
                    case RETURN:
                    case ASSERT:
                    case THROW:
                        // scan statement-text for returns, assertions and throws
                        parseReferencesInStatement(theStatement, theStatement.getStatementText(), declaredVariables);
                        break;
                    case BREAK:
                    case CONTINUE:
                        // do nothing
                        break;
                    default:
                        throw new ParsingException("Control-Statement-Type '" + theStatement.getType()
                                + "' is unknown in ModelExpander.extractReferences!");
                    }
                } else if (content instanceof JavaStatement) {
                    JavaStatement theStatement = (JavaStatement) content;
                    switch (theStatement.getType()) {
                    case ANNOTATION:
                    case IMPORT:
                    case PACKAGE:
                        // do nothing
                        break;
                    case UNSPECIFIED:
                        // some other statement. scan statement-text
                        parseReferencesInStatement(theStatement, theStatement.getStatementText(), declaredVariables);
                        break;
                    default:
                        throw new ParsingException("Statement-Type '" + theStatement.getType()
                                + "' is unknown in ModelExpander.extractReferences!");
                    }
                }
            }
        } catch (Exception e) {
            throw new ParsingException("Parsing variable references failed.");
        }
    }

    /**
     * split a word-list to single line statements.
     * 
     * @param wordlist - word-list remaining after structural statements
     * @return List of statements
     */
    private static List<JavaFileContent> parseWordListToStatements(List<WordInFile> wordlist) {
        List<JavaFileContent> result = new ArrayList<JavaFileContent>();
        List<WordInFile> current = new ArrayList<WordInFile>();
        for (WordInFile word : wordlist) {
            // add everything till next semicolon to current statement
            current.add(word);
            if (word.equals(KeyWord.SEMICOLON)) {
                List<WordInFile> statement = new ArrayList<WordInFile>();
                statement.addAll(current);
                current.clear();
                JavaStatement newStatement = new JavaStatement(StatementType.UNSPECIFIED);
                newStatement.setStatementText(statement);
                result.add(newStatement);
            }
        }
        return result;
    }

    /**
     * Parse variable references in a statement.
     * 
     * @param theStatement - the statement to attach referenced variables
     * @param textToScan - the statement text to parse
     * @param declaredVariables - List of in file declared variables to search for
     */
    private static void parseReferencesInStatement(JavaStatement theStatement, List<WordInFile> textToScan,
            Set<String> declaredVariables) {
        for (int i = 0; i < textToScan.size(); i++) {
            // if free-word equals declared variable identifier add as referenced variable
            if (textToScan.get(i).getKey().equals(KeyWord.WORD)
                    && declaredVariables.contains(textToScan.get(i).getWord())) {
                textToScan.set(i, new WordInFile(textToScan.get(i).getWord(), KeyWord.VARIDENT));
                theStatement.getReferencedVariables().add(new JavaVariable(textToScan.get(i).getWord(), null));
            }
        }
    }

    /**
     * Parse variable declarations and method calls in a statement.
     * 
     * @param theStatement - the statement to attach findings
     * @param textToScan - the statement text to parse
     * @return scanned and edited text (for working reset of catched exceptions map in try statement)
     */
    private static List<WordInFile> parseDeclarationsAndCallsInStatement(JavaStatement statement,
            List<WordInFile> textToScan) {
        for (int i = 0; i < textToScan.size(); i++) {
            // check if variable declaration. same syntax as method parameter
            WordInFile declaredVar = ModelBuildHelper.isVariableDeclaration(textToScan.subList(i, textToScan.size()),
                    false);
            if (declaredVar != null) {
                List<WordInFile> datatype = new ArrayList<WordInFile>();
                while (!textToScan.get(i).equals(declaredVar)) {
                    if (textToScan.get(i).getWord() != null) {
                        textToScan.set(i, new WordInFile(textToScan.get(i).getWord(), KeyWord.VARTYPE));
                    } else {
                        textToScan.set(i, new WordInFile(textToScan.get(i).getKey().toString(), KeyWord.VARTYPE));
                    }
                    datatype.add(textToScan.get(i));
                    i++;
                }
                textToScan.set(i, new WordInFile(textToScan.get(i).getWord(), KeyWord.VARIDENT));
                statement.getDeclaredVariables().add(new JavaVariable(declaredVar.getWord(), datatype));
            } else if ((textToScan.get(i).getKey().equals(KeyWord.WORD) || textToScan.get(i).equals(KeyWord.THIS)
                    || textToScan.get(i).equals(KeyWord.SUPER))
                    && (textToScan.size() > i + 1 && textToScan.get(i + 1).equals(KeyWord.OPENPARANTHESE))) {
                // otherwise check if method call: free-word/super/this followed by "("
                textToScan.set(i, new WordInFile(textToScan.get(i).getWord(), KeyWord.METHODREF));
                statement.getCalledMethods().add(textToScan.get(i));
                // skip "("
                i++;
            } else if (textToScan.get(i).getKey().equals(KeyWord.WORD) && textToScan.size() > i + 2
                    && textToScan.get(i + 1).equals(KeyWord.LESS)) {
                // otherwise check if call of constructor with generic
                int endOfGeneric = ModelBuildHelper.parseGeneric(textToScan, i + 2);
                if (endOfGeneric != 0 && textToScan.size() > endOfGeneric + 1
                        && textToScan.get(endOfGeneric + 1).getKey().equals(KeyWord.OPENPARANTHESE)) {
                    statement.getCalledMethods().add(textToScan.get(i));
                    while (i <= endOfGeneric) {
                        textToScan.set(i, new WordInFile(textToScan.get(i).getWord(), KeyWord.METHODREF));
                        i++;
                    }
                }
            }
        }
        return textToScan;
    }
}

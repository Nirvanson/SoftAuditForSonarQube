package plugin.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.KeyWord;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.util.ParsingException;

/**
 * Refines basic code model (by ModelBuilder) with structural (control) statements.
 * 
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelStructureExpander {
    
	/**
     * Searching the main class/interface/enum declaration of the file and send it's content to parsing.
     * 
     * @param contents - the basic model
     * @throws ParsingException
     * @returns extended model
     */
    public static List<JavaFileContent> parseStatementStructure(List<JavaFileContent> contents) throws ParsingException {
    	for (JavaFileContent content : contents) {
            if (content instanceof JavaClass) {
                content.setContent(parseStructuralStatements(content.getContent()));
            }
        }
    	return contents;
    }
	
    /**
     * Entry-point for parsing structural (control) statements in class-body.
     * 
     * @param contentlist - basic model of the class for refinement
     * @throws ParsingException
     * @return refined model
     */
    private static List<JavaFileContent> parseStructuralStatements(List<JavaFileContent> contentlist)
            throws ParsingException {
        try {
            List<JavaFileContent> result = new ArrayList<JavaFileContent>();
            if (!(contentlist == null)) {
                for (JavaFileContent content : contentlist) {
                    if (content instanceof JavaMethod && content.getContent() != null) {
                        List<JavaFileContent> newMethodContent = new ArrayList<JavaFileContent>();
                        for (JavaFileContent methodcontent : content.getContent()) {
                            if (methodcontent instanceof JavaClass
                                    || methodcontent instanceof JavaStatementWithAnonymousClass) {
                                // class in method - recoursiv call
                                methodcontent.setContent(parseStructuralStatements(methodcontent.getContent()));
                                newMethodContent.add(methodcontent);
                            } else if (methodcontent instanceof WordList) {
                                // wordlist in method. start parsing
                                newMethodContent
                                        .addAll(parseControlStatements(((WordList) methodcontent).getWordlist()));
                            }
                        }
                        content.setContent(newMethodContent);
                    } else if (content instanceof JavaClass || content instanceof JavaStatementWithAnonymousClass) {
                        // class in class - recoursiv call
                        content.setContent(parseStructuralStatements(content.getContent()));
                    }
                    result.add(content);
                }
            }
            return result;
        } catch (Exception e) {
            throw new ParsingException("Parsing structural (control) statements failed.");
        }
    }

    /**
     * Parsing control statements in word-list in method.
     * 
     * @param content - word-list to parse
     * @return parsed model
     */
    private static List<JavaFileContent> parseControlStatements(List<WordInFile> content) {
        List<JavaFileContent> result = new ArrayList<JavaFileContent>();
        List<WordInFile> otherContent = new ArrayList<WordInFile>();
        List<WordInFile> potentialLabels = new ArrayList<WordInFile>();
        for (int i = 0; i < content.size(); i++) {
            WordInFile word = content.get(i);
            if (ModelBuildHelper.keywords.contains(word)) {
                // java keyword detected - parse the statement
                if (!otherContent.isEmpty()) {
                    List<WordInFile> wordlist = new ArrayList<WordInFile>();
                    wordlist.addAll(otherContent);
                    otherContent.clear();
                    result.add(new WordList(wordlist));
                }
                potentialLabels.clear();
                i = parseSingleControlStatement(word.getKey(), i, content, result);
            } else {
                if (word.equals(KeyWord.OPENBRACE) && (i == 0 || (!content.get(i - 1).equals(KeyWord.CLOSEBRACKET)
                        && !content.get(i - 1).equals(KeyWord.ASSIGN)))) {
                    // anonymous block detected - parse it
                    if (!otherContent.isEmpty()) {
                        List<WordInFile> wordlist = new ArrayList<WordInFile>();
                        wordlist.addAll(otherContent);
                        otherContent.clear();
                        result.add(new WordList(wordlist));
                    }
                    potentialLabels.clear();
                    i = parseAnonymousBlock(content, result, i);
                } else if (word.getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
                    // maybe label for control statement detected. keep it if false hit
                    potentialLabels.add(word);
                    potentialLabels.add(content.get(i + 1));
                    i++;
                } else {
                    // no control statement indicator. add it to remaining wordlist
                    if (!potentialLabels.isEmpty()) {
                        otherContent.addAll(potentialLabels);
                        potentialLabels.clear();
                    }
                    otherContent.add(word);
                }
            }
        }
        // add remaining wordlist after last control statement
        if (!potentialLabels.isEmpty()) {
            otherContent.addAll(potentialLabels);
        }
        if (!otherContent.isEmpty()) {
            result.add(new WordList(otherContent));
        }
        return result;
    }

    /**
     * Parse single control statement indicated by keyword (Delegate to specialized methods).
     * 
     * @param key - detected key
     * @param i - position of key in parsed word-list
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @return end-position
     */
    private static int parseSingleControlStatement(KeyWord key, int i, List<WordInFile> content,
            List<JavaFileContent> result) {
        int endposition;
        switch (key) {
        case SWITCH:
            endposition = parseSwitch(content, result, i);
            break;
        case DO:
            endposition = parseDoWhile(content, result, i);
            break;
        case WHILE:
            endposition = parseWhile(content, result, i);
            break;
        case FOR:
            endposition = parseFor(content, result, i);
            break;
        case IF:
            endposition = parseIf(content, result, i);
            break;
        case TRY:
            endposition = parseTry(content, result, i);
            break;
        case SYNCHRONIZED:
            endposition = parseSynchronized(content, result, i);
            break;
        case RETURN:
            endposition = parseSingleLineStatement(content, result, i, StatementType.RETURN);
            break;
        case BREAK:
            endposition = parseSingleLineStatement(content, result, i, StatementType.BREAK);
            break;
        case CONTINUE:
            endposition = parseSingleLineStatement(content, result, i, StatementType.CONTINUE);
            break;
        case ASSERT:
            endposition = parseSingleLineStatement(content, result, i, StatementType.ASSERT);
            break;
        case THROW:
            endposition = parseSingleLineStatement(content, result, i, StatementType.THROW);
            break;
        default:
            endposition = i;
        }
        return endposition;
    }

    /**
     * Parse single line statement indicated by keyword.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @param type - type of statement to parse
     * @return end-position
     */
    private static int parseSingleLineStatement(List<WordInFile> content, List<JavaFileContent> result, int i,
            StatementType type) {
        // add everything until the next semicolon to statement
        List<WordInFile> statementContent = new ArrayList<WordInFile>();
        while (!content.get(i).equals(KeyWord.SEMICOLON)) {
            statementContent.add(content.get(i));
            i++;
        }
        statementContent.add(content.get(i));
        JavaControlStatement statement = new JavaControlStatement(type);
        statement.setStatementText(statementContent);
        result.add(statement);
        return i;
    }

    /**
     * Parse synchronized statement block.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseSynchronized(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "synchronized("
        i += 2;
        JavaControlStatement synchronizedStatement = new JavaControlStatement(StatementType.SYNCHRONIZED);
        // parse words in parantheses as condition
        List<WordInFile> condition = new ArrayList<WordInFile>();
        int openParanthesis = 1;
        while (openParanthesis > 0) {
            if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                openParanthesis--;
            }
            if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                openParanthesis++;
            }
            if (openParanthesis != 0) {
                condition.add(content.get(i));
            }
            i++;
        }
        synchronizedStatement.setCondition(condition);
        // skip "{"
        i++;
        // add everything until brace is closed to block
        int openBraces = 1;
        List<WordInFile> blockcontent = new ArrayList<WordInFile>();
        while (openBraces > 0) {
            if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                openBraces--;
            }
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                openBraces++;
            }
            if (openBraces != 0) {
                blockcontent.add(content.get(i));
            }
            i++;
        }
        synchronizedStatement.setContent(parseControlStatements(blockcontent));
        result.add(synchronizedStatement);
        return i - 1;
    }

    /**
     * Parse switch statement with cases.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseSwitch(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "switch("
        i += 2;
        JavaControlStatement switchStatement = new JavaControlStatement(StatementType.SWITCH);
        // parse switchvalue as condition
        List<WordInFile> condition = new ArrayList<WordInFile>();
        int openParanthesis = 1;
        while (openParanthesis > 0) {
            if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                openParanthesis--;
            }
            if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                openParanthesis++;
            }
            if (openParanthesis != 0) {
                condition.add(content.get(i));
            }
            i++;
        }
        switchStatement.setCondition(condition);
        // skip "{"
        i++;
        // parse content
        List<JavaFileContent> switchContent = new ArrayList<JavaFileContent>();
        int openBraces = 1;
        List<WordInFile> casecontent = new ArrayList<WordInFile>();
        List<WordInFile> casecondition = new ArrayList<WordInFile>();
        JavaControlStatement currentCase = new JavaControlStatement(StatementType.CASE);
        currentCase.setCondition(new ArrayList<WordInFile>());
        if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
            openBraces--;
        } else if (content.get(i).equals(KeyWord.OPENBRACE)) {
            openBraces++;
        }
        while (openBraces > 0) {
            // accept only cases in switch braces, to avoid to detect cases of
            // inner switches...
            while (openBraces == 1 && (content.get(i).equals(KeyWord.CASE) || content.get(i).equals(KeyWord.DEFAULT))) {
                if (!casecontent.isEmpty()) {
                    // add previous case to switch
                    List<WordInFile> onecase = new ArrayList<WordInFile>();
                    onecase.addAll(casecontent);
                    casecontent.clear();
                    List<JavaFileContent> parsedCaseContent = parseControlStatements(onecase);
                    if (parsedCaseContent.size() == 1 && parsedCaseContent.get(0) instanceof JavaControlStatement
                            && ((JavaControlStatement) parsedCaseContent.get(0)).getType()
                                    .equals(StatementType.BLOCK)) {
                        // if casecontent is completely in braces dont't count
                        // it as anonymous block
                        currentCase.setContent(parsedCaseContent.get(0).getContent());
                    } else {
                        currentCase.setContent(parsedCaseContent);
                    }
                    // remove last comma
                    currentCase.getCondition().remove(currentCase.getCondition().size() - 1);
                    switchContent.add(currentCase);
                    currentCase = new JavaControlStatement(StatementType.CASE);
                    currentCase.setCondition(new ArrayList<WordInFile>());
                }
                // skip "case" or "default"
                i++;
                // ignore parantheses around value and parse it
                if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                    openParanthesis = 1;
                    // skip "("
                    i++;
                    while (openParanthesis > 0) {
                        if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                            openParanthesis--;
                        }
                        if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                            openParanthesis++;
                        }
                        if (openParanthesis != 0) {
                            casecondition.add(content.get(i));
                        }
                        i++;
                    }
                }
                // parse case value and set as condition
                while (!content.get(i).equals(KeyWord.DOUBLEDOT)) {
                    casecondition.add(content.get(i));
                    i++;
                }
                if (casecondition.isEmpty()) {
                    // add "default" if no casecondition found
                    casecondition.add(content.get(i - 1));
                }
                currentCase.getCondition().addAll(casecondition);
                currentCase.getCondition().add(new WordInFile(null, KeyWord.COMMA));
                casecondition.clear();
                // skip ":"
                i++;
            }
            // skip labels
            while (content.get(i).getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
                i += 2;
            }
            // check ending of switch or inner braces
            if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                openBraces--;
            } else if (content.get(i).equals(KeyWord.OPENBRACE)) {
                openBraces++;
            }
            // if case not ended add word to content
            if (openBraces > 0) {
                casecontent.add(content.get(i));
            }
            i++;
        }
        // finish switch, add last case to switch
        List<WordInFile> onecase = new ArrayList<WordInFile>();
        onecase.addAll(casecontent);
        casecontent.clear();
        List<JavaFileContent> parsedCaseContent = parseControlStatements(onecase);
        if (parsedCaseContent.size() == 1 && parsedCaseContent.get(0) instanceof JavaControlStatement
                && ((JavaControlStatement) parsedCaseContent.get(0)).getType().equals(StatementType.BLOCK)) {
            // if casecontent is completely in braces dont't count it as
            // anonymous block
            currentCase.setContent(parsedCaseContent.get(0).getContent());
            currentCase.setContentInBlock(true);
        } else {
            currentCase.setContent(parsedCaseContent);
        }
        // remove last comma
        currentCase.getCondition().remove(currentCase.getCondition().size() - 1);
        switchContent.add(currentCase);
        switchStatement.setContent(switchContent);
        result.add(switchStatement);
        return i - 1;
    }

    /**
     * Parse anonymous statement block.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseAnonymousBlock(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "{"
        i++;
        // add everything until brace is closed to block
        int openBraces = 1;
        List<WordInFile> blockcontent = new ArrayList<WordInFile>();
        while (openBraces > 0) {
            if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                openBraces--;
            }
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                openBraces++;
            }
            if (openBraces != 0) {
                blockcontent.add(content.get(i));
            }
            i++;
        }
        JavaControlStatement blockstatement = new JavaControlStatement(StatementType.BLOCK);
        blockstatement.setContent(parseControlStatements(blockcontent));
        result.add(blockstatement);
        return i - 1;
    }

    /**
     * Parse try statement with resources, catched exceptions and finally block.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseTry(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "try"
        i++;
        List<WordInFile> resources = null;
        if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
            // try with resource detected
            int openParantheses = 1;
            i++;
            resources = new ArrayList<WordInFile>();
            while (openParantheses > 0) {
                if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                    openParantheses--;
                }
                if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                    openParantheses++;
                }
                if (openParantheses != 0) {
                    resources.add(content.get(i));
                }
                i++;
            }
        }
        // skip {
        i++;
        // add everything until brace is closed to tryblock
        List<WordInFile> tryblock = new ArrayList<WordInFile>();
        int openBraces = 1;
        while (openBraces > 0) {
            if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                openBraces--;
            }
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                openBraces++;
            }
            if (openBraces != 0) {
                tryblock.add(content.get(i));
            }
            i++;
        }
        JavaControlStatement tryStatement = new JavaControlStatement(StatementType.TRY);
        tryStatement.setContent(parseControlStatements(tryblock));
        if (resources != null) {
            tryStatement.setResources(parseControlStatements(resources));
        }
        // add catched exception as condition (....) if present
        tryStatement.setCatchedExceptions(new HashMap<List<WordInFile>, List<JavaFileContent>>());
        while (content.size() > i && content.get(i).equals(KeyWord.CATCH)) {
            List<WordInFile> exception = new ArrayList<WordInFile>();
            int openParanthesis = 1;
            i++;
            i++;
            while (openParanthesis > 0) {
                if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                    openParanthesis--;
                }
                if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                    openParanthesis++;
                }
                if (openParanthesis != 0) {
                    exception.add(content.get(i));
                }
                i++;
            }
            // add catchblock
            List<WordInFile> catchblock = new ArrayList<WordInFile>();
            openBraces = 1;
            i++;
            while (openBraces > 0) {
                if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                    openBraces--;
                }
                if (content.get(i).equals(KeyWord.OPENBRACE)) {
                    openBraces++;
                }
                if (openBraces != 0) {
                    catchblock.add(content.get(i));
                }
                i++;
            }
            tryStatement.getCatchedExceptions().put(exception, parseControlStatements(catchblock));
        }
        // check for finally block and add if present
        if (content.size() > i && content.get(i).equals(KeyWord.FINALLY)) {
            i++;
            List<WordInFile> finallyblock = new ArrayList<WordInFile>();
            openBraces = 1;
            i++;
            while (openBraces > 0) {
                if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                    openBraces--;
                }
                if (content.get(i).equals(KeyWord.OPENBRACE)) {
                    openBraces++;
                }
                if (openBraces != 0) {
                    finallyblock.add(content.get(i));
                }
                i++;
            }
            tryStatement.setOthercontent(parseControlStatements(finallyblock));
        }
        result.add(tryStatement);
        return i - 1;
    }

    /**
     * Parse if statement with else block.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseIf(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "if("
        i += 2;
        JavaControlStatement ifStatement = new JavaControlStatement(StatementType.IF);
        // parse condition
        List<WordInFile> condition = new ArrayList<WordInFile>();
        int openParanthesis = 1;
        while (openParanthesis > 0) {
            if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                openParanthesis--;
            }
            if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                openParanthesis++;
            }
            if (openParanthesis != 0) {
                condition.add(content.get(i));
            }
            i++;
        }
        ifStatement.setCondition(condition);
        // parse if-block
        List<JavaFileContent> ifContent = new ArrayList<JavaFileContent>();
        // skip labels
        while (content.get(i).getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
            i += 2;
        }
        if (ModelBuildHelper.keywords.contains(content.get(i))) {
            // content is single structural statement - parse directly
            i = parseSingleControlStatement(content.get(i).getKey(), i, content, ifContent) + 1;
            ifStatement.setContent(ifContent);
        } else {
            List<WordInFile> ifblock = new ArrayList<WordInFile>();
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                // in braces - parse block
                ifStatement.setContentInBlock(true);
                int openBraces = 1;
                i++;
                while (openBraces > 0) {
                    if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                        openBraces--;
                    }
                    if (content.get(i).equals(KeyWord.OPENBRACE)) {
                        openBraces++;
                    }
                    if (openBraces != 0) {
                        ifblock.add(content.get(i));
                    }
                    i++;
                }
            } else {
                // single (non structural) statement - parse till next semicolon
                do {
                    ifblock.add(content.get(i));
                    i++;
                } while (!content.get(i - 1).equals(KeyWord.SEMICOLON));
            }
            ifStatement.setContent(parseControlStatements(ifblock));
        }
        // check for else block
        if (content.size() > i && content.get(i).equals(KeyWord.ELSE)) {
            i++;
            List<JavaFileContent> elseContent = new ArrayList<JavaFileContent>();
            // skip labels
            while (content.get(i).getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
                i += 2;
            }
            if (ModelBuildHelper.keywords.contains(content.get(i))) {
                // content is single structural statement - parse directly
                i = parseSingleControlStatement(content.get(i).getKey(), i, content, elseContent) + 1;
                ifStatement.setOthercontent(elseContent);
            } else {
                List<WordInFile> elseblock = new ArrayList<WordInFile>();
                if (content.get(i).equals(KeyWord.OPENBRACE)) {
                    // in braces - parse block
                    ifStatement.setOtherContentInBlock(true);
                    i++;
                    int openBraces = 1;
                    while (openBraces > 0) {
                        if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                            openBraces--;
                        }
                        if (content.get(i).equals(KeyWord.OPENBRACE)) {
                            openBraces++;
                        }
                        if (openBraces != 0) {
                            elseblock.add(content.get(i));
                        }
                        i++;
                    }
                } else {
                    // single (non structural) statement - parse till next
                    // semicolon
                    do {
                        elseblock.add(content.get(i));
                        i++;
                    } while (!content.get(i - 1).equals(KeyWord.SEMICOLON));
                }
                ifStatement.setOthercontent(parseControlStatements(elseblock));
            }
        }
        result.add(ifStatement);
        return i - 1;
    }

    /**
     * Parse for-loop statement.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseFor(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "for("
        i += 2;
        JavaControlStatement forStatement = new JavaControlStatement(StatementType.FOR);
        // parse declaration
        List<WordInFile> declaration = new ArrayList<WordInFile>();
        int openParanthesis = 1;
        int state = 0;
        while (openParanthesis > 0) {
            if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                openParanthesis--;
            }
            if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                openParanthesis++;
            }
            if (openParanthesis != 0 && !content.get(i).equals(KeyWord.SEMICOLON)) {
                declaration.add(content.get(i));
            }
            if (content.get(i).equals(KeyWord.SEMICOLON)) {
                List<WordInFile> temp = new ArrayList<WordInFile>();
                temp.addAll(declaration);
                declaration.clear();
                state++;
                if (state == 1) {
                    forStatement.setInitialization(temp);
                } else {
                    forStatement.setCondition(temp);
                }
            }
            i++;
        }
        if (state == 0) {
            forStatement.setCondition(declaration);
        } else {
            forStatement.setIncrement(declaration);
        }
        // parse content
        List<JavaFileContent> forContent = new ArrayList<JavaFileContent>();
        // skip labels
        while (content.get(i).getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
            i += 2;
        }
        if (ModelBuildHelper.keywords.contains(content.get(i))) {
            // content is single structural statement - parse directly
            i = parseSingleControlStatement(content.get(i).getKey(), i, content, forContent) + 1;
            forStatement.setContent(forContent);
        } else {
            List<WordInFile> forblock = new ArrayList<WordInFile>();
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                // in braces - parse block
                forStatement.setContentInBlock(true);
                int openBraces = 1;
                i++;
                while (openBraces > 0) {
                    if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                        openBraces--;
                    }
                    if (content.get(i).equals(KeyWord.OPENBRACE)) {
                        openBraces++;
                    }
                    if (openBraces != 0) {
                        forblock.add(content.get(i));
                    }
                    i++;
                }
            } else {
                // single (non structural) statement - parse till next semicolon
                do {
                    forblock.add(content.get(i));
                    i++;
                } while (!content.get(i - 1).equals(KeyWord.SEMICOLON));
            }
            forStatement.setContent(parseControlStatements(forblock));
        }
        result.add(forStatement);
        return i - 1;
    }

    /**
     * Parse while-loop statement.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseWhile(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "while("
        i += 2;
        JavaControlStatement whileStatement = new JavaControlStatement(StatementType.WHILE);
        // parse condition
        List<WordInFile> condition = new ArrayList<WordInFile>();
        int openParanthesis = 1;
        while (openParanthesis > 0) {
            if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                openParanthesis--;
            }
            if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                openParanthesis++;
            }
            if (openParanthesis != 0) {
                condition.add(content.get(i));
            }
            i++;
        }
        whileStatement.setCondition(condition);
        // parse content
        List<JavaFileContent> whileContent = new ArrayList<JavaFileContent>();
        // skip labels
        while (content.get(i).getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
            i += 2;
        }
        if (ModelBuildHelper.keywords.contains(content.get(i))) {
            // content is single structural statement - parse directly
            i = parseSingleControlStatement(content.get(i).getKey(), i, content, whileContent) + 1;
            whileStatement.setContent(whileContent);
        } else {
            List<WordInFile> whileblock = new ArrayList<WordInFile>();
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                // in braces - parse block
                whileStatement.setContentInBlock(true);
                int openBraces = 1;
                i++;
                while (openBraces > 0) {
                    if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                        openBraces--;
                    }
                    if (content.get(i).equals(KeyWord.OPENBRACE)) {
                        openBraces++;
                    }
                    if (openBraces != 0) {
                        whileblock.add(content.get(i));
                    }
                    i++;
                }
            } else {
                // single (non structural) statement - parse till next semicolon
                do {
                    whileblock.add(content.get(i));
                    i++;
                } while (!content.get(i - 1).equals(KeyWord.SEMICOLON));
            }
            whileStatement.setContent(parseControlStatements(whileblock));
        }
        result.add(whileStatement);
        return i - 1;
    }

    /**
     * Parse do-while-loop statement.
     * 
     * @param content - parsed word-list
     * @param result - resulting code-model to add the statement
     * @param i - position of key in parsed word-list
     * @return end-position
     */
    private static int parseDoWhile(List<WordInFile> content, List<JavaFileContent> result, int i) {
        // skip "do"
        i++;
        JavaControlStatement whileStatement = new JavaControlStatement(StatementType.DOWHILE);
        // parse content
        List<JavaFileContent> whileContent = new ArrayList<JavaFileContent>();
        // skip labels
        while (content.get(i).getKey().equals(KeyWord.WORD) && content.get(i + 1).equals(KeyWord.DOUBLEDOT)) {
            i += 2;
        }
        if (ModelBuildHelper.keywords.contains(content.get(i))) {
            // content is single structural statement - parse directly
            i = parseSingleControlStatement(content.get(i).getKey(), i, content, whileContent) + 1;
            whileStatement.setContent(whileContent);
        } else {
            List<WordInFile> whileblock = new ArrayList<WordInFile>();
            if (content.get(i).equals(KeyWord.OPENBRACE)) {
                // in braces - parse block
                whileStatement.setContentInBlock(true);
                int openBraces = 1;
                i++;
                while (openBraces > 0) {
                    if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
                        openBraces--;
                    }
                    if (content.get(i).equals(KeyWord.OPENBRACE)) {
                        openBraces++;
                    }
                    if (openBraces != 0) {
                        whileblock.add(content.get(i));
                    }
                    i++;
                }
            } else {
                // single (non structural) statement - parse till next semicolon
                do {
                    whileblock.add(content.get(i));
                    i++;
                } while (!content.get(i - 1).equals(KeyWord.SEMICOLON));
            }
            whileStatement.setContent(parseControlStatements(whileblock));
        }
        // skip "while("
        i += 2;
        // parse condition
        List<WordInFile> condition = new ArrayList<WordInFile>();
        int openParanthesis = 1;
        while (openParanthesis > 0) {
            if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                openParanthesis--;
            }
            if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
                openParanthesis++;
            }
            if (openParanthesis != 0) {
                condition.add(content.get(i));
            }
            i++;
        }
        whileStatement.setCondition(condition);
        result.add(whileStatement);
        return i;
    }
}

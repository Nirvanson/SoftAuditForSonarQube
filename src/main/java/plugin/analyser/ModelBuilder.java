package plugin.analyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.KeyWord;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.model.WordType;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaEnumValues;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.model.components.JavaVariable;
import plugin.util.Logger;
import plugin.util.ParsingException;

/**
 * Builds basic code-model out of the word-list from FileNormalizer. Parsing class and method structure.
 * 
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelBuilder {

	/**
     * Wrapper for building basic model in correct order.
     * 
     * @param wordList - normalized file
     * @throws ParsingException
     * @return basic model
     */
	public static List<JavaFileContent> parseBasicModel(List<WordInFile> wordList) throws ParsingException {
		// step 1 - parse basic model from wordlist (class, imports, packages)
		List<JavaFileContent> fileModel = parseClassStructure(wordList);
		// step 2 - complete basic model by parsing inner classes and methods
		for (JavaFileContent content : fileModel) {
            if (content instanceof JavaClass) {
                content.setContent(parseClassContent(content));
            }
        }
		Logger.getLogger().printModel("basic", fileModel);
		return fileModel;
	}
	
    /**
     * Parsing the basic structure of a java class/interface/enum to model. package, imports, class-definition and
     * annotations outside class definition
     * 
     * @param wordList - Java File as word-list
     * @throws ParsingException
     * @returns basic model of the file
     */
    private static List<JavaFileContent> parseClassStructure(List<WordInFile> wordList) throws ParsingException {
        try {
            List<JavaFileContent> fileModel = new ArrayList<JavaFileContent>();
            boolean packageStatement = false;
            boolean importStatement = false;
            int classDefinitionState = 0;
            int openBraces = 0;
            JavaClass foundClass = null;
            List<WordInFile> content = new ArrayList<WordInFile>();
            List<WordInFile> temporary = new ArrayList<WordInFile>();
            String typeString = "";
            for (int i = 0; i < wordList.size(); i++) {
                WordInFile word = wordList.get(i);
                if (packageStatement) {
                    // in a package statement check if ending by ;
                    if (word.equals(KeyWord.SEMICOLON)) {
                        packageStatement = false;
                        content.add(word);
                        List<WordInFile> statementcontent = new ArrayList<WordInFile>();
                        statementcontent.addAll(content);
                        JavaStatement packagestatement = new JavaStatement(StatementType.PACKAGE);
                        packagestatement.setStatementText(statementcontent);
                        fileModel.add(packagestatement);
                        content.clear();
                    } else {
                        content.add(word);
                    }
                } else if (importStatement) {
                    // in a import statement check if ending by ;
                    if (word.equals(KeyWord.SEMICOLON)) {
                        importStatement = false;
                        content.add(word);
                        List<WordInFile> statementcontent = new ArrayList<WordInFile>();
                        statementcontent.addAll(content);
                        JavaStatement importstatement = new JavaStatement(StatementType.IMPORT);
                        importstatement.setStatementText(statementcontent);
                        fileModel.add(importstatement);
                        content.clear();
                    } else {
                        content.add(word);
                    }
                } else {
                    switch (classDefinitionState) {
                    // only modifiers or nothing from class definition found
                    case 0:
                        if (word.equals(KeyWord.ANNOTATION)) {
                            // Annotation placeholder outside of class definition found. Add as Statement
                            fileModel.add(new JavaStatement(StatementType.ANNOTATION));
                        } else if (word.equals(KeyWord.IMPORT)) {
                            // start import
                            importStatement = true;
                            content.addAll(temporary);
                            temporary.clear();
                            if (!content.isEmpty()) {
                                List<WordInFile> someContent = new ArrayList<WordInFile>();
                                someContent.addAll(content);
                                fileModel.add(new WordList(someContent));
                                content.clear();
                            }
                            content.add(word);
                        } else if (word.equals(KeyWord.PACKAGE)) {
                            // start package
                            packageStatement = true;
                            content.addAll(temporary);
                            temporary.clear();
                            if (!content.isEmpty()) {
                                List<WordInFile> someContent = new ArrayList<WordInFile>();
                                someContent.addAll(content);
                                fileModel.add(new WordList(someContent));
                                content.clear();
                            }
                            content.add(word);
                        } else if (word.getKey().getType().equals(WordType.MODIFIER)
                                || word.getKey().equals(KeyWord.SYNCHRONIZED)) {
                            // potentially class definition modifier
                            temporary.add(word);
                        } else if (!wordList.get(i - 1).equals(KeyWord.DOT)
                                && (word.getKey().getType().equals(WordType.DECLARATOR))) {
                            // class/enum/interface declaration detected. parse it to JavaClassModel - all the same
                            classDefinitionState++;
                            if (!content.isEmpty()) {
                                List<WordInFile> someContent = new ArrayList<WordInFile>();
                                someContent.addAll(content);
                                fileModel.add(new WordList(someContent));
                                content.clear();
                            }
                            if (!temporary.isEmpty()) {
                                List<WordInFile> modifiers = new ArrayList<WordInFile>();
                                modifiers.addAll(temporary);
                                temporary.clear();
                                foundClass = new JavaClass(wordList.get(i + 1).getWord(), null, word.getKey(),
                                        modifiers, new ArrayList<String>(), new ArrayList<String>());
                            } else {
                                foundClass = new JavaClass(wordList.get(i + 1).getWord(), null, word.getKey(),
                                        new ArrayList<WordInFile>(), new ArrayList<String>(), new ArrayList<String>());
                            }
                            i++;
                        } else if ((word.equals(KeyWord.WORD) && !word.getWord().isEmpty())
                                || !word.equals(KeyWord.WORD)) {
                            // otherwise add word to list (if not empty)
                            content.addAll(temporary);
                            temporary.clear();
                            content.add(word);
                        }
                        break;
                    // class/enum/interface keyword detected
                    case 1:
                        if (word.getKey().equals(KeyWord.OPENBRACE)) {
                            // class definition done
                            classDefinitionState = 4;
                            openBraces++;
                        } else if (word.getKey().equals(KeyWord.EXTENDS)) {
                            classDefinitionState++;
                        } else if (word.getKey().equals(KeyWord.IMPLEMENTS)) {
                            classDefinitionState++;
                            classDefinitionState++;
                        }
                        break;
                    // in extends list
                    case 2:
                        if (word.getKey().equals(KeyWord.IMPLEMENTS)) {
                            if (!typeString.isEmpty()) {
                                foundClass.getExtending().add(typeString);
                                typeString = "";
                            }
                            classDefinitionState++;
                        } else if (word.getKey().equals(KeyWord.WORD)) {
                            typeString += word.getWord();
                            if (wordList.get(i + 1).getKey().equals(KeyWord.LESS)) {
                                // extended type is some generic stuff like List<String>
                                i++;
                                int endOfGeneric = ModelBuildHelper.parseGeneric(wordList, i + 1);
                                while (i < endOfGeneric) {
                                    i++;
                                }
                            }
                        } else if (word.getKey().equals(KeyWord.DOT)) {
                            typeString += ".";
                        } else if (word.getKey().equals(KeyWord.COMMA)) {
                            if (!typeString.isEmpty()) {
                                foundClass.getExtending().add(typeString);
                                typeString = "";
                            }
                        } else if (word.getKey().equals(KeyWord.OPENBRACE)) {
                            // class definition done
                            if (!typeString.isEmpty()) {
                                foundClass.getExtending().add(typeString);
                                typeString = "";
                            }
                            classDefinitionState = 4;
                            openBraces++;
                        }
                        break;
                    // in implements list
                    case 3:
                        if (word.getKey().equals(KeyWord.EXTENDS)) {
                            if (!typeString.isEmpty()) {
                                foundClass.getImplementing().add(typeString);
                                typeString = "";
                            }
                            classDefinitionState--;
                        } else if (word.getKey().equals(KeyWord.WORD)) {
                            typeString += word.getWord();
                            if (wordList.get(i + 1).getKey().equals(KeyWord.LESS)) {
                                // implemented type is some generic stuff like List<String>
                                i++;
                                int endOfGeneric = ModelBuildHelper.parseGeneric(wordList, i + 1);
                                while (i < endOfGeneric) {
                                    i++;
                                }
                            }
                        } else if (word.getKey().equals(KeyWord.DOT)) {
                            typeString += ".";
                        } else if (word.getKey().equals(KeyWord.COMMA)) {
                            if (!typeString.isEmpty()) {
                                foundClass.getImplementing().add(typeString);
                                typeString = "";
                            }
                        } else if (word.getKey().equals(KeyWord.OPENBRACE)) {
                            // class definition done
                            if (!typeString.isEmpty()) {
                                foundClass.getImplementing().add(typeString);
                                typeString = "";
                            }
                            classDefinitionState++;
                            openBraces++;
                        }
                        break;
                    // in class body
                    case 4:
                        if (word.getKey().equals(KeyWord.CLOSEBRACE)) {
                            openBraces--;
                            if (openBraces == 0) {
                                // end of class body, add to class and result
                                classDefinitionState = 0;
                                List<WordInFile> classcontent = new ArrayList<WordInFile>();
                                classcontent.addAll(content);
                                foundClass.setContent(Arrays.asList(new WordList(classcontent)));
                                fileModel.add(foundClass);
                                foundClass = null;
                                content.clear();
                            } else {
                                content.add(word);
                            }
                        } else {
                            // add word to class-body
                            if (word.getKey().equals(KeyWord.OPENBRACE)) {
                                openBraces++;
                            }
                            content.add(word);
                        }
                        break;
                    }
                }
            }
            if (!content.isEmpty()) {
                fileModel.add(new WordList(content));
            }
            return fileModel;
        } catch (Exception e) {
            throw new ParsingException("Building base model (outer class structure) failed.");
        }
    }
    
    /**
     * Parsing the inner structure of a class/enum/interface/method determined by methods and inner
     * classes/interfaces/enums. Is called recursive for found methods/interfaces/classes/enums.
     * 
     * @param parent - the model-component to parse inner structure
     * @throws ParsingException 
     * @returns model of the parent-content structured by classes and methods
     */
    private static List<JavaFileContent> parseClassContent(JavaFileContent parent) throws ParsingException {
        try {
            List<JavaFileContent> result = new ArrayList<JavaFileContent>();
            JavaFileContent content = null;
            if (parent.getContent() != null && !parent.getContent().isEmpty()) {
                content = parent.getContent().get(0);
            }
            KeyWord parenttype = null;
            if (parent instanceof JavaClass) {
                parenttype = ((JavaClass) parent).getType();
            } else if (parent instanceof JavaStatementWithAnonymousClass) {
                parenttype = KeyWord.CLASS;
            }
            if (content instanceof WordList) {
                if (parenttype != null && parenttype.equals(KeyWord.ENUM)) {
                    // parse enumvalues
                    content = parseEnumValues(result, ((WordList) content).getWordlist());
                }
                // parse methods and classes in Wordlist
                if (content != null) {
                    boolean abstractClass = false;
                    if (parent instanceof JavaClass && parenttype == KeyWord.CLASS
                            && ((JavaClass) parent).getModifiers().contains(new WordInFile(null, KeyWord.ABSTRACT))) {
                        // for parsing abstract classes like interfaces (methodheaders without body)
                        abstractClass = true;
                    }
                    for (JavaFileContent classcontent : parseMethodsAndClasses(((WordList) content).getWordlist(),
                            parenttype, abstractClass)) {
                        if (classcontent instanceof JavaClass || classcontent instanceof JavaMethod
                                || classcontent instanceof JavaStatementWithAnonymousClass) {
                            classcontent.setContent(parseClassContent(classcontent));
                        }
                        result.add(classcontent);
                    }
                }
            } else if (content == null) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            throw new ParsingException("Parsing inner class / method structure failed.");
        }
    }

    /**
     * Parsing the content of a class or method for inner methods and classes.
     * 
     * @param wordlist - the class/method body as word-list
     * @param parenttype - enum/class/interface if parsing JavaClass
     * @param abstractClass - true if parsing body of an abstract class
     * @returns model of the class/method-body
     */
    private static List<JavaFileContent> parseMethodsAndClasses(List<WordInFile> wordlist, KeyWord parenttype,
            boolean abstractClass) {
        List<JavaFileContent> result = new ArrayList<JavaFileContent>();
        int openBraces = 0;
        int methodDetectionState = 0;
        int classDefinitionState = 0;
        JavaClass foundClass = null;
        String typeString = "";
        String maybeMethodName = null;
        List<WordInFile> maybeReturnType = new ArrayList<WordInFile>();
        List<WordInFile> modifiers = new ArrayList<WordInFile>();
        List<WordInFile> content = new ArrayList<WordInFile>();
        List<WordInFile> temporary = new ArrayList<WordInFile>();
        List<JavaVariable> parameter = new ArrayList<JavaVariable>();
        JavaMethod method = null;
        for (int i = 0; i < wordlist.size(); i++) {
            WordInFile word = wordlist.get(i);
            if (classDefinitionState > 0) {
                switch (classDefinitionState) {
                // class/enum/interface keyword detected
                case 1:
                    if (word.getKey().equals(KeyWord.OPENBRACE)) {
                        // class definition done
                        classDefinitionState = 4;
                        openBraces++;
                    } else if (word.getKey().equals(KeyWord.EXTENDS)) {
                        classDefinitionState++;
                    } else if (word.getKey().equals(KeyWord.IMPLEMENTS)) {
                        classDefinitionState++;
                        classDefinitionState++;
                    }
                    break;
                // in extends list
                case 2:
                    if (word.getKey().equals(KeyWord.IMPLEMENTS)) {
                        if (!typeString.isEmpty()) {
                            foundClass.getExtending().add(typeString);
                            typeString = "";
                        }
                        classDefinitionState++;
                    } else if (word.getKey().equals(KeyWord.WORD)) {
                        typeString += word.getWord();
                        if (wordlist.get(i + 1).getKey().equals(KeyWord.LESS)) {
                            // extended type is some generic stuff like List<String>
                            i++;
                            int endOfGeneric = ModelBuildHelper.parseGeneric(wordlist, i + 1);
                            while (i < endOfGeneric) {
                                i++;
                            }
                        }
                    } else if (word.getKey().equals(KeyWord.DOT)) {
                        typeString += ".";
                    } else if (word.getKey().equals(KeyWord.COMMA)) {
                        if (!typeString.isEmpty()) {
                            foundClass.getExtending().add(typeString);
                            typeString = "";
                        }
                    } else if (word.getKey().equals(KeyWord.OPENBRACE)) {
                        // class definition done
                        if (!typeString.isEmpty()) {
                            foundClass.getExtending().add(typeString);
                            typeString = "";
                        }
                        classDefinitionState = 4;
                        openBraces++;
                    }
                    break;
                // in implements list
                case 3:
                    if (word.getKey().equals(KeyWord.EXTENDS)) {
                        if (!typeString.isEmpty()) {
                            foundClass.getImplementing().add(typeString);
                            typeString = "";
                        }
                        classDefinitionState--;
                    } else if (word.getKey().equals(KeyWord.WORD)) {
                        typeString += word.getWord();
                        if (wordlist.get(i + 1).getKey().equals(KeyWord.LESS)) {
                            // implemented type is some generic stuff like List<String>
                            i++;
                            int endOfGeneric = ModelBuildHelper.parseGeneric(wordlist, i + 1);
                            while (i < endOfGeneric) {
                                i++;
                            }
                        }
                    } else if (word.getKey().equals(KeyWord.DOT)) {
                        typeString += ".";
                    } else if (word.getKey().equals(KeyWord.COMMA)) {
                        if (!typeString.isEmpty()) {
                            foundClass.getImplementing().add(typeString);
                            typeString = "";
                        }
                    } else if (word.getKey().equals(KeyWord.OPENBRACE)) {
                        // class definition done
                        if (!typeString.isEmpty()) {
                            foundClass.getImplementing().add(typeString);
                            typeString = "";
                        }
                        classDefinitionState++;
                        openBraces++;
                    }
                    break;
                // in class body
                case 4:
                    if (word.getKey().equals(KeyWord.CLOSEBRACE)) {
                        openBraces--;
                        if (openBraces == 0) {
                            // end of class body, add to class and result
                            classDefinitionState = 0;
                            List<WordInFile> classcontent = new ArrayList<WordInFile>();
                            classcontent.addAll(content);
                            foundClass.setContent(Arrays.asList(new WordList(classcontent)));
                            result.add(foundClass);
                            foundClass = null;
                            content.clear();
                        } else {
                            content.add(word);
                        }
                    } else {
                        // add word to class-body
                        if (word.getKey().equals(KeyWord.OPENBRACE)) {
                            openBraces++;
                        }
                        content.add(word);
                    }
                    break;
                }
            } else {
                switch (methodDetectionState) {
                case 0:
                    // Nothing or only modifiers detected
                    if ((parenttype != null) && !parenttype.equals(KeyWord.ENUM) && word.equals(KeyWord.ANNOTATION)) {
                        // Annotation placeholder outside of method or enum definition found. Add as Statement
                        content.addAll(temporary);
                        temporary.clear();
                        if (!content.isEmpty()) {
                            List<WordInFile> somecontent = new ArrayList<WordInFile>();
                            somecontent.addAll(content);
                            result.add(new WordList(somecontent));
                            content.clear();
                        }
                        result.add(new JavaStatement(StatementType.ANNOTATION));
                    } else if (word.getKey().getType().equals(WordType.MODIFIER)
                            || word.getKey().equals(KeyWord.SYNCHRONIZED)) {
                        // add modifier to potential header
                        temporary.add(word);
                        modifiers.add(word);
                    } else if ((i == 0 || !wordlist.get(i - 1).equals(KeyWord.DOT))
                            && (word.getKey().getType().equals(WordType.DECLARATOR))) {
                        // class/enum/interface/annotation declaration detected. parse it to JavaClassModel
                        classDefinitionState++;
                        if (!content.isEmpty()) {
                            List<WordInFile> someContent = new ArrayList<WordInFile>();
                            someContent.addAll(content);
                            result.add(new WordList(someContent));
                            content.clear();
                        }
                        if (!temporary.isEmpty()) {
                            List<WordInFile> classmodifiers = new ArrayList<WordInFile>();
                            classmodifiers.addAll(temporary);
                            temporary.clear();
                            foundClass = new JavaClass(wordlist.get(i + 1).getWord(), null, word.getKey(),
                                    classmodifiers, new ArrayList<String>(), new ArrayList<String>());
                        } else {
                            foundClass = new JavaClass(wordlist.get(i + 1).getWord(), null, word.getKey(),
                                    new ArrayList<WordInFile>(), new ArrayList<String>(), new ArrayList<String>());
                        }
                        i++;
                    } else if (word.getKey().equals(KeyWord.NEW)) {
                        // no method or class header, maybe statement with anonymous class
                        maybeMethodName = null;
                        maybeReturnType.clear();
                        modifiers.clear();
                        methodDetectionState = 0;
                        content.addAll(temporary);
                        temporary.clear();
                        int start = i;
                        List<WordInFile> anonymousClassType = new ArrayList<WordInFile>();
                        i++;
                        if (wordlist.get(i).getKey().equals(KeyWord.WORD)) {
                            // detect type of anonymous class. can't be something else than a word...
                            anonymousClassType.add(wordlist.get(i));
                            if (wordlist.get(i + 1).getKey().equals(KeyWord.LESS)) {
                                // type is some generic stuff like List<String>
                                i++;
                                int endOfGeneric = ModelBuildHelper.parseGeneric(wordlist, i + 1);
                                while (i < endOfGeneric) {
                                    anonymousClassType.add(wordlist.get(i));
                                    i++;
                                }
                                anonymousClassType.add(wordlist.get(i));
                                i++;
                            }
                            if (wordlist.get(i).getKey().equals(KeyWord.OPENPARANTHESE)) {
                                int openParantheses = 1;
                                anonymousClassType.add(wordlist.get(i));
                                while (openParantheses > 0) {
                                    i++;
                                    if (wordlist.get(i).getKey().equals(KeyWord.OPENPARANTHESE)) {
                                        openParantheses++;
                                    } else if (wordlist.get(i).getKey().equals(KeyWord.CLOSPARANTHESE)) {
                                        openParantheses--;
                                    }
                                    anonymousClassType.add(wordlist.get(i));
                                }
                                i++;
                                if (wordlist.get(i).getKey().equals(KeyWord.OPENBRACE)) {
                                    // anonymous class in statement found....
                                    int openBracesOfClass = 1;
                                    List<WordInFile> statementbefore = new ArrayList<WordInFile>();
                                    boolean finished = false;
                                    int position = content.size() - 1;
                                    int openParanthesesInStatement = 0;
                                    while (!finished && position >= 0) {
                                        if (content.get(position).equals(KeyWord.OPENBRACE)
                                                || content.get(position).equals(KeyWord.CLOSEBRACE)
                                                || content.get(position).equals(KeyWord.SEMICOLON)) {
                                            finished = true;
                                        } else {
                                            if (content.get(position).equals(KeyWord.OPENPARANTHESE)) {
                                                openParanthesesInStatement++;
                                            } else if (content.get(position).equals(KeyWord.CLOSPARANTHESE)) {
                                                openParanthesesInStatement--;
                                            }
                                            statementbefore.add(content.get(position));
                                            content.remove(position);
                                            position--;
                                        }
                                    }
                                    Collections.reverse(statementbefore);
                                    if (!content.isEmpty()) {
                                        List<WordInFile> somecontent = new ArrayList<WordInFile>();
                                        somecontent.addAll(content);
                                        result.add(new WordList(somecontent));
                                        content.clear();
                                    }
                                    JavaStatementWithAnonymousClass anoclass = new JavaStatementWithAnonymousClass(
                                            statementbefore, anonymousClassType);
                                    List<WordInFile> classBody = new ArrayList<WordInFile>();
                                    while (openBracesOfClass > 0) {
                                        i++;
                                        if (wordlist.get(i).getKey().equals(KeyWord.OPENBRACE)) {
                                            openBracesOfClass++;
                                        } else if (wordlist.get(i).getKey().equals(KeyWord.CLOSEBRACE)) {
                                            openBracesOfClass--;
                                        }
                                        if (openBracesOfClass > 0) {
                                            classBody.add(wordlist.get(i));
                                        }
                                    }
                                    anoclass.setContent(Arrays.asList(new WordList(classBody)));
                                    finished = false;
                                    List<WordInFile> statementafter = new ArrayList<WordInFile>();
                                    while (!finished && i < wordlist.size()) {
                                        i++;
                                        if (openParanthesesInStatement > 0) {
                                            if (wordlist.get(i).equals(KeyWord.OPENPARANTHESE)) {
                                                openParanthesesInStatement++;
                                            } else if (wordlist.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                                                openParanthesesInStatement--;
                                            }
                                        } else {
                                            if (wordlist.get(i).equals(KeyWord.OPENPARANTHESE)) {
                                                openParanthesesInStatement++;
                                            } else if (wordlist.get(i).equals(KeyWord.SEMICOLON)) {
                                                finished = true;
                                            }
                                        }
                                        statementafter.add(wordlist.get(i));
                                    }
                                    anoclass.setStatementAfterClass(statementafter);
                                    result.add(anoclass);
                                } else {
                                    // no anonymous class
                                    i = start;
                                    content.add(word);
                                }
                            } else {
                                // no anonymous class
                                i = start;
                                content.add(word);
                            }
                        } else {
                            // no anonymous class
                            i = start;
                            content.add(word);
                        }
                    } else if ((parenttype != null) && (word.getKey().equals(KeyWord.WORD)
                            || word.getKey().getType().equals(WordType.DATATYPE))) {
                        // add potential returntype to potential header
                        temporary.add(word);
                        maybeReturnType.add(word);
                        if (word.getKey().equals(KeyWord.WORD)) {
                            maybeMethodName = word.getWord();
                        }
                        methodDetectionState++;
                    } else {
                        // mo method header! reset
                        content.addAll(temporary);
                        content.add(word);
                        temporary.clear();
                        modifiers.clear();
                    }
                    break;
                case 1:
                    // modifiers and returntype or constructor detected
                    if (word.getKey().equals(KeyWord.OPENPARANTHESE)) {
                        // potential constructor
                        maybeReturnType.clear();
                        temporary.add(word);
                        methodDetectionState++;
                        methodDetectionState++;
                    } else if (word.getKey().equals(KeyWord.DOT) && wordlist.get(i + 1).getKey().equals(KeyWord.WORD)) {
                        // potentially dot in returntype
                        temporary.add(word);
                        temporary.add(wordlist.get(i + 1));
                        maybeReturnType.add(word);
                        maybeReturnType.add(wordlist.get(i + 1));
                        i++;
                    } else if (word.getKey().equals(KeyWord.WORD)
                            && wordlist.get(i + 1).getKey().equals(KeyWord.OPENPARANTHESE)) {
                        // add potential methodname and open parentheses to potential header
                        temporary.add(word);
                        temporary.add(wordlist.get(i + 1));
                        maybeMethodName = word.getWord();
                        methodDetectionState++;
                        methodDetectionState++;
                        i++;
                    } else if (word.getKey().equals(KeyWord.OPENBRACKET)
                            && wordlist.get(i + 1).getKey().equals(KeyWord.CLOSEBRACKET)) {
                        // returntype is an array
                        temporary.add(word);
                        temporary.add(wordlist.get(i + 1));
                        maybeReturnType.add(word);
                        maybeReturnType.add(wordlist.get(i + 1));
                        methodDetectionState++;
                        i++;
                    } else if (word.getKey().equals(KeyWord.LESS)) {
                        // returntype is some generic stuff like List<String>
                        int endOfGeneric = ModelBuildHelper.parseGeneric(wordlist, i + 1);
                        if (endOfGeneric == 0) {
                            // no valid generic returnType --> no method header! --> reset and check word again
                            i--;
                            modifiers.clear();
                            maybeMethodName = null;
                            maybeReturnType.clear();
                            methodDetectionState = 0;
                            content.addAll(temporary);
                            temporary.clear();
                        } else {
                            while (i < endOfGeneric) {
                                temporary.add(wordlist.get(i));
                                maybeReturnType.add(wordlist.get(i));
                                i++;
                            }
                            maybeReturnType.add(wordlist.get(i));
                            temporary.add(wordlist.get(i));
                            methodDetectionState++;
                        }
                    } else {
                        // no method header! reset and check word again
                        i--;
                        maybeMethodName = null;
                        maybeReturnType.clear();
                        modifiers.clear();
                        methodDetectionState = 0;
                        content.addAll(temporary);
                        temporary.clear();
                    }
                    break;
                case 2:
                    // modifiers and extended returntype detected ([] / <>)
                    if (word.getKey().equals(KeyWord.WORD)
                            && wordlist.get(i + 1).getKey().equals(KeyWord.OPENPARANTHESE)) {
                        // add potential methodname and open parentheses to potential header
                        temporary.add(word);
                        temporary.add(wordlist.get(i + 1));
                        maybeMethodName = word.getWord();
                        methodDetectionState++;
                        i++;
                    } else {
                        // no method header! reset and check word again
                        i--;
                        maybeMethodName = null;
                        maybeReturnType.clear();
                        modifiers.clear();
                        methodDetectionState = 0;
                        content.addAll(temporary);
                        temporary.clear();
                    }
                    break;
                case 3:
                    // modifiers, returntype, methodname and open paranthese detected
                    WordInFile param = ModelBuildHelper.isVariableDeclaration(wordlist.subList(i, wordlist.size() - 1), false);
                    if (param != null) {
                        // add potential parameter to potential methodheader
                        List<WordInFile> paramtype = new ArrayList<WordInFile>();
                        while (!wordlist.get(i).equals(param)) {
                            paramtype.add(wordlist.get(i));
                            temporary.add(wordlist.get(i));
                            i++;
                        }
                        temporary.add(wordlist.get(i));
                        parameter.add(new JavaVariable(param.getWord(), paramtype));
                        methodDetectionState++;
                    } else if (word.getKey().equals(KeyWord.CLOSPARANTHESE)) {
                        // end of parameter list, add to potential header
                        temporary.add(word);
                        methodDetectionState++;
                        methodDetectionState++;
                    } else {
                        // no method header! reset and check word again
                        i--;
                        methodDetectionState = 0;
                        maybeReturnType.clear();
                        modifiers.clear();
                        maybeMethodName = null;
                        content.addAll(temporary);
                        temporary.clear();
                    }
                    break;
                case 4:
                    // modifiers, returntype, methodname, open paranthese and at least one parameter detected
                    WordInFile secondaryparam = ModelBuildHelper.isVariableDeclaration(wordlist.subList(i, wordlist.size() - 1),
                            true);
                    if (secondaryparam != null) {
                        // add potential parameter to potential methodheader
                        List<WordInFile> paramtype = new ArrayList<WordInFile>();
                        // skip ","
                        i++;
                        while (!wordlist.get(i).equals(secondaryparam)) {
                            paramtype.add(wordlist.get(i));
                            temporary.add(wordlist.get(i));
                            i++;
                        }
                        temporary.add(wordlist.get(i));
                        parameter.add(new JavaVariable(secondaryparam.getWord(), paramtype));
                    } else if (word.getKey().equals(KeyWord.CLOSPARANTHESE)) {
                        // end of parameter list, add to potential header
                        temporary.add(word);
                        methodDetectionState++;
                    } else {
                        // no method header! reset and check word again
                        i--;
                        methodDetectionState = 0;
                        maybeMethodName = null;
                        maybeReturnType.clear();
                        modifiers.clear();
                        parameter.clear();
                        content.addAll(temporary);
                        temporary.clear();
                    }
                    break;
                case 5:
                    // modifiers, returntype, methodname, and parameter list detected
                    if (word.getKey().equals(KeyWord.THROWS) || word.getKey().equals(KeyWord.WORD)
                            || word.getKey().equals(KeyWord.COMMA)) {
                        // speciefing stuff like extends / throws / implements detected
                        temporary.add(word);
                    } else if ((parenttype.equals(KeyWord.INTERFACE) || abstractClass)
                            && word.getKey().equals(KeyWord.SEMICOLON)) {
                        // method interface...
                        if (!content.isEmpty()) {
                            List<WordInFile> somecontent = new ArrayList<WordInFile>();
                            somecontent.addAll(content);
                            result.add(new WordList(somecontent));
                            content.clear();
                        }
                        List<WordInFile> methodmodifiers = new ArrayList<WordInFile>();
                        if (!modifiers.isEmpty()) {
                            methodmodifiers.addAll(modifiers);
                        }
                        List<WordInFile> methodreturntype = new ArrayList<WordInFile>();
                        if (!maybeReturnType.isEmpty()) {
                            methodreturntype.addAll(maybeReturnType);
                        }
                        List<JavaVariable> params = new ArrayList<JavaVariable>();
                        params.addAll(parameter);
                        method = new JavaMethod(maybeMethodName, methodreturntype, methodmodifiers, params, null);
                        maybeReturnType.clear();
                        modifiers.clear();
                        maybeMethodName = null;
                        parameter.clear();
                        result.add(method);
                        method = null;
                        methodDetectionState = 0;
                        temporary.clear();
                    } else if (word.getKey().equals(KeyWord.OPENBRACE)) {
                        // valid method header. add all before as ClassContent to result.
                        if (!content.isEmpty()) {
                            List<WordInFile> somecontent = new ArrayList<WordInFile>();
                            somecontent.addAll(content);
                            result.add(new WordList(somecontent));
                            content.clear();
                        }
                        // reset temporary values
                        openBraces++;
                        methodDetectionState++;
                        List<JavaVariable> params = new ArrayList<JavaVariable>();
                        params.addAll(parameter);
                        List<WordInFile> methodmodifiers = new ArrayList<WordInFile>();
                        if (!modifiers.isEmpty()) {
                            methodmodifiers.addAll(modifiers);
                        }
                        List<WordInFile> methodreturntype = new ArrayList<WordInFile>();
                        if (!maybeReturnType.isEmpty()) {
                            methodreturntype.addAll(maybeReturnType);
                        }
                        method = new JavaMethod(maybeMethodName, methodreturntype, methodmodifiers, params, null);
                        maybeMethodName = null;
                        maybeReturnType.clear();
                        modifiers.clear();
                        parameter.clear();
                        temporary.clear();
                    } else {
                        // no method header! reset and check word again
                        i--;
                        methodDetectionState = 0;
                        maybeMethodName = null;
                        maybeReturnType.clear();
                        modifiers.clear();
                        parameter.clear();
                        content.addAll(temporary);
                        temporary.clear();
                    }
                    break;
                case 6:
                    // valid method header detected, collect words in body
                    if (word.getKey().equals(KeyWord.CLOSEBRACE)) {
                        openBraces--;
                        if (openBraces == 0) {
                            // end of method body, add to method and result
                            methodDetectionState = 0;
                            List<WordInFile> methodcontent = new ArrayList<WordInFile>();
                            methodcontent.addAll(content);
                            method.setContent(Arrays.asList(new WordList(methodcontent)));
                            result.add(method);
                            method = null;
                            content.clear();
                        } else {
                            content.add(word);
                        }
                    } else {
                        // add word to method-body
                        if (word.getKey().equals(KeyWord.OPENBRACE)) {
                            openBraces++;
                        }
                        content.add(word);
                    }
                    break;
                }
            }
        }
        if (!temporary.isEmpty()) {
            content.addAll(temporary);
        }
        if (!content.isEmpty()) {
            result.add(new WordList(content));
        }
        return result;
    }

    /**
     * Parsing values of an enum.
     * 
     * @param result - the body-content of the enum, to which the value-list gets added
     * @param wordlist - enum body as word list to parse
     * @returns enum body after value-list as word-list
     */
    private static JavaFileContent parseEnumValues(List<JavaFileContent> result, List<WordInFile> wordlist) {
        JavaEnumValues values = new JavaEnumValues(new ArrayList<List<WordInFile>>());
        WordList offcut = null;
        List<WordInFile> otherContent = new ArrayList<WordInFile>();
        boolean endOfValues = false;
        for (int i = 0; i < wordlist.size(); i++) {
            if (!endOfValues) {
                boolean endOfValue = false;
                List<WordInFile> value = new ArrayList<WordInFile>();
                int openParantheses = 0;
                while (!endOfValue) {
                    if (i == wordlist.size()) {
                        // end of enum reached
                        values.getValues().add(value);
                        endOfValue = true;
                        endOfValues = true;
                    } else {
                        // count open parantheses to avoid counting an comma inside a value as valueseparator
                        if (wordlist.get(i).equals(KeyWord.OPENPARANTHESE)) {
                            openParantheses++;
                        } else if (wordlist.get(i).equals(KeyWord.CLOSPARANTHESE)) {
                            openParantheses--;
                        }
                        if (openParantheses == 0 && (wordlist.get(i).equals(KeyWord.COMMA)
                                || wordlist.get(i).equals(KeyWord.SEMICOLON))) {
                            // end of value reached
                            endOfValue = true;
                            values.getValues().add(value);
                            if (wordlist.get(i).equals(KeyWord.SEMICOLON)) {
                                // end of valuelist reached
                                endOfValues = true;
                            }
                        } else {
                            // word of value detected
                            value.add(wordlist.get(i));
                            i++;
                        }
                    }
                }
            } else {
                otherContent.add(wordlist.get(i));
            }
        }
        result.add(values);
        if (!otherContent.isEmpty()) {
            offcut = new WordList(otherContent);
        }
        return offcut;
    }
}

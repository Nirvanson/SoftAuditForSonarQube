package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plugin.model.JavaClass;
import plugin.model.JavaFileContent;
import plugin.model.JavaMethod;
import plugin.model.JavaStatement;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.model.KeyWord;
import plugin.model.StatementType;
import plugin.model.WordType;

public class JavaFileNormalizer {
	/**
	 * Prepares File for parsing. remove comments, empty lines, leading
	 * spaces....
	 * 
	 * @param file
	 *            - the file to analyze
	 * @returns List of relevant lines
	 * @throws IOException
	 */
	public List<String> prepareFile(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String fileline;
		boolean isLiteralA = false;
		boolean isLiteralB = false;
		boolean blockComment = false;
		while ((fileline = br.readLine()) != null) {
			// remove leading and ending spaces
			String line = fileline.trim();
			// initialize empty line
			String newLine = "";
			// split line in chars
			String[] chars = line.split("");
			// for each char
			for (int i = 0; i < chars.length; i++) {
				String c = chars[i];
				if (!blockComment && !isLiteralA && !isLiteralB) {
					switch (c) {
					case "\"":
						if (chars.length > i + 1 && chars[i + 1].equals("\"")) {
							// empty literal - ignore
							i++;
						} else {
							// beginning of a "literal", add " as placeholder to
							// new line
							isLiteralA = true;
							newLine += '"';
						}
						break;
					case "'":
						if (chars.length > i + 1 && chars[i + 1].equals("'")) {
							// empty literal - ignore
							i++;
						} else {
							// beginning of a 'literal', add ' as placeholder to
							// new line
							isLiteralB = true;
							newLine += "'";
						}
						break;
					case "/":
						if (chars.length > i + 1 && chars[i + 1].equals("/")) {
							// begin of an inline comment. skip rest of line
							i = chars.length;
						} else if (chars.length > i + 1 && chars[i + 1].equals("*")) {
							// begin of an blockcomment. skip till endsequence
							blockComment = true;
							i++;
						} else {
							// false alarm. add character to new line
							newLine += c;
						}
						break;
					default:
						// just a simple character. Add to new line
						newLine += c;
					}
				} else if (blockComment) {
					// detect end of blockcomment and add " " instead of it (for
					// things like "public/*comment*/String...")
					if (c.equals("*") && chars.length > i + 1 && chars[i + 1].equals("/")) {
						i++;
						newLine += " ";
						blockComment = false;
					}
				} else if (isLiteralA || isLiteralB) {
					// detect escaped characters in Literals and skip them
					if (c.equals("\\")) {
						i++;
					} else {
						// detect end of literals
						if (isLiteralA) {
							if (c.equals("\"")) {
								isLiteralA = false;
							}
						} else if (isLiteralB) {
							if (c.equals("'")) {
								isLiteralB = false;
							}
						}
					}
				}
			}
			// remove leading and ending spaces from new line (maybe caused by
			// block-comment replacement or inline-comment removement)
			String resultLine = newLine.trim();
			// remove unnecessary spaces
			while (resultLine.contains("  ")) {
				resultLine = resultLine.replaceAll("  ", " ");
			}
			// add non empty lines to result
			if (!resultLine.isEmpty()) {
				result.add(resultLine);
			}
		}
		br.close();
		return result;
	}

	/**
	 * Converts relevant lines to single normalized code String for pattern
	 * recognition
	 * 
	 * @param lines
	 *            - List of relevant Lines
	 * @returns normalized Code-String
	 */
	public String convertToSingleString(List<String> lines) {
		String fullCode = "";
		for (String line : lines) {
			// add normalized line with space before new line
			fullCode += (line + " ");
		}
		// remove spaces before and after brackets
		String normalizedCode = "";
		List<String> breaks = Arrays.asList("\"", "'", "(", ")", "{", "}", "[", "]", "+", "-", "*", "/", "%", "<", ">",
				"=", "!", "~", "&", "|", "^", "?", ":", ",", ";", ".");
		String[] chars = fullCode.split("");
		for (int i = 0; i < chars.length; i++) {
			if (chars[i].equals(" ") && chars.length > i + 1 && breaks.contains(chars[i + 1])) {
				// skip space before break-characters
				continue;
			}
			normalizedCode += chars[i];
			if (breaks.contains(chars[i]) && chars.length > i + 1 && chars[i + 1].equals(" ")) {
				// skip space after break-characters
				i++;
			}
		}
		return normalizedCode;
	}

	/**
	 * Splits file into words, (braces, semicolons, operators , ... count as
	 * words!)
	 * 
	 * @param normalizedCode
	 *            - Code as single normalized Line
	 * @returns word-list
	 */
	public List<WordInFile> createJavaWordList(String normalizedCode) {
		// characters in breaks and operators between words are always a single
		// word
		List<String> breaks = Arrays.asList("\"", "'", "(", ")", "{", "}", "[", "]", "?", ":", ",", ";", "@", ".", "+",
				"-", "*", "/", "%", "<", ">", "=", "!", "~", "&", "|", "^");
		List<WordInFile> step1 = new ArrayList<WordInFile>();
		String[] chars = normalizedCode.split("");
		String word = "";
		for (int i = 0; i < chars.length; i++) {
			// if previous char was an operator
			if (breaks.contains(chars[i])) {
				if (!word.isEmpty()) {
					// add previous word to list if not empty
					step1.add(KeyWord.findKeyword(word));
					word = "";
				}
				step1.add(KeyWord.findKeyword(chars[i]));
				// if char is break
			} else if (chars[i].equals(" ")) {
				// add previous word to list and reset
				step1.add(KeyWord.findKeyword(word));
				word = "";
			} else {
				// add char to current word
				word += chars[i];
			}
		}
		List<WordInFile> result = new ArrayList<WordInFile>();
		int annotationState = 0;
		int openParentheses = 0;
		for (int i = 0; i < step1.size(); i++) {
			WordInFile wordInStep1 = step1.get(i);
			// skip ; after } or ;
			if (!(i > 0 && (step1.get(i - 1).equals(KeyWord.CLOSEBRACE) || step1.get(i - 1).equals(KeyWord.SEMICOLON))
					&& wordInStep1.equals(KeyWord.SEMICOLON))) {
				switch (annotationState) {
				case 0:
					if (wordInStep1.equals(KeyWord.ANNOTATION)) {
						// start of annotation. keep "@" as placeholder
						result.add(wordInStep1);
						annotationState++;
					} else if ((wordInStep1.equals(KeyWord.WORD) && !wordInStep1.getWord().isEmpty())
							|| !wordInStep1.equals(KeyWord.WORD)) {
						// otherwise add word to list (if not empty)
						result.add(wordInStep1);
					}
					break;
				case 1:
					if (wordInStep1.equals(KeyWord.INTERFACE)) {
						// annotation declaration. keep it!
						annotationState = 0;
						result.add(wordInStep1);
					} else {
						// annotation started by @ followed by free word
						annotationState++;
					}
					break;
				case 2:
					if (wordInStep1.equals(KeyWord.OPENPARANTHESE)) {
						// annotation has parentheses behind name
						annotationState++;
						openParentheses++;
					} else {
						// otherwise it ended, check word again
						annotationState = 0;
						i--;
					}
					break;
				case 3:
					if (wordInStep1.equals(KeyWord.OPENPARANTHESE)) {
						// Parentheses in Parentheses
						openParentheses++;
					} else if (wordInStep1.equals(KeyWord.CLOSPARANTHESE)) {
						// Parentheses closed
						openParentheses--;
						if (openParentheses == 0) {
							// if first parentheses closed it ended
							annotationState = 0;
						}
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Parsing the basic structure of a java class/interface/enum to model.
	 * package, imports, classdefinition, Annotations outside class definition
	 * 
	 * @param wordList - Java File as Words
	 * @returns basic model of the file
	 */
	public List<JavaFileContent> parseClassStructure(List<WordInFile> wordList) {
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
					List<JavaFileContent> statement = new ArrayList<JavaFileContent>();
					statement.addAll(content);
					fileModel.add(new JavaStatement(statement, StatementType.PACKAGE));
					content.clear();
				} else {
					content.add(word);
				}
			} else if (importStatement) {
				// in a import statement check if ending by ;
				if (word.equals(KeyWord.SEMICOLON)) {
					importStatement = false;
					content.add(word);
					List<JavaFileContent> statement = new ArrayList<JavaFileContent>();
					statement.addAll(content);
					fileModel.add(new JavaStatement(statement, StatementType.IMPORT));
					content.clear();
				} else {
					content.add(word);
				}
			} else {
				switch (classDefinitionState) {
				// only modifiers or nothing from class definition found
				case 0:
					if (word.equals(KeyWord.ANNOTATION)) {
						// Annotation placeholder outside of class definition
						// found. Add as Statement
						fileModel.add(new JavaStatement(Arrays.asList(word), StatementType.ANNOTATION));
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
					} else if (word.equals(KeyWord.CLASS) || word.equals(KeyWord.INTERFACE)
							|| word.equals(KeyWord.ENUM)) {
						// class/enum/interface declaration detected. parse it
						// to JavaClassModel - all the same
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
							foundClass = new JavaClass(wordList.get(i + 1).getWord(), null, word.getKey(), modifiers,
									new ArrayList<String>(), new ArrayList<String>());
						} else {
							foundClass = new JavaClass(wordList.get(i + 1).getWord(), null, word.getKey(),
									new ArrayList<WordInFile>(), new ArrayList<String>(), new ArrayList<String>());
						}
						i++;
					} else if ((word.equals(KeyWord.WORD) && !word.getWord().isEmpty()) || !word.equals(KeyWord.WORD)) {
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
							// extended type is some generic stuff like
							// List<String>
							i++;
							int endOfGeneric = parseGeneric(wordList, i + 1);
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
							// implemented type is some generic stuff like
							// List<String>
							i++;
							int endOfGeneric = parseGeneric(wordList, i + 1);
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
	}

	

	/**
	 * Parsing the content of a class or method for inner methods and classes
	 * 
	 * @param wordlist - the class/method boddy as wordlist
	 * @returns model of the class/method-body
	 */
	public List<JavaFileContent> parseMethodsAndClasses(List<WordInFile> wordlist) {
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		int openBraces = 0;
		int methodDetectionState = 0;
		int classDefinitionState = 0;
		JavaClass foundClass = null;
		String typeString = "";
		String maybeMethodName = null;
		List<WordInFile> content = new ArrayList<WordInFile>();
		List<WordInFile> temporary = new ArrayList<WordInFile>();
		List<String> parameter = new ArrayList<String>();
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
							// extended type is some generic stuff like
							// List<String>
							i++;
							int endOfGeneric = parseGeneric(wordlist, i + 1);
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
							// implemented type is some generic stuff like
							// List<String>
							i++;
							int endOfGeneric = parseGeneric(wordlist, i + 1);
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
					if (word.equals(KeyWord.ANNOTATION)) {
						// Annotation placeholder outside of method definition
						// found. Add as Statement
						content.addAll(temporary);
						temporary.clear();
						if (!content.isEmpty()) {
							List<WordInFile> somecontent = new ArrayList<WordInFile>();
							somecontent.addAll(content);
							result.add(new WordList(somecontent));
							content.clear();
						}
						result.add(new JavaStatement(Arrays.asList(word), StatementType.ANNOTATION));
					} else if (word.getKey().getType().equals(WordType.MODIFIER)
							|| word.getKey().equals(KeyWord.SYNCHRONIZED)) {
						// add modifier to potential header
						temporary.add(word);
					}
					if (word.equals(KeyWord.CLASS) || word.equals(KeyWord.INTERFACE) || word.equals(KeyWord.ENUM)) {
						// class/enum/interface declaration detected. parse it
						// to JavaClassModel - all the same
						classDefinitionState++;
						if (!content.isEmpty()) {
							List<WordInFile> someContent = new ArrayList<WordInFile>();
							someContent.addAll(content);
							result.add(new WordList(someContent));
							content.clear();
						}
						if (!temporary.isEmpty()) {
							List<WordInFile> modifiers = new ArrayList<WordInFile>();
							modifiers.addAll(temporary);
							temporary.clear();
							foundClass = new JavaClass(wordlist.get(i + 1).getWord(), null, word.getKey(), modifiers,
									new ArrayList<String>(), new ArrayList<String>());
						} else {
							foundClass = new JavaClass(wordlist.get(i + 1).getWord(), null, word.getKey(),
									new ArrayList<WordInFile>(), new ArrayList<String>(), new ArrayList<String>());
						}
						i++;
					} else if (word.getKey().equals(KeyWord.WORD)
							|| word.getKey().getType().equals(WordType.DATATYPE)) {
						// add potential returntype to potential header
						temporary.add(word);
						if (word.getKey().equals(KeyWord.WORD)) {
							maybeMethodName = word.getWord();
						}
						methodDetectionState++;
					} else {
						// mo method header! reset
						content.addAll(temporary);
						content.add(word);
						temporary.clear();
					}
					break;
				case 1:
					// modifiers and returntype or constructor detected
					if (word.getKey().equals(KeyWord.OPENPARANTHESE)) {
						// potential constructor
						temporary.add(word);
						methodDetectionState++;
						methodDetectionState++;
					} else if (word.getKey().equals(KeyWord.DOT) && wordlist.get(i + 1).getKey().equals(KeyWord.WORD)) {
						// potentially dot in returntype
						temporary.add(word);
						temporary.add(wordlist.get(i + 1));
						i++;
					} else if (word.getKey().equals(KeyWord.WORD)
							&& wordlist.get(i + 1).getKey().equals(KeyWord.OPENPARANTHESE)) {
						// add potential methodname and open parentheses to
						// potential header
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
						methodDetectionState++;
						i++;
					} else if (word.getKey().equals(KeyWord.LESS)) {
						// returntype is some generic stuff like List<String>
						int endOfGeneric = parseGeneric(wordlist, i + 1);
						if (endOfGeneric == 0) {
							// no valid generic returnType --> no method header!
							// reset and check word again
							i--;
							maybeMethodName = null;
							methodDetectionState = 0;
							content.addAll(temporary);
							temporary.clear();
						} else {
							while (i < endOfGeneric) {
								temporary.add(wordlist.get(i));
								i++;
							}
							temporary.add(wordlist.get(i));
							methodDetectionState++;
						}
					} else {
						// no method header! reset and check word again
						i--;
						maybeMethodName = null;
						methodDetectionState = 0;
						content.addAll(temporary);
						temporary.clear();
					}
					break;
				case 2:
					// modifiers and extended returntype detected ([] / <>)
					if (word.getKey().equals(KeyWord.WORD)
							&& wordlist.get(i + 1).getKey().equals(KeyWord.OPENPARANTHESE)) {
						// add potential methodname and open parentheses to
						// potential header
						temporary.add(word);
						temporary.add(wordlist.get(i + 1));
						maybeMethodName = word.getWord();
						methodDetectionState++;
						i++;
					} else {
						// no method header! reset and check word again
						i--;
						maybeMethodName = null;
						methodDetectionState = 0;
						content.addAll(temporary);
						temporary.clear();
					}
					break;
				case 3:
					// modifiers, returntype, methodname and open paranthese
					// detected
					WordInFile param = isParameter(wordlist.subList(i, wordlist.size() - 1), false);
					if (param != null) {
						// add potential parameter to potential methodheader
						while (!wordlist.get(i).equals(param)) {
							temporary.add(wordlist.get(i));
							i++;
						}
						temporary.add(wordlist.get(i));
						parameter.add(param.getWord());
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
						maybeMethodName = null;
						content.addAll(temporary);
						temporary.clear();
					}
					break;
				case 4:
					// modifiers, returntype, methodname, open paranthese and at
					// least one parameter detected
					WordInFile secondaryparam = isParameter(wordlist.subList(i, wordlist.size() - 1), true);
					if (secondaryparam != null) {
						// add potential parameter to potential methodheader
						while (!wordlist.get(i).equals(secondaryparam)) {
							temporary.add(wordlist.get(i));
							i++;
						}
						temporary.add(wordlist.get(i));
						parameter.add(secondaryparam.getWord());
					} else if (word.getKey().equals(KeyWord.CLOSPARANTHESE)) {
						// end of parameter list, add to potential header
						temporary.add(word);
						methodDetectionState++;
					} else {
						// no method header! reset and check word again
						i--;
						methodDetectionState = 0;
						maybeMethodName = null;
						parameter.clear();
						content.addAll(temporary);
						temporary.clear();
					}
					break;
				case 5:
					// modifiers, returntype, methodname, and parameter list
					// detected
					if (word.getKey().equals(KeyWord.THROWS) || word.getKey().equals(KeyWord.WORD)
							|| word.getKey().equals(KeyWord.COMMA)) {
						// speciefing stuff like extends / throws / implements
						// detected
						temporary.add(word);
					} else if (word.getKey().equals(KeyWord.OPENBRACE)) {
						// valid method header. add all before as ClassContent
						// to
						// result.
						if (!content.isEmpty()) {
							List<WordInFile> somecontent = new ArrayList<WordInFile>();
							somecontent.addAll(content);
							result.add(new WordList(somecontent));
							content.clear();
						}
						// reset temporary values
						openBraces++;
						methodDetectionState++;
						temporary.clear();
						List<String> params = new ArrayList<String>();
						params.addAll(parameter);
						method = new JavaMethod(maybeMethodName, params, null);
						maybeMethodName = null;
						parameter.clear();
					} else {
						// no method header! reset and check word again
						i--;
						methodDetectionState = 0;
						maybeMethodName = null;
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
							List<WordInFile> classcontent = new ArrayList<WordInFile>();
							classcontent.addAll(content);
							method.setContent(Arrays.asList(new WordList(classcontent)));
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
	 * checks if following words are valid parameter declaration
	 * 
	 * @param words
	 *            - wordlist beginning from first word after "(" or another
	 *            parameter
	 * @param withSeparator
	 *            - search for "," before parameter declaration
	 * @return the parameter identifier word
	 */
	private WordInFile isParameter(List<WordInFile> words, boolean withSeparator) {
		int position = 0;
		if (withSeparator) {
			if (!words.get(position).getKey().equals(KeyWord.COMMA)) {
				// separator "," is missing
				return null;
			} else {
				// correct separator ","
				position++;
			}
		}
		while (words.get(position).getKey().equals(KeyWord.ANNOTATION)) {
			// annotation on parameter
			position++;
		}
		if (words.get(position).getKey().equals(KeyWord.FINAL)) {
			// optional modifier final for parameter
			position++;
		}
		if (!(words.get(position).getKey().equals(KeyWord.WORD)
				|| words.get(position).getKey().getType().equals(WordType.DATATYPE))) {
			// no valid parameter type
			return null;
		} else {
			// valid parameter type beginning
			position++;
		}
		while (words.get(position).getKey().equals(KeyWord.DOT)) {
			position++;
			if (!(words.get(position).getKey().equals(KeyWord.WORD)
					|| words.get(position).getKey().getType().equals(WordType.DATATYPE))) {
				// no valid parameter type
				return null;
			} else {
				// valid parameter type x.y
				position++;
			}
		}
		if (words.get(position).getKey().equals(KeyWord.OPENBRACKET)
				&& words.get(position + 1).getKey().equals(KeyWord.CLOSEBRACKET)) {
			// parameter type array "String[]"
			position++;
			position++;
		} else if (words.get(position).getKey().equals(KeyWord.DOT)
				&& words.get(position + 1).getKey().equals(KeyWord.DOT)
				&& words.get(position + 2).getKey().equals(KeyWord.DOT)) {
			// variable array of parameters "String..."
			position++;
			position++;
			position++;
		} else if (words.get(position).getKey().equals(KeyWord.LESS)) {
			// parse generic datatypes like Hashmap<A,B>
			position++;
			position = parseGeneric(words, position);
			if (position == 0) {
				return null;
			}
			position++;
		}
		if (words.get(position).getKey().equals(KeyWord.WORD)) {
			// correct parameter identifier
			return words.get(position);
		}
		// no correct identifier
		return null;
	}

	/**
	 * parse recursive through generic type elements in Parameter declaration
	 * like HashMap<String, Integer>
	 * 
	 * @param words
	 *            - wordlist beginning from first word after "<"
	 * @param position
	 *            - the current position in isParameter method
	 * @return position after ">" or 0 if invalid
	 */
	private int parseGeneric(List<WordInFile> words, int position) {
		if (words.get(position).getKey().equals(KeyWord.WORD)
				|| words.get(position).getKey().getType().equals(WordType.DATATYPE)
				|| words.get(position).getKey().equals(KeyWord.QUESTIONMARK)) {
			position++;
		} else {
			return 0;
		}
		while (words.get(position).getKey().equals(KeyWord.DOT)) {
			position++;
			if (!(words.get(position).getKey().equals(KeyWord.WORD)
					|| words.get(position).getKey().getType().equals(WordType.DATATYPE))) {
				// no valid parameter type
				return 0;
			} else {
				// valid parameter type x.y
				position++;
			}
		}
		if (words.get(position).getKey().equals(KeyWord.OPENBRACKET)
				&& words.get(position + 1).getKey().equals(KeyWord.CLOSEBRACKET)) {
			// parameter type array "String[]"
			position++;
			position++;
		} else if (words.get(position).getKey().equals(KeyWord.LESS)) {
			// recursive generic parsing...
			position++;
			position = parseGeneric(words, position);
			if (position == 0) {
				return 0;
			}
			position++;
		}
		if (words.get(position).getKey().equals(KeyWord.COMMA)) {
			boolean comma = true;
			position++;
			while (comma) {
				if (words.get(position).getKey().equals(KeyWord.WORD)
						|| words.get(position).getKey().getType().equals(WordType.DATATYPE)
						|| words.get(position).getKey().equals(KeyWord.QUESTIONMARK)) {
					position++;
				} else {
					return 0;
				}
				while (words.get(position).getKey().equals(KeyWord.DOT)) {
					position++;
					if (!(words.get(position).getKey().equals(KeyWord.WORD)
							|| words.get(position).getKey().getType().equals(WordType.DATATYPE))) {
						// no valid parameter type
						return 0;
					} else {
						// valid parameter type x.y
						position++;
					}
				}
				if (words.get(position).getKey().equals(KeyWord.OPENBRACKET)
						&& words.get(position + 1).getKey().equals(KeyWord.CLOSEBRACKET)) {
					// parameter type array "String[]"
					position++;
					position++;
				} else if (words.get(position).getKey().equals(KeyWord.LESS)) {
					// recursive generic parsing...
					position++;
					position = parseGeneric(words, position);
					if (position == 0) {
						return 0;
					}
					position++;
				}
				if (!words.get(position).getKey().equals(KeyWord.COMMA)) {
					// another generic type behind a comma
					comma = false;
				} else {
					position++;
				}
			}
		}
		if (words.get(position).getKey().equals(KeyWord.GREATER)) {
			// close this generic
			// position++;
			return position;
		}
		// invalid generic
		return 0;
	}
}

package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plugin.model.JavaClassContent;
import plugin.model.JavaMethod;
import plugin.model.JavaWord;
import plugin.model.KeyWord;
import plugin.model.WordType;

public class JavaFileNormalizer {
	/**
     * Prepares File for parsing. remove comments, empty lines, leading spaces....
     * 
     * @param file - the file to analyze
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
    		for (int i=0; i<chars.length; i++) {
    			String c = chars[i];
    			if (!blockComment && !isLiteralA && !isLiteralB) {
    				switch(c) {
    				case "\"":
    					if (chars.length>i+1 && chars[i+1].equals("\"")) {
    						//empty literal - ignore
    						i++;
    					} else {
    						// beginning of a "literal", add " as placeholder to new line
    						isLiteralA = true;
    						newLine += '"';
    					}
    					break;
    				case "'":
    					if (chars.length>i+1 && chars[i+1].equals("'")) {
    						//empty literal - ignore
    						i++;
    					} else {
    						// beginning of a 'literal', add ' as placeholder to new line
    						isLiteralB = true;
    						newLine += "'";
    					}
    					break;
    				case "/":
    					if (chars.length>i+1 && chars[i+1].equals("/")) {
    						// begin of an inline comment. skip rest of line
    						i = chars.length;
    					} else if (chars.length>i+1 && chars[i+1].equals("*")) {
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
    				// detect end of blockcomment and add " " instead of it (for things like "public/*comment*/String...")
    				if (c.equals("*") && chars.length>i+1 && chars[i+1].equals("/")) {
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
    		// remove leading and ending spaces from new line (maybe caused by block-comment replacement or inline-comment removement)
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
     * Converts relevant lines to single normalized code String for pattern recognition 
     * 
     * @param lines - List of relevant Lines
     * @returns normalized Code-String
     */
    public String convertToSingleString(List<String> lines) {
    	String fullCode = "";
    	for (String line: lines) { 
    	    // add normalized line with space before new line
    		fullCode += (line + " ");
    	}
    	// remove spaces before and after brackets
		String normalizedCode = "";
		List<String> breaks = Arrays.asList("\"", "'", "(", ")", "{", "}", "[", "]", "+", "-", "*", "/", "%", "<", ">", "=", 
    			"!", "~", "&", "|", "^", "?", ":", ",", ";", ".");
		String[] chars = fullCode.split("");
		for (int i=0; i<chars.length; i++) {
			if (chars[i].equals(" ") && chars.length>i+1 && breaks.contains(chars[i+1])) {
				// skip space before break-characters
				continue;
			} 
			normalizedCode += chars[i];
			if (breaks.contains(chars[i]) && chars.length>i+1 && chars[i+1].equals(" ")) {
				// skip space after break-characters
				i++;
			} 
		}
    	return normalizedCode;
    }
    
    /**
     * Splits file into words, (braces, semicolons, operators , ... count as words!)
     * 
     * @param normalizedCode - Code as single normalized Line
     * @returns word-list
     */
    public List<JavaWord> createJavaWordList(String normalizedCode) {
    	// characters between words breaks are always a single word, operators not
    	List<String> breaks = Arrays.asList("\"", "'", "(", ")", "{", "}", "[", "]", "?", ":", ",", ";", "@", ".");
    	List<String> operators = Arrays.asList("+", "-", "*", "/", "%", "<", ">", "=", "!", "~", "&", "|", "^");
    	List<JavaWord> result = new ArrayList<JavaWord>();
    	String[] chars = normalizedCode.split("");
		String word = "";
		boolean operator = false;
		for (int i=0; i<chars.length; i++) {
			//if previous char was an operator
			if (operator) {
				if (operators.contains(chars[i])) {
					// add char to operator word if also operator
					word += chars[i];
				} else {
					// end of operator word. add to word list
					result.add(KeyWord.findKeyword(word));
					operator = false;
					
					if (breaks.contains(chars[i])) {
						// if break character add as word
						result.add(KeyWord.findKeyword(chars[i]));
						word = "";
					} else {
						// otherwise start new word
						word = chars[i];
					}
				}
			// if char is operator
			} else if (operators.contains(chars[i])) {
				if (!word.isEmpty()) {
					// add previous word to list if not empty
					result.add(KeyWord.findKeyword(word));
				}
				// start operator word 
				operator = true;
				word = chars[i];
			// if char is break
			} else if (breaks.contains(chars[i])) {
				if (!word.isEmpty()) {
					// add previous word to list if not empty
					result.add(KeyWord.findKeyword(word));
					word = "";
				}
				// add break char as word
				result.add(KeyWord.findKeyword(chars[i]));
			// if char is space
			} else if (chars[i].equals(" ")){
				// add previous word to list and reset
				result.add(KeyWord.findKeyword(word));
				word = "";
			} else {
				// add char to current word
				word += chars[i];
			}
		}
    	return result;
    }
    
    /**
     * Reduces word list by unused stuff like annotations, package and import statements
     * 
     * @param fullList - Full Code as word list
     * @returns reduced-word-list
     */
    public List<JavaWord> reduceWordList(List<JavaWord> fullList) {
    	List<JavaWord> reducedList = new ArrayList<JavaWord>();
    	boolean packageStatement = false;
    	boolean importStatement = false;
    	int annotation = 0;
    	int openParentheses = 0;
    	for (int i=0; i<fullList.size(); i++) {
    		JavaWord word = fullList.get(i);
    		if (packageStatement) {
    			// in a package statement check if ending by ;
    			if (word.equals(KeyWord.SEMICOLON)) {
    				packageStatement = false;
    			}
    		} else if (importStatement) {
    			// in a import statement check if ending by ;
    			if (word.equals(KeyWord.SEMICOLON)) {
    				importStatement = false;
    			}
    		} else if (annotation==1) {
    			if (word.equals(KeyWord.INTERFACE)) {
    				// annotation declaration. keep it!
    				annotation = 0;
    				reducedList.add(word);
    			} else {
    				// annotation started by @ followed by free word
    				annotation++;
    			}
    		} else if (annotation==2) {
    			if (word.equals(KeyWord.OPENPARANTHESE)) {
    				// annotation has parentheses behind name
    				annotation++;
    				openParentheses++;
    			} else {
    				// otherwise it ended
    				annotation = 0;
    			}
    		} else if (annotation==3) {
    			if (word.equals(KeyWord.OPENPARANTHESE)) {
    				// Parentheses in Parentheses
    				openParentheses++;
    			} else if (word.equals(KeyWord.CLOSPARANTHESE)) {
    				// Parentheses closed
    				openParentheses--;
    				if (openParentheses==0) {
    					// if first parentheses closed it ended
    					annotation = 0;
    				}
    			}
    		} else {
    			if (word.equals(KeyWord.ANNOTATION)) {
    				// start annotation
    				annotation++;
    			} else if (word.equals(KeyWord.IMPORT)) {
    				// start import
    				importStatement = true;
    			} else if (word.equals(KeyWord.PACKAGE)) {
    				// start package
    				packageStatement = true;
    			} else {
    				// otherwise add word to list
    				reducedList.add(word);
    			}
    		}    		
    	}
    	return reducedList;
    }
    
    /**
     * Splits reduced word list to methods (and Word-Lists for other stuff)
     * 
     * @param wordlist - reduced Word-List
     * @returns list of java class contents (Methods or WordLists)
     */
    public List<JavaClassContent> splitToMethods(List<JavaWord> wordlist) {
    	List<JavaClassContent> result = new ArrayList<JavaClassContent>();
    	int openBraces = 0;
    	int methodDetectionState = 0;
    	int position = 0;
    	String maybeMethodName = null;
    	List<JavaWord> content = new ArrayList<JavaWord>();
    	List<JavaWord> temporary = new ArrayList<JavaWord>();
    	List<String> parameter = new ArrayList<String>();
    	JavaMethod method = null;
    	for(int i=0; i<wordlist.size(); i++) {
    		JavaWord word = wordlist.get(i);
    		System.out.println("word: " + word + " Position: " + position + " MethodStatus: " + methodDetectionState);
    			switch (methodDetectionState){
    				case 0:
    					// Nothing or only modifiers detected
    					if (word.getKey().getType().equals(WordType.MODIFIER) || word.getKey().equals(KeyWord.SYNCHRONIZED)) {
    						//add modifier to potential header
    						temporary.add(word);
    					} else if (word.getKey().equals(KeyWord.WORD) || word.getKey().getType().equals(WordType.DATATYPE)) {
    						// add potential returntype to potential header
    						temporary.add(word);
    						maybeMethodName = word.getWord();
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
    					} else if (word.getKey().equals(KeyWord.WORD) && wordlist.get(i+1).getKey().equals(KeyWord.OPENPARANTHESE)) {
    						// add potential methodname and open parentheses to potential header
    						temporary.add(word);
    						temporary.add(wordlist.get(i+1));
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
    				case 2:
    					// modifiers, returntype, methodname and open paranthese detected
    					JavaWord param = isParameter(wordlist.subList(i, wordlist.size()-1), false);
    					if (param!=null) {
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
    				case 3:
    					// modifiers, returntype, methodname, open paranthese and at least one parameter detected
    					JavaWord secondaryparam = isParameter(wordlist.subList(i, wordlist.size()-1), true);
    					if (secondaryparam!=null) {
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
    						// no method header! reset  and check word again
    						i--;
    						methodDetectionState = 0;
    						maybeMethodName = null;
    						parameter.clear();
    						content.addAll(temporary);
    						temporary.clear();
    					}
    					break;
    				case 4:
    					// modifiers, returntype, methodname, and parameter list detected
    					if (word.getKey().getType().equals(WordType.SPECIFIER) || word.getKey().equals(KeyWord.WORD)) {
    						// speciefing stuff like extends / throws / implements detected
    						temporary.add(word);
    					} else if (word.getKey().equals(KeyWord.OPENBRACE)) {
    						// valid method header. add all before as ClassContent to result.
    						if (!content.isEmpty()) {
    							List<JavaWord> somecontent = new ArrayList<JavaWord>();
    							somecontent.addAll(content);
    							result.add(new JavaClassContent(position, somecontent));
    							content.clear();
    							position++;
    						}
    						// reset temporary values
    						openBraces++;
    						methodDetectionState++;
    						temporary.clear();
    						List<String> params = new ArrayList<String>();
    						params.addAll(parameter);
    						method = new JavaMethod(position, maybeMethodName, params, null);
    						position++;
    						maybeMethodName = null;
    						parameter.clear();
    					} else {
    						// no method header! reset  and check word again
    						i--;
    						methodDetectionState = 0;
    						maybeMethodName = null;
    						parameter.clear();
    						content.addAll(temporary);
    						temporary.clear();
    					}
    					break;
    				case 5:
    					// valid method header detected, collect words in body
    					if (word.getKey().equals(KeyWord.CLOSEBRACE)) {
    						openBraces--;
    						if (openBraces==0) {
    							// end of method body, add to method and result
    							methodDetectionState = 0;
    							List<JavaWord> classcontent = new ArrayList<JavaWord>();
    							classcontent.addAll(content);
    							method.setContent(classcontent);
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
    	if (!content.isEmpty()) {
    		result.add(new JavaClassContent(position, content));
    	}
    	return result;
    }
    
    private JavaWord isParameter(List<JavaWord> words, boolean withSeparator) {
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
    	if (words.get(position).getKey().equals(KeyWord.FINAL)) {
    		// optional modifier final for parameter
    		position++;
    	}
    	if (!(words.get(position).getKey().equals(KeyWord.WORD) || words.get(position).getKey().getType().equals(WordType.DATATYPE))) {
    		// no valid parameter type
    		return null;
    	} else {
    		// valid parameter type beginning
    		position++;
    	}
    	if (words.get(position).getKey().equals(KeyWord.OPENBRACKET) && words.get(position+1).getKey().equals(KeyWord.CLOSEBRACKET)) {
    		// parameter type array "String[]"
    		position++;
    		position++;
    	} else if (words.get(position).getKey().equals(KeyWord.DOT) && words.get(position+1).getKey().equals(KeyWord.DOT) && words.get(position+2).getKey().equals(KeyWord.DOT)) {
    		// variable array of parameters "String..."
    		position++;
    		position++;
    		position++;
    	} else if (words.get(position).getKey().equals(KeyWord.LESS)) {
    		// parse generic datatypes like Hashmap<A,B>
    		position++;
    		position = parseGeneric(words, position);
    		if (position==0) {
    			return null;
    		}
    	}
    	if (words.get(position).getKey().equals(KeyWord.WORD)) {
    		// correct parameter identifier
    		return words.get(position);
    	}
    	// no correct identifier
    	return null;
    }

	private int parseGeneric(List<JavaWord> words, int position) {
		if (words.get(position).getKey().equals(KeyWord.WORD) || words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
    		position++;
    	} else {
    		return 0;
    	}
    	if (words.get(position).getKey().equals(KeyWord.OPENBRACKET) && words.get(position+1).getKey().equals(KeyWord.CLOSEBRACKET)) {
    		// parameter type array "String[]"
    		position++;
    		position++;
    	} else if (words.get(position).getKey().equals(KeyWord.LESS)) {
    		//recursive generic parsing...
    		position++;
    		position = parseGeneric(words, position);
    		if (position==0) {
    			return 0;
    		}
    	}
    	if (words.get(position).getKey().equals(KeyWord.COMMA)) {
    		boolean comma = true;
    		position++;
    		while (comma) {
    			if (words.get(position).getKey().equals(KeyWord.WORD) || words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
    	    		position++;
    	    	} else {
    	    		return 0;
    	    	}
    	    	if (words.get(position).getKey().equals(KeyWord.OPENBRACKET) && words.get(position+1).getKey().equals(KeyWord.CLOSEBRACKET)) {
    	    		// parameter type array "String[]"
    	    		position++;
    	    		position++;
    	    	} else if (words.get(position).getKey().equals(KeyWord.LESS)) {
    	    		//recursive generic parsing...
    	    		position++;
    	    		position = parseGeneric(words, position);
    	    		if (position==0) {
    	    			return 0;
    	    		}
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
    		position++;
    		return position;
    	}
    	// invalid generic
		return 0;
	}
}

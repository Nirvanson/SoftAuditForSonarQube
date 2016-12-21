package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plugin.model.JavaWord;
import plugin.model.KeyWord;

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
    			"!", "~", "&", "|", "^", "?", ":", ",", ";");
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
    			// annotation started by @ followed by free word
    			annotation++;
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
}

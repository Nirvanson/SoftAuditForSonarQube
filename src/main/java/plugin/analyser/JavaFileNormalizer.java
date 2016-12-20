package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * Splits file into words, removes operators, brackets, line-endings, ...
     * 
     * @param lines - List of relevant Lines
     * @returns word-list
     */
    public List<String> splitToWords(List<String> lines) {
    	List<String> breaks = Arrays.asList(" ", "(", ")", "{", "}", "[", "]", "+", "-", "*", "/", "%", "<", ">", "=", 
    			"!", "~", "&", "|", "^", "?", ":", ",", ";");
    	List<String> result = new ArrayList<String>();
    	for (String line: lines) {
    		String[] chars = line.split("");
    		String word = "";
    		for (int i=0; i<chars.length; i++) {
    			if (breaks.contains(chars[i])) {
    				if (!word.isEmpty()) {
    					result.add(word);
    					word = "";
    				}
    			} else {
    				word += chars[i];
    			}
    		}
    	}
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
		List<String> brackets = Arrays.asList("(", ")", "{", "}", "[", "]");
		String[] chars = fullCode.split("");
		for (int i=0; i<chars.length; i++) {
			if (chars[i].equals(" ") && chars.length>i+1 && brackets.contains(chars[i+1])) {
				// skip space before bracket
				continue;
			} 
			normalizedCode += chars[i];
			if (brackets.contains(chars[i]) && chars.length>i+1 && chars[i+1].equals(" ")) {
				// skip space after bracket
				i++;
			} 
		}
    	return normalizedCode;
    }
}

package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaFileNormalizer {
	/**
     * Prepares File for parsing. remove comments, empty lines, leading spaces....
     * 
     * @param file - the file to analyze
     * @throws IOException 
     */
    public List<String> prepareFile(File file) throws IOException{
    	// Step 1 remove empty lines, leading and ending whitespaces and all comments
    	List<String> step1 = new ArrayList<String>();
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String fileline;
    	boolean isLiteralA = false;
    	boolean isLiteralB = false;
    	boolean blockComment = false;
    	while ((fileline = br.readLine()) != null) {
    		String line = fileline.trim();
    		String newLine = "";
    		boolean inlineComment = false;
    		String[] chars = line.split("");
    	
    		for (int i=0; i<chars.length; i++) {
    			if (inlineComment) {
    				break;
    			}
    			String c = chars[i];
    			if (!blockComment && !isLiteralA && !isLiteralB) {
    				switch(c) {
    				case "\"":  
    					isLiteralA = true;
    					newLine = newLine + '"';
    					break;
    				case "'":
    					isLiteralB = true;
    					newLine = newLine + '"';
    					break;
    				case "/":
    					if (chars.length>i+1 && chars[i+1].equals("/")) {
    						inlineComment = true;
    					} else if (chars.length>i+1 && chars[i+1].equals("*")) {
    						blockComment = true;
    					}
    					break;
    				default:
    					newLine = newLine + c;
    				}
    			} else if (blockComment) {
    				if (c.equals("*") && chars.length>i+1 && chars[i+1].equals("/")) {
    					i++;
    					blockComment = false;
    				}
    			} else if (isLiteralA || isLiteralB) {
    				if (c.equals("\\")) {
    					i++;
    				} else {
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
    		String resultLine = newLine.trim();
    		if (!resultLine.isEmpty()) {
    			step1.add(resultLine);
    		}
    	}
    	br.close();
    	return step1;
    }
}

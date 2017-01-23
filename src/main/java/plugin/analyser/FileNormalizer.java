package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import plugin.model.WordInFile;
import plugin.util.ParsingException;
import plugin.model.KeyWord;

/**
 * Parsing .java file. Removes unused elements like comments, literals and empty spaces and extracts a word list from
 * which the code model can be build. In the resulting word-list some simple keyword-measurements can be done.
 * 
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class FileNormalizer {

	/**
     * Wrapper for normalizing file in correct order.
     * 
     * @param file - file to normalize
     * @throws ParsingException
     * @return detailed fileModel
     */
	public static List<WordInFile> doFileNormalization(File file) throws ParsingException {
		// step 1 - remove unneeded, blank-spaces, comments, ...
		List<String> normalizedLines = prepareFile(file);
	    // step 2 - put all together in one line, remove unneeded spaces
	    String singleLineCode = FileNormalizer.convertToSingleString(normalizedLines);
	    // step 3 - Build wordlist
	    List<WordInFile> wordList = FileNormalizer.createJavaWordList(singleLineCode);
	    return wordList;
	}
	
    /**
     * Prepares File for parsing. remove comments, empty lines, leading spaces....
     * 
     * @param file - the file to analyze
     * @returns List of relevant lines
     * @throws ParsingException
     */
    private static List<String> prepareFile(File file) throws ParsingException {
        try {
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
                            // beginning of a "literal", add " as placeholder to new line
                            isLiteralA = true;
                            newLine += '"';
                            break;
                        case "'":
                            // beginning of a 'literal', add ' as placeholder to new line
                            isLiteralB = true;
                            newLine += "'";
                            break;
                        case "/":
                            if (chars.length > i + 1 && chars[i + 1].equals("/")) {
                                // begin of an inline comment. skip rest of line
                                i = chars.length;
                            } else if (chars.length > i + 1 && chars[i + 1].equals("*")) {
                                // begin of an blockcomment. skip everything till endsequence
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
                        // detect end of blockcomment and add space instead of it
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
                // remove leading and ending spaces from new line (maybe caused by previous conversions)
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
        } catch (Exception e) {
            throw new ParsingException("Preparation of file failed.");
        }
    }

    /**
     * Converts relevant lines to single normalized code String for easier word splitting.
     * 
     * @param lines - List of relevant Lines
     * @throws ParsingException
     * @returns normalized Code-String
     */
    private static String convertToSingleString(List<String> lines) throws ParsingException {
        try {
            String fullCode = "";
            for (String line : lines) {
                // add normalized line with space before new line
                fullCode += (line + " ");
            }
            // remove spaces before and after brackets
            String normalizedCode = "";
            String[] chars = fullCode.split("");
            for (int i = 0; i < chars.length; i++) {
                if (chars[i].equals(" ") && chars.length > i + 1 && ModelBuildHelper.breaks.contains(chars[i + 1])) {
                    // skip space before break-characters
                    continue;
                }
                normalizedCode += chars[i];
                if (ModelBuildHelper.breaks.contains(chars[i]) && chars.length > i + 1 && chars[i + 1].equals(" ")) {
                    // skip space after break-characters
                    i++;
                }
            }
            return normalizedCode;
        } catch (Exception e) {
            throw new ParsingException("Conversion to single String failed.");
        }
    }

    /**
     * Splits code into words (special characters count as words!).
     * 
     * @param normalizedCode - Code as single normalized Line
     * @throws ParsingException
     * @returns word-list
     */
    private static List<WordInFile> createJavaWordList(String normalizedCode) throws ParsingException {
        try {
            // characters in breaks and operators between words are always a single word
            List<WordInFile> step1 = new ArrayList<WordInFile>();
            String[] chars = normalizedCode.split("");
            String word = "";
            for (int i = 0; i < chars.length; i++) {
                // if previous char was an operator
                if (ModelBuildHelper.breaks.contains(chars[i])) {
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
                switch (annotationState) {
                case 0:
                    if (wordInStep1.equals(KeyWord.ANNOTATION)) {
                        // start of annotation.
                        annotationState++;
                    } else if ((wordInStep1.equals(KeyWord.WORD) && ModelBuildHelper.isNumber(wordInStep1.getWord()))
                            || (wordInStep1.equals(KeyWord.DOT)) && i < step1.size() - 1
                                    && step1.get(i + 1).getWord() != null
                                    && ModelBuildHelper.isNumber(step1.get(i + 1).getWord())) {
                        // it's a number... collect all words together that build one number
                        i = collectNumberWords(result, step1, i);
                    } else if ((wordInStep1.equals(KeyWord.WORD) && !wordInStep1.getWord().isEmpty()  && !wordInStep1.getWord().trim().isEmpty())
                            || !wordInStep1.equals(KeyWord.WORD)) {
                        // otherwise add word to list (if not empty)
                        result.add(wordInStep1);
                    }
                    break;
                case 1:
                    if (wordInStep1.equals(KeyWord.INTERFACE)) {
                        // annotation declaration. keep it!
                        annotationState = 0;
                        result.add(new WordInFile(null, KeyWord.ANNOTATIONINTERFACE));
                    } else {
                        // annotation started by @ followed by free word, keep "@" as placeholder
                        result.add(step1.get(i - 1));
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
            return result;
        } catch (Exception e) {
            throw new ParsingException("Word-list creation failed.");
        }
    }

    private static int collectNumberWords(List<WordInFile> result, List<WordInFile> step1, int i) {
        boolean endfound = false;
        String resultingNumber = "";
        while (!endfound) {
            if (step1.get(i).equals(KeyWord.WORD) && ModelBuildHelper.isNumber(step1.get(i).getWord())) {
                resultingNumber += step1.get(i).getWord();
            } else if (step1.get(i).equals(KeyWord.DOT)) {
                resultingNumber += ".";
            } else if (resultingNumber.toLowerCase().endsWith("e") && step1.get(i).equals(KeyWord.SUB)) {
                resultingNumber += "-";
            } else {
                endfound = true;
            }
            i++;
        }
        result.add(new WordInFile(resultingNumber, KeyWord.CONSTANT));
        return i - 2;
    }
}

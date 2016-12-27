package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plugin.model.WordInFile;
import plugin.model.KeyWord;

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
}

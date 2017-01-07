package plugin.analyzer;

import java.util.Arrays;
import java.util.List;

import plugin.model.KeyWord;
import plugin.model.WordInFile;
import plugin.model.WordType;

public class ModelBuildHelper {
	
	public final static List<KeyWord> keywords = Arrays.asList(KeyWord.SWITCH, KeyWord.DO, KeyWord.WHILE, KeyWord.FOR, KeyWord.IF,
			KeyWord.TRY, KeyWord.RETURN, KeyWord.SYNCHRONIZED, KeyWord.BREAK, KeyWord.CONTINUE, KeyWord.ASSERT, KeyWord.THROW);
	
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
	public static WordInFile isParameter(List<WordInFile> words, boolean withSeparator) {
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
		if (words.get(position).getKey().equals(KeyWord.DOT) && !words.get(position + 1).getKey().equals(KeyWord.DOT)) {
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
	 * @return position of last ">" or 0 if invalid
	 */
	public static int parseGeneric(List<WordInFile> words, int position) {
		if (words.get(position).getKey().equals(KeyWord.WORD)
				|| words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
			position++;
		} else if (words.get(position).getKey().equals(KeyWord.QUESTIONMARK)){
			position++;
			if (words.get(position).getKey().equals(KeyWord.EXTENDS)) {
				position++;
				if (words.get(position).getKey().equals(KeyWord.WORD)
						|| words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
					position++;
				}
			}
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
						|| words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
						position++;
			} else if (words.get(position).getKey().equals(KeyWord.QUESTIONMARK)){
				position++;
				if (words.get(position).getKey().equals(KeyWord.EXTENDS)) {
					position++;
					if (words.get(position).getKey().equals(KeyWord.WORD)
							|| words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
						position++;
					}
				}
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

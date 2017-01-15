package plugin.analyser;

import java.util.Arrays;
import java.util.List;

import plugin.model.KeyWord;
import plugin.model.WordInFile;
import plugin.model.WordType;

/**
 * Provides helping function for scanning word-lists for patterns like variable declarations.
 * 
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelBuildHelper {

    /** Keywords for control-statements. */
    public final static List<KeyWord> keywords = Arrays.asList(KeyWord.SWITCH, KeyWord.DO, KeyWord.WHILE, KeyWord.FOR,
            KeyWord.IF, KeyWord.TRY, KeyWord.RETURN, KeyWord.SYNCHRONIZED, KeyWord.BREAK, KeyWord.CONTINUE,
            KeyWord.ASSERT, KeyWord.THROW);
    /** Single-char words in java code. */
    public final static List<String> breaks = Arrays.asList("\"", "'", "(", ")", "{", "}", "[", "]", "?", ":", ",", ";",
            "@", ".", "+", "-", "*", "/", "%", "<", ">", "=", "!", "~", "&", "|", "^");
    /** Allowed chars for assignment-operator. */
    public final static List<KeyWord> operators = Arrays.asList(KeyWord.ADD, KeyWord.SUB, KeyWord.MULT, KeyWord.DIV,
            KeyWord.MOD, KeyWord.AND, KeyWord.OR, KeyWord.BITXOR);

    public static boolean isNumber(String potentialNumber) {
        String[] chars = potentialNumber.split("");
        if (!chars[0].matches("[0-9]")) {
            return false;
        }
        for (int i=1; i<chars.length-1; i++) {
            if (!chars[i].matches("[0-9e]")) {
                return false;
            }
        }
        if (!chars[chars.length-1].matches("[0-9defl]")) {
            return false;
        }
        return true;
    }
    
    /**
     * checks if following words are valid parameter/variable declaration
     * 
     * @param words - word-list beginning from first word of variable-type
     * @param withSeparator - search for "," before parameter declaration
     * @return the variable/parameter identifier word
     */
    public static WordInFile isVariableDeclaration(List<WordInFile> words, boolean withSeparator) {
        try {
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
            while (words.get(position).getKey().getType().equals(WordType.MODIFIER)
                    || words.get(position).getKey().getType().equals(WordType.STATEMENTORMODIFIER)) {
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
                if (position >= words.size()) {
                    return null;
                }
            }
            if (words.get(position).getKey().equals(KeyWord.DOT)
                    && !words.get(position + 1).getKey().equals(KeyWord.DOT)) {
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
        } catch (Exception e) {
            // if parsing fails no valid variable / parameter declaration. No Error
            return null;
        }
    }

    /**
     * parse recursive through generic type elements in Parameter declaration like HashMap<String, Integer>
     * 
     * @param words - wordlist
     * @param position - the current position in isParameter method (first after <)
     * @return position of last ">" or 0 if invalid
     */
    public static int parseGeneric(List<WordInFile> words, int position) {
        try {
            if (words.get(position).getKey().equals(KeyWord.WORD)
                    || words.get(position).getKey().getType().equals(WordType.DATATYPE)) {
                position++;
            } else if (words.get(position).getKey().equals(KeyWord.QUESTIONMARK)) {
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
                    } else if (words.get(position).getKey().equals(KeyWord.QUESTIONMARK)) {
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
                return position;
            }
            // invalid generic
            return 0;
        } catch (Exception e) {
            // no valid generic, no Error!
            return 0;
        }
    }
}

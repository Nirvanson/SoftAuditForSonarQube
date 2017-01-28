package plugin.model;

/**
 * A model-representation of a word in the source file. Special chars like braces and operators are also words.
 * 
 * @author Jan Rucks
 * @version 1.0
 */
public class WordInFile extends JavaFileContent {
    /** The word as String. */
    private final String word;
    /** The matching Keyword. */
    private final KeyWord key;

    /**
     * Constructor with word as string and matching keyword.
     * 
     * @param word
     * @param key
     */
    public WordInFile(String word, KeyWord key) {
        super(null);
        this.word = word;
        this.key = key;
    }

    @Override
    public String toString() {
        if (word == null) {
            return this.key.toString();
        }
        return this.getWord();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WordInFile) {
            // is equal if key is the same and word is null or same
            WordInFile test = (WordInFile) o;
            if ((test.getWord() == null && test.getKey().equals(this.key))
                    || (test.getWord() != null && test.getWord().equals(this.word) && test.getKey().equals(this.key))) {
                return true;
            }
        } else if (o instanceof KeyWord) {
            // is equal if key matches
            KeyWord test = (KeyWord) o;
            if (test.equals(this.key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the word as string.
     * 
     * @return word
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Get the matching keyword.
     * 
     * @return key
     */
    public KeyWord getKey() {
        return this.key;
    }
}

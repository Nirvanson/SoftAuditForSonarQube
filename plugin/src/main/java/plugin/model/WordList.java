package plugin.model;

import java.util.List;

/**
 * Placeholder for Java-Components in the recursive process of parsing the model..
 * 
 * @author Jan Rucks
 * @version 1.0
 */
public class WordList extends JavaFileContent {
    /** The word-list which has to be parsed later. */
    private final List<WordInFile> wordlist;

    /**
     * Constructor with word-list.
     * 
     * @param wordlist
     */
    public WordList(List<WordInFile> wordlist) {
        super(null);
        this.wordlist = wordlist;
    }

    /**
     * Get the word-list.
     * 
     * @return wordList
     */
    public List<WordInFile> getWordlist() {
        return wordlist;
    }
}

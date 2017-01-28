package plugin.model;

/**
 * Triple implementation for parsing-result. Only to be used with <File, List<WordInFile>, List<JavaFileContent>>.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class AnalyzeTriple<T, U, V> {
    /** The File. */
    private final T file;
    /** The file as word-list. */
    private final U wordList;
    /** The parsed model. */
    private final V model;

    /**
     * Constructor with all 3 elements.
     * 
     * @param file
     * @param wordList
     * @param model
     */
    public AnalyzeTriple(T file, U wordList, V model) {
        this.file = file;
        this.wordList = wordList;
        this.model = model;
    }

    /**
     * Get the file.
     * 
     * @return file
     */
    public T getFile() {
        return file;
    }

    /**
     * Get the word-list.
     * 
     * @return wordList
     */
    public U getWordList() {
        return wordList;
    }

    /**
     * Get the model.
     * 
     * @return model
     */
    public V getModel() {
        return model;
    }
}

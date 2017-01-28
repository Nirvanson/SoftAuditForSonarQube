package plugin.util;

/**
 * Exception while analyzing generated file-model..
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class AnalyzeException extends Exception {

    private static final long serialVersionUID = 7528883914220182044L;

    /**
     * Constructor with message.
     * 
     * @param message - for logging
     */
    public AnalyzeException(String message) {
        super(message);
    }
}

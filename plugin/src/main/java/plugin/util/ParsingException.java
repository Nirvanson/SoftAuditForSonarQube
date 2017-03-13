package plugin.util;

/**
 * Exception while parsing source-file to model.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class ParsingException extends Exception {

    private static final long serialVersionUID = 8586584750799943766L;

    /**
     * Constructor with message.
     * 
     * @param message - for logging
     */
    public ParsingException(String message) {
        super(message);
    }
}

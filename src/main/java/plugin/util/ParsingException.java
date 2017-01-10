package plugin.util;

public class ParsingException extends Exception {

	private static final long serialVersionUID = 8586584750799943766L;
	private final ParsingErrorType type;
	
	public ParsingException(ParsingErrorType type, String message) {
		super(message);
		this.type = type;
	}

	@Override
	public void printStackTrace() {
		System.err.println("ParsingException of type: " + type);
		super.printStackTrace();
	}
	
	public ParsingErrorType getType() {
		return type;
	}
}

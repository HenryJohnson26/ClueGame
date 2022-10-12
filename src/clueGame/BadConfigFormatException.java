package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Incorrect file format on config file");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}	
}

package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	//Default constructor
	public BadConfigFormatException() {
		super("Incorrect file format on config file");
	}
	
	//Constructor that takes in error message and writes to file
	public BadConfigFormatException(String message) throws FileNotFoundException {
		super(message);
		//write output to file
		PrintWriter output = new PrintWriter("logfile.txt");
		output.print(message);
		output.close();
	}	
}

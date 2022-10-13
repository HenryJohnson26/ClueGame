package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class BoardAdjTargetTest {

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
		
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
		// Initialize will load config files 
		board.initialize();
	}

}

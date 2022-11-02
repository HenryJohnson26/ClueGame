package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class GameSetupTests {
	public static Board board;

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	
	@BeforeAll
	public static void setup() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	//Will test to make sure people are loaded in correctly and that the human and computer players are correct
	@Test
	public void testPlayers() {
		
	}
	
	//Will test to make sure that the deck of cards is loaded correctly, the solution is chosen properly, and that the cards are dealt correctly
	@Test
	public void testCards() {
		
	}

}

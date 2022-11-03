package tests;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.CardType;

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
		//test number of players
		ArrayList<Player> players = new ArrayList<Player>();
		assertEquals(6, players.size());
		//sees that the first player is a human
		assertTrue(players.get(0) instanceof HumanPlayer);
		assertEquals(players.get(0).getPlayerName(), "Lokaste Alberic");
		//sees that the others are computers
		for(int i = 1; i<players.size();i++) {
			assertTrue(players.get(i) instanceof ComputerPlayer);
		}
	}
	
	//Will test to make sure that the deck of cards is loaded correctly, the solution is chosen properly, and that the cards are dealt correctly
	@Test
	public void testCards() {
		assertEquals(board.getDeck().size(), 18);
		assertEquals(board.getPlayerCards().size(), 6);
		assertEquals(board.getRoomCards().size(), 9);
		assertEquals(board.getWeaponCards().size(), 6);
		
		//test that each arraylist has the right type of card
		assertEquals(board.getPlayerCards().get(0).getType(), CardType.PLAYER);
		assertEquals(board.getRoomCards().get(0).getType(), CardType.ROOM);
		assertEquals(board.getWeaponCards().get(0).getType(), CardType.WEAPON);

		//test that each player has three cards
		board.deal();
		for(Player p : board.getPlayers()) {
			assertEquals(p.getHand().size(), 3);
		}
		
		//test the solution
		Solution solution = board.getSolution();
		assertEquals(solution.getPersonSolution().getType(), CardType.PLAYER);
		assertEquals(solution.getRoomSolution().getType(), CardType.ROOM);
		assertEquals(solution.getWeaponSolution().getType(), CardType.WEAPON);
		
		assertFalse(board.getDeck().contains(solution.getPersonSolution()));
		assertFalse(board.getDeck().contains(solution.getRoomSolution()));
		assertFalse(board.getDeck().contains(solution.getWeaponSolution()));
	}

}

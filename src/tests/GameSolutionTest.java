package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
	private static Board board;
	
	@BeforeAll
	public static void setup() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	//Tests the checkAccusation method
	@Test
	public void testAccusation() {
		//create some test cards
		Card weapon1 = new Card("Bat");
		Card room1 = new Card("Basement");
		Card room2 = new Card("Bathroom");
		Card person1 = new Card("Bob");
		
		//set the desired solution
		Solution answer = new Solution(room1, person1, weapon1);
		board.setSolution(answer);
		
		//accusation should be true
		assertTrue(board.checkAccusation(answer));
		
		//accusation should be false
		Solution checkAnswer = new Solution(room2, person1, weapon1);
		assertFalse(board.checkAccusation(checkAnswer));
	}
	
	//Tests the handleSuggestion method
	@Test
	public void testSuggestionHandle() {
		//create some test cards
		Card weapon1 = new Card("Bat");
		Card weapon2 = new Card("Knife");
		Card weapon3 = new Card("Gun");
		Card weapon4 = new Card("Duct Tape");
		Card room1 = new Card("Basement");
		Card room2 = new Card("Bathroom");
		Card room3 = new Card("Garage");
		Card room4 = new Card("Patio");
		Card person1 = new Card("Bob");
		Card person2 = new Card("Bill");
		Card person3 = new Card("Charlie");
		Card person4 = new Card("Steve");
		
		//create a test players and set their hands
		HumanPlayer tester = new HumanPlayer("Bob", "Red", 1, 1);
		ComputerPlayer tester2 = new ComputerPlayer("Bill", "Blue", 2, 2);
		ComputerPlayer tester3 = new ComputerPlayer("Charlie", "Orange", 3, 3);
		tester.setHand(room1, person1, weapon1);
		tester2.setHand(room2, person2, weapon2);
		tester3.setHand(room3, person3, weapon3);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(tester);
		players.add(tester2);
		players.add(tester3);
		board.setPlayers(players);
		
		//tests for when none of the players can disprove
		assertEquals(board.handleSuggestion(tester, room4, person4, weapon4), null);
		//tests for when player making suggestion can disprove
		assertEquals(board.handleSuggestion(tester, room1, person1, person1), null);
		//tests for when both other players can disprove
		assertEquals(board.handleSuggestion(tester, room4, person2, weapon3), person2);
	}
	
	//Tests the disproveSuggestion method
	@Test
	public void testSuggestionDisprove() {
		//create some test cards
		Card weapon1 = new Card("Bat");
		Card weapon2 = new Card("Knife");
		Card room1 = new Card("Basement");
		Card room2 = new Card("Bathroom");
		Card person1 = new Card("Bob");
		Card person2 = new Card("Bill");
		
		//create a test player and set their hand
		HumanPlayer tester = new HumanPlayer("Bob", "Red", 1, 1);
		tester.setHand(room1, person1, weapon1);
		
		//tests for if suggestion can be disproved by 1 card or no card
		assertEquals(tester.DisproveSuggestion(room1, person2, weapon2), room1);
		assertEquals(tester.DisproveSuggestion(room2, person2, weapon2), null);
		
		//tests for if the suggestion can be disproved by multiple cards in player hand
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		for(int i = 0; i < 50; i++) {
			if(tester.DisproveSuggestion(room1, person1, weapon1) == room1) {
				count1++;
			}
			else if(tester.DisproveSuggestion(room1, person1, weapon1) == person1) {
				count2++;
			}
			else {
				count3++;
			}
		}
		assertTrue(count1 > 0);
		assertTrue(count2 > 0);
		assertTrue(count3 > 0);
	}

}

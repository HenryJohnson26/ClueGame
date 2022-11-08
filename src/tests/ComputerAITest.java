package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class ComputerAITest {
	private static Board board;
	private static Player computer;

	@BeforeAll
	public static void setup(){
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		computer = new ComputerPlayer("computer", "gray", 1, 1);
	}
	
	@Test
	void testComputerSuggestion() {
		//create a suggustjion that returns a solution
		//check that its suggestion is not null and that it does not contain one of its in hand or seen cards
		computer.setHand(board.getRoomCards().get(0), board.getPlayerCards().get(0), board.getWeaponCards().get(0));
		computer.setSeen(board.getRoomCards().get(1));
		Room room = board.getRoomMap().get('M');
		Solution suggestion = ((ComputerPlayer) computer).createSuggestion(room, board);
		assertTrue(suggestion!=null);
		assertFalse(computer.getHand().contains(suggestion.getPersonSolution())
				|| computer.getHand().contains(suggestion.getRoomSolution())
				 || computer.getHand().contains(suggestion.getWeaponSolution()));
		assertFalse(computer.getSeen().contains(suggestion.getPersonSolution())
				|| computer.getSeen().contains(suggestion.getRoomSolution())
				 || computer.getSeen().contains(suggestion.getWeaponSolution()));
	}
	
	@Test
	void testComputerMovement() {
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>();
		BoardCell walkway1 = new BoardCell(2, 2, 'W');
		BoardCell walkway2 = new BoardCell(2, 3, 'W');
		BoardCell room = board.getRoomMap().get('L').getCenterCell();
		targets.add(walkway2);
		targets.add(walkway1);
		targets.add(room);

		assertEquals(((ComputerPlayer)computer).selectTarget(targets, board), room);
		
		targets.remove(room);
		BoardCell walkway3 = new BoardCell(4, 4, 'W');
		targets.add(walkway3);
		
		int walkway1counter = 0;
		int walkway2counter = 0;
		int walkway3counter = 0;
		for(int i = 0; i < 50; i++) {
			if(((ComputerPlayer)computer).selectTarget(targets, board)==walkway1)walkway1counter++;
			if(((ComputerPlayer)computer).selectTarget(targets, board)==walkway2)walkway2counter++;
			if(((ComputerPlayer)computer).selectTarget(targets, board)==walkway3)walkway3counter++;
		}
		
		assertTrue(walkway1counter > 0);
		assertTrue(walkway2counter > 0);
		assertTrue(walkway3counter > 0);
		
	}
	

}

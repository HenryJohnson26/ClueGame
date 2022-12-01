package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

import clueGame.BoardCell;

public class BoardAdjTargetTest {
	private static Board board;


	// We make the Board static because we can load it one time and 
	// then do all the tests. 
		
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		board.setCurrentPlayer(0);
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		//Testing the Basement, it has one door and one secret passage
		Set<BoardCell> testList = board.getAdjList(14, 31);
		assertEquals(2, testList.size());
		//door
		assertTrue(testList.contains(board.getCell(17, 31)));
		//secret passage
		assertTrue(testList.contains(board.getCell(2, 2)));
		
		// Testing the Gym which has two doors
		testList = board.getAdjList(14, 23);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(13, 21)));
		assertTrue(testList.contains(board.getCell(13, 26)));

		// Testing bathroom, it has two doors
		testList = board.getAdjList(6, 17);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(14, 19)));
		assertTrue(testList.contains(board.getCell(7, 20)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(13, 6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 6)));
		assertTrue(testList.contains(board.getCell(13, 7)));
		//room adjacency
		assertTrue(testList.contains(board.getCell(19, 3)));

		testList = board.getAdjList(7, 11);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 10)));
		assertTrue(testList.contains(board.getCell(7, 12)));
		assertTrue(testList.contains(board.getCell(8, 11)));
		// room adjacency
		assertTrue(testList.contains(board.getCell(3, 11)));

		
		testList = board.getAdjList(21, 31);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 30)));
		assertTrue(testList.contains(board.getCell(20, 31)));
		//room adjacency
		assertTrue(testList.contains(board.getCell(23, 32)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(25, 9);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(24, 9)));
		assertTrue(testList.contains(board.getCell(25, 8)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(11, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(10, 7)));
		assertTrue(testList.contains(board.getCell(12, 7)));
		assertTrue(testList.contains(board.getCell(11, 6)));
		assertTrue(testList.contains(board.getCell(11, 8)));

		// Test adjacent to walkways
		testList = board.getAdjList(11, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(10, 13)));
		assertTrue(testList.contains(board.getCell(12, 13)));
		assertTrue(testList.contains(board.getCell(11, 12)));
		assertTrue(testList.contains(board.getCell(11, 14)));

		// Test next to closet
		testList = board.getAdjList(8,6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 6)));
		assertTrue(testList.contains(board.getCell(9, 6)));
		assertTrue(testList.contains(board.getCell(8, 7)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInPatio() {
		// test a roll of 1
		board.calcTargets(board.getCell(5, 30), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		//door
		assertTrue(targets.contains(board.getCell(7, 26)));
		//secret passage
		assertTrue(targets.contains(board.getCell(19, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(5, 30), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		//secret passage
		assertTrue(targets.contains(board.getCell(19, 3)));
		assertTrue(targets.contains(board.getCell(7, 24)));	
		assertTrue(targets.contains(board.getCell(8, 25)));
		assertTrue(targets.contains(board.getCell(9, 26)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(5, 30), 4);
		targets= board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCell(7, 23)));
		assertTrue(targets.contains(board.getCell(8, 24)));	
		assertTrue(targets.contains(board.getCell(7, 25)));
		assertTrue(targets.contains(board.getCell(8, 26)));	
	}
	
	@Test
	public void testTargetsInStorageRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(4, 6)));
		board.getCell(4, 7).setOccupied(false);
		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 2), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());

		assertTrue(targets.contains(board.getCell(3, 11)));
		assertTrue(targets.contains(board.getCell(5, 7)));	
		assertTrue(targets.contains(board.getCell(6, 6)));
		assertTrue(targets.contains(board.getCell(3, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(2, 2), 4);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(3, 11)));
		assertTrue(targets.contains(board.getCell(1, 6)));	
		assertTrue(targets.contains(board.getCell(4, 7)));
		assertTrue(targets.contains(board.getCell(7, 6)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(18, 10), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(17, 10)));
		assertTrue(targets.contains(board.getCell(18, 9)));	
		assertTrue(targets.contains(board.getCell(21, 13)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(18, 10), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(21, 13)));
		assertTrue(targets.contains(board.getCell(20, 9)));
		assertTrue(targets.contains(board.getCell(16, 11)));	
		assertTrue(targets.contains(board.getCell(15, 10)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(18, 10), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(21, 13)));
		assertTrue(targets.contains(board.getCell(21, 9)));
		assertTrue(targets.contains(board.getCell(16, 12)));	
		assertTrue(targets.contains(board.getCell(16, 8)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 34), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(17, 33)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 34), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(19, 33)));
		assertTrue(targets.contains(board.getCell(18, 32)));
		assertTrue(targets.contains(board.getCell(17, 31)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(17, 34), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(14, 31)));
		assertTrue(targets.contains(board.getCell(17, 30)));
		assertTrue(targets.contains(board.getCell(17, 32)));	
		assertTrue(targets.contains(board.getCell(18, 31)));	

	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 34), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(10, 34)));
		assertTrue(targets.contains(board.getCell(11, 33)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 34), 3);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 34)));
		assertTrue(targets.contains(board.getCell(11, 31)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 34), 4);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(11, 30)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(8, 21).setOccupied(true);
		board.calcTargets(board.getCell(6, 21), 4);
		board.getCell(8, 21).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(5, 20)));
		assertTrue(targets.contains(board.getCell(7, 24)));
		assertTrue( targets.contains( board.getCell(4, 23))) ;
		assertFalse( targets.contains( board.getCell(8, 21))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		//setting room as occupied
		board.getCell(21, 13).setOccupied(true);
		board.getCell(19, 20).setOccupied(true);
		board.calcTargets(board.getCell(19, 17), 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(21, 13)));	
		assertTrue(targets.contains(board.getCell(18, 17)));	
		assertTrue(targets.contains(board.getCell(20, 17)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(4, 7).setOccupied(true);
		board.calcTargets(board.getCell(3, 11), 3);
		board.getCell(4, 7).setOccupied(false);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(7, 13)));
		assertTrue(targets.contains(board.getCell(7, 9)));	
		assertTrue(targets.contains(board.getCell(9, 11)));

	}
}

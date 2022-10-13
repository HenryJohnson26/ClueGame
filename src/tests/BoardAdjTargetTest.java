package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		//Testing the Basement, it has one door and one secret passage
		Set<BoardCell> testList = board.getAdjList(14, 32);
		assertEquals(2, testList.size());
		//door
		assertTrue(testList.contains(board.getCell(17, 32)));
		//secret passage
		assertTrue(testList.contains(board.getCell(16, 35)));
		
		// Testing the Gym which has two doors
		testList = board.getAdjList(16, 24);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(13, 22)));
		assertTrue(testList.contains(board.getCell(13, 27)));

		// Testing bathroom, it has two doors
		testList = board.getAdjList(6, 17);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(14, 20)));
		assertTrue(testList.contains(board.getCell(7, 21)));
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

		
		testList = board.getAdjList(21, 32);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 31)));
		assertTrue(testList.contains(board.getCell(20, 32)));
		//room adjacency
		assertTrue(testList.contains(board.getCell(23, 33)));
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
		board.calcTargets(board.getCell(5, 31), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		//door
		assertTrue(targets.contains(board.getCell(7, 27)));
		//secret passage
		assertTrue(targets.contains(board.getCell(7, 35)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(5, 31), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		//secret passage
		assertTrue(targets.contains(board.getCell(7, 35)));
		assertTrue(targets.contains(board.getCell(7, 25)));	
		assertTrue(targets.contains(board.getCell(8, 26)));
		assertTrue(targets.contains(board.getCell(9, 27)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(5, 31), 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(7, 24)));
		assertTrue(targets.contains(board.getCell(8, 25)));	
		assertTrue(targets.contains(board.getCell(7, 26)));
		assertTrue(targets.contains(board.getCell(8, 27)));	
	}
	
	@Test
	public void testTargetsInStorageRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(22, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(4, 6)));
		
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
		board.calcTargets(board.getCell(17, 35), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(19, 34)));
		assertTrue(targets.contains(board.getCell(18, 33)));
		assertTrue(targets.contains(board.getCell(1, 32)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(17, 35), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(14, 32)));
		assertTrue(targets.contains(board.getCell(17, 31)));
		assertTrue(targets.contains(board.getCell(17, 33)));	
		assertTrue(targets.contains(board.getCell(18, 32)));	

	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 34), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(10, 35)));
		assertTrue(targets.contains(board.getCell(11, 34)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 35), 3);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 35)));
		assertTrue(targets.contains(board.getCell(11, 32)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 35), 4);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 31)));
		assertTrue(targets.contains(board.getCell(17, 35)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(8, 22).setOccupied(true);
		board.calcTargets(board.getCell(6, 22), 4);
		board.getCell(14, 24).setOccupied(true);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(5, 21)));
		assertTrue(targets.contains(board.getCell(10, 22)));
		assertTrue(targets.contains(board.getCell(7, 25)));	
		assertFalse( targets.contains( board.getCell(8, 22))) ;
		assertFalse( targets.contains( board.getCell(4, 24))) ;
	
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
		board.getCell(8, 10).setOccupied(false);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(2, 7)));
		assertTrue(targets.contains(board.getCell(6, 7)));	
		assertTrue(targets.contains(board.getCell(9, 11)));

	}
}

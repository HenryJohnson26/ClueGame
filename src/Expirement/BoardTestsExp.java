package Expirement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setup() {
		board = new TestBoard();
	}
	
	//Tests adjacency list for top left corner
	@Test
	public void TestAdjacency() {
		TestBoardCell cell = board.getCell(0, 0);	
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	//Tests adjacency list for bottom right corner
	@Test
	public void TestAdjacency2() {
		TestBoardCell cell1 = board.getCell(3, 3);	
		Set<TestBoardCell> testList2 = cell1.getAdjList();
		Assert.assertTrue(testList2.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList2.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList2.size());
	}
	
	//Tests adjacency list for right edge
	@Test
	public void TestAdjacency3() {
		TestBoardCell cell2 = board.getCell(1, 3);
		Set<TestBoardCell> testList3 = cell2.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList3.size());
	}
	
	//Tests adjacency list for left edge
	@Test 
	public void TestAdjacency4() {
		TestBoardCell cell3 = board.getCell(3, 0);
		Set<TestBoardCell> testList4 = cell3.getAdjList();
		Assert.assertTrue(testList4.contains(board.getCell(3, 1)));
		Assert.assertTrue(testList4.contains(board.getCell(2, 0)));
		Assert.assertEquals(2, testList4.size());
	}
	
	
	//Tests the creation of targets
	@Test
	public void TestTargets() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	//Tests for when there are occupied squares and rooms on the board, but not in range for the player to move to
	@Test
	public void TestTargets2() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	//Tests for an occupied room
	@Test
	public void TestTargets3() {
		board.getCell(2, 2).setRoom(true);
		board.getCell(2,2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertFalse(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	//Tests for an empty board
	@Test
	public void TestTargets4() {
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	//Tests for an occupied square in the player's movement range
	@Test
	public void TestTargets5() {
		board.getCell(1, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertFalse(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	
//	public BoardTestsExp(int tests) {
//		
//	}
}

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

class FileInitTests {
	//constants to test whether file was loaded correctly
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 34;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	//Tests to ensure data is loaded correctly
	@Test
	public void testRoomLabels() {
		assertEquals("Bedroom", board.getRoom('B').getName() );
		assertEquals("Living Room", board.getRoom('L').getName() );
		assertEquals("Storage Room", board.getRoom('S').getName() );
		assertEquals("Bathroom", board.getRoom('A').getName() );
		assertEquals("Basement", board.getRoom('M').getName() );
		assertEquals("Balconey", board.getRoom('C').getName() );
		assertEquals("Patio", board.getRoom('P').getName() );
		assertEquals("Garage", board.getRoom('G').getName() );
		assertEquals("Gym", board.getRoom('Y').getName() );
	}
	
	//Tests to ensure board dimensions are correct
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	//Tests for a doorway in each direction as well as cells that aren't doors
	@Test
	public void DoorDirections() {
		BoardCell cell = board.getCell(4, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(14, 20);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(4, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(21, 32);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(3, 7);
		assertFalse(cell.isDoorway());
	}
	
	//Tests that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway()) {
					numDoors++;
				}
			}
		Assert.assertEquals(13, numDoors);
	}
	
	//Tests rooms to make sure initial is correct
	@Test
	public void testRooms() {
		//Tests for standard room location
		BoardCell cell = board.getCell( 24, 15);
		Room room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals( room.getName(), "Living Room" );
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isDoorway());

		//Tests for label
		cell = board.getCell(1, 11);
		room = board.getRoom( cell );
		assertTrue( room != null );
		assertEquals( room.getName(), "Bedroom" );
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		//Tests for room center
		cell = board.getCell(19, 3);
		room = board.getRoom( cell );
		assertTrue( room != null );
		assertEquals( room.getName(), "Garage" );
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		//Tests for secret passage
		cell = board.getCell(7, 35);
		room = board.getRoom( cell );
		assertTrue( room != null );
		assertEquals( room.getName(), "Patio" );
		assertTrue( cell.getSecretPassage() == 'G' );
		
		//Tests for walkway
		cell = board.getCell(11, 32);
		room = board.getRoom( cell );
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" );
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		//Tests for closet
		cell = board.getCell(9, 3);
		room = board.getRoom( cell );
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" );
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
	}

}

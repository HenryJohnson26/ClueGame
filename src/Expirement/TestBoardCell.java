package Expirement;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int column;
	private Set<TestBoardCell> adjacencyList;
	private boolean isRoom;
	private boolean isOccupied;
	
	public TestBoardCell(int r, int c) {
		adjacencyList = new HashSet<>();
		row = r;
		column = c;
	}
	
	public void createAdjList(TestBoard board) {
		//if it is within bounds add it
		if(row-1>=0) {
			 this.addAdjacency(board.getCell(row-1,column));
		}
		if(row+1<TestBoard.ROWS) {
			 this.addAdjacency(board.getCell(row+1,column));
		}
		if(column-1>=0) {
			 this.addAdjacency(board.getCell(row,column-1));
		}
		if(column+1<TestBoard.COLS) {
			 this.addAdjacency(board.getCell(row,column+1));
		}
	}
	
	public void addAdjacency( TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList(){
		return adjacencyList;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	public boolean isRoom() {
		return isRoom;
	}
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	
	@Override
	public String toString() {
			return "col: "+column+" row "+row;
	}
}

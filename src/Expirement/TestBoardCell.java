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
	
}

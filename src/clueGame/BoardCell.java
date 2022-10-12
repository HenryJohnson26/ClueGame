package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	//instance variables
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private char secretPassage;
	private Set<BoardCell> adjList = new HashSet();
	private boolean isDoorway = false;
	private boolean isLabel = false;
	private boolean isRoomCenter = false;
	
	//constructor to create new cells
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
	}
	
	//methods to determine the type of cell
	public boolean isDoorway() {
		return isDoorway;
	}
	
	public boolean isLabel() {
		return isLabel;
	}
	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	//getters and setters
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}
	
	public void setDoor() {
		isDoorway = true;
	}
	
	public void setLabel() {
		isLabel = true;
	}
	
	public void setRoomCenter() {
		isRoomCenter = true;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}
	
	public void setSecretPassage(char room) {
		secretPassage = room;
	}
	
	public char getInitial() {
		return initial;
	}

}

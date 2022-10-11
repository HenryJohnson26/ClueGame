package clueGame;

import java.util.Set;

public class BoardCell {
	//instance variables
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean isDoorway;
	private boolean isLabel;
	private boolean isRoomCenter;
	
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
	}
	
	//skeleton methods
	public boolean isDoorway() {
		return isDoorway;
	}
	
	public boolean isLabel() {
		return isLabel;
	}
	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	public DoorDirection getDoorDirection() {
		return null;
	}
	
	public char getSecretPassage() {
		char c = '@';
		return c;
	}

}

package clueGame;

public class BoardCell {
	//instance variables
	boolean isDoorway;
	boolean isLabel;
	boolean isRoomCenter;
	
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

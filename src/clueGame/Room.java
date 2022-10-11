package clueGame;

public class Room {
	//instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	//skeleton methods
	public String getName() {
		String name = "";
		return name;
	}
	
	public BoardCell getLabelCell() {
		return new BoardCell();
	}
	
	public BoardCell getCenterCell() {
		return new BoardCell();
	}

}

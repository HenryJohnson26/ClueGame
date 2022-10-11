package clueGame;

public class Room {
	//instance variables
	private String name;
	private String initial;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name, String initial) {
		this.name = name;
		this.initial = initial;
	}
	
	//skeleton methods
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return new BoardCell();
	}
	
	public BoardCell getCenterCell() {
		return new BoardCell();
	}

}

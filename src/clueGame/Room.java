package clueGame;

public class Room {
	//instance variables
	private String name;
	private String initial;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	//constructor that creates new rooms
	public Room(String name, String initial) {
		this.name = name;
		this.initial = initial;
	}
	
	//getters and setters
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return new BoardCell();
	}
	
	public void setLabelCell(BoardCell cell) {
		
	}
	
	public BoardCell getCenterCell() {
		return new BoardCell();
	}
	
	public void setCenterCell(BoardCell cell) {
		
	}

}

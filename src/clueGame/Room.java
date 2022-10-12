package clueGame;

public class Room {
	//instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	//constructor that creates new rooms
	public Room(String name) {
		this.name = name;
	}
	
	//getters and setters
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}

}

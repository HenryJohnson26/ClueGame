package clueGame;

import java.util.HashSet;
import java.util.Set;

import Expirement.TestBoard;
import Expirement.TestBoardCell;

public class BoardCell {
	//instance variables
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private char secretPassage;
	private Set<BoardCell> adjList = new HashSet<>();
	private boolean isDoorway = false;
	private boolean isLabel = false;
	private boolean isRoomCenter = false;
	private boolean isOccupied = false;
	private boolean isSecretPassage = false;
	
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
		isSecretPassage = true;
	}
	
	public boolean getIsSecretPassage() {
		return isSecretPassage;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public Set<BoardCell> cellGetAdjList(){
		return adjList;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
	public boolean isRoom() {
		if(initial != 'W' && initial != 'X') {
			return true;
		}
		return false;
	}
	
	public void createAdjList(Board board) {
		//add adjacency from room to corresponding room from secret passage
		if(this.getIsSecretPassage()) {
			board.getRoomMap().get(board.getCell(row, col).getInitial()).getCenterCell().addAdjacency(board.getRoomMap().get(secretPassage).getCenterCell());
		}
		//adds adjacency to room center cell
		else if(this.isDoorway()) {
			if(this.getDoorDirection() == DoorDirection.UP) {
				board.getRoomMap().get(board.getCell(row-1, col).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row-1, col).getInitial()).getCenterCell());
			}
			else if(this.getDoorDirection() == DoorDirection.DOWN) {
				board.getRoomMap().get(board.getCell(row+1, col).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row+1, col).getInitial()).getCenterCell());
			}
			else if(this.getDoorDirection() == DoorDirection.LEFT) {
				board.getRoomMap().get(board.getCell(row, col-1).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row, col-1).getInitial()).getCenterCell());
			}
			else if(this.getDoorDirection() == DoorDirection.RIGHT) {
				board.getRoomMap().get(board.getCell(row, col+1).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row, col+1).getInitial()).getCenterCell());
			}
		}
		//default cases for walkway cells
		if(initial == 'W') {
			if(row-1>=0 && board.getCell(row-1, col).getInitial() == 'W') {
				 this.addAdjacency(board.getCell(row-1,col));
			}
			if(row+1<board.getNumRows() && board.getCell(row+1, col).getInitial() == 'W') {
				 this.addAdjacency(board.getCell(row+1,col));
			}
			if(col-1>=0 && board.getCell(row, col-1).getInitial() == 'W') {
				 this.addAdjacency(board.getCell(row,col-1));
			}
			if(col+1<board.getNumColumns() && board.getCell(row, col+1).getInitial() == 'W') {
				 this.addAdjacency(board.getCell(row,col+1));
			}
		}
	}
	
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	@Override
	public String toString() {
		return "" + initial;
	}
	
}

package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	public static ArrayList<BoardCell> doorways = new ArrayList<BoardCell>();
	public static final int CELL_WIDTH = 21;
	
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
	
	public void createAdjList(Board board) {
		//add adjacency from room to corresponding room from secret passage
		if(this.getIsSecretPassage()) {
			board.getRoomMap().get(board.getCell(row, col).getInitial()).getCenterCell().addAdjacency(board.getRoomMap().get(secretPassage).getCenterCell());
		}
		//adds adjacency to room center cell
		else if(this.isDoorway()) {
			switch(this.getDoorDirection()) {
			case UP:
				board.getRoomMap().get(board.getCell(row-1, col).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row-1, col).getInitial()).getCenterCell());
				break;
			case DOWN:
				board.getRoomMap().get(board.getCell(row+1, col).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row+1, col).getInitial()).getCenterCell());
				break;
			case LEFT:
				board.getRoomMap().get(board.getCell(row, col-1).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row, col-1).getInitial()).getCenterCell());
				break;
			case RIGHT:
				board.getRoomMap().get(board.getCell(row, col+1).getInitial()).getCenterCell().addAdjacency(this);
				this.addAdjacency(board.getRoomMap().get(board.getCell(row, col+1).getInitial()).getCenterCell());
				break;
			default:
				break;
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

	//Add cell to adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}	
	
	//draws the boardCell
	public void drawCell(BoardCell cell, Set<BoardCell> targets, Graphics g, Board board) {
		//sets walkway cells to the appropriate color
		if(cell.getInitial() == 'W') {
			//draws the cell border
			g.setColor(Color.black);
			g.fillRect(CELL_WIDTH * col, CELL_WIDTH * row, CELL_WIDTH, CELL_WIDTH);
			//determines which cells need to be highlighted for human player movement
			if(board.getPlayers().get(board.getCurrentPlayer()) == board.getHumanPlayer()) {
				if(targets.contains(cell)) {
					g.setColor(Color.cyan);
				}
				else {
					g.setColor(Color.yellow);
				}
			}
			else {
				g.setColor(Color.yellow);
			}
			g.fillRect(CELL_WIDTH*col+1, CELL_WIDTH*row+1, CELL_WIDTH-1, CELL_WIDTH-1);
			
		}
		else if(cell.getInitial() == 'X') {
			g.setColor(Color.black);
			g.fillRect(CELL_WIDTH * col, CELL_WIDTH * row, CELL_WIDTH, CELL_WIDTH);
		}
		else {
			//checks to see if a room is in the targets list
			ArrayList<Character> roomChars = new ArrayList<Character>();
			for(BoardCell c : targets) {
				if(c.isRoomCenter()) {
					roomChars.add(c.getInitial());
				}
			}
			
			//sets all cells of a room to the appropriate color
			if(roomChars.contains(cell.getInitial())) {
				if(board.getPlayers().get(board.getCurrentPlayer()) == board.getHumanPlayer() ) {
					g.setColor(Color.cyan);
				}
			}
			else {
				g.setColor(Color.gray);
			}
			g.fillRect(CELL_WIDTH * col, CELL_WIDTH * row, CELL_WIDTH, CELL_WIDTH);
		}		
		
		if(cell.isDoorway()) {
			doorways.add(cell);
		}
	}
	
	//Draws the doorways for the board
	public void drawDoorways(BoardCell cell, Graphics g) {
		g.setColor(Color.blue);
		switch(cell.getDoorDirection()) {
			case UP:
				g.fillRect(CELL_WIDTH*col, CELL_WIDTH*row-5, CELL_WIDTH, 5);
				break;
			case DOWN:
				g.fillRect(CELL_WIDTH*col, CELL_WIDTH*row+CELL_WIDTH, CELL_WIDTH, 5);
				break;
			case LEFT:
				g.fillRect(CELL_WIDTH*col-5, CELL_WIDTH*row, 5, CELL_WIDTH);
				break;
			case RIGHT:
				g.fillRect(CELL_WIDTH*col+CELL_WIDTH, CELL_WIDTH*row, 5, CELL_WIDTH);
				break;
			default:
				break;
		 }
	}
	
	//Draws the room labels for each room
	public void drawRoomLabel(BoardCell cell, Graphics g, String roomLabel) {
		g.setColor(Color.blue);
		g.drawString(roomLabel, col*CELL_WIDTH, row*CELL_WIDTH+(CELL_WIDTH-1));
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
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "r: "+row+" c: "+col+" initial: "+initial;
	}
}

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	 //instance variables
	 private int numRows;
	 private int numCols;
	 private BoardCell[][] grid;
	 private String layoutConfigFile;
	 private String setupConfigFile;
	 private Map<Character, Room> roomMap = new HashMap<>();
	 private ArrayList<ArrayList<String>> cells = new ArrayList<>();
	 private FileReader input;
	 private Set<BoardCell> targets = new HashSet<>();
	 private Set<BoardCell> visited = new HashSet<>();
	
     //variable and methods used for singleton pattern
     private static Board theInstance = new Board();
     // constructor is private to ensure only one can be created
     private Board() {
            super();
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     //initialize the board (since we are using singleton pattern)
     public void initialize() {
    	 //handles exceptions
    	 try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());	
		}
    	 
    	 //Gets the number of rows and columns from the cells array list and initializes the grid
    	 numRows = cells.size();
    	 numCols = cells.get(0).size();
    	 grid = new BoardCell[numRows][numCols];
    	 
    	 //Creates the grid 
    	 for(int row = 0; row < cells.size(); row++) {
    		 for(int col = 0; col < cells.get(row).size(); col++) {
    			 grid[row][col] = new BoardCell(row, col, cells.get(row).get(col).charAt(0));
    			 grid[row][col].setDoorDirection(DoorDirection.NONE);
    			 
    			 //special cell cases
    			 if(cells.get(row).get(col).length() == 2) {
    				 String[] parse = cells.get(row).get(col).split("");
    				 switch(parse[1]) {
    				 case "^":
    					 grid[row][col].setDoorDirection(DoorDirection.UP);
    					 grid[row][col].setDoor();
    					 break;
    				 case ">":
    					 grid[row][col].setDoorDirection(DoorDirection.RIGHT);
    					 grid[row][col].setDoor();
    					 break;
    				 case "<":
    					 grid[row][col].setDoorDirection(DoorDirection.LEFT);
    					 grid[row][col].setDoor();
    					 break;
    				 case "v":
    					 grid[row][col].setDoorDirection(DoorDirection.DOWN);
    					 grid[row][col].setDoor();
    					 break;
    				 case "#":
    					 grid[row][col].setLabel();
    					 roomMap.get(parse[0].charAt(0)).setLabelCell(grid[row][col]);
    					 break;
    				 case "*":
    					 grid[row][col].setRoomCenter();
    					 roomMap.get(parse[0].charAt(0)).setCenterCell(grid[row][col]);
    					 break;
    				 default:
    					 grid[row][col].setSecretPassage(parse[1].charAt(0));
    					 break;
    				 }
    			 }
    		 }
    	 }
    	 createCellAdjList();
     }
     
    //creates an adjacency list for each cell
	private void createCellAdjList() {
    	 for(int i = 0; i < numRows; i++) {
    		 for(int j = 0; j < numCols; j++) {
    			 grid[i][j].createAdjList(theInstance);
    		 }
    	 }
	}
     
     //sets the layout and setup files to the specified files
     public void setConfigFiles(String layout, String setup) {
    	 layoutConfigFile = "data/" + layout;
    	 setupConfigFile = "data/" + setup;
     }
     
     //loads the setup config file and creates the appropriate rooms
     public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
    	 input = new FileReader(setupConfigFile);
    	 Scanner in = new Scanner(input);
    	 String line;
    	 Character label;
    	 while(in.hasNext()) {
    		 line = in.nextLine();
    		 String[] parse = line.split(", ", -1);
    		 if(parse.length > 1) {
    			 //Throws exception if in the wrong format
    			 if(parse[2].length() != 1 || parse.length != 3) {
        			 throw new BadConfigFormatException("Incorrect format on " + setupConfigFile);
        		 } 
    			 else {
    				Room r = new Room(parse[1]);
    	    		label = parse[2].charAt(0);
    	    		roomMap.put(label, r);
        		 }
    		 }		 
    	 }
    	 in.close();
     }
     
     //loads the layout config file and puts each value into an array list
     public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
    	 input = new FileReader(layoutConfigFile);
    	 Scanner in = new Scanner(input);
    	 String line;
    	 int counter = 0;
    	 Character label;
    	 ArrayList<String> cell;
    	 while(in.hasNext()) {
    		 cell = new ArrayList<>();
    		 line = in.nextLine();
    		 String[] parse = line.split(",", -1);
    		 for(int i = 0; i < parse.length; i++) {
    			 if(!parse[i].equals("")) {
    				 label = parse[i].charAt(0);
    				 //throws exception if the label is not a valid room
        			 if(!roomMap.containsKey(label)) {
        				 throw new BadConfigFormatException("Invalid cell value at " + counter + "," + i + ". Invalid room character.");
        			 }
        			 cell.add(parse[i]);
    			 }
    		 }
    		 cells.add(cell);
    		 counter++;
    	 }
    	 
    	 //throws exception if the size of the columns is not consistent
    	 int boardWidth = cells.get(0).size();
    	 for(ArrayList<String> s : cells) {
    		 if(boardWidth != s.size()) {
    			 throw new BadConfigFormatException("Variable board width on layout file");
    		 }
    	 }
    	 in.close(); 
     }
     
   //calculates the targets of the given cell and path length
   	 private void _calcTargets(BoardCell startCell, int pathlength) {
   		//checks if it the cell is visited
   		if(!visited.contains(startCell)) {
   			//if not, add it and run the following
   			visited.add(startCell);
   			//checks to see if it is a target
   			if(pathlength == 0) {
   				//see if cell is occupied and if it is a room
   				if(!startCell.getOccupied()||startCell.isRoom()) {
   					//if it is not an occupied walkway, add it to targets
   					targets.add(startCell);
   				}
   				visited.remove(startCell);
   				return;
   			} 
   			else {
   				if(startCell.isRoom()) {
   					targets.add(startCell);
   				}
   				for(BoardCell cell : startCell.cellGetAdjList()) {
   					if(cell.isRoom()) {
   						targets.add(cell);
   					}
   					else if(!cell.getOccupied()) {
   						_calcTargets(cell, pathlength - 1);	
   					}
   					
   				}
   				visited.remove(startCell);
   			}
   		}
   		return;	
   	 }
   	 
   	 //helper method to calcTargets
   	 public void calcTargets(BoardCell startCell, int pathlength) {
   		 targets.clear();
   		 _calcTargets(startCell, pathlength); 
   		 targets.remove(startCell);
   	 }
     
     //returns the room based on the char symbol given
     public Room getRoom(char roomSymbol) {
    	 return roomMap.get(roomSymbol);
     }
     
     //returns the room based on the value of the cell
     public Room getRoom(BoardCell cell) {
    	 return roomMap.get(cell.getInitial());
     }
     
     //other getters and setters
     public int getNumRows() {
    	 return numRows;
     }
     
     public int getNumColumns() {
    	 return numCols;
     }
     
     public BoardCell getCell(int row, int col) {
    	 return grid[row][col];
     }

	 public Set<BoardCell> getAdjList(int row,  int col){
		 return getCell(row,col).cellGetAdjList();
	}
	 
	 public Set<BoardCell> getTargets(){
		return targets; 
	 }
	 
	 public Map<Character, Room> getRoomMap() {
		 return roomMap;
	 }
}
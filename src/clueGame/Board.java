package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Board {
	 //instance variables
	 private int numRows;
	 private int numCols;
	 private BoardCell[][] grid;
	 private String layoutConfigFile;
	 private String setupConfigFile;
	 private Map<Character, Room> roomMap;
	 private ArrayList<ArrayList<String>> cells;
	 private ArrayList<Room> rooms;
	 private FileReader input;
	
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
    	 for(int i = 0; i < cells.size(); i++) {
    		 for(int j = 0; j < cells.get(i).size(); j++) {
    			 grid[i][j] = new BoardCell(i, j, cells.get(i).get(j).charAt(0));
    		 }
    	 }
     }
     
     
     //sets the layout and setup files to the specified files
     public void setConfigFiles(String layout, String setup) {
    	 layoutConfigFile = layout;
    	 setupConfigFile = setup;
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
    	 ArrayList<String> cell = new ArrayList();
    	 while(in.hasNext()) {
    		 line = in.nextLine();
    		 String[] parse = line.split(",", -1);
    		 for(int i = 0; i < parse.length; i++) {
    			 if(roomMap.containsKey(parse[i].charAt(0))) {
    				 throw new BadConfigFormatException("Invalid cell value at " + counter + "," + i + ". Invalid room character.");
    			 }
    			 cell.add(parse[i]);
    		 }
    		 cells.add(cell);
    		 counter++;
    		 cell.clear();
    	 }
    	 int boardWidth = cells.get(0).size();
    	 for(ArrayList<String> s : cells) {
    		 if(boardWidth != s.size()) {
    			 throw new BadConfigFormatException("Variable board width on layout file");
    		 }
    	 }
    	 in.close(); 
     }
     
     //returns the room based on the char symbol given
     public Room getRoom(char roomSymbol) {
    	 return roomMap.get(roomSymbol);
     }
     
     //returns the room based on the value of the cell
     public Room getRoom(BoardCell cell) {
    	 return roomMap.get(cell.getInitial());
     }
     
     //other getters
     public int getNumRows() {
    	 return numRows;
     }
     
     public int getNumColumns() {
    	 return numCols;
     }
     
     public BoardCell getCell(int row, int col) {
    	 return grid[row][col];
     }
}

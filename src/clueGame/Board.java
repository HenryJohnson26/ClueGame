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
    	 numRows = cells.size();
    	 numCols = cells.get(0).size();
    	 grid = new BoardCell[numRows][numCols];
     }
     
     
     //sets the layout and setup files to the specified files
     public void setConfigFiles(String layout, String setup) {
    	 layoutConfigFile = layout;
    	 setupConfigFile = setup;
     }
     
     //loads the setup config file and creates the appropriate rooms
     public void loadSetupConfig() throws FileNotFoundException {
    	 input = new FileReader(setupConfigFile);
    	 Scanner in = new Scanner(input);
    	 String line;
    	 while(in.hasNext()) {
    		 line = in.nextLine();
    		 String[] parse = line.split(", ", -1);
    		 if(parse.length > 1) {
    			Room r = new Room(parse[1], parse[2]); 
    			rooms.add(r);
    		 }		 
    	 }
    	 in.close();
     }
     
     //loads the layout config file and puts each value into an array list
     public void loadLayoutConfig() throws FileNotFoundException {
    	 input = new FileReader(layoutConfigFile);
    	 Scanner in = new Scanner(input);
    	 String line;
    	 while(in.hasNext()) {
    		 ArrayList<String> cell = new ArrayList();
    		 line = in.nextLine();
    		 String[] parse = line.split(",", -1);
    		 for(int i = 0; i < parse.length; i++) {
    			 cell.add(parse[i]);
    		 }
    		 cells.add(cell);
    	 }
    	 in.close(); 
     }
     
     public Room getRoom(char roomSymbol) {
    	 return new Room();
     }
     
     public Room getRoom(BoardCell cell) {
    	 return new Room();
     }
     
     public int getNumRows() {
    	 return numRows;
     }
     
     public int getNumColumns() {
    	 return numCols;
     }
     
     public BoardCell getCell(int row, int col) {
    	 return new BoardCell();
     }
}

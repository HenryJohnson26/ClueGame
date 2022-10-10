package clueGame;

public class Board {
	//instance variables
	private int numRows;
	private int numCols;
	
	
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
     }
     
     
     //skeleton methods
     public void setConfigFiles(String layout, String setup) {
     }
     
     public void loadSetupConfig() {
    	 
     }
     
     public void loadLayoutConfig() {
    	 
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

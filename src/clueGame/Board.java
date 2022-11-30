package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel {
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
	 private ArrayList<Player> players = new ArrayList<Player>();
	 private ArrayList<Card> playerCards = new ArrayList<Card>();
	 private ArrayList<Card> roomCards = new ArrayList<Card>();
	 private ArrayList<Card> weaponCards = new ArrayList<Card>();
	 private ArrayList<Card> deck = new ArrayList<Card>();
	 private Solution solution;
	 private Random random = new Random();
	 private Player humanPlayer;
	 private boolean finishTurn = true;
	 private int currentPlayer = -1;
	 private int roll;

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
    	 makeGrid();
    	 createCellAdjList();
    	 
    	 //make solution
    	 Card room = roomCards.get(random.nextInt(roomCards.size()-1));
    	 deck.remove(room);
    	 Card person = playerCards.get(random.nextInt(playerCards.size()-1));
    	 deck.remove(person);
    	 Card weapon = weaponCards.get(random.nextInt(weaponCards.size()-1));
    	 deck.remove(weapon);
    	 solution = new Solution(room, person, weapon);
    	 
    	 deal();
     }
     
    //makes the grid of BoardCells for the game board
	private void makeGrid() {
		for(int row = 0; row < cells.size(); row++) {
    		 for(int col = 0; col < cells.get(row).size(); col++) {
    			 grid[row][col] = new BoardCell(row, col, cells.get(row).get(col).charAt(0));
    			 grid[row][col].setDoorDirection(DoorDirection.NONE);
    			 
    			 //special cell cases
    			 if(cells.get(row).get(col).length() == 2) {
    				 String[] parse = cells.get(row).get(col).split("");
    				 switch(parse[1]) {
    				 //cell is a door
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
    				 //cell is a labelCell of a room
    				 case "#":
    					 grid[row][col].setLabel();
    					 roomMap.get(parse[0].charAt(0)).setLabelCell(grid[row][col]);
    					 break;
    				 //cell is a centerCell of a room
    				 case "*":
    					 grid[row][col].setRoomCenter();
    					 roomMap.get(parse[0].charAt(0)).setCenterCell(grid[row][col]);
    					 break;
    				 //cell is a secret passage
    				 default:
    					 grid[row][col].setSecretPassage(parse[1].charAt(0));
    					 break;
    				 }
    			 }
    		 }
    	 }
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
    	 //TODO:refractor
    	 input = new FileReader(setupConfigFile);
    	 Scanner in = new Scanner(input);
    	 String line;
    	 Character label;
    	 while(in.hasNext()) {
    		 line = in.nextLine();
    		 String[] parse = line.split(", ", -1);
    		 if(parse.length > 1) {
    			 //Throws exception if in the wrong format
    			 
    			 //first checks if its a room or a space
	    		if(parse[0].equals("Room") || parse[0].equals("Space")) {
	    			 makeRoomObjects(parse);
    			 }
	    		 //TODO:write badconfigFile for non rooms
	    		else if(parse[0].equals("Player")) {
	    			makePlayerObject(parse);
	    		}
	    		else if(parse[0].equals("Weapon")) {
	    			makeWeaponObject(parse);
	    		}
    		 }	
    	 }
    	 in.close();
     }
     
    //helper method that creates a room object and room card
	private void makeRoomObjects(String[] parse) throws BadConfigFormatException, FileNotFoundException {
		Character label;
		if(parse[2].length() != 1 || parse.length != 3) {
			 throw new BadConfigFormatException("Incorrect room format on file " + setupConfigFile);
		 } 
		 else {
			Room r = new Room(parse[1]);
			label = parse[2].charAt(0);
			roomMap.put(label, r);
			if(parse[0].equals("Room")) {
			Card card = new Card(parse[1]);
			card.setType(CardType.ROOM);
			roomCards.add(card);
			deck.add(card);
			}
		 }
	}
	
	//helper method that creates a weapon card
	private void makeWeaponObject(String[] parse) throws BadConfigFormatException, FileNotFoundException {
		//throws if it has the wrong number of parses
		if(parse.length!=2) {
			throw new BadConfigFormatException("Incorrect weapon format on file " + setupConfigFile);
		}
		else {
			Card card = new Card(parse[1]);
			card.setType(CardType.WEAPON);
			weaponCards.add(card);
			deck.add(card);
		}
	}
	
	//helper method that creates a player object and player card
	private void makePlayerObject(String[] parse) throws BadConfigFormatException, FileNotFoundException {
		if(parse.length!=6) {
			throw new BadConfigFormatException("Incorrect player format on file " + setupConfigFile);
		}
		else {
			//catches a NumberFormatException form Integer.parseInt and throws a badConfigFormat instead
			try {
			if(parse[5].equals("human")) {
				humanPlayer = new HumanPlayer(parse[1], parse[2],
						(int)(Integer.parseInt(parse[3])), (int)(Integer.parseInt(parse[4])));
				players.add(humanPlayer);
			}
			else {
				players.add(new ComputerPlayer(parse[1], parse[2],
						(int)(Integer.parseInt(parse[3])), (int)(Integer.parseInt(parse[4]))));
			}
			Card card = new Card(parse[1]);
			card.setType(CardType.PLAYER);
			playerCards.add(card);
			deck.add(card);
		}catch(NumberFormatException e) {
			throw new BadConfigFormatException("Incorrect player format on file " + setupConfigFile + "invalid row or column number");
			}
		}
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
   		 if(!players.get(currentPlayer).isWasMoved()) {
   		 targets.remove(startCell);
   		 }
   		 players.get(currentPlayer).setWasMoved(false);
   	 }
   	 
   	 //deals cards to players
   	 public void deal() {
   		 Collections.shuffle(deck);
   		 int counter = 0;
   		 for(Player p : players) {
   			 for(int i = 0; i<3;i++) {
   				 p.getHand().add(deck.get(counter));
   				 counter++;
   			 }
   		 }
   	 }
   	 
   	 //checks an accusation made by the player
   	 public boolean checkAccusation(Solution accusation) {
   		 return (solution.getRoomSolution().equals(accusation.getRoomSolution()) && 
   				 solution.getPersonSolution().equals(accusation.getPersonSolution()) && 
   				 solution.getWeaponSolution().equals(accusation.getWeaponSolution())); 
   	 }
   	 
   	 //Handles suggestions made by players
   	 public Card handleSuggestion(Player p, Card room, Card person, Card weapon) {
   		 for(Player player : players) {
   			 if(player.getPlayerName().equals(person.getName())&&!p.getPlayerName().equals(person.getName())) {
   				 player.setPosition(p.getPlayerRow(), p.getPlayerCol());
   				 player.setWasMoved(true);
   				 repaint();
   			 }
   		 }
   		 for(Player player : players) {
   			 if(player != p) {
   				 if(player.DisproveSuggestion(room, person, weapon) != null) return player.DisproveSuggestion(room, person, weapon);
   			 }
   		 }
   		 return null;
   	 }
   	 
   	 //Method to draw the board
   	 public void paintComponent(Graphics g) {
   		 super.paintComponent(g);
   		 
   		 //draws cells
   		 for(int i = 0; i < numRows; i++) {
   			 for(int j = 0; j < numCols; j++) {
   				 grid[i][j].drawCell(grid[i][j], targets, g, this);
   			 }
   		 }
   		 
   		 //draws doorways
   		 for(BoardCell cell : BoardCell.doorways) {
   			 cell.drawDoorways(cell, g);
   		 }
   		 
   		 //draws room labels
   		 for(Map.Entry<Character, Room> rooms : roomMap.entrySet()) {
   			 if(rooms.getValue().getLabelCell() != null) {
   				BoardCell cell = rooms.getValue().getLabelCell();
      			 String label = rooms.getValue().getName();
      			 cell.drawRoomLabel(cell, g, label);
   			 }
   		 }
   		 
   		 //draws players
   		 for(Player player : players) {
   			 player.drawPlayer(g);
   		 }
   	 }
   	 
   	 //helper method for the actionListener in GameControlPanel that determines what to do when NEXT button is pressed
   	 public void newTurn(GameControlPanel gameControl) {
  		 if(finishTurn) {
  			 currentPlayer = (currentPlayer+1) % players.size();
  	   		 roll = random.nextInt(6) + 1;
  	   		 calcTargets(getCell(players.get(currentPlayer).getPlayerRow(), players.get(currentPlayer).getPlayerCol()), roll);
  	   		 //current turn is the human player
  	   		 if(players.get(currentPlayer).equals(humanPlayer)) {	 
  	   			 repaint();
  	   			 finishTurn = false;
  	   		 }
  	   		 //Current turn is a computer player
  	   		 else {
  	   			 if(((ComputerPlayer)players.get(currentPlayer)).getHasSolution()) {
  	   				 //If the computer player makes an accusation, we know they are correct
  	   				 //because a computer can't suggest its own hand cards
  	   				 //thus if the last suggestion returned no result, we know that that is the answer
  					JOptionPane.showMessageDialog(null,  "Sorry, the computer won!", "Loser", JOptionPane.INFORMATION_MESSAGE);
  					System.exit(0);
  	   			 }
  	   			 else {
  	   			BoardCell cell = ((ComputerPlayer)players.get(currentPlayer)).selectTarget(targets, this);
  	   			//creates a suggestion if the computers target is a room
  	   			players.get(currentPlayer).setPosition(cell.getRow(), cell.getCol());
  	   			if(cell.isRoom()) {
  	   				Solution suggestion = ((ComputerPlayer)players.get(currentPlayer)).createSuggestion(getRoom(cell), this);
  	   			gameControl.setGuess(suggestion.getPersonSolution().getName()+", "+suggestion.getWeaponSolution().getName()+ ","+suggestion.getRoomSolution().getName());
				Card result = handleSuggestion(players.get(currentPlayer), suggestion.getRoomSolution(), suggestion.getPersonSolution(), suggestion.getWeaponSolution());
				if(result!=null) {
				gameControl.setGuessResult("Guess Disproven", playerHasCard(result).getPlayerColor());
				//side.updatePanel(result, ClueGame.board.playerHasCard(result));
				}
				else {
					((ComputerPlayer)players.get(currentPlayer)).setHasSolution(true);
					gameControl.setGuessResult("No result", Color.gray);
				}
				
  	   			}
  	   			repaint();
  	   		 }
  	   		 repaint();
  	   		 }
  		 } 
   	 }
   	 
   	 public Player playerHasCard(Card c) {
   		 for(Player p : players) {
   			 for(Card card : p.getHand()) {
   				if(card.equals(c)) {
   					return p;
   				}
   			 }
   		 }
   		 return null;
   	 }
     
   	 //method for testing accusations
   	 public void setSolution(Solution theAnswer) {
   		 solution = theAnswer;
   	 }
   	 
   	 public void setPlayers(ArrayList<Player> p) {
   		 players.clear();
   		 players = p;
   	 }
   	 
     //returns the room based on the char symbol given
     public Room getRoom(char roomSymbol) {
    	 return roomMap.get(roomSymbol);
     }
     
     //returns the room based on the value of the cell
     public Room getRoom(BoardCell cell) {
    	 return roomMap.get(cell.getInitial());
     }
     
     public boolean getFinishedTurn() {
    	 return finishTurn;
     }
     
     //other getters and setters
   	 public Player getHumanPlayer() {
   		 return humanPlayer;
   	 }
   	 
     public void setFinishTurn(boolean turn) {
   		 finishTurn = turn;
   	 }
   	 
   	 public boolean getTurn() {
   		 return finishTurn;
   	 }
   	 
     public int getRoll() {
    	 return roll;
     }
     
     public int getCurrentPlayer() {
    	 return currentPlayer;
     }
     
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
	 
	 public ArrayList<Player> getPlayers(){
		 return players;
	 }
	 public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
	public ArrayList<Card> getDeck() {
		return deck;
	}
    public Solution getSolution() {
		return solution;
	}
}
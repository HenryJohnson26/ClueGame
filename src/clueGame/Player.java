package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
	private String name;
	private Color color;
	private int row, col;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seen;
	protected Random rand = new Random();
	private boolean wasMoved = false;


	public Player(String n, String color, int r, int col) {
		name = n;
		//turning string into color
		switch(color) {
		case "Red":
			this.color = Color.red;
			break;
		case "Blue":
			this.color = Color.blue;
			break;
		case "Green":
			this.color = Color.green;
			break;
		case "Orange":
			this.color = Color.orange;
			break;
		case "Purple":
			this.color = Color.magenta;
			break;
		default:
			this.color = Color.white;
			break;
		}
		
		row = r;
		this.col = col;
		hand = new ArrayList<Card>();
		seen = new ArrayList<Card>();
	}
	
	//checks to see if player can disprove suggestion
	public Card DisproveSuggestion(Card room, Card person, Card weapon) {
		ArrayList<Card> canDisprove = new ArrayList<Card>();
		for(Card c : hand) {
			if(c.equals(room) || c.equals(person) || c.equals(weapon)) {
				canDisprove.add(c);
			}
		}
		if(canDisprove.size() == 0) return null;
		return canDisprove.get(rand.nextInt(canDisprove.size()));
	}
	
	//Draws each player at their starting position
	public void drawPlayer(Graphics g) {
		g.setColor(color);
		g.fillOval(col*21, row*21, 21, 21);
	}
	
	//Getters and setters
	public ArrayList<Card> getHand() {
		return hand;
	}	
	
	public String getPlayerName() {
		return name;
	}
	
	public Color getPlayerColor() {
		return color;
	}
	
	public int getPlayerRow() {
		return row;
	}
	
	public int getPlayerCol() {
		return col;
	}
	public ArrayList<Card> getSeen() {
		return seen;
	}
	
	//Updates the players position
	public void setPosition(int r, int c) {
		ClueGame.board.getCell(row, col).setOccupied(true);
		row = r;
		col = c;
		ClueGame.board.getCell(r, c).setOccupied(true);
	}
	
	//methods for testing
	public void setHand(Card room, Card person, Card weapon) {
		hand.clear();
		hand.add(room);
		hand.add(person);
		hand.add(weapon);
	}
	
	public void setSeen(Card card) {
		seen.add(card);
	}
	
	public boolean isWasMoved() {
		return wasMoved;
	}

	public void setWasMoved(boolean wasMoved) {
		this.wasMoved = wasMoved;
	}
}

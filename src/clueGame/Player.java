package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color color;
	private int row, col;
	private ArrayList<Card> hand;
	private ArrayList<Card> seen;
	
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
			this.color = Color.gray;
			break;
		}
		
		row = r;
		this.col = col;
		hand = new ArrayList<Card>();
		seen = new ArrayList<Card>();
	}
	
	//checks to see if player can disprove suggestion
	public Card DisproveSuggestion(Card room, Card person, Card weapon) {
		return null;
	}
	
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
	
	//methods for testing
	public void setHand(Card room, Card person, Card weapon) {
		hand.clear();
		hand.add(room);
		hand.add(person);
		hand.add(weapon);
	}
}

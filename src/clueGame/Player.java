package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color color;
	private int row, col;
	private ArrayList<Card> hand;
	
	public Player(String n, String color, int r, int col) {
		name = n;
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
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void updateHand(Card card) {
		
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
}

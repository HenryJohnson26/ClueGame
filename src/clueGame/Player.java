package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color color;
	private int row, col;
	private ArrayList<Card> hand;
	
	public Player(String n, Color color, int r, int col) {
		name = n;
		this.color = color;
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

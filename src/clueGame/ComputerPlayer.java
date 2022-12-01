package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private boolean hasSolution = false;
	
	public ComputerPlayer(String n, String color, int r, int col) {
		super(n, color, r, col);
	}

	//Creates a suggestion for the computer player based on what cards they've seen
	public Solution createSuggestion(Room room, Board board) {
		ArrayList<Card> possibleWeapons = new ArrayList<Card>();
		ArrayList<Card> possiblePlayers = new ArrayList<Card>();
		Card r = new Card("");
		//Chooses a weapon card
		for(Card c : board.getWeaponCards()) {
			if (!hand.contains(c) && !seen.contains(c)) {
				possibleWeapons.add(c);
			}
		}
		//Chooses a player card
		for(Card p : board.getPlayerCards()) {
			if (!hand.contains(p) && !seen.contains(p)) {
				possiblePlayers.add(p);
			}
		}
		
		//Gets the room card of the player
		for(Card c : board.getRoomCards()) {
			if(c.getName().equals(room.getName())) r=c;
		}
		
		return new Solution(r, possiblePlayers.get(rand.nextInt(possiblePlayers.size() - 1)), possibleWeapons.get(rand.nextInt(possibleWeapons.size() - 1)));
	}
	
	//Chooses the target cell that the computer player will move to
	public BoardCell selectTarget(Set<BoardCell> targetList, Board board) {
		if(targetList.size() == 0) {
			return null;
		}
		ArrayList<BoardCell> possibleLocations = new ArrayList<BoardCell>();
		ArrayList<BoardCell> possibleRooms = new ArrayList<BoardCell>();
		for(BoardCell b : targetList) {
			if(b.isRoomCenter()) {
				for(Card c : board.getRoomCards()) {
					if(c.getName().equals(board.getRoom(b).getName()) && !seen.contains(c) && !hand.contains(c)) {
						possibleRooms.add(b);
					}
				}
			}
			else {
				possibleLocations.add(b);
			}
		}
		if(possibleRooms.size() == 0) return possibleLocations.get(rand.nextInt(possibleLocations.size()));
		return possibleRooms.get(rand.nextInt(possibleRooms.size()));
	}
	
	public boolean getHasSolution() {
		return hasSolution;
	}

	public void setHasSolution(boolean hasSolution) {
		this.hasSolution = hasSolution;
	}

}

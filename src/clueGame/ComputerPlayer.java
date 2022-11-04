package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String n, String color, int r, int col) {
		super(n, color, r, col);
	}

	public Solution createSuggestion(Room room) {
		return null;
	}
	public BoardCell selectTarget(ArrayList<BoardCell> targetList) {
		return null;
	}
	
}

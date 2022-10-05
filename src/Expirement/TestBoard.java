package Expirement;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private Set<TestBoardCell> targets;
	
	public TestBoard() {
		targets = new HashSet<>();
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	public TestBoardCell getCell( int row, int col) {
		return null;
	}
	
}

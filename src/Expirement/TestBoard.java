package Expirement;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		targets = new HashSet<>();
		visited = new TreeSet<>();
		grid = new TestBoardCell[ROWS][COLS];
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		if(!visited.contains(startCell)) {
			visited.add(startCell);
			if(pathlength == 0) {
				targets.add(startCell);
				return;
			} else {
				for(TestBoardCell cell : startCell.getAdjList()) {
					calcTargets(cell, pathlength - 1);
					visited.remove(startCell);
				}
			}
		}
		
	}
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	public TestBoardCell getCell( int row, int col) {
		return new TestBoardCell(row, col, this);
	}
	
}

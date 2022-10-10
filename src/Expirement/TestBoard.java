package Expirement;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		targets = new HashSet<>();
		visited = new HashSet<>();
		grid = new TestBoardCell[ROWS][COLS];
		for(int r = 0; r<ROWS; r++) {
			for(int c = 0; c< COLS;c++) {
				grid[r][c] = new TestBoardCell(r,c);
			}
		}
		for(int r = 0; r<ROWS; r++) {
			for(int c = 0; c< COLS;c++) {
				grid[r][c].createAdjList(this);
			}
		}
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
			if(!visited.contains(startCell)) {
			visited.add(startCell);
			if(pathlength == 0) {
				if(!startCell.isOccupied()||startCell.isRoom()) {
					targets.add(startCell);
				}
				visited.remove(startCell);
				return;
			} 
			else {
				if(startCell.isRoom()) {
					targets.add(startCell);
				}
				for(TestBoardCell cell : startCell.getAdjList()) {
					calcTargets(cell, pathlength - 1);
					visited.remove(startCell);
				}
			}
		}
		return;
		
	}
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	public TestBoardCell getCell( int row, int col) {
		return grid[row][col];
	}
	
}

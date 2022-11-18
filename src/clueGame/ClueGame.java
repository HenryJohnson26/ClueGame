package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	public static Board board = Board.getInstance();
	private GameControlPanel bottom;
	private KnownCardsPanel side;
	
	//constructor that creates the frame and adds all of the panels to the main frame
	public ClueGame() {
		setTitle("ClueGame");
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		//mainPanel.add(testBoard, BorderLayout.CENTER);
		KnownCardsPanel side = new KnownCardsPanel();
		GameControlPanel bottom = new GameControlPanel();
		//add(mainPanel);
		setVisible(true);
		add(board, BorderLayout.CENTER);
		side = new KnownCardsPanel();
		bottom = new GameControlPanel();
		side.setSize(new Dimension(250, 1000));
		bottom.setSize(new Dimension(900, 250));	
		add(side, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);
		revalidate();
		addMouseListener(new boardListener());
	}
	
	 //boardListener class that tells the board what to do when the human player clicks on the board
	 private class boardListener implements MouseListener{
			@Override
			public void mouseClicked(MouseEvent e) {
				//Ensures that it is the human players turn
				if(board.getPlayers().get(board.getCurrentPlayer()) == board.getHumanPlayer() && board.getTurn() == false) {
					int counter = 0;
					//variables to find the position of the mouse click
					double x1 = (e.getPoint().getX() / BoardCell.CELL_WIDTH) - 1;
					double y1 = (e.getPoint().getY() / BoardCell.CELL_WIDTH) - 2;
					int col = (int)Math.round(x1);
					int row = (int)Math.round(y1);
					
					//Determines if the clicked on cell is in the target list
					for(BoardCell c : board.getTargets()) {
						if(c.getRow() != row || c.getCol() != col) {
							counter++;
						}
						else {
							board.getPlayers().get(board.getCurrentPlayer()).setPosition(row, col);
							board.setFinishTurn(true);
						}
						//Enables any cell of a room to be a valid target if clicked on
						if(board.getCell(row, col).getInitial() == c.getInitial() && c.isRoom()) {
							board.getPlayers().get(board.getCurrentPlayer()).setPosition(c.getRow(), c.getCol());
							board.setFinishTurn(true);
							counter--;
						}
					}
					//Error message if clicked on cell is not in the target list
					if(counter == board.getTargets().size()) {
						JOptionPane.showMessageDialog(null,  "This is not a target.", "Invalid Target", JOptionPane.INFORMATION_MESSAGE);
					}
					//Clear target list and repaint the board
					if(board.getTurn()) {
						board.getTargets().clear();
						repaint();
					}
				}
			}

			//Empty definitions for unused event methods
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}	 
	}
	   	 
	//main method to start the game
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		JOptionPane.showMessageDialog(null,  "You are " + board.getHumanPlayer().getPlayerName() + 
				". Can you find the solution before the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}

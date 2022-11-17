package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
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
	
	 //
	 private class boardListener implements MouseListener{
			@Override
			public void mouseClicked(MouseEvent e) {
				if(board.getPlayers().get(board.getCurrentPlayer()) == board.getHumanPlayer()) {
					int counter = 0;
					
					double x1 = (e.getPoint().getX() / BoardCell.CELL_WIDTH) - 1;
					double y1 = (e.getPoint().getY() / BoardCell.CELL_WIDTH) - 2;
					int x = (int)Math.round(x1);
					int y = (int)Math.round(y1);
					
					for(BoardCell c : board.getTargets()) {
						if(c.getRow() != y && c.getCol() != x) {
							counter++;
						}
						else {
							board.getPlayers().get(board.getCurrentPlayer()).setPosition(y, x);
							board.setFinishTurn(true);
						}
					}
					if(counter == board.getTargets().size()) {
						JOptionPane.showMessageDialog(null,  "This is not a target.", "Invalid Target", JOptionPane.INFORMATION_MESSAGE);
					}
					board.getTargets().clear();
					repaint();
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
	   	 
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		JOptionPane.showMessageDialog(null,  "You are " + board.getHumanPlayer().getPlayerName() + 
				". Can you find the solution before the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}

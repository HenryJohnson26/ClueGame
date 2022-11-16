package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	private static Board testBoard = Board.getInstance();
	
	//constructor that creates the frame and adds all of the panels to the main frame
	public ClueGame() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setTitle("ClueGame");
		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		testBoard.initialize();
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		mainPanel.add(testBoard, BorderLayout.CENTER);
		KnownCardsPanel side = new KnownCardsPanel();
		GameControlPanel bottom = new GameControlPanel();
		add(mainPanel);
		setVisible(true);
		side.setSize(new Dimension(250, 1000));
		bottom.setSize(new Dimension(900, 250));	
		add(side, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);
		revalidate();
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		JOptionPane.showMessageDialog(null,  "You are " + testBoard.getHumanPlayer().getPlayerName() + 
				". Can you find the solution before the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

}

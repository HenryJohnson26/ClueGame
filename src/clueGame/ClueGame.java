package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private static Board testBoard = Board.getInstance();
	
	//constructor that creates the frame and adds all of the panels to the main frame
	public ClueGame() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setTitle("ClueGame");
		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		testBoard.initialize();
		setSize(890, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		mainPanel.add(testBoard, BorderLayout.CENTER);
		KnownCardsPanel side = new KnownCardsPanel();
		GameControlPanel bottom = new GameControlPanel();
		mainPanel.add(bottom, BorderLayout.SOUTH);
		mainPanel.add(side, BorderLayout.EAST);
		add(mainPanel);
		setVisible(true);
		revalidate();
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}

}

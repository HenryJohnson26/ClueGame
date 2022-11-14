package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	private static Board testBoard = Board.getInstance();
	
	public ClueGame() {
		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		testBoard.initialize();
		setSize(750, 930);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		add(testBoard, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}

}

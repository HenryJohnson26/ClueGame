package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private static JPanel topPanel;
	private static JPanel bottomPanel;

	private static JTextField theGuess;
	private static JTextField theGuessResult;
	private static JTextField playerTurn;
	private static JTextField dieRoll;
	private JPanel bottomLeft = new JPanel(new BorderLayout());
	private JPanel bottomRight = new JPanel();


	public GameControlPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0, 2));
		theGuess = new JTextField("");
		theGuess.setEditable(false);
		theGuessResult = new JTextField("");
		theGuessResult.setEditable(false);
		playerTurn = new JTextField();
		playerTurn.setEditable(false);
		dieRoll = new JTextField();
		dieRoll.setSize(70, 20);
		dieRoll.setEditable(false);
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0, 2));
		JPanel topLeft = new JPanel(new BorderLayout());
		topLeft.add(new JLabel("Whose turn?"), BorderLayout.NORTH);
		topLeft.add(playerTurn, BorderLayout.SOUTH);
		JPanel topRight = new JPanel();
		topRight.add(new JLabel("Roll:"));
		topRight.add(dieRoll);
		topPanel.add(topLeft, BorderLayout.WEST);
		topPanel.add(topRight, BorderLayout.EAST);
		topPanel.add(new JButton("Make Accusation"));
		topPanel.add(new JButton("NEXT!"));
		bottomLeft.add(theGuess, BorderLayout.CENTER);
		bottomLeft.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		bottomRight.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		bottomRight.add(theGuessResult, BorderLayout.CENTER);
		bottomPanel.add(bottomLeft, BorderLayout.WEST);
		bottomPanel.add(bottomRight, BorderLayout.EAST);
	}
	
	public void setTurn(Player player, int roll) {
		playerTurn.setText(player.getPlayerName());
		dieRoll.setSize(70,20);
		dieRoll.setText(((Integer)(roll)).toString());
		playerTurn.setBackground(player.getPlayerColor());
	}
	
	public void setGuess(String guess) {
		theGuess.setSize(150, 20);
		theGuess.setText(guess);
	}
	
	public void setGuessResult(String result) {
		theGuessResult.setSize(200,20);
		theGuessResult.setText(result);
	}
	
	//main to test panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();// create the frame 
		
		JPanel wholeFrame = new JPanel(new BorderLayout());
		wholeFrame.setLayout(new GridLayout(2, 0));
		wholeFrame.add(topPanel, BorderLayout.NORTH);
		wholeFrame.add(bottomPanel, BorderLayout.SOUTH);
		
		panel.add(wholeFrame);
		
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer("Col. Mustard", "orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

}

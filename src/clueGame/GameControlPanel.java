package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {
	private static JPanel topPanel;
	private static JPanel bottomPanel;
	private JTextField turnTextField;
	private JTextField rollNum;

	public GameControlPanel() {
		turnTextField = new JTextField();
		turnTextField.setEditable(false);
		
		rollNum = new JTextField();
		rollNum.setEditable(false);
		topPanel = new JPanel();
		JPanel topLeft = new JPanel(new BorderLayout());
		topLeft.add(new JLabel("Whose turn?"), BorderLayout.NORTH);
		topLeft.add(turnTextField, BorderLayout.SOUTH);
		JPanel topRight = new JPanel();
		topRight.add(new JLabel("Roll:"));
		topRight.add(rollNum);
		topPanel.add(topLeft);
		topPanel.add(topRight);
		topPanel.add(new JButton("Make Accusation"));
		topPanel.add(new JButton("NEXT!"));
		bottomPanel = new JPanel();
		JPanel bottomLeft = new JPanel(new BorderLayout());
		bottomLeft.add(new JTextField());
		bottomLeft.add(new JLabel("Guess"), BorderLayout.NORTH);
		bottomLeft.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel bottomRight = new JPanel();
		bottomRight.add(new JLabel("Guess Result"), BorderLayout.NORTH);
		bottomRight.setBorder(BorderFactory.createLineBorder(Color.black));
		bottomRight.add(new JTextField(), BorderLayout.SOUTH);
		bottomPanel.add(bottomLeft);
		bottomPanel.add(bottomRight);
	}
	
	public void setTurn(Player player, int roll) {
		turnTextField.setText(player.getPlayerName());
		rollNum.setText(""+roll);
	}
	
	public void setGuess(String guess) {
		
	}
	
	public void setGuessResult(String result) {
		
	}
	
	//main to test panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();// create the frame 
		
		JPanel wholeFrame = new JPanel(new BorderLayout());
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

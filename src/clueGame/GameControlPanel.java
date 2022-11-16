package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel implements ActionListener {
	private static JPanel topPanel;
	private static JPanel bottomPanel;

	private static JTextField theGuess;
	private static JTextField theGuessResult;
	private static JTextField playerTurn;
	private static JTextField dieRoll;
	private JPanel bottomLeft = new JPanel(new BorderLayout());
	private JPanel bottomRight = new JPanel(new BorderLayout());

	public GameControlPanel() {
		setSize(890, 180);
		//makes a bottom panel to split the main panel in half
		bottomPanel = new JPanel();
		//creates grid layout for two other objects
		bottomPanel.setLayout(new GridLayout(0, 2));
		theGuess = new JTextField("");
		theGuess.setEditable(false);
		theGuessResult = new JTextField("");
		theGuessResult.setEditable(false);
		playerTurn = new JTextField();
		playerTurn.setEditable(false);
		dieRoll = new JTextField();
		dieRoll.setEditable(false);
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0, 4));
		JPanel topLeft = new JPanel(new BorderLayout());
		topLeft.add(new JLabel("Whose turn?"), BorderLayout.NORTH);
		topLeft.add(playerTurn, BorderLayout.SOUTH);
		JPanel topRight = new JPanel();
		topRight.add(new JLabel("Roll:"));
		topRight.add(dieRoll);
		topPanel.add(topLeft, BorderLayout.WEST);
		topPanel.add(topRight, BorderLayout.EAST);
		JButton next = new JButton("NEXT!");
		next.addActionListener(this);
		topPanel.add(new JButton("Make Accusation"));
		topPanel.add(next);
		bottomLeft.add(theGuess, BorderLayout.CENTER);
		bottomLeft.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		bottomRight.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		bottomRight.add(theGuessResult, BorderLayout.WEST);
		bottomPanel.add(bottomLeft, BorderLayout.WEST);
		bottomPanel.add(bottomRight, BorderLayout.EAST);
		
		JPanel wholeFrame = new JPanel(new BorderLayout());
		wholeFrame.setLayout(new GridLayout(2, 0));
		wholeFrame.add(topPanel, BorderLayout.NORTH);
		wholeFrame.add(bottomPanel, BorderLayout.SOUTH);
		add(wholeFrame);
		wholeFrame.revalidate();
		
		ClueGame.board.newTurn();
		setTurn();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ClueGame.board.newTurn();
		setTurn();
	}
	
	//sets text field to who's turn it is, the color that character and the roll
	public void setTurn() {
		playerTurn.setText(ClueGame.board.getPlayers().get(ClueGame.board.getCurrentPlayer()).getPlayerName());
		dieRoll.setSize(20,20);
		dieRoll.setText(((Integer)(ClueGame.board.getRoll())).toString());
		playerTurn.setBackground(ClueGame.board.getPlayers().get(ClueGame.board.getCurrentPlayer()).getPlayerColor());
	}
	
	//sets text field for guesses
	public void setGuess(String guess) {
		theGuess.setSize(150, 20);
		theGuess.setText(guess);
	}
	
	//sets text field for 
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
		wholeFrame.revalidate();
		panel.add(wholeFrame);
		
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		//panel.setTurn(new ComputerPlayer("Col. Mustard", "Orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

}

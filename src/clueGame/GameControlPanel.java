package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JPanel bottomRight = new JPanel(new BorderLayout());
	private GameControlPanel controlPanel;
	private AccusationPanel accusationPanel;

	public GameControlPanel() {
		controlPanel = this;
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
		
		//Creates the next and accusation buttons and adds action listeners to them
		JButton next = new JButton("NEXT!");
		JButton accusation = new JButton("Make Accusation");
		next.addActionListener(new nextListener());
		accusation.addActionListener(new accusationListener());
		topPanel.add(accusation);
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
		
		ClueGame.board.newTurn(this);
		setTurn();
	}
	
	//button listener to determine what happens when the next button is pressed
	private class nextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			theGuess.setBackground(null);
			theGuess.setText("");
			theGuessResult.setBackground(null);
			theGuessResult.setText("");
			repaint();
			ClueGame.board.newTurn(controlPanel);
			setTurn();	
		}	
	}
	
	//button listener to make an accusation
	private class accusationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!ClueGame.board.getFinishedTurn()) {
				accusationPanel = new AccusationPanel();
				for(Player p : ClueGame.board.getPlayers()) {
					accusationPanel.addPerson(p.getPlayerName());
				}
				for(Card wcard : ClueGame.board.getWeaponCards()) {
					accusationPanel.addWeapon(wcard.getName());
				}
				for(Card rcard : ClueGame.board.getRoomCards()) {
					accusationPanel.addRoom(rcard.getName());
				}
				accusationPanel.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null,  "You can not make an accusation because it is not your turn.", "Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
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
		theGuess.setSize(250, 20);
		theGuess.setText(guess);
		theGuess.setBackground(ClueGame.board.getPlayers().get(ClueGame.board.getCurrentPlayer()).getPlayerColor());
	}
	
	//sets text field for 
	public void setGuessResult(String result, Color color) {
		theGuessResult.setSize(200,20);
		theGuessResult.setText(result);
		theGuessResult.setBackground(color);
	}
	
	//creates the accusation panel
	private class AccusationPanel extends JDialog{
		private JComboBox<String> room;
		private JComboBox<String> person;
		private JComboBox<String> weapon;
		private JButton submit;
		private JButton close;
		private String roomStr;

		public AccusationPanel() {
			setTitle("Make an accusation!");
			setSize(300, 300);
			setLayout(new GridLayout(4, 2));
			
			room = new JComboBox<String>();
			person = new JComboBox<String>();
			weapon = new JComboBox<String>();
			submit = new JButton("Submit");
			close = new JButton("Cancel");
			
			submit.addActionListener(new SubmitListener());
			close.addActionListener(new CancelListener());
			
			add(new JLabel("Current room:"));
			add(room);
			add(new JLabel("Person: "));
			add(person);
			add(new JLabel("Weapon: "));
			add(weapon);
			add(submit);
			add(close);
		}
		
		public void addRoom(String room) {
			this.room.addItem(room);
		}
		
		public void addPerson(String person) {
			this.person.addItem(person);
		}
		
		public void addWeapon(String Weapon) {
			this.weapon.addItem(Weapon);
		}

		//Handles when the player submits and accusation 
		private class SubmitListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				Card playerCard = new Card("");
			for(Card c : ClueGame.board.getPlayerCards()) {
				if(c.getName().equals(person.getSelectedItem())) {
					playerCard = c;
				}
			}
			Card weaponCard = new Card("");
			for(Card c : ClueGame.board.getWeaponCards()) {
				if(c.getName().equals(weapon.getSelectedItem())) {
					weaponCard = c;
				}
			}
			Card roomCard = new Card("");
			for(Card c : ClueGame.board.getRoomCards()) {
				if(c.getName().equals(room.getSelectedItem())) {
					roomCard = c;
				}
			}
			Solution accusation = new Solution(roomCard, playerCard, weaponCard);
			if(ClueGame.board.checkAccusation(accusation)) {
				JOptionPane.showMessageDialog(null,  "You won! The solution was " + ClueGame.board.getSolution().getRoomSolution().getName() + ", " + 
						ClueGame.board.getSolution().getPersonSolution().getName() + ", " + 
						ClueGame.board.getSolution().getWeaponSolution().getName(),"Chicken diner", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null,  "Sorry, not correct! The correct solution was " + ClueGame.board.getSolution().getRoomSolution().getName() + ", " + 
						ClueGame.board.getSolution().getPersonSolution().getName() + ", " + 
						ClueGame.board.getSolution().getWeaponSolution().getName(), "Loser", JOptionPane.INFORMATION_MESSAGE);
			}
			System.exit(0);
		}
	}
		//Will close the dialog box if the cancel button is clicked
		private class CancelListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				accusationPanel.setVisible(false);		
			}
		}
	}
}

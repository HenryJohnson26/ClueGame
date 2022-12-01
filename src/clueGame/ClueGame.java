package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import clueGame.SuggestionPanel.CancelListener;
//import clueGame.SuggestionPanel.SubmitListener;

import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	public static Board board = Board.getInstance();
	public GameControlPanel bottom;
	private KnownCardsPanel side;
	private SuggestionPanel suggestionPanel;
	
	//constructor that creates the frame and adds all of the panels to the main frame
	public ClueGame() {
		setTitle("ClueGame");
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		//mainPanel.add(testBoard, BorderLayout.CENTER);
		side = new KnownCardsPanel();
		bottom = new GameControlPanel();
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
							//call the suggestion window
							suggestionPanel = new SuggestionPanel();
							suggestionPanel.setRoom(board.getRoom(board.getCell(row, col)).getName());
							for(Player p : board.getPlayers()) {
								suggestionPanel.addPerson(p.getPlayerName());
							}
							for(Card wcard : board.getWeaponCards()) {
								suggestionPanel.addWeapon(wcard.getName());
							}
							suggestionPanel.setVisible(true);
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
	
	//Creates the suggestion panel
	private class SuggestionPanel extends JDialog{
		private JTextField room;
		private JComboBox<String> person;
		private JComboBox<String> weapon;
		private JButton submit;
		private JButton close;
		private String roomStr;

		public SuggestionPanel() {
			setTitle("Make a suggestion!");
			setSize(300, 300);
			setLayout(new GridLayout(4, 2));
			
			room = new JTextField();
			room.setEditable(false);
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
		
		public void setRoom(String room) {
			this.room.setText(room);
			roomStr = room;
		}
		
		public void addPerson(String person) {
			this.person.addItem(person);
		}
		
		public void addWeapon(String Weapon) {
			this.weapon.addItem(Weapon);
		}

		//Handles when a suggestion is submitted by the player
		private class SubmitListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				Card roomCard = new Card("");
				for(Card c : ClueGame.board.getRoomCards()) {
					if(c.getName().equals(roomStr)) {
						roomCard = c;
					}
				}
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
				bottom.setGuess(playerCard.getName()+", "+weaponCard.getName()+ ","+roomStr);
				Card result = ClueGame.board.handleSuggestion(ClueGame.board.getHumanPlayer(), roomCard, playerCard, weaponCard);
				if(result!=null) {
				bottom.setGuessResult(result.getName(), ClueGame.board.playerHasCard(result).getPlayerColor());
				side.updatePanel(result, ClueGame.board.playerHasCard(result));
				}
				else {
					bottom.setGuessResult("No result", Color.gray);
				}
				suggestionPanel.setVisible(false);	
			}
			
		}
		
		//Will close the dialog box if the cancel button is clicked
		private class CancelListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				suggestionPanel.setVisible(false);		
			}
		}
	}
	public GameControlPanel getControlPanel() {
		return bottom;
	}
}

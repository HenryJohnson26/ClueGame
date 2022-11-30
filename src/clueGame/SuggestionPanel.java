package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SuggestionPanel extends JDialog{
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


	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Card roomCard = new Card("");
			for(Card c : ClueGame.board.getRoomCards()) {
				if(c.getName().equals(roomStr)) {
					roomCard = c;
				}
			}
			ClueGame.board.handleSuggestion(ClueGame.board.getHumanPlayer(), roomCard, null, null);
		}
		
	}
	
	//Will close the dialog box if the cancel button is clicked
	private class CancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);		
		}
	}
}
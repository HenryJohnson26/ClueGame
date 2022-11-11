package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KnownCardsPanel extends JPanel{

	private static JPanel mainPanel;
	private JPanel roomPanel;
	private JPanel playerPanel;
	private JPanel weaponPanel;
	private JPanel handRoomPanel;
	private JPanel handPlayerPanel;
	private JPanel handWeaponPanel;
	private JPanel seenRoomPanel;
	private JPanel seenPlayerPanel;
	private JPanel seenWeaponPanel;
	private Map<Card, Player> seenRoomCards;
	private Map<Card, Player> seenPlayerCards;
	private Map<Card, Player> seenWeaponCards;
	private int roomPanelSize;
	private int playerPanelSize;
	private int weaponPanelSize;
	private Board testBoard = Board.getInstance();


	public KnownCardsPanel() {
		roomPanelSize = 0;
		playerPanelSize = 0;
		weaponPanelSize = 0;
		mainPanel = new JPanel(new GridLayout(0,3));
		roomPanel = new JPanel(new GridLayout(0,2));
		playerPanel = new JPanel(new GridLayout(0,2));
		weaponPanel = new JPanel(new GridLayout(0,2));
		handRoomPanel = new JPanel(new GridLayout(0,3));
		handPlayerPanel = new JPanel(new GridLayout(0,3));
		handWeaponPanel = new JPanel(new GridLayout(0,3));
		createHandPanels();
		seenRoomPanel = new JPanel(new GridLayout(roomPanelSize, 0));
		seenPlayerPanel = new JPanel(new GridLayout(playerPanelSize, 0));
		seenWeaponPanel = new JPanel(new GridLayout(weaponPanelSize, 0));
		roomPanel.add(handRoomPanel);
		playerPanel.add(handPlayerPanel);
		weaponPanel.add(handWeaponPanel);
		seenRoomCards = new HashMap<Card, Player>();
		seenPlayerCards = new HashMap<Card, Player>();
		seenWeaponCards = new HashMap<Card, Player>();
		updateRoomPanel();
		updatePlayerPanel();
		updateWeaponPanel();
		roomPanel.add(seenRoomPanel);
		playerPanel.add(seenPlayerPanel);
		weaponPanel.add(seenWeaponPanel);
		mainPanel.add(roomPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(weaponPanel);
	}
	private void createHandPanels() {
		for(Card card : testBoard.getHumanPlayer().getHand()) {
			int roomCounter = 0;
			int playerCounter = 0;
			int weaponCounter = 0;
			switch(card.getType()){
			case PLAYER:
				JLabel label = new JLabel(card.getName());
				label.setBackground(testBoard.getHumanPlayer().getPlayerColor());
				handRoomPanel.add(label);
				roomCounter++;
				break;
			case WEAPON:
				JLabel label1 = new JLabel(card.getName());
				label1.setBackground(testBoard.getHumanPlayer().getPlayerColor());
				handRoomPanel.add(label1);
				weaponCounter++;
				break;
			default:
				JLabel label2 = new JLabel(card.getName());
				label2.setBackground(testBoard.getHumanPlayer().getPlayerColor());
				handRoomPanel.add(label2);	
				playerCounter++;
				break;	
			}
			if(roomCounter == 0) {
				handRoomPanel.add(new JLabel("None"));
			}
			if(playerCounter == 0) {
				handPlayerPanel.add(new JLabel("None"));
			}
			if(weaponCounter == 0) {
				handWeaponPanel.add(new JLabel("None"));
			}
		}
	}
	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();// create the frame 
		//set the file names to config files 
		
	}
	
	public void updatePanel(Card card, Player player) {
		mainPanel.removeAll();
		switch (card.getType()) {
		case ROOM:
			seenRoomCards.put(card, player);
			updateRoomPanel();
			break;
		case WEAPON:
			seenWeaponCards.put(card, player);
			updateWeaponPanel();
			break;
		default:
			seenPlayerCards.put(card, player);
			updatePlayerPanel();
			break;	
		}
		
		mainPanel.add(roomPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(weaponPanel);
	}
	
	private void updateRoomPanel() {
		roomPanelSize++;
		seenRoomPanel.removeAll();
		roomPanel.removeAll();
		seenRoomPanel.setLayout(new GridLayout(roomPanelSize, 0));
		if(seenRoomCards.isEmpty()) {
			seenRoomPanel.add(new JLabel("None"));
		}
		else {
			for(Map.Entry<Card, Player> cardPair: seenRoomCards.entrySet()) {
				JTextField seenRoom = new JTextField();
				seenRoom.setEditable(false);
				seenRoom.setText(cardPair.getKey().getName());
				seenRoom.setBackground(cardPair.getValue().getPlayerColor());
				seenRoomPanel.add(seenRoom);
			}
		}
		roomPanel.add(handRoomPanel);
		roomPanel.add(seenRoomPanel);
	}
	private void updatePlayerPanel() {
		playerPanelSize++;
		seenPlayerPanel.removeAll();
		playerPanel.removeAll();
		seenPlayerPanel.setLayout(new GridLayout(playerPanelSize, 0));
		if(seenPlayerCards.isEmpty()) {
			seenPlayerPanel.add(new JLabel("None"));
		}
		else {
			for(Map.Entry<Card, Player> cardPair: seenPlayerCards.entrySet()) {
				JTextField seenPlayer = new JTextField();
				seenPlayer.setEditable(false);
				seenPlayer.setText(cardPair.getKey().getName());
				seenPlayer.setBackground(cardPair.getValue().getPlayerColor());
				seenPlayerPanel.add(seenPlayer);
			}
		}
		playerPanel.add(handPlayerPanel);
		playerPanel.add(seenPlayerPanel);
	}	
	private void updateWeaponPanel() {
		weaponPanelSize++;
		seenWeaponPanel.removeAll();
		weaponPanel.removeAll();
		seenWeaponPanel.setLayout(new GridLayout(weaponPanelSize, 0));
		if(seenWeaponCards.isEmpty()) {
			seenWeaponPanel.add(new JLabel("None"));
		}
		else {
			for(Map.Entry<Card, Player> cardPair: seenWeaponCards.entrySet()) {
				JTextField seenWeapon = new JTextField();
				seenWeapon.setEditable(false);
				seenWeapon.setText(cardPair.getKey().getName());
				seenWeapon.setBackground(cardPair.getValue().getPlayerColor());
				seenWeaponPanel.add(seenWeapon);
			}
		}
		weaponPanel.add(handWeaponPanel);
		weaponPanel.add(seenWeaponPanel);
	}
}

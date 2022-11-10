package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
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

	public KnownCardsPanel() {
		roomPanelSize = 0;
		playerPanelSize = 0;
		weaponPanelSize = 0;
		mainPanel = new JPanel(new BorderLayout());
		seenRoomPanel = new JPanel(new GridLayout(roomPanelSize, 0));
		seenPlayerPanel = new JPanel(new GridLayout(playerPanelSize, 0));
		seenWeaponPanel = new JPanel(new GridLayout(weaponPanelSize, 0));
		seenRoomCards = new HashMap<Card, Player>();
		seenPlayerCards = new HashMap<Card, Player>();
		seenWeaponCards = new HashMap<Card, Player>();
	}
	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();// create the frame 
		Board testBoard = Board.getInstance();
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
		
		mainPanel.add(seenRoomPanel);
	}
	
	public void updateRoomPanel() {
		roomPanelSize++;
		seenRoomPanel.removeAll();
		seenRoomPanel.setLayout(new GridLayout(roomPanelSize, 0));
		
		for(Map.Entry<Card, Player> cardPair: seenRoomCards.entrySet()) {
			JTextField seenRoom = new JTextField();
			seenRoom.setEditable(false);
			seenRoom.setText(cardPair.getKey().getName());
			seenRoom.setBackground(cardPair.getValue().getPlayerColor());
			seenRoomPanel.add(seenRoom);
		}
	}
	public void updatePlayerPanel() {
		playerPanelSize++;
		seenPlayerPanel.removeAll();
		seenPlayerPanel.setLayout(new GridLayout(playerPanelSize, 0));
		
		for(Map.Entry<Card, Player> cardPair: seenPlayerCards.entrySet()) {
			JTextField seenPlayer = new JTextField();
			seenPlayer.setEditable(false);
			seenPlayer.setText(cardPair.getKey().getName());
			seenPlayer.setBackground(cardPair.getValue().getPlayerColor());
			seenPlayerPanel.add(seenPlayer);
		}
	}	public void updateWeaponPanel() {
		weaponPanelSize++;
		seenWeaponPanel.removeAll();
		seenWeaponPanel.setLayout(new GridLayout(weaponPanelSize, 0));
		
		for(Map.Entry<Card, Player> cardPair: seenWeaponCards.entrySet()) {
			JTextField seenWeapon = new JTextField();
			seenWeapon.setEditable(false);
			seenWeapon.setText(cardPair.getKey().getName());
			seenWeapon.setBackground(cardPair.getValue().getPlayerColor());
			seenWeaponPanel.add(seenWeapon);
		}
	}
}

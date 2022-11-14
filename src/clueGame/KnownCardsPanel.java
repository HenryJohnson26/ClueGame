package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
	private int roomHandSize;
	private int playerHandSize;
	private int weaponHandSize;
	private static Board testBoard = Board.getInstance();


	public KnownCardsPanel() {
		roomPanelSize = 1;
		playerPanelSize = 1;
		weaponPanelSize = 1;
		roomHandSize = 1;
		playerHandSize = 1;
		weaponHandSize = 1;
		mainPanel = new JPanel(new GridLayout(3, 1));
		mainPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards:"));
		roomPanel = new JPanel(new GridLayout(2, 1));
		roomPanel.setPreferredSize(new Dimension(100, 100));
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms:"));
		playerPanel = new JPanel(new GridLayout(2,1));
		playerPanel.setBorder(new TitledBorder(new EtchedBorder(), "People:"));
		weaponPanel = new JPanel(new GridLayout(2,1));
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons:"));
		handRoomPanel = new JPanel(new GridLayout(1,1));
		handPlayerPanel = new JPanel(new GridLayout(1, 1));
		handWeaponPanel = new JPanel(new GridLayout(1, 1));
		handRoomPanel.add(new JLabel("In Hand:"));
		handPlayerPanel.add(new JLabel("In Hand:"));
		handWeaponPanel.add(new JLabel("In Hand:"));
		createHandPanels();
		seenRoomPanel = new JPanel(new GridLayout(roomPanelSize, 1));
		seenPlayerPanel = new JPanel(new GridLayout(playerPanelSize, 1));
		seenWeaponPanel = new JPanel(new GridLayout(weaponPanelSize, 1));
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
		mainPanel.setSize(250, 100);
	}
	private void createHandPanels() {
		int roomCounter = 0;
		int playerCounter = 0;
		int weaponCounter = 0;
		for(Card card : testBoard.getHumanPlayer().getHand()) {
			switch(card.getType()){
			case PLAYER:
				JTextField label = new JTextField(card.getName());
				label.setBackground(testBoard.getHumanPlayer().getPlayerColor());
				label.setEditable(false);
				playerHandSize ++;
				handPlayerPanel.setLayout(new GridLayout(playerHandSize, 1));
				handPlayerPanel.add(label);
				playerCounter++;
				break;
			case WEAPON:
				JTextField label1 = new JTextField(card.getName());
				label1.setBackground(testBoard.getHumanPlayer().getPlayerColor());
				label1.setEditable(false);
				weaponHandSize ++;
				handWeaponPanel.setLayout(new GridLayout(weaponHandSize, 1));
				handWeaponPanel.add(label1);
				weaponCounter++;
				break;
			default:
				JTextField label2 = new JTextField(card.getName());
				label2.setBackground(testBoard.getHumanPlayer().getPlayerColor());
				label2.setEditable(false);
				roomHandSize ++;
				handRoomPanel.setLayout(new GridLayout(roomHandSize, 1));
				handRoomPanel.add(label2);	
				roomCounter++;
				break;	
			}
		}
		if(roomCounter == 0) {
			JTextField field = new JTextField("None");
			field.setEditable(false);
			field.setSize(getPreferredSize());
			handRoomPanel.add(field);
		}
		if(playerCounter == 0) {
			JTextField field = new JTextField("None");
			field.setEditable(false);
			handPlayerPanel.add(field);
		}
		if(weaponCounter == 0) {
			JTextField field = new JTextField("None");
			field.setEditable(false);
			handWeaponPanel.add(field);
		}
	}
	public static void main(String[] args) {
		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		testBoard.initialize();
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();// create the frame 
		JPanel wholeFrame = new JPanel(new BorderLayout());
		wholeFrame.setLayout(new GridLayout(1, 1));
		//set the file names to config files 
		for(Player player : testBoard.getPlayers()) {
			if(player!=testBoard.getHumanPlayer()) {
				for(Card card : player.getHand()) {
					panel.updatePanel(card, player);
				}
			}
		}
		wholeFrame.add(mainPanel, BorderLayout.CENTER);
		wholeFrame.revalidate();
		panel.add(wholeFrame);
		frame.setContentPane(panel);
		frame.setSize(250, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
//		for(Card card : testBoard.getDeck()) {
//			if(!testBoard.getHumanPlayer().getHand().contains(card)) {
//				updatePanel(card)
//			}
//		}
		

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
		seenRoomPanel.setLayout(new GridLayout(roomPanelSize, 1));
		seenRoomPanel.add(new JLabel("Seen:"));
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
		seenPlayerPanel.setLayout(new GridLayout(playerPanelSize, 1));
		seenPlayerPanel.add(new JLabel("Seen:"));
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
		seenWeaponPanel.setLayout(new GridLayout(weaponPanelSize, 1));
		seenWeaponPanel.add(new JLabel("Seen:"));
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

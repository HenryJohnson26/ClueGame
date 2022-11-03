package clueGame;

public class Card {

	private String cardName;
	private CardType type;
	
	public boolean equals(Card card) {
		return true;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}
	
}

package clueGame;

public class Card {

	private String cardName;
	private CardType type;
	
	public Card(String name) {
		cardName = name;
	}
	
	public boolean equals(Card card) {
		return (cardName.equals(card.getName()));
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}
	public String getName() {
		return cardName;
	}
}

/**
 * 
 */

/**
 * @author dangi
 *
 */
public class Card {
	private CardSuit suit;
	private CardValue value;

	public Card(CardSuit suit, CardValue value) {
		this.suit = suit;
		this.value = value;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public CardValue getValue() {
		return value;
	}

	public String toString() {
		return "(" + suit.name() + "," + value.name() + ")";
	}

	public void setSuit(CardSuit suit) {
		this.suit = suit;
	}

	public void setValue(CardValue value) {
		this.value = value;
	}
	
	public boolean matches(Card other) {
		return (other.getSuit() == this.getSuit() || other.getValue() == this.getValue() || other.getSuit() == CardSuit.WILD || this.getSuit() == CardSuit.WILD);
	}

}

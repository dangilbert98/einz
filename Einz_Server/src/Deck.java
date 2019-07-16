import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 
 */

/**
 * @author dangi
 *
 */
public class Deck {
	private Stack<Card> deck;
	Discard dis;

	public Deck(Discard dis) {
		deck = new Stack<Card>();
		this.dis = dis;
		shuffle();
	}

	public void shuffle() {
		// add all cards in deck to discard
		System.out.println("~~Shuffling deck");
		while (!deck.isEmpty()) {
			dis.addCard(deck.pop());
		}

		List<Card> tempCards = new ArrayList<Card>();
		int size = 1;
		while (!dis.isEmpty()) {
			Card temp = dis.removeTopCard();
			if (temp.getValue() == CardValue.PLAIN_WILD || temp.getValue() == CardValue.PLUS_4) {
				temp.setSuit(CardSuit.WILD);
			}
			int ranIndex = (int) (Math.random() * size);
			tempCards.add(ranIndex, temp);
			size++;
		}
		while (!tempCards.isEmpty()) {
			deck.push(tempCards.remove(0));
		}
		System.out.println("~~Shuffle done!");
	}

	public Card removeTopCard() {
		Card c = deck.pop();
		if (deck.isEmpty())
			shuffle();
		return c;
	}

	public List<Card> dealCards(int amount) {
		List<Card> deal = new ArrayList<Card>();
		for (int i = 0; i < amount; i++) {
			deal.add(removeTopCard());
		}
		return deal;
	}

}

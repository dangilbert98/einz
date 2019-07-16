import java.util.Stack;

/**
 * 
 */

/**
 * @author dangi
 *
 */
public class Discard {
	private Stack<Card> discard;

	public Discard() {
		discard = new Stack<Card>();
		
		System.out.println("~~Filling discard");
		// WITH ZEROS
		discard.add(new Card(CardSuit.RED, CardValue.ZERO));
		discard.add(new Card(CardSuit.RED, CardValue.ONE));
		discard.add(new Card(CardSuit.RED, CardValue.TWO));
		discard.add(new Card(CardSuit.RED, CardValue.THREE));
		discard.add(new Card(CardSuit.RED, CardValue.FOUR));
		discard.add(new Card(CardSuit.RED, CardValue.FIVE));
		discard.add(new Card(CardSuit.RED, CardValue.SIX));
		discard.add(new Card(CardSuit.RED, CardValue.SEVEN));
		discard.add(new Card(CardSuit.RED, CardValue.EIGHT));
		discard.add(new Card(CardSuit.RED, CardValue.NINE));
		discard.add(new Card(CardSuit.RED, CardValue.SKIP));
		discard.add(new Card(CardSuit.RED, CardValue.REVERSE));
		discard.add(new Card(CardSuit.RED, CardValue.PLUS_2));

		discard.add(new Card(CardSuit.YELLOW, CardValue.ZERO));
		discard.add(new Card(CardSuit.YELLOW, CardValue.ONE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.TWO));
		discard.add(new Card(CardSuit.YELLOW, CardValue.THREE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.FOUR));
		discard.add(new Card(CardSuit.YELLOW, CardValue.FIVE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.SIX));
		discard.add(new Card(CardSuit.YELLOW, CardValue.SEVEN));
		discard.add(new Card(CardSuit.YELLOW, CardValue.EIGHT));
		discard.add(new Card(CardSuit.YELLOW, CardValue.NINE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.SKIP));
		discard.add(new Card(CardSuit.YELLOW, CardValue.REVERSE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.PLUS_2));

		discard.add(new Card(CardSuit.GREEN, CardValue.ZERO));
		discard.add(new Card(CardSuit.GREEN, CardValue.ONE));
		discard.add(new Card(CardSuit.GREEN, CardValue.TWO));
		discard.add(new Card(CardSuit.GREEN, CardValue.THREE));
		discard.add(new Card(CardSuit.GREEN, CardValue.FOUR));
		discard.add(new Card(CardSuit.GREEN, CardValue.FIVE));
		discard.add(new Card(CardSuit.GREEN, CardValue.SIX));
		discard.add(new Card(CardSuit.GREEN, CardValue.SEVEN));
		discard.add(new Card(CardSuit.GREEN, CardValue.EIGHT));
		discard.add(new Card(CardSuit.GREEN, CardValue.NINE));
		discard.add(new Card(CardSuit.GREEN, CardValue.SKIP));
		discard.add(new Card(CardSuit.GREEN, CardValue.REVERSE));
		discard.add(new Card(CardSuit.GREEN, CardValue.PLUS_2));

		discard.add(new Card(CardSuit.BLUE, CardValue.ZERO));
		discard.add(new Card(CardSuit.BLUE, CardValue.ONE));
		discard.add(new Card(CardSuit.BLUE, CardValue.TWO));
		discard.add(new Card(CardSuit.BLUE, CardValue.THREE));
		discard.add(new Card(CardSuit.BLUE, CardValue.FOUR));
		discard.add(new Card(CardSuit.BLUE, CardValue.FIVE));
		discard.add(new Card(CardSuit.BLUE, CardValue.SIX));
		discard.add(new Card(CardSuit.BLUE, CardValue.SEVEN));
		discard.add(new Card(CardSuit.BLUE, CardValue.EIGHT));
		discard.add(new Card(CardSuit.BLUE, CardValue.NINE));
		discard.add(new Card(CardSuit.BLUE, CardValue.SKIP));
		discard.add(new Card(CardSuit.BLUE, CardValue.REVERSE));
		discard.add(new Card(CardSuit.BLUE, CardValue.PLUS_2));

		// WITHOUT ZEROS
		discard.add(new Card(CardSuit.RED, CardValue.ONE));
		discard.add(new Card(CardSuit.RED, CardValue.TWO));
		discard.add(new Card(CardSuit.RED, CardValue.THREE));
		discard.add(new Card(CardSuit.RED, CardValue.FOUR));
		discard.add(new Card(CardSuit.RED, CardValue.FIVE));
		discard.add(new Card(CardSuit.RED, CardValue.SIX));
		discard.add(new Card(CardSuit.RED, CardValue.SEVEN));
		discard.add(new Card(CardSuit.RED, CardValue.EIGHT));
		discard.add(new Card(CardSuit.RED, CardValue.NINE));
		discard.add(new Card(CardSuit.RED, CardValue.SKIP));
		discard.add(new Card(CardSuit.RED, CardValue.REVERSE));
		discard.add(new Card(CardSuit.RED, CardValue.PLUS_2));

		discard.add(new Card(CardSuit.YELLOW, CardValue.ONE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.TWO));
		discard.add(new Card(CardSuit.YELLOW, CardValue.THREE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.FOUR));
		discard.add(new Card(CardSuit.YELLOW, CardValue.FIVE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.SIX));
		discard.add(new Card(CardSuit.YELLOW, CardValue.SEVEN));
		discard.add(new Card(CardSuit.YELLOW, CardValue.EIGHT));
		discard.add(new Card(CardSuit.YELLOW, CardValue.NINE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.SKIP));
		discard.add(new Card(CardSuit.YELLOW, CardValue.REVERSE));
		discard.add(new Card(CardSuit.YELLOW, CardValue.PLUS_2));

		discard.add(new Card(CardSuit.GREEN, CardValue.ONE));
		discard.add(new Card(CardSuit.GREEN, CardValue.TWO));
		discard.add(new Card(CardSuit.GREEN, CardValue.THREE));
		discard.add(new Card(CardSuit.GREEN, CardValue.FOUR));
		discard.add(new Card(CardSuit.GREEN, CardValue.FIVE));
		discard.add(new Card(CardSuit.GREEN, CardValue.SIX));
		discard.add(new Card(CardSuit.GREEN, CardValue.SEVEN));
		discard.add(new Card(CardSuit.GREEN, CardValue.EIGHT));
		discard.add(new Card(CardSuit.GREEN, CardValue.NINE));
		discard.add(new Card(CardSuit.GREEN, CardValue.SKIP));
		discard.add(new Card(CardSuit.GREEN, CardValue.REVERSE));
		discard.add(new Card(CardSuit.GREEN, CardValue.PLUS_2));

		discard.add(new Card(CardSuit.BLUE, CardValue.ONE));
		discard.add(new Card(CardSuit.BLUE, CardValue.TWO));
		discard.add(new Card(CardSuit.BLUE, CardValue.THREE));
		discard.add(new Card(CardSuit.BLUE, CardValue.FOUR));
		discard.add(new Card(CardSuit.BLUE, CardValue.FIVE));
		discard.add(new Card(CardSuit.BLUE, CardValue.SIX));
		discard.add(new Card(CardSuit.BLUE, CardValue.SEVEN));
		discard.add(new Card(CardSuit.BLUE, CardValue.EIGHT));
		discard.add(new Card(CardSuit.BLUE, CardValue.NINE));
		discard.add(new Card(CardSuit.BLUE, CardValue.SKIP));
		discard.add(new Card(CardSuit.BLUE, CardValue.REVERSE));
		discard.add(new Card(CardSuit.BLUE, CardValue.PLUS_2));

		// WILDS
		discard.add(new Card(CardSuit.WILD, CardValue.PLAIN_WILD));
		discard.add(new Card(CardSuit.WILD, CardValue.PLAIN_WILD));
		discard.add(new Card(CardSuit.WILD, CardValue.PLAIN_WILD));
		discard.add(new Card(CardSuit.WILD, CardValue.PLAIN_WILD));

		discard.add(new Card(CardSuit.WILD, CardValue.PLUS_4));
		discard.add(new Card(CardSuit.WILD, CardValue.PLUS_4));
		discard.add(new Card(CardSuit.WILD, CardValue.PLUS_4));
		discard.add(new Card(CardSuit.WILD, CardValue.PLUS_4));

		System.out.println("~~discard filled!");
	}

	public void addCard(Card c) {
		discard.push(c);
	}

	public Card showingCard() {
		return discard.peek();
	}

	public Card removeTopCard() {
		return discard.pop();
	}

	public boolean isEmpty() {
		return discard.size() == 0;
	}
}

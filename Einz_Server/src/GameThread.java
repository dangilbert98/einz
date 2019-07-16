import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author dangi
 *
 */
public class GameThread extends Thread {
	/**
	 * 
	 */
	private ServerSocket gameSocket;
	private Socket host;
	private List<Player> players;

	private int direction = 1;
	private int currPlayer;

	private Deck deck;
	private Discard discard;

	private boolean gameOver;
	private Player winner;

	private final int STARTING_HAND_SIZE = 7;
	private final int BAD_EINZ_PENALTY = 4;
	private final int EINZ_PENALTY = 4;

	/**
	 * @param s
	 */
	public GameThread(ServerSocket s, Socket host) {
		this.gameSocket = s;
		this.host = host;
		this.players = new ArrayList<Player>();
		this.gameOver = false;
		this.winner = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		System.out.println("starting end of seach thread");
		EndSearchThread end = new EndSearchThread(host, gameSocket);
		end.start();
		ServerSocket calloutSS = null;
		try {
			calloutSS = new ServerSocket(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int calloutPort = calloutSS.getLocalPort();
		System.out.println("callout port is " + calloutPort);

		boolean listenForNewPlayers = true;
		while (listenForNewPlayers) {
			try {
				System.out.println("waiting for player");
				Socket s = gameSocket.accept();
				System.out.println("Player recieved");
				Player p = new Player(s, calloutPort);
				p.start();
				players.add(p);
				System.out.println("Player added");
				/*
				 * Socket calloutS = calloutSS.accept(); EinzCalloutThread calloutT = new
				 * EinzCalloutThread(calloutS, this, p); calloutT.start();
				 */

			} catch (Exception e) {
				listenForNewPlayers = false;
			}
		}
		System.out.println("done searching");
		try {
			host.close();
			System.out.println("Ended host connection");
		} catch (IOException e) {
			e.printStackTrace();
		}

		discard = new Discard();
		deck = new Deck(discard);

		while (!allNamesIn()) {
		}

		currPlayer = players.size() - 1;
		moveToNextPlayer();

		for (Player player : players) {
			System.out.println("Dealing " + player.getPlayerName() + " hand");
			player.addToHand(deck.dealCards(STARTING_HAND_SIZE));
		}
		Card first = null;
		boolean validFirst = false;
		while (!validFirst) {
			first = deck.removeTopCard();
			validFirst = isValidFirstCard(first);
			if (!validFirst)
				discard.addCard(first);
		}
		// tell first player to expect action
		updatePlayersAdvanced(first);
		processSubmittedCard(first);
		// process first card
		Player previousPlayer = null;
		while (!gameOver) {
			updatePlayers();
			boolean canSubmit = players.get(currPlayer).canSubmitCard();
			if (canSubmit) {
				Card submitted = players.get(currPlayer).getSubmittedCard();
				if (previousPlayer != null) {
					previousPlayer.setCanBeCalledOut(isEinz(previousPlayer));
				}
				processSubmittedCard(submitted);
			} else {
				players.get(currPlayer).addToHand(deck.dealCards(1));
				// so they can see new card
				updatePlayers();
				if (previousPlayer != null) {
					previousPlayer.setCanBeCalledOut(isEinz(previousPlayer));
				}
				canSubmit = players.get(currPlayer).canSubmitCard();
				if (canSubmit) {
					Card submitted = players.get(currPlayer).getSubmittedCard();
					processSubmittedCard(submitted);
				}
			}

			// checks if current player has won
			winner = players.get(currPlayer);
			gameOver = isWinner(winner);
			previousPlayer = players.get(currPlayer);

			moveToNextPlayer();
		}
		updateWinner();
		closeAllPlayers();
	}

	public void closeAllPlayers() {
		for (Player player : players) {
			player.close();
		}
	}

	public void updateWinner() {
		// updates all players of a winner
	}

	public boolean allNamesIn() {
		boolean allIn = true;
		for (Player player : players) {
			if (!player.hasName())
				allIn = false;
		}
		System.out.println("It is " + allIn + " that all names are in");
		return allIn;
	}

	public boolean isEinz(Player player) {
		return (player.getHandSize() == 1);
	}

	public boolean checkCallout(Player player) {
		return player.getCanBeCalledOut();
	}

	public void einzCallout(Player sendingPlayer) {
		boolean foundPlayer = false;
		for (Player player : players) {
			if (checkCallout(player)) {
				foundPlayer = true;
				player.addToHand(deck.dealCards(EINZ_PENALTY));
			}
		}
		if (!foundPlayer) {
			sendingPlayer.addToHand(deck.dealCards(BAD_EINZ_PENALTY));
		}
	}

	public boolean isWinner(Player player) {
		return (player.getHandSize() == 0);
	}

	public void reverse() {
		direction *= -1;
	}

	public void moveToNextPlayer() {
		players.get(currPlayer).setMyTurn(false);
		currPlayer = nextPlayer();
		players.get(currPlayer).setMyTurn(true);
		System.out.println("now its " + players.get(currPlayer).getPlayerName() + "'s turn");
	}

	public boolean isValidFirstCard(Card top) {
		if (top.getValue() == CardValue.PLUS_2 || top.getValue() == CardValue.PLUS_4
				|| top.getValue() == CardValue.REVERSE || top.getValue() == CardValue.SKIP) {
			return false;
		} else if (top.getSuit() == CardSuit.WILD) {
			return false;
		} else
			return true;
	}

	public void processFirstCard(Card submitted) {
		if (submitted.getValue() == CardValue.SKIP) {
			moveToNextPlayer();
		}
		discard.addCard(submitted);
	}

	public void processSubmittedCard(Card submitted) {
		if (submitted.getSuit() == CardSuit.WILD) {
			CardSuit newSuit = players.get(currPlayer).getWildNewColor();
			if (submitted.getValue() == CardValue.PLUS_4) {
				moveToNextPlayer();
				players.get(currPlayer).addToHand(deck.dealCards(4));
			}
			submitted.setSuit(newSuit);
		} else if (submitted.getValue() == CardValue.REVERSE) {
			reverse();
		} else if (submitted.getValue() == CardValue.PLUS_2) {
			moveToNextPlayer();
			players.get(currPlayer).addToHand(deck.dealCards(2));
		} else if (submitted.getValue() == CardValue.SKIP) {
			moveToNextPlayer();
		}
		discard.addCard(submitted);
	}

	public int nextPlayer() {
		int tempIndex = currPlayer + direction;
		int size = players.size();
		if (tempIndex >= size) {
			tempIndex = 0;
		} else if (tempIndex < 0) {
			tempIndex = size - 1;
		}
		return tempIndex;
	}

	public void updatePlayers() {
		updatePlayersAdvanced(discard.showingCard());
	}

	public void updatePlayersAdvanced(Card top) {
		for (Player player : players) {
			player.update(players, top);
		}
	}
}
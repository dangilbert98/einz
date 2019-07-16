import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
	private static final String host = "localhost";
	private static final int HOST_PORT_NUMBER = 3141;

	private static final String HOST_GAME = "host";
	private static final String JOIN_GAME = "join";

	private static Socket clientHost;
	private static Socket clientPlayer;
	private static String name;
	private static int gamePort;
	private static DataInputStream reader;
	private static DataOutputStream writer;
	private static DataInputStream host_reader = null;
	private static DataOutputStream host_writer = null;
	private static List<Card> hand;
	private static int calloutPort;
	private static boolean myTurn = false;
	private static List<sudoPlayer> players;
	private static Card topCard;
	private static Scanner s;

	public static void main(String[] args) {
		s = new Scanner(System.in);
		System.out.println("join or host?");
		String type = s.next();
		s.nextLine();
		boolean valid = false;
		while (!valid) {
			if (type.equals(JOIN_GAME)) {
				valid = true;
				System.out.println("Please enter the game number:");
				gamePort = s.nextInt();
				s.nextLine();
			} else if (type.equals(HOST_GAME)) {
				valid = true;
				try {
					clientHost = new Socket(host, HOST_PORT_NUMBER);
					host_reader = new DataInputStream(clientHost.getInputStream());
					host_writer = new DataOutputStream(clientHost.getOutputStream());
					//System.out.println("~Waiting for game port");
					gamePort = host_reader.readInt();
					System.out.println("\t~Game port is " + gamePort);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		///
		try {
			clientPlayer = new Socket(host, gamePort);
			reader = new DataInputStream(clientPlayer.getInputStream());
			writer = new DataOutputStream(clientPlayer.getOutputStream());

			//System.out.println("~Waiting for callout port");
			calloutPort = reader.readInt();
			//System.out.println("~Got callout port");

			System.out.println("Please enter your name:");
			name = s.nextLine();
			System.out.println("Thanks, " + name);
			writer.writeUTF(name);

			if (host_writer != null) {
				// send kill
				System.out.println("Type 'kill' to end seach");
				while(!s.next().equals("kill")){
					s.nextLine();
					System.out.println("Try again");
				}
				System.out.println("Ok, killing");
				host_writer.writeBoolean(true);
			}

			hand = new ArrayList<Card>();

			// doesnt contain me
			players = new ArrayList<sudoPlayer>();
			processUpdate(true);

			// handle first card wild scenario
			if (myTurn) {
				valid = false;
				if (topCard.getSuit() == CardSuit.WILD) {
					processWild();
				}
			}

			// loop
			boolean running = true;
			while (running) {
				try {
					processUpdate(false);

					if (myTurn) {
						// first, check if can play a card
						List<Card> playable = getPlayableCards();

						// send can submit
						reader.readBoolean();
						//System.out.println("~~canSub = " + !playable.isEmpty());
						boolean canSub = (!playable.isEmpty());
						writer.writeBoolean(canSub);

						if (canSub) {
							displayPlayable(playable);

							// user choice
							int choice = getChosenIndex(playable);

							// write suit and value
							Card subCard = playable.get(choice - 1);
							writer.writeUTF(subCard.getSuit().name());
							writer.writeUTF(subCard.getValue().name());

							// send new suit if wild
							if (subCard.getSuit() == CardSuit.WILD) {
								processWild();
							}
						} else {
							System.out.println("*Can not play card*\nDrawing...");
							// get new drawn card
							processUpdate(false);

							// check for playable cards
							playable = getPlayableCards();

							// send can submit
							reader.readBoolean();
							canSub = (!playable.isEmpty());
							writer.writeBoolean(canSub);

							if (canSub) {
								displayPlayable(playable);

								int choice = getChosenIndex(playable);

								// write suit and value
								Card subCard = playable.get(choice - 1);
								writer.writeUTF(subCard.getSuit().name());
								writer.writeUTF(subCard.getValue().name());

								// send new suit if wild
								if (subCard.getSuit() == CardSuit.WILD) {
									processWild();
								}
							}
							else {
								System.out.println("*Can not play card*\n");
							}
						}
						System.out.println("Your turn is over");
					}
				} catch (Exception e) {
					running = false;
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.close();
	}

	public static void processUpdate(boolean first) throws IOException {
		// update method start
		String out = "";
		out += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
		int numP = reader.readInt();
		sudoPlayer me = null;
		players.clear();
		for (int i = 0; i < numP; i++) {
			String in = reader.readUTF();
			String[] splitted = in.split(",");
			sudoPlayer temp = new sudoPlayer(splitted[0], Integer.parseInt(splitted[1]),
					Boolean.parseBoolean(splitted[2]));
			if(temp.isTurn()) out += "It is " + temp.getName() + "'s turn\n";
			if (splitted[0].equals(name)) {
				me = temp;
			} else {
				players.add(temp);
			}
		}
		out += "Other Players:\n" + players + "\n\n";
		int myHandSize = me.getHandSize();
		hand.clear();
		for (int i = 0; i < myHandSize; i++) {
			String card = reader.readUTF();
			String[] cardSplit = card.split(",");
			hand.add(new Card(CardSuit.valueOf(cardSplit[0]), CardValue.valueOf(cardSplit[1])));
		}
		out += "Hand:\n" + hand + "\n\n";

		myTurn = me.isTurn();

		String top = reader.readUTF();
		String[] topSplit = top.split(",");
		topCard = new Card(CardSuit.valueOf(topSplit[0]), CardValue.valueOf(topSplit[1]));
		out += "Top card is " + topCard.toString() + "\n\n";
		
		
		out += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
		if(!first) System.out.println(out);
		// end update
	}

	public static void processWild() throws IOException {
		reader.readBoolean();
		boolean valid = false;
		while (!valid) {
			System.out.println("Please enter the suit you wish to change to:");
			String newS = s.next();
			newS = newS.toUpperCase();
			s.hasNextLine();
			try {
				writer.writeUTF(CardSuit.valueOf(newS).name());
				valid = true;
			} catch (Exception e) {
				System.out.println("Bad Suit, try again:");
			}
		}
	}

	public static void displayPlayable(List <Card> playable) {
		System.out.println("You can play: ");
		for (int i = 0; i < playable.size(); i++) {
			System.out.println(i + 1 + ") " + playable.get(i).toString());
		}
		System.out.println("Which number card would you like to play?");
	}

	public static List<Card> getPlayableCards() {
		List<Card> playable = new ArrayList<Card>();
		for (Card c : hand) {
			if (c.matches(topCard)) {
				//System.out.println(c + " matches " + topCard);
				playable.add(c);
			}
		}
		return playable;
	}

	public static int getChosenIndex(List<Card> playable) {
		int choice = 0;
		boolean valid = false;
		while (!valid) {
			try {
				choice = s.nextInt();
				s.nextLine();
				if (choice <= 0 || choice > playable.size()) {
					System.out.println("Not a valid option, try again");
				} else {
					valid = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Not a number, try again.");
			}
		}
		return choice;
	}

}

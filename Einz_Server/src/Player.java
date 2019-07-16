import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author dangi
 *
 */
public class Player extends Thread {
	/**
	 * 
	 */
	private String playerName = null;
	private boolean myTurn;

	private volatile boolean running = true;
	/**
	 * 
	 */
	private Socket playerSocket;
	private DataInputStream reader;
	private DataOutputStream writer;
	private List<Card> hand;
	private boolean canBeCalledOut;


	/**
	 * @param name
	 * @param s
	 */
	public Player(Socket s, int calloutPort) {
		this.playerSocket = s;
		try {
			this.writer = new DataOutputStream(playerSocket.getOutputStream());
			this.reader = new DataInputStream(playerSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.hand = new ArrayList<Card>();
		this.myTurn = false;
		this.canBeCalledOut = false;
		
		try {
			writer.writeInt(calloutPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			System.out.println("~waiting for name");
			playerName = reader.readUTF();
			System.out.println("~Welcome, " + playerName);
			while (running) {
			}
			reader.close();
			writer.close();
			playerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasName() {
		return (playerName != null);
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void close() {
		running  = false;
	}

	public void addToHand(List<Card> newCards) {
		hand.addAll(newCards);
	}

	public void setMyTurn(boolean t) {
		myTurn = t;
	}

	public Card getSubmittedCard() {
		try {
			CardSuit s = CardSuit.valueOf(reader.readUTF());
			CardValue v = CardValue.valueOf(reader.readUTF());
			Card ret = new Card(s, v);
			
			//remove from hand
			int rIndex = -1;
			for(int i = 0; i < hand.size(); i++) {
				if(hand.get(i).equals(ret)){
					rIndex = i;
					break;
				}
			}
			hand.remove(rIndex);
			
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getHandSize() {
		return hand.size();
	}

	public boolean canSubmitCard() {
		boolean canSub = false;
		try {
			writer.writeBoolean(true);
			canSub = reader.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return canSub;
	}

	public CardSuit getWildNewColor() {
		String suit = null;
		try {
			writer.writeBoolean(true);
			suit = reader.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CardSuit.valueOf(suit);
	}

	public String toUpdateString() {
		return playerName + "," + hand.size() + "," + myTurn;
	}

	public void update(List<Player> players, Card topCard) {
		try {
			writer.writeInt(players.size());
			for (Player p : players) {
				writer.writeUTF(p.toUpdateString());
			}
			for(Card c : hand) {
				writer.writeUTF(c.toString());
			}
			writer.writeUTF(topCard.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public boolean getCanBeCalledOut() {
		return canBeCalledOut;
	}

	public void setCanBeCalledOut(boolean canBeCalledOut) {
		this.canBeCalledOut = canBeCalledOut;
	}
}

public class sudoPlayer {
	private String name;
	private int handSize;
	private boolean turn;
	
	public sudoPlayer(String name, int handSize, boolean turn) {
		this.name = name;
		this.handSize = handSize;
		this.turn = turn;
	}
	
	public String toString() {
		return name + "," + handSize + "," + turn;
	}

	public String getName() {
		return name;
	}

	public int getHandSize() {
		return handSize;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHandSize(int handSize) {
		this.handSize = handSize;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
}

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author dangi
 *
 */
public class EinzCalloutThread extends Thread {
	private Socket calloutSocket;
	private GameThread game;
	private DataInputStream reader;
	private Player player;

	/**
	 * @param s
	 */
	public EinzCalloutThread(Socket s, GameThread game, Player player) {
		this.calloutSocket = s;
		this.game = game;
		this.player = player;
		try {
			reader = new DataInputStream(calloutSocket.getInputStream());
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
		while (true) {
			try {
				reader.readBoolean();
				game.einzCallout(player);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dangi
 *
 */

public class Einz_Server {
	private static final int PORT_NUMBER = 3141;
	private static ServerSocket server;

	private static final int MAX_GAMES = 10;
	private static int numGames = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start main server
		try {
			server = new ServerSocket(PORT_NUMBER);
			System.out.println("Running on port " + server.getLocalPort());

		} catch (IOException IOex) {
			System.out.println("Server error");
			IOex.printStackTrace();
		}

		while (numGames < MAX_GAMES) {
			try {
				Socket newGameStarter = server.accept();
				System.out.println("Got a new player");
				DataOutputStream writer = new DataOutputStream(newGameStarter.getOutputStream());

				ServerSocket nG = new ServerSocket(0);
				int nGPort = nG.getLocalPort();
				System.out.println("\tOn port " + nGPort);
				writer.writeInt(nGPort);

				Thread t = new GameThread(nG, newGameStarter);
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

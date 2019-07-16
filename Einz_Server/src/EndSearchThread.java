import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */

/**
 * @author dangi
 *
 */
public class EndSearchThread extends Thread {
	/**
	 * 
	 */
	Socket endSocket;
	ServerSocket searchSocket;

	/**
	 * @param s
	 */
	public EndSearchThread(Socket s, ServerSocket searchSocket) {
		this.endSocket = s;
		this.searchSocket = searchSocket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		boolean done = false;
		try {
			DataInputStream r = new DataInputStream(endSocket.getInputStream());
			System.out.println("~~waiting for end signal");
			r.readBoolean();
			searchSocket.close();
			System.out.println("~~signal received");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Client {
	JButton sendButton, clearButton;
	JTextField typeField;
	static JTextArea msgArea;
	JPanel southPanel;
	String username = "";
	JScrollPane sp;
	String data = "Ready to begin";
	String ip = null;

	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean

	/**
	 * Main
	 * 
	 * @param args
	 *            parameters from command line
	 */
	public static void main(String[] args) {
		// JFrame login = new JFrame("Login Page");
		// JTextField username;
		// JTextField password;
		// JButton logMeIn = new JButton("Login");
		// logMeIn.addActionListener(new ButtonListener());

		Client client = new Client(); // start the client
		try {
			client.go(); // begin the connection
		} catch (Exception e) {
			msgArea.append("\n Connection to Server Failed!");
			// e.printStackTrace();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				// e.printStackTrace();

			}
			msgArea.append("\n Exiting program!");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				// e.printStackTrace();

			}
			System.exit(0);
		}
	}

	public void go() {
		try {
			mySocket = new Socket(ip, 5000);
			// attempt socket connection (local address). This will wait until a
			// connection is made

			InputStreamReader stream1 = new InputStreamReader(
					mySocket.getInputStream()); // Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream());
			// assign printwriter to network stream

		} catch (IOException e) { // connection error occured
			msgArea.append("\n Connection to Server Failed!");
			// e.printStackTrace();
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				// e.printStackTrace();

			}
			msgArea.append("\n Exiting program!");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				// e.printStackTrace();

			}
			System.exit(0);
		}

		// Send first bit of information
		output.println(data);
		output.flush();

		try {
			if (input.ready()) {
				String message = input.readLine();

			}
		} catch (IOException e) {
			msgArea.append("\n" + "Failed to receive msg from the server!");
			running = false;
			// e.printStackTrace();
		}

		output.println(data);
		output.flush();
		while (running) {
			try {
				if (input.ready()) {
					String message = input.readLine();
					msgArea.append("\n" + message);
					if (message.toLowerCase().equals("quit")) {
						running = false;
					}
				}
			} catch (IOException e) {
				msgArea.append("\n" + "Failed to receive msg from the server!");
				running = false;
				// e.printStackTrace();
			}
		}

	}

	// ************ Inner class to represent AI object
	class Enemy implements Runnable {
		private int x, y;
		private Image picture;
		private boolean alive = true;

		public Enemy(int startX, int startY, Image pic) { // constructor

			// set starting coords
			this.x = startX;
			this.y = startY;
			this.picture = pic;

		}

		public void run() { // Run
			while (alive) {
				this.x += (int) (Math.random() * 6 - 3);
				this.y += (int) (Math.random() * 6 - 3);

				try {
					Thread.sleep(30);
				} catch (Exception exc) {
				}

			}
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public Image getImage() {
			return this.picture;
		}

		public void kill() {
			this.alive = false;
		}

	}
}

/* [ChatProgramServer.java]
 * Description: This is an example of a chat server.
 * The program  waits for a client and accepts a message. 
 * It then responds to the message and quits.
 * This server demonstrates how to employ multithreading to accepts multiple clients
 * @author Mangat
 * @version 1.0a
 */

//imports for network communication
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class ServerComm2 {

	ServerSocket serverSock;// server socket for connection
	static boolean running = true; // controls if the server is accepting
									// clients
	static ArrayList<ConnectionHandler> clientList = new ArrayList();
	Player[] players;
	static boolean[] clientsRunning = new boolean[8];
	int gameType;
	int mapNum = 0;
	int [][]currentMap = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,1,1,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	double playerPositions[][] = new double[8][2];

	public static void main(String[] args) {
		new ServerComm2().go(); // start the server
	}

	public void go() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please Enter the port to watch:");
		int port = keyboard.nextInt();
		// Game Mode Key
		// 1 = deathmatch
		// 2 = ???
		System.out.println("Please enter the gametype:");
		gameType = keyboard.nextInt();
		Socket client = null;// hold the client connection

		try {
			serverSock = new ServerSocket(port);
			// serverSock.setSoTimeout(50000); //5 second timeout

			while (running) {
				Boolean isSpace = false;
				int index = 0;
				for (int i = 0; i < clientsRunning.length; i++) {
					if (clientsRunning[i] == false) {
						isSpace = true;
						index = i;
					}
				}
				if (isSpace == true) {
					clientsRunning[index] = true;
					client = serverSock.accept();
					System.out.println("Client connected");

					ConnectionHandler clientHandler = new ConnectionHandler(client);

					clientList.add(clientHandler);
					for (int i = 0; i < clientList.size(); i++) {
						ConnectionHandler yo = clientList.get(i);
						if (yo != null)
							yo.setPlayerList(clientList);
					}

					Thread t = new Thread(clientHandler);
					t.start(); // start the new thread
				}

			}
		} catch (Exception e) {
			// System.out.println("Error accepting connection");
			// close all and quit
			try {
				client.close();
			} catch (Exception e1) {
				System.out.println("Failed to close socket");
			}
			System.exit(-1);
		}
	}

	// ***** Inner class - thread for client connection
	class ConnectionHandler implements Runnable {

		private PrintWriter output; // assign printwriter to network stream
		private BufferedReader input; // Stream for network input
		private Socket client; // keeps track of the client socket
		private boolean running;
		String name;
		ArrayList<ConnectionHandler> clientList = new ArrayList();

		void updateLocations() {
			int i = 0;
			for (ConnectionHandler c : clientList) {
				for (ConnectionHandler cc : clientList) {
					try {
						output= new PrintWriter(client.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Ray's computer is satan incarnate");
					}
					output.println("1 0 3");
					output.println(playerPositions[i][0] + " " + playerPositions[i][1] + " " + i);
					output.flush();
					i++;
				}
				i = 0;

			}
		}

		void playerUpdate(double x, double y, int i) {
			
			playerPositions[i][0]=x;
			playerPositions[i][1]=y;

		}

		void giveID() {
			int i = 0;
			for (ConnectionHandler cc : clientList) {
				try {
					output= new PrintWriter(client.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Ray's computer is satan incarnate");
				}
				output.println(i);
				output.flush();
				i++;
			}
		}

		/*
		 * ConnectionHandler Constructor
		 * 
		 * @param the socket belonging to this client connection
		 */
		ConnectionHandler(Socket s) {
			this.client = s; // constructor assigns client to this
			try { // assign all connections to client
				this.output = new PrintWriter(client.getOutputStream());
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			running = true;
		} // end of constructor

		/*
		 * run executed on start of thread
		 */
		public void run() {

			// Get a message from the client
			String data = "";
			while (clientList == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			output.println(clientList.indexOf(client) + mapNum + clientList.size());

			while (running) { // loop until a server is closed
				try {
					// Blah blah blah something about selecting game modes and
					// starting

					// create the players
					players = new Player[clientList.size()];
					int ff = 0;
					for (ConnectionHandler c : clientList) {
						Hitbox h = new Hitbox();

						double x, y,d;
						do {
							x = Math.random()*45;
							y = Math.random()*45;
							d = Math.random()*2*Math.PI;

						} while (currentMap[(int) x][(int) y] != 0);

						players[ff] = new Player(x, y, d);

						updateLocations();

					}
					DeathMatch dm=new DeathMatch(players);
					if (input.ready()) { // check for an incoming message
						data = input.readLine();
						System.out.println(data);
						// Data code book
						// 0 is position update ( 0,PID,X,Y)
						// 1 is a shot fired ( 1,PID,TYPE,X,Y,Direction)
						// 2 is a kill ( 2,VID,KID)
//						StringTokenizer st = new StringTokenizer(data, ",");
//						while (st.hasMoreTokens()) {
//							if (st.nextToken().equals("0")) {
//								playerUpdate(Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()),Integer.parseInt(st.nextToken()));
//								updateLocations();
//							} else if (st.nextToken().equals("1")) {
//
//							} else {
//								int ded= Integer.parseInt(st.nextToken());
//								playerUpdate(-1,-1,ded);
//								dm.addDeath(ded);
//								dm.addScore(1, Integer.parseInt(st.nextToken()));
//								if (dm.checkWinner())
//									running=false;
//							}
//						}

						if (data.toLowerCase().equals("quit")) {
							running = false;
							boolean done = false;
							for (int i = 0; i < clientsRunning.length && done == false; i++) {
								if (clientsRunning[i] = true) {
									clientsRunning[i] = false;
									done = true;
								}
							}
						}

					}
				} catch (IOException e) {
					System.out.println("Failed to receive msg from the client");
					e.printStackTrace();
				}
			}

			// Send a message to the client
			output.println("We got your message! Goodbye.");
			output.flush();

			// close the socket
			try {
				input.close();
				output.close();
				client.close();
			} catch (Exception e) {
				System.out.println("Failed to close socket");
			}
		} // end of run()

		Socket getClient() {
			return client;

		}

		void setPlayerList(ArrayList<ConnectionHandler> in) {
			this.clientList = in;
			output.println("01 " + clientList.indexOf(client) + clientList.size());
		}

	} // end of inner class
} // end of ChatProgramServer class
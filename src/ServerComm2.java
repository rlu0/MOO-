/* [ChatProgramServer.java]
 * Description: This is an example of a chat server.
 * The program  waits for a client and accepts a message. 
 * It then responds to the message and quits.
 * This server demonstrates how to employ multithreading to accepts multiple clients
 * @author Mangat
 * @version 1.0a
 */

//imports for network communication
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ServerComm2
{

	ServerSocket serverSock;// server socket for connection
	static boolean running = true; // controls if the server is accepting
									// clients
	static ArrayList<ConnectionHandler> clientList = new ArrayList();
	Player[] players;
	static boolean[] clientsRunning = new boolean[8];
	int gameType;
	int mapNum = 0;
	int[][] currentMap = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1,
					1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
					0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1,
					1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1 } };
	double playerPositions[][] = new double[8][2];

	public static void main(String[] args)
	{
		new ServerComm2().go(); // start the server
	}

	boolean doingStuff;

	public void go()
	{
		class buttonListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				doingStuff = true;
				System.out.println(doingStuff);
			}

		}

		boolean ready = false;
		JFrame window = new JFrame("MOOD");
		JPanel menuThing = new JPanel();
		JLabel DOOM = new JLabel("MOOD THe GaMe - Server");
		JLabel spacer = new JLabel("");
		JLabel usernameL = new JLabel("Map: ");
		JLabel IPL = new JLabel("Game Type: ");
		JLabel portL = new JLabel("Port: ");
		JTextField portF = new JTextField();
		JTextField usernameF = new JTextField();
		JTextField IPF = new JTextField();
		JButton joinB = new JButton("Start Server");
		buttonListener thing = new buttonListener();
		joinB.addActionListener(thing);
		GridLayout lay = new GridLayout(5, 2, 2, 2);
		menuThing.add(DOOM);
		menuThing.add(spacer);
		menuThing.setLayout(lay);
		menuThing.add(usernameL);
		menuThing.add(usernameF);
		menuThing.add(IPL);
		menuThing.add(IPF);
		menuThing.add(portL);
		menuThing.add(portF);
		menuThing.add(joinB);

		window.add(menuThing);
		window.setResizable(false);
		window.setVisible(true);
		window.setSize(300, 175);
		int port = -1;
		while (ready != true)
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			if (doingStuff == true)
			{
				try
				{
					gameType = Integer.parseInt(IPF.getText());
					//System.out.println("first");
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null,
							"The GameType is not an integer!");
					doingStuff = false;
				}
				if (doingStuff == true)
				{
					try
					{
						port = Integer.parseInt(portF.getText());
						//System.out.println("second");
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null,
								"The Port is not an integer!");
						doingStuff = false;
					}
					if (doingStuff == true)
					{
						try
						{
							//System.out.println("third");
							mapNum = Integer.parseInt(usernameF.getText());
							System.out.println(mapNum);
							if (mapNum >= 10 || mapNum < -1)
							{
								JOptionPane.showMessageDialog(null,
										"The map is not an integer or is not listed!");
								doingStuff = false;
							}
							else
							{
								ready = true;
							}
						}
						catch (Exception e)
						{
							JOptionPane.showMessageDialog(null,
									"The map is not an integer or is not listed!");
							doingStuff = false;
						}
					}
				}
			}
		}
		window.setVisible(false);
		// Scanner keyboard = new Scanner(System.in);
		// System.out.println("Please Enter the port to watch:");
		// int port = keyboard.nextInt();
		// Game Mode Key
		// 1 = deathmatch
		// 2 = ???
		// System.out.println("Please enter the gametype:");
		// gameType = keyboard.nextInt();
		Socket client = null;// hold the client connection
		Boolean isSpace = false;
		while (running)
		{
			try
			{
				serverSock = new ServerSocket(port);
				// serverSock.setSoTimeout(50000); //5 second timeout

				int index = 0;
				for (int i = 0; i < clientsRunning.length; i++)
				{
					if (clientsRunning[i] == false)
					{
						isSpace = true;
						index = i;
					}
				}
				if (isSpace == true)
				{
					clientsRunning[index] = true;
					client = serverSock.accept();
					System.out.println("Client connected");

					ConnectionHandler clientHandler = new ConnectionHandler(
							client);

					clientList.add(clientHandler);
					// updates client list for existing players
//					for (int i = 0; i < clientList.size(); i++)
//					{
//						ConnectionHandler yo = clientList.get(i);
//						if (yo != null)
//							yo.setPlayerList(clientList);
//					}
					
					(new Thread(clientHandler)).start();
					
//					Thread t = new Thread(clientHandler);
//					t.start(); // start the new thread
					
				}

			}
			catch (Exception e)
			{
				try
				{
					client.close();
				}
				catch (Exception e1)
				{
					System.out.println("Failed to close socket");
				}
			}
		}

	}

	// ***** Inner class - thread for client connection
	class ConnectionHandler implements Runnable
	{

		private PrintWriter output; // assign printwriter to network stream
		private BufferedReader input; // Stream for network input
		private Socket client; // keeps track of the client socket
		private boolean running;
		String name;
		ArrayList<ConnectionHandler> clientList = new ArrayList();

		void updateLocations()
		{
			int i = 0;
			for (ConnectionHandler c : clientList)
			{
				for (ConnectionHandler cc : clientList)
				{
					try
					{
						output = new PrintWriter(client.getOutputStream());
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						System.out.println("Ray's computer is satan incarnate");
					}
					//output.println("1 0 3");
					//output.println(playerPositions[i][0] + " "
					//		+ playerPositions[i][1] + " " + i);
					//output.flush();
					i++;
				}
				i = 0;

			}
		}

		void playerUpdate(double x, double y, int i)
		{

			playerPositions[i][0] = x;
			playerPositions[i][1] = y;

		}

		void giveID()
		{
			int i = 0;
			for (ConnectionHandler cc : clientList)
			{
				try
				{
					output = new PrintWriter(client.getOutputStream());
				}
				catch (IOException e)
				{
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
		ConnectionHandler(Socket s)
		{
			this.client = s; // constructor assigns client to this
			try
			{ // assign all connections to client
				this.output = new PrintWriter(client.getOutputStream());
				InputStreamReader stream = new InputStreamReader(
						client.getInputStream());
				this.input = new BufferedReader(stream);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			running = true;
		} // end of constructor

		/*
		 * run executed on start of thread
		 */
		public void run()
		{

			// Get a message from the client
			String data = "";
			System.out.println("Done this");
			while (clientList == null)
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			data = null;
			while (data == null)
			{
				System.out.println(clientList.indexOf(client) + " " + mapNum + " " + clientList.size());
				output.println(
						clientList.indexOf(client) + " " + mapNum + " " + clientList.size());
				output.flush();
				System.out.println("flushed");
				try
				{
					Thread.sleep(5000);
				}
				catch(Exception ie)
				{
					
				}
				try
				{
					if (input.ready())
					{
						data = input.readLine();
						System.out.println(data);
					}
				}
				catch (IOException e)
				{
					
				}
			}
			while (running)
			{ // loop until a server is closed
				
				try
				{
					/*
					// create the players
					players = new Player[clientList.size()];
					int ff = 0;
					for (ConnectionHandler c : clientList)
					{
						Hitbox h = new Hitbox();

						double x, y, d;
						do
						{
							x = Math.random() * 45;
							y = Math.random() * 45;
							d = Math.random() * 2 * Math.PI;

						}
						while (currentMap[(int) x][(int) y] != 0);

						players[ff] = new Player(x, y, d);

						//updateLocations();

					}
					DeathMatch dm = new DeathMatch(players);
					*/
					if (input.ready())
					{ // check for an incoming message
						data = input.readLine();
						System.out.println(data);
						// Data code book
						// 0 is position update ( 0,PID,X,Y)
						// 1 is a shot fired ( 1,PID,TYPE,X,Y,Direction)
						// 2 is a kill ( 2,VID,KID)
						// StringTokenizer st = new StringTokenizer(data, ",");
						// while (st.hasMoreTokens()) {
						// if (st.nextToken().equals("0")) {
						// playerUpdate(Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()),Integer.parseInt(st.nextToken()));
						// updateLocations();
						// } else if (st.nextToken().equals("1")) {
						//
						// } else {
						// int ded= Integer.parseInt(st.nextToken());
						// playerUpdate(-1,-1,ded);
						// dm.addDeath(ded);
						// dm.addScore(1, Integer.parseInt(st.nextToken()));
						// if (dm.checkWinner())
						// running=false;
						// }
						// }

						if (data.toLowerCase().equals("quit"))
						{
							running = false;
							boolean done = false;
							for (int i = 0; i < clientsRunning.length
									&& done == false; i++)
							{
								if (clientsRunning[i] = true)
								{
									clientsRunning[i] = false;
									done = true;
								}
							}
						}

					}
				}
				catch (IOException e)
				{
					System.out.println("Failed to receive msg from the client");
					e.printStackTrace();
				}
			}

			// Send a message to the client
			output.println("We got your message! Goodbye.");
			output.flush();

			// close the socket
			try
			{
				input.close();
				output.close();
				client.close();
			}
			catch (Exception e)
			{
				System.out.println("Failed to close socket");
			}
		} // end of run()

		Socket getClient()
		{
			return client;

		}

		void setPlayerList(ArrayList<ConnectionHandler> in)
		{
			this.clientList = in;
			output.println(
					"01 " + clientList.indexOf(client) + clientList.size());
		}

	} // end of inner class
} // end of ChatProgramServer class
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

public class Serverito
{
	ServerSocket serverSock;// server socket for connection
	static boolean running = true; // controls if the server is accepting
									// clients
	static ArrayList<ConnectionHandler> clientList = new ArrayList();
	Player[] players;
	static boolean[] clientsRunning = new boolean[8];
	int gameType;
	int mapNum = 0;
	int[][] currentMap = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1 },
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
		new Serverito().go(); // start the server
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
					// System.out.println("first");
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
						// System.out.println("second");
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
							// System.out.println("third");
							mapNum = Integer.parseInt(usernameF.getText());
							System.out.println(mapNum);
							if (mapNum >= 10 || mapNum < -1)
							{
								JOptionPane
										.showMessageDialog(null,
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
							JOptionPane
									.showMessageDialog(null,
											"The map is not an integer or is not listed!");
							doingStuff = false;
						}
					}
				}
			}
		}
		window.setVisible(false);
		System.out.println("Game started on port: " + port);
		Socket client = null;// hold the client connection
		Boolean isSpace = false;
		try
		{
			serverSock = new ServerSocket(port);
		}
		catch (Exception e)
		{
			System.out.println("YOU FORGOT TO CLOSE IT DAMMIT");
		}
		while (running)
		{
			try
			{
				client = serverSock.accept();
				System.out.println("Client connected");

				ConnectionHandler clientHandler = new ConnectionHandler(
						client);

				clientList.add(clientHandler);

				(new Thread(clientHandler)).start();
				// }

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
					// output.println("1 0 3");
					// output.println(playerPositions[i][0] + " "
					// + playerPositions[i][1] + " " + i);
					// output.flush();
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
			while (running)
			{ // loop until a server is closed
				System.out.println("yeash");
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception e)
				{
					System.out.println("");
				}
				output.println("1 " + mapNum + " 7");
				output.flush();
				try
				{
					if (input.ready())
					{ // check for an incoming message
						data = input.readLine();
						System.out.println(data);
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
			output.println("01 " + clientList.indexOf(client)
					+ clientList.size());
		}

		// end of inner class
	} // end of ChatProgramServer class
}

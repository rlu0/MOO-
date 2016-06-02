import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Client
{
	JButton sendButton, clearButton;
	JTextField typeField;
	static JTextArea msgArea;
	JPanel southPanel;
	String username = "";
	JScrollPane sp;
	String data = "Ready to begin";
	String ip = "10.242.168.169";
	Player you;
	boolean newMap = true;

	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean

	// Various maps will be designed and put into here
	static int[][] mapNeg1 = {};
	static int[][] map0 = new int[45][45];
	static int[][] map1 = {};
	static int[][] map2 = {};
	static int[][] map3 = {};
	static int[][] map4 = {};
	static int[][] map5 = {};
	static int[][] map6 = {};
	static int[][] map7 = {};
	static int[][] map8 = {};
	static int[][] map9 = {};
	static int[][][] allMaps = { map0, map1, map2, map3, map4, map5, map6,
			map7, map8,
			map9, mapNeg1 };
	int[][] currentMap;
	int playerNum;
	int numPlayers;

	// Things relating to movement
	static boolean wPressed = false;
	static boolean aPressed = false;
	static boolean sPressed = false;
	static boolean dPressed = false;

	static boolean wSent1 = true;
	static boolean wSent2 = true;
	static boolean aSent1 = true;
	static boolean aSent2 = true;
	static boolean sSent1 = true;
	static boolean sSent2 = true;
	static boolean dSent1 = true;
	static boolean dSent2 = true;

	/**
	 * Main
	 * 
	 * @param args parameters from command line
	 */
	public static void main(String[] args)
	{
		try
		{
			Scanner inFile = new Scanner(new File("gridMap1.txt"));
			for (int j = 0; j < 45; j++)
				for (int i = 0; i < 45; i++)
				{
					map0[j][i] = inFile.nextInt();
				}
		}
		catch (Exception e)
		{
			System.out.println("Now die");
		}

		Client client = new Client(); // start the client
		try
		{
			client.go(); // begin the connection
		}
		catch (Exception e)
		{
			msgArea.append("\n Connection to Server Failed!");
			// e.printStackTrace();
			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			msgArea.append("\n Exiting program!");
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			System.exit(0);
		}

	}

	public void go()
	{
		try
		{
			mySocket = new Socket(ip, 5000);
			// attempt socket connection (local address). This will wait until a
			// connection is made

			InputStreamReader stream1 = new InputStreamReader(
					mySocket.getInputStream()); // Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream());
			// assign printwriter to network stream

		}
		catch (IOException e)
		{ // connection error occured
			msgArea.append("\n Connection to Server Failed!");
			// e.printStackTrace();
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			msgArea.append("\n Exiting program!");
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			System.exit(0);
		}

		// Send first bit of information
		output.println(data);
		output.flush();

		// Establishes the world and a location based on the playerAssignment
		try
		{
			if (input.ready())
			{
				// Message formatted: playerNum mapNum numEnemies
				String message = input.readLine();

				playerNum = Integer.parseInt(message.substring(0,
						message.indexOf(" ")));
				message = message.substring(message.indexOf(" ") + 1);
				int mapNum = Integer.parseInt(message.substring(0,
						message.indexOf(" ")));
				message = message.substring(message.indexOf(" ") + 1);
				currentMap = allMaps[mapNum];
				numPlayers = Integer.parseInt(message) + 1;

			}
		}
		catch (IOException e)
		{
			running = false;
			// e.printStackTrace();
		}

		System.out.println("Yo mamma1");
		(new Thread(new gameWindow())).run();
		System.out.println("Yo mamma2");
		MyKeyListenerA keyboard = new MyKeyListenerA();

		while (running)
		{
			try
			{
				if (input.ready())
				{
					String message = input.readLine();
					msgArea.append("\n" + message);
					if (message.toLowerCase().equals("quit"))
					{
						running = false;
					}
				}
			}
			catch (IOException e)
			{
				msgArea.append("\n" + "Failed to receive msg from the server!");
				running = false;
				// e.printStackTrace();
			}

			if (wPressed == true)
			{
				if (wSent1 == false)
				{
					wSent1 = true;
					wSent2 = false;
					output.println("gw");
				}
			}
			if (wPressed == false)
			{
				if (wSent2 == false)
				{
					wSent2 = true;
					wSent1 = false;
					output.println("dw");
				}
			}
			if (aPressed == true)
			{
				if (aSent1 == false)
				{
					aSent1 = true;
					aSent2 = false;
					output.println("ga");
				}
			}
			if (aPressed == false)
			{
				if (aSent1 == false)
				{
					aSent2 = true;
					aSent1 = false;
					output.println("da");
				}
			}
			if (sPressed == true)
			{
				if (sSent1 == false)
				{
					sSent1 = true;
					sSent2 = false;
					output.println("gs");
				}
			}
			if (sPressed == false)
			{
				if (sSent1 == false)
				{
					sSent2 = true;
					sSent1 = false;
					output.println("ds");
				}
			}
			if (dPressed == true)
			{
				if (dSent1 == false)
				{
					dSent1 = true;
					dSent2 = false;
					output.println("gd");
				}
			}
			if (dPressed == false)
			{
				if (dSent1 == false)
				{
					dSent1 = true;
					dSent2 = false;
					output.println("dd");
				}
			}
		}

	}

	class gameWindow implements Runnable
	{

		@Override
		public void run()
		{
			System.out.println("Yo mamma");
			JFrame window = new JFrame("MOOD");
			JPanel bananarama = new GameDisp();
			window.add(bananarama);
			window.setVisible(true);
			window.setSize(450, 450);
			window.repaint();
			while (running)
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
			window.repaint();
		}

	}

	class GameDisp extends JPanel
	{
		GameDisp()
		{
			setSize(450, 450);
		}

		public void paintComponent(Graphics g)
		{
			int y = 0;

			for (int j = 0; j < currentMap.length; j++)
			{
				int x = 0;
				for (int i = 0; i < currentMap[j].length; i++)
				{
					if (currentMap[j][i] == 1)
					{
						g.setColor(Color.RED);
						g.drawRect(x, y, x + 5, y + 5);
					}
					x += 5;
				}
				y += 5;
			}
			if (newMap == true)
			{
				double playx = 0;
				double playy = 0;
				while (currentMap[(int) (playy)][(int) (playx)] != 0)
				{
					playx = Math.random() * 450;
					playy = (Math.random() * 450);
				}
				you = new Player(playx, playy);
			}
			double currentX = you.getX();
			double currentY = you.getY();
			g.setColor(Color.BLUE);
			g.drawOval((int) (currentX - 2.5), (int) (currentY - 2.5),
					(int) (currentX + 2.5), (int) (currentY + 2.5));

		}
	}

	public class MyKeyListenerA implements KeyListener
	{

		public void keyTyped(KeyEvent e)
		{
			// if (KeyEvent.getKeyText(e.getKeyCode()).equals("Enter"))
			// {
			//
			// }
		}

		public void keyPressed(KeyEvent e)
		{

			System.out.println("keyPressed="
					+ KeyEvent.getKeyText(e.getKeyCode()));

			if (KeyEvent.getKeyText(e.getKeyCode()).equals("W"))
			{
				wPressed = true;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("A"))
			{
				aPressed = true;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("S"))
			{
				sPressed = true;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("D"))
			{
				dPressed = true;
			}
		}

		public void keyReleased(KeyEvent e)
		{
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("W"))
			{
				wPressed = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("A"))
			{
				aPressed = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("S"))
			{
				sPressed = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("D"))
			{
				dPressed = false;
			}
		}
	}

}

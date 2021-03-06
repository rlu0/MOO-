import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
/**
 * 
 */
import javax.swing.*;
/**
 * Contains the client for MOOD
 * @version June 16 2016
 * @author Ray, Micheal,Tony
 *
 */
public class Client
{
	JButton sendButton, clearButton;
	JTextField typeField;
	static JTextArea msgArea;
	JPanel southPanel;
	String username = "";
	JScrollPane sp;
	String data = "Ready to begin";
	String ip = "127.0.0.1";
	Player you;
	boolean newMap = true;

	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean

	// [x][0] is x position [x][1] is y position [x][2] is direction
	double[][] playerLocations = new double[8][3];

	String[][] riddleList = {
			{
					"What has a foot but no legs?",
					"What is the longest word in the dictionary?",
					"What word becomes shorter when you add two letters to it?",
					"What is so delicate that just saying it breaks it?",
					"What tree goes in your hand?",
					"How do you make one disappear?",
					"What is tall when it's young and short when its tall?",
					"What can you catch but not throw?",
					"What goes up but never goes down?",
					"What is the largest thing in existence?",
					"What has a neck but no head?",
					"What starts with P, ends with E and has over 1000 letters?",
					"What gets bigger the more you remove from it?",
					"What grows when you feed it and dies when watered?",
					"What's your name man?",
					"Did you know that 45% of this game was made in the last day?",
					"Haribo", "Who da best?", "Is this game fun?",
					"Why are my clothes on fire?",
					"Even Now The evil seed of what you have done",
					"Did you remember to turn off the stove?",
					"What's the trick to tying laces quickly?",
					"What's the best kind of shirt?",
					"A flock of seagulls approaches you, what do you do?",
					"Disco Fever!!!!!!",
					"I wonder what's for dinner?",
					"Gee, I wonder what Ganon's up to?",
					"Now I know what a meatball feels like!",
					"All your base are belong to us" },
			{
					"A: A snail!",
					"A: Smiles, since there is a mile between each s!",
					"A: Short",
					"A: Silence",
					"A: Palm trees!",
					"A: Add the letter G to the beginning of it",
					"A: A candle!",
					"A: A cold",
					"A: Your age",
					"A: Yo Mamma",
					"A: A bottle",
					"A: The Post office",
					"A: A hole",
					"A: fires",
					"A: Alexander Hamilton",
					"Really its true!",
					"I don't know what else to say",
					"C'est Moi",
					"Depends how brain dead you are",
					"Look into  your heart, that's where the answer always lies",
					"germinates within you",
					"You'll find out when you get home",
					"A: Use velcro",
					"A: Ones with 4 holes in them",
					"A: You run, you run so far away",
					"The BeeGees would be proud",
					"I hope its a spaghetti!",
					"Mah Boi",
					"Seriously, clean out your computer, this place is filthy!",
					"What you say!" } };

//Load images
	Image handgun = new ImageIcon("handgun.png").getImage();
	Image boots = new ImageIcon("boots.png").getImage();
	Image uzis = new ImageIcon("uzi.png").getImage();
	Image shotty = new ImageIcon("shotty.png").getImage();
	Image plasma = new ImageIcon("plasma.png").getImage();
	Image handgunG = new ImageIcon("handgun.gif").getImage();
	Image bootsG = new ImageIcon("boots.gif").getImage();
	Image uzisG = new ImageIcon("uzi.gif").getImage();
	Image shottyG = new ImageIcon("shotty.gif").getImage();
	Image plasmaG = new ImageIcon("plasma.gif").getImage();

	//depending on what gun fire
	public void soundFire()
	{
		if (currentGun == 0)
		{

		}
		else if (currentGun == 1)
		{
			hg.play();
		}
		else if (currentGun == 2)
		{
			sg.play();
		}

		else if (currentGun == 3)
		{
			sg.play();
		}
		else if (currentGun == 4)
		{
			uzi.play();
		}
		else if (currentGun == 5)
		{
			pls.play();
		}

	}

	int currentGun = 1;

	//Load in the sound files
	AudioClip doot = Applet.newAudioClip(getCompleteURL("OST.wav"));
	AudioClip hg = Applet.newAudioClip(getCompleteURL("dspistol.wav"));
	AudioClip sg = Applet.newAudioClip(getCompleteURL("dsshotgun.wav"));
	AudioClip uzi = Applet.newAudioClip(getCompleteURL("OST.wav"));
	AudioClip pls = Applet.newAudioClip(getCompleteURL("dsrlaunc.wav"));

	// Gets the URL needed for newAudioClip
	public URL getCompleteURL(String fileName)
	{
		try
		{
			return new URL(
					"file:" + System.getProperty("user.dir") + "/" + fileName);
		}
		catch (MalformedURLException e)
		{
			System.err.println(e.getMessage());
		}
		return null;
	}

	// Various maps will be designed and put into here
	static int[][] mapNeg1 = new int[45][44];
	static int[][] map0 = new int[45][45];
	static int[][] map1 = new int[45][44];
	static int[][] map2 = new int[45][45];
	static int[][] map3 = new int[50][50];
	static int[][] map4 = new int[51][50];
	static int[][] map5 = new int[50][50];
	static int[][] map6 = new int[51][51];
	static int[][] map7 = new int[52][51];
	static int[][] map8 = new int[52][51];
	static int[][] map9 = new int[52][51];
	static int[][][] allMaps = { map0, map1, map2, map3, map4, map5, map6,
			map7,
			map8, map9, mapNeg1 };
	int[][] currentMap = mapNeg1;
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

	// 3D stuff
	static double playerx = -1;
	static double playery = -1;
	static double direction = -1;
	static double uberDirection;
	static int sizex = 450;
	static int sizey = 450;
	// Things relating to movement
	static boolean laPressed = false;
	static boolean raPressed = false;

	static int walkrate = 1;

	double forwardAccel = 0.025;
	double sidewaysAccel = 0.02;
	double backwardAccel = 0.02;
	double maxAccel = 0.025;

	double quadDrag = 0.05;
	double linearDrag = 0.12;
	double constDrag = 0.006;

	Robot robot;
	int mouseX;
	double turnAmount;
	double mouseSens = 0.002;

	int frameTime = 28;

	// List of things:
	ArrayList<Player> players = new ArrayList<Player>();
	Wall[] walls;
	ArrayList<Hitscan> hitscans = new ArrayList<Hitscan>();

	/**
	 * Main
	 * 
	 * @param args parameters from command line
	 */
	public static void main(String[] args)
	{

		for (int k = 0; k < 11; k++)
		{
			// System.out.println("");
			try
			{
				//Loading maps
				String filename = "";
				if (k == 10)
					filename = "mapNeg1";
				else
					filename = "map" + k;
				// System.out.println(filename);
				File f = new File(filename);
				Scanner inFile = new Scanner(f);
				int j = 0;
				while (inFile.hasNext())
				{
					String currentLine = inFile.nextLine();
					String[] split = currentLine.split(" ");

					for (int i = 0; i < split.length; i++)
					{
						int currentInt = Integer.valueOf(split[i]);
						allMaps[k][j][i] = currentInt;
						// System.out.print(allMaps[k][j][i]);
					}
					j++;
					// System.out.println("");
				}
			}
			catch (Exception e)
			{

			}
		}

		Client client = new Client(); // start the client

		try
		{
			client.go(); // begin the connection
		}
		catch (Exception e)
		{
			// msgArea.append("\n Connection to Server Failed!");
			// e.printStackTrace();
			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			// msgArea.append("\n Exiting program!");
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex)
			{
				// e.printStackTrace();

			}
			// System.exit(0);
		}

	}

	boolean doingStuff = false;

	
/**
 * initializes the walls
 */
	void setWallArray()
	{

		int noOfWalls = 0;
		for (int i = 0; i < currentMap.length; i++)
		{
			for (int j = 0; j < currentMap[i].length; j++)
			{
				if (currentMap[i][j] == 1)
				{
					noOfWalls++;
				}
			}
		}
		walls = new Wall[noOfWalls];

		int counter = 0;
		for (int i = 0; i < currentMap.length; i++)
		{
			for (int j = 0; j < currentMap[i].length; j++)
			{
				if (currentMap[i][j] == 1)
				{
					walls[counter] = new Wall(j, i, 1, 1);
					// System.out.println("generating walls");
					counter++;
				}

			}
		}

	}

	
	/**
	 * Takes the info from the server and uses it to move the other players in the map
	 */
	void movePlayers()
	{
		// Per player physics
		for (int i = 0; i < players.size(); i++)
		{

			// movement vectors
			Vector moveForce = new Vector(0, players.get(i).direction, false);

			if (players.get(i).isMoveForward)
			{
				moveForce.addComponents(new Vector(forwardAccel,
						players.get(i).direction, false));
			}
			if (players.get(i).isMoveBack)
			{
				moveForce.addComponents(new Vector(-backwardAccel,
						players.get(i).direction, false));
			}
			if (players.get(i).isMoveRight)
			{
				moveForce.addComponents(new Vector(sidewaysAccel,
						players.get(i).direction + (Math.PI / 2), false));
			}
			if (players.get(i).isMoveLeft)
			{
				moveForce.addComponents(new Vector(sidewaysAccel,
						players.get(i).direction - (Math.PI / 2), false));
			}
			if (players.get(i).isTurnLeft)
			{
				players.get(i)
						.setDirection(players.get(i).getDirection() - 0.1);
			}
			if (players.get(i).isTurnRight)
			{
				players.get(i)
						.setDirection(players.get(i).getDirection() + 0.1);
			}

			// mouse turning
			players.get(i)
					.setDirection(players.get(i).getDirection() + turnAmount);
			turnAmount = 0;

			// set movement vector to acceleration
			moveForce.calcLengthAngle();
			if (moveForce.length > 0.0001)
			{
				moveForce.length = maxAccel;

			}
			players.get(i).acceleration = moveForce;

			// add acceleration to velocity
			
			players.get(i).acceleration.calcComponents();
			players.get(i).velocity.addComponents(players.get(i).acceleration);

			// add drag to velocity
			double playerSpeed = players.get(i).velocity.length;
			players.get(i).velocity.length -= playerSpeed * playerSpeed
					* quadDrag;
			players.get(i).velocity.length -= playerSpeed * linearDrag;
			players.get(i).velocity.length = Math
					.max(players.get(i).velocity.length - constDrag, 0);
			players.get(i).velocity.calcComponents();

	
			// add velocity to position
			players.get(i).setX(
					players.get(i).getX() + players.get(i).velocity.getX());
			players.get(i).setY(
					players.get(i).getY() + players.get(i).velocity.getY());

		

			// wall collisions

			for (int j = 0; j < walls.length; j++)
			{
				double[] coord = players.get(i).hit.RCIntersect(walls[j].hit,
						players.get(i).hit);
				if (coord[0] != Double.MAX_VALUE
						&& coord[1] != Double.MAX_VALUE)
				{
					double xDisplace = players.get(i).getX() - coord[0];
					double yDisplace = players.get(i).getY() - coord[1];

				
					double centerDistance = Math
							.sqrt(Math.pow(players.get(i).getX() - coord[0], 2)
									+ Math.pow(
											players.get(i).getY() - coord[1],
											2));
					double displaceDist = players.get(i).hit.getR()
							- centerDistance;

					Vector displace = new Vector(xDisplace, yDisplace, true);
					displace.length = displaceDist;
					displace.calcComponents();


					players.get(i)
							.setX(players.get(i).getX() + displace.getX());
					players.get(i)
							.setY(players.get(i).getY() + displace.getY());

				}
			}

			// player collisions

			for (int j = 1; j < players.size(); j++)
			{
				if (i == j)
				{
					continue;
				}
				double[] coord = players.get(i).hit
						.CCIntersect(players.get(j).hit, players.get(i).hit);
				if (coord[0] != Double.MAX_VALUE
						&& coord[1] != Double.MAX_VALUE)
				{
					double xDisplace = players.get(i).getX() - coord[0];
					double yDisplace = players.get(i).getY() - coord[1];

					double centerDistance = Math
							.sqrt(Math.pow(players.get(i).getX() - coord[0], 2)
									+ Math.pow(
											players.get(i).getY() - coord[1],
											2));
					double displaceDist = players.get(i).hit.getR()
							- centerDistance;

					Vector displace = new Vector(xDisplace, yDisplace, true);
					displace.length = displaceDist;
					displace.calcComponents();

					
					players.get(i)
							.setX(players.get(i).getX() + displace.getX());
					players.get(i)
							.setY(players.get(i).getY() + displace.getY());

				}
			}

		}
	}

	
	/**
	 * Generates the lines for the hitscans
	 */
	void generateShots()
	{
		if (players.get(0).isShoot && players.get(0).canShoot)
		{
			hitscans.add(new Hitscan(players.get(0), 20, 1));
			// System.out.println("new shot");
			players.get(0).canShoot = false;
		}

	}

	// Checks for collisions of bullets
	void calcShots()
	{

		for (int i = 0; i < hitscans.size(); i++)
		{

			

			if (hitscans.get(i).framesLeft == 0)
			{
				hitscans.remove(i);
				i--;
				continue;
			}

			hitscans.get(i).update();


			double shortestLength = hitscans.get(i).vector.length;
			double[] shortestCoord = new double[2];
			shortestCoord[0] = hitscans.get(i).hit.getX2();
			shortestCoord[1] = hitscans.get(i).hit.getY2();

			for (int j = 0; j < walls.length; j++)
			{

				// System.out.println("hit");

				double[] coord = hitscans.get(i).hit.RLIntersect(walls[j].hit,
						hitscans.get(i).hit);

				if (coord[0] != Double.MAX_VALUE
						&& coord[1] != Double.MAX_VALUE)
				{

					// System.out.printf("hit: .4%f .4%f%n", coord[0],
					// coord[1]);

					double currentLength = Math.sqrt(
							Math.pow(hitscans.get(i).hit.getX1() - coord[0], 2)
									+ Math.pow(hitscans.get(i).hit.getY1()
											- coord[1], 2));

					if (currentLength < shortestLength)
					{
						shortestLength = currentLength;
						shortestCoord = coord;
					}

				}
			}

			hitscans.get(i).hit = new Line(hitscans.get(i).shooter.getX(),
					hitscans.get(i).shooter.getY(),
					shortestCoord[0], shortestCoord[1]);

			// System.out.println(hitscans.get(i).hit.getX2() + " " +
			// hitscans.get(i).hit.getY2());
			hitscans.get(i).framesLeft--;

		}
	}

	/**
	 * Checks if it hit players
	 */
	void checkHits()
	{
		for (int i = 0; i < hitscans.size(); i++)
		{
			for (int j = 0; j < players.size(); j++)
			{
				if (players.get(j).equals(hitscans.get(i).shooter))
				{
					continue;
				}
				if (hitscans.get(i).hit.CLIntersect(players.get(j).hit,
						hitscans.get(i).hit))
				{
					players.get(j).setHP(players.get(j).getHP() - 30);
					System.out.println("PLAYER " + j + " HIT");
				}
			}
		}
	}

	//Main program Thread
	public void go()
	{

		class buttonListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				doingStuff = true;
				// System.out.println(doingStuff);
			}

		}

		//Window Things
		
		boolean ready = false;
		JFrame window = new JFrame("MOOD");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel menuThing = new JPanel();
		JLabel DOOM = new JLabel("MOOD THe GaMe");
		JLabel spacer = new JLabel("");
		JLabel usernameL = new JLabel("Username: ");
		JLabel IPL = new JLabel("IP Address: ");
		JLabel portL = new JLabel("Port: ");
		JTextField portF = new JTextField();
		JTextField usernameF = new JTextField();
		JTextField IPF = new JTextField();
		JButton joinB = new JButton("Join");
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

		
		
		while (ready != true)
		{

			//Connection things ( port and IP)
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
				
				ip = IPF.getText();
				int port = -1;
				try
				{
					port = Integer.parseInt(portF.getText());
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null,
							"The Port is not an integer!");
					doingStuff = false;
				}
				
				
				username = usernameF.getText();
				if (doingStuff == true)
				{
					try
					{
						mySocket = new Socket(ip, port);
						InputStreamReader stream1 = new InputStreamReader(
								mySocket.getInputStream()); // Stream
															// for
															// network
															// input
						input = new BufferedReader(stream1);

						output = new PrintWriter(mySocket.getOutputStream());
						// assign printwriter to network stream
						ready = true;

					}
					catch (IOException e)
					{
						JOptionPane.showMessageDialog(null,
								"The IP entered or Port is not available!");
						try
						{
							Thread.sleep(500);
						}
						catch (InterruptedException ex)
						{
							// e.printStackTrace();

						}
						doingStuff = false;
					}
				}
			}
		}
		window.setVisible(false);
		// Send first bit of information
		data = "Ready";
		output.println(data);
		output.flush();
		System.out.println(data);
		boolean loadingConf = false;
		while (loadingConf == false)
		{
			try
			{
				
				Thread.sleep(100);
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
			// Establishes the world and a location based on the
			// playerAssignment
			try
			{
				if (input.ready())
				{
					System.out.println("Made it here");

					// Message formatted: playerNum mapNum numEnemies
					String message = input.readLine();
					System.out.println("Message:" + message);
					playerNum = Integer.parseInt(
							message.substring(0, message.indexOf(" ")));
					System.out.println(playerNum);
					message = message.substring(message.indexOf(" ") + 1);
					System.out.println("Message:" + message);
					int mapNum = Integer.parseInt(
							message.substring(0, message.indexOf(" ")));
					System.out.println("Map Num:" + mapNum);
					message = message.substring(message.indexOf(" ") + 1);
					currentMap = allMaps[mapNum];
					setWallArray();
					System.out.println("done array");
					numPlayers = Integer.parseInt(message) + 1;
					loadingConf = true;

				}
			}
			catch (IOException e)
			{
				// running = false;
				// e.printStackTrace();
				System.out.println("error thrown, that's bad");
			}
		}
		data = null;
		output.println(username);
		output.flush();
		JFrame loadingFrame = new JFrame("MOOD - Lobby");

		JPanel information = new JPanel();
		JTextArea display = new JTextArea(
				"Welcome to Lobby " + (int) (Math.random() * 100));
		JTextArea riddlin = new JTextArea("Riddle: ");

		// Could add a chat room
		loadingFrame.add(information);

		//Loading screen
		GridLayout lay2 = new GridLayout(2, 1, 2, 2);
		information.setLayout(lay2);
		information.add(display);
		information.add(riddlin);
		loadingFrame.setSize(400, 200);
		loadingFrame.setVisible(true);
		display.append("\n Hello " + username);
		display.append("\n Please wait while the server sets things up!");
		int riddleCount = 0;
		int currentRiddle = 0;
		while (running && data == null)
		{
			//Run riddles
			if (riddleCount == 0)
			{
				currentRiddle = (int) (Math.random() * riddleList[0].length);
				riddlin.append("\n" + riddleList[0][currentRiddle]);
			}
			if (riddleCount == 5)
			{
				riddlin.append("\n" + riddleList[1][currentRiddle]);
			}
			riddleCount++;
			if (riddleCount == 10)
			{
				riddleCount = 0;
				riddlin.setText("Riddle: ");
			}
			try
			{
				Thread.sleep(1000);
				System.out.println("wait for it");
			}
			catch (Exception e)
			{

			}
			try
			{
				
				//Actual game portion
				if (input.ready())
				{
					data = input.readLine();
					System.out.println(data);
					if (data.toLowerCase().equals("starting"))
					{
						display.append("\n Starting");
						try
						{
							playerLocations[7][2] = -1;
							for (int i = 0; i < 8; i++)
							{
								double xPos = 0;
								double yPos = 0;
								double dir = 0;
								
								//break down the data from server
								if (input.ready())
								{
									data = input.readLine();
									data = data
											.substring(data.indexOf(" ") + 1);
									xPos = Double.parseDouble(data.substring(0,
											data.indexOf(" ")));
									if (data.indexOf(" ") != -1)
									{
										System.out.println("yep 2");
										data = data
												.substring(data.indexOf(" ") + 1);
										yPos = Double.parseDouble(data
												.substring(0,
														data.indexOf(" ")));
										if (data.indexOf(" ") != -1)
										{
											System.out.println("yep 3");
											data = data
													.substring(data
															.indexOf(" ") + 1);
											dir = Double.parseDouble(data);
										}
										playerLocations[i][0] = xPos;
										playerLocations[i][1] = yPos;
										playerLocations[i][2] = dir;
										if (xPos != 0 && yPos != 0)
											players.add(new Player(xPos, yPos,
													dir));
									}
								}
							}
						}
						catch (Exception e)
						{

						}
					}
					else
					{
						data = null;
					}
				}
				
			}
			catch (Exception e)
			{

			}

		}
		loadingFrame.setVisible(false);
		doot.loop();
		// output.println("Stahp");
		// output.flush();
		System.out.println("Made it 1");
		(new Thread(new GameDisp())).start();
		System.out.println("Made it 2");
		(new Thread(new recieveInfo())).start();
		System.out.println("Made it 3");
		// System.out.println("cp " + players.get(0).getX() + " " +
		// players.get(0).getY()
		// + " " + players.get(0).getDirection());
		while (running)
		{
			try
			{
				Thread.sleep(30);
			}
			catch (Exception e)
			{
				System.out.println("why is this happening");
			}
			// System.out.println("made it 3");
			// Every 10 milliseconds the client returns the player's position
			if (players.size() != 0)
			{
				// System.out.println("cp " + players.get(0).getX() + " " +
				// players.get(0).getY() + " "
				// + players.get(0).getDirection());
				output.println("cp " + players.get(0).getX() + " "
						+ players.get(0).getY() + " "
						+ players.get(0).getDirection());
				output.flush();
			}
		}
	}

	
	/**
	 * 
	 * Recieves info from the server
	 *
	 */
	public class recieveInfo implements Runnable
	{

		@Override
		public void run()
		{
			while (running)
			{
				try
				{
					Thread.sleep(10);
				}
				catch (Exception ex)
				{

				}
				try
				{
					if (input.ready())
					{

						String message = input.readLine();
						// System.out.println(message);
						// msgArea.append("\n" + message);
						if (message.toLowerCase().equals("quit"))
						{
							running = false;
						}
						if (message.indexOf("pl") != -1)
						{
							// formatted pl player# xPos yPos direction
							// System.out.println(message);
							message = message
									.substring(message.indexOf(" ") + 1);
							int playNum = Integer.parseInt(
									message.substring(0, message.indexOf(" ")));
							message = message
									.substring(message.indexOf(" ") + 1);
							double xPos = Double.parseDouble(
									message.substring(0, message.indexOf(" ")));
							message = message
									.substring(message.indexOf(" ") + 1);
							double yPos = Double.parseDouble(
									message.substring(0, message.indexOf(" ")));
							message = message
									.substring(message.indexOf(" ") + 1);
							double direction = Double
									.parseDouble(message.substring(0));

							System.out.println(playNum + " " + xPos + " "
									+ yPos
									+ " " + direction);
						}
					}
				}
				catch (IOException e)
				{
					running = false;
				}

			}
		}

	}

	/**
	 * Contains the actual drawing portion of the game
	 * 
	 *
	 */
	public class GameDisp extends JPanel implements Runnable
	{
		//Creates a display
		GameDisp()
		{
			setSize(sizex, sizey);
		}

		/**
		 *  Given two lengths of two lines finds the angle between the two
		 * @param opp opposite line
		 * @param adj adjacent line
		 * @return the angle between the two
		 */
		public double findAngle(double opp, double adj)
		{
			return Math.atan(opp / adj);
		}

		/**
		 * Finds the distance between two points
		 * @param pt an array of points
		 * @return the distance between the two points
		 * 
		 */
		public double distance(double[] pt)
		{
			return Math
					.sqrt(Math.pow((pt[0] - players.get(0).getX()), 2)
							+ Math.pow((pt[1] - players.get(0).getY()), 2));
		}

		/**
		 * finds the point on the opposite side of the wall thats closest
		 * @param angle the angle that is being viewed at
		 * @return the point as a double ( x,y)
		 */
		public double[] locatePoint(double angle)
		{
			angle += uberDirection;

			if (angle > 2 * Math.PI)
			{
				angle -= 2 * Math.PI;
			}
			else if (angle < 0)
			{
				angle += 2 * Math.PI;
			}

			// System.out.println("Angle Start:" +angle);
			double newPt[] = null;
			int i = 0;
			while (true)
			{
				i++;
			//Iterates through at increments of 0.01 to determine the closest wall using tan
				double tempx;
				double tempy;
				if (angle < Math.PI / 2 && angle > 0)
				{
					tempx = players.get(0).getX() + i * 0.01;
					tempy = players.get(0).getY()
							- (Math.tan(angle) * i * 0.01);
				}
				else if (angle < Math.PI && angle > Math.PI / 2)
				{
					// uber = 1;
					tempx = players.get(0).getX() - i * 0.01;
					tempy = players.get(0).getY()
							- (Math.tan(Math.PI - angle) * i * 0.01);
				}
				else if (angle < 3 * Math.PI / 2 && angle > Math.PI)
				{
					// uber = 2;
					tempx = players.get(0).getX() - i * 0.01;
					tempy = players.get(0).getY()
							+ (Math.tan(angle - Math.PI) * i * 0.01);
				}
				else if (angle < 2 * Math.PI && angle > 3 * Math.PI / 2)
				{
					// uber = 3;
					tempx = players.get(0).getX() + i * 0.01;
					tempy = players.get(0).getY()
							+ (Math.tan(2 * Math.PI - angle) * i * 0.01);
				}
				else if (angle == 0)
				{
					// uber = 4;
					tempx = players.get(0).getX() + i * 0.01;
					tempy = players.get(0).getY();
				}
				else if (angle == Math.PI / 2)
				{
					// uber = 5;
					tempx = players.get(0).getX();
					tempy = players.get(0).getY() - i * 0.01;
				}
				else if (angle == Math.PI)
				{
					// uber = 6;
					tempx = players.get(0).getX() - i * 0.01;
					tempy = players.get(0).getY();
				}
				else
				{
					// uber = 7;
					tempx = players.get(0).getX();
					tempy = players.get(0).getY() + i * 0.01;
				}
				// If the player's FOV is at a Northern or Southern direction, changes the method of display
				boolean changeRender = false;
				if (angle > Math.PI / 4 && angle < 3 * Math.PI / 4)
				{
					changeRender = true;
				}
				else if (angle > 5 * Math.PI / 4 && angle < 7 * Math.PI / 4)
				{
					changeRender = true;
				}

				// Determines whether a different approach is necessary (if the increments jump past the wall blocks)
				if (tempx < 0 || tempx > currentMap[1].length || tempy < 0
						|| tempy > currentMap.length
						|| changeRender == true)
				{
					i = 0;
					
					
					//Similar to above, a variation that is slightly more precise but more processing intensive
					while (true)
					{
						i++;
						if (angle < Math.PI / 2 && angle > 0)
						{
							tempy = players.get(0).getY() - i * 0.01;
							tempx = players.get(0).getX()
									+ ((i * 0.01) / Math.tan(angle));
						}
						else if (angle < Math.PI && angle > Math.PI / 2)
						{
							tempy = players.get(0).getY() - i * 0.01;
							tempx = players.get(0).getX()
									- ((i * 0.01) / Math.tan(Math.PI - angle));
						}
						else if (angle < 3 * Math.PI / 2 && angle > Math.PI)
						{
							tempy = players.get(0).getY() + i * 0.01;
							tempx = players.get(0).getX()
									- ((i * 0.01) / Math.tan(angle - Math.PI));
						}
						else if (angle < 2 * Math.PI && angle > 3 * Math.PI / 2)
						{
							tempy = players.get(0).getY() + i * 0.01;
							tempx = players.get(0).getX() + ((i * 0.01)
									/ Math.tan(2 * Math.PI - angle));
						}
						else if (angle == 0)
						{
							tempx = players.get(0).getX() + i * 0.01;
							tempy = players.get(0).getY();
						}
						else if (angle == Math.PI / 2)
						{
							tempx = players.get(0).getX();
							tempy = players.get(0).getY() - i * 0.01;
						}
						else if (angle == Math.PI)
						{
							tempx = players.get(0).getX() - i * 0.01;
							tempy = players.get(0).getY();
						}
						else
						{
							tempx = players.get(0).getX();
							tempy = players.get(0).getY() + i * 0.1;
						}
						// Checks whether a wall has been reached or the loop needs another increment
						if (currentMap[(int) tempy][(int) tempx] == 1)
						{
							newPt = new double[2];
							newPt[0] = tempx;
							newPt[1] = tempy;
							return newPt;
						}
					}
				}
				// Checks whether a wall has been reached for the larger picture loop
				if (currentMap[(int) tempy][(int) tempx] == 1)
				{
					newPt = new double[2];
					newPt[0] = tempx;
					newPt[1] = tempy;
					return newPt;

				}
			}
		}

		/*
		 * Draws the various images and pictures on the main window
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		public void paintComponent(Graphics g)
		{

			Graphics2D graphics2D = (Graphics2D) g;

			// Doesn't affect speed, makes the image seem nicer
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			Image[] guns = { boots, handgun, shotty, uzis, plasma, bootsG,
					handgunG, shottyG, uzisG, plasmaG };
			uberDirection = -players.get(0).direction;
			g.fillRect(0, 0, sizex, sizey);
			double[] centrePt = null;
			centrePt = locatePoint(0);

			// determines the distance to the middle and calculates what to draw
			double dTM = distance(centrePt);
			double viewInc = (Math.tan(Math.PI * 5 / 18) * dTM) / 225;
			double[] newPt = null;
			double angle = 0;
			
			// increments screen by 1 pixel each time in drawing the image of the screen
			for (int i = 0; i < 226; i++)
			{
				// First chunk determines what is in the left side of the screen
				angle = findAngle(i * viewInc, dTM);
				newPt = locatePoint(angle);

				// Distance to the new wall to establish high to display the lines
				double distA = distance(newPt);
				int col = (int) (distA);
				// Alters the wall colors based on how far the player is from the wall
				Color color;
				if (distA < 1)
				{
					color = new Color(255, 0, 0);
				}
				else
				{
					color = new Color((int) (255 / distA), 0, 0);
				}
				g.setColor(color);
				
				// Draws a line part of the wall
				g.drawLine(sizex / 2 - i,
						(int) ((sizey / 2.0) - 225.0 / distA),
						sizex / 2 - i,
						(int) ((sizey / 2.0) + 225.0 / distA));

				// }

				// Right half of Screen
				// if (direction <= Math.PI / 4
				// || direction > Math.PI * 2 - Math.PI / 4) {
				// angle = findAngle(i * viewInc, dTM);
				newPt = locatePoint(Math.PI * 2 - angle);
				// System.out.println("RS:" + newPt[0] + "," + newPt[1]);
				// distA = distance(newPt);
				distA = distance(newPt);
				// System.out.println("left");

				if (distA < 1)
				{
					color = new Color(255, 0, 0);
				}
				else
				{
					color = new Color((int) (255 / distA), 0, 0);
				}

				// color = new Color((int) (Math.random() * 256),
				// (int) (Math.random() * 256),
				// (int) (Math.random() * 256));
				g.setColor(color);
				g.drawLine(sizex / 2 + i,
						(int) ((sizey / 2.0) - 225.0 / distA),
						sizex / 2 + i,
						(int) ((sizey / 2.0) + 225.0 / distA));

				// }
				// System.out.println("Iteration" + i);
				// System.out.println(distA);
				// System.out.println(angle);
				// System.out.println(newPt[0] + " , " + newPt[1]);
			}

			g.setColor(Color.YELLOW);
			g.drawOval(sizex / 2 - 5, sizey / 2 - 5, 10, 10);

			double drawScale = 2;

			// Top down view

			// Draw Floor
			// g.setColor(new Color(200,200,200));
			// g.fillRect(0, 0, this.getWidth(), this.getHeight());

			// Draw Walls
			g.setColor(new Color(40, 40, 40));
			for (int i = 0; i < walls.length; i++)
			{
				g.fillRect((int) Math.round(walls[i].hit.getX1() * drawScale),
						(int) Math.round(walls[i].hit.getY1() * drawScale),
						(int) Math.round(
								(walls[i].hit.getX2() - walls[i].hit.getX1())
										* drawScale),
						(int) Math.round(
								(walls[i].hit.getY2() - walls[i].hit.getY1())
										* drawScale));
			}

			// Draw Player
			g.setColor(new Color(10, 84, 173));
			for (int i = 0; i < players.size(); i++)
			{
				g.fillOval((int) Math.round((players.get(i).hit.getX()
						- players.get(i).hit.getR() * 4) * drawScale),
						(int) Math.round((players.get(i).hit.getY()
								- players.get(i).hit.getR() * 4) * drawScale),
						(int) Math.round(
								players.get(i).hit.getR() * 8 * drawScale),
						(int) Math.round(
								players.get(i).hit.getR() * 8 * drawScale));

				g.drawLine(
						(int) Math.round(players.get(i).hit.getX() * drawScale),
						(int) Math.round(players.get(i).hit.getY() * drawScale),
						(int) Math.round(
								(players.get(i).hit.getX()
										+ Math.cos(players.get(i).direction)
										* 4)
										* drawScale),
						(int) Math.round(
								(players.get(i).hit.getY()
										+ Math.sin(players.get(i).direction)
										* 4)
										* drawScale));
			}

			// Draw Shots
			g.setColor(new Color(255, 100, 0));
			for (int i = 0; i < hitscans.size(); i++)
			{
				g.drawLine(
						(int) Math
								.round(hitscans.get(i).hit.getX1() * drawScale),
						(int) Math
								.round(hitscans.get(i).hit.getY1() * drawScale),
						(int) Math
								.round(hitscans.get(i).hit.getX2() * drawScale),
						(int) Math.round(
								hitscans.get(i).hit.getY2() * drawScale));
			}

			// Loops through and ideally displays the players based on how far they are from the individual
			for (int k = 0; k < playerLocations.length; k++)
			{
				// Determines an angle
				double[] fat = { playerLocations[k][0], playerLocations[k][1] };
				double dist = distance(fat);
				double theta;
				
				// Finds whether there is a wall between the player and the enemy
				if (playerLocations[k][0] < players.get(0).getX())
				{
					if (playerLocations[k][1] < players.get(0).getY())
					{
						theta = Math.PI
								- Math.acos(dist / (players.get(0).getX()
										- playerLocations[k][0]));
					}
					else
					{
						theta = Math.PI
								+ Math.acos(dist / (players.get(0).getX()
										- playerLocations[k][0]));
					}
				}
				else
				{
					if (playerLocations[k][1] < players.get(0).getY())
					{
						theta = Math.acos(dist / (playerLocations[k][0]
								- players.get(0).getX()));
					}
					else
					{
						theta = 2 * Math.PI
								- Math.acos(dist / (playerLocations[k][0]
										- players.get(0).getX()));
					}
				}
				boolean objectHit = false;
				int p = 0;
				// Determines if the player even needs to be checked
				if (Math.abs(theta - players.get(0).getDirection()) < 5
				* Math.PI / 18)
				{
					// Loops through to find where the collision occurs (the enemy or the wall)
					while (!objectHit)
					{
						p++;
						double tempx;
						double tempy;
						if (theta < Math.PI / 2 && theta > 0)
						{
							tempx = players.get(0).getX() + p * 0.01;
							tempy = players.get(0).getY()
									- (Math.tan(theta) * p * 0.01);
						}
						else if (theta < Math.PI && theta > Math.PI / 2)
						{
							// uber = 1;
							tempx = players.get(0).getX() - p * 0.01;
							tempy = players.get(0).getY()
									- (Math.tan(Math.PI - theta) * p * 0.01);
						}
						else if (theta < 3 * Math.PI / 2 && theta > Math.PI)
						{
							// uber = 2;
							tempx = players.get(0).getX() - p * 0.01;
							tempy = players.get(0).getY()
									+ (Math.tan(theta - Math.PI) * p * 0.01);
						}
						else if (theta < 2 * Math.PI && theta > 3 * Math.PI / 2)
						{
							// uber = 3;
							tempx = players.get(0).getX() + p * 0.01;
							tempy = players.get(0).getY()
									+ (Math.tan(2 * Math.PI - theta) * p
									* 0.01);
						}
						else if (theta == 0)
						{
							// uber = 4;
							tempx = players.get(0).getX() + p * 0.01;
							tempy = players.get(0).getY();
						}
						else if (theta == Math.PI / 2)
						{
							// uber = 5;
							tempx = players.get(0).getX();
							tempy = players.get(0).getY() - p * 0.01;
						}
						else if (theta == Math.PI)
						{
							// uber = 6;
							tempx = players.get(0).getX() - p * 0.01;
							tempy = players.get(0).getY();
						}
						else
						{
							// uber = 7;
							tempx = players.get(0).getX();
							tempy = players.get(0).getY() + p * 0.01;
						}
						
						// Determines whether the wall is hit first, or the enemy is hit first
						if (currentMap[(int) tempy][(int) tempx] == 1)
						{
							objectHit = true;
						}
						else if ((int) (players.get(0).getX()) == (int) (tempx)
								&& players.get(0).getY() == (int) (tempy))
						{
							objectHit = true;
							g.fillRect((int) (Math.tan(theta) * dTM - 10),
									(int) (sizey / 2 - sizey / (2 * dist)),
									(int) (Math.tan(theta) * dTM + 10),
									(int) (sizey / 2 + sizey / (2 * dist)));
						}
					}

				}
			}

			// Draws stuff
			g.setColor(Color.WHITE);
			g.drawString(players.get(0).getX() + " " + players.get(0).getY()
					+ " " + players.get(0).isMoveForward, 10,
					10);
			g.drawImage(guns[currentGun], 150, 400, this);
		}

		/*
		 * Main body of the client, loops in the game window
		 * @see java.lang.Runnable#run()
		 */
		public void run()
		{

			// moves cursor to middle
			try
			{
				robot = new Robot();
			}
			catch (AWTException e1)
			{
				
			}

			// Displays the game window
			JFrame window = new JFrame("MOOD");
			JPanel bananarama = new GameDisp();
			window.add(bananarama);
			window.setResizable(false);
			window.setVisible(true);
			window.setSize(455, 475);
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

			// Create a new blank cursor.
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			    cursorImg, new Point(0, 0), "blank cursor");

			// Set the blank cursor to the JFrame.
			window.getContentPane().setCursor(blankCursor);

			// Adds listeners to determine whether things are pressed
			window.addKeyListener(new MyKeyListener());
			//window.addMouseMotionListener(new MouseMovementListener());
			window.addMouseListener(new MouseClickListener());
			window.setLocationRelativeTo(null);

			// Loops until the game ends (running is set to false) and calculates various aspects of the game
			while (running)
			{
				// Cheks when the projectiles and people have moved
				long startTime = System.currentTimeMillis();
				movePlayers();
				generateShots();
				calcShots();
				window.repaint();
				long endTime = System.currentTimeMillis();

				//delays the game, caps it to 30 fps
				long currentDelay = frameTime - (endTime - startTime);
				if (currentDelay > 0)
					try
					{
						Thread.sleep(currentDelay);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
			}
			window.setVisible(false);

		}

	}

	/*
	 * KeyListener specifically to listen whether escape is pressed on any of the loading pages
	 */
	public class MyKeyListener implements KeyListener
	{

		public void keyTyped(KeyEvent e)
		{
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape"))
			{
				output.println("quit");
				output.flush();
				running = false;
			}
		}

		// Determines whether the key has been pressed to move around the map
		public void keyPressed(KeyEvent e)
		{
			// Forwards movement
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")
					&& !players.get(0).isMoveForward)
			{
				wPressed = true;
				players.get(0).isMoveForward = true;
			}
			// Left movement
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")
					&& !players.get(0).isMoveLeft)
			{
				aPressed = true;
				players.get(0).isMoveLeft = true;
			}
			// Backwards movement
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")
					&& !players.get(0).isMoveBack)
			{
				sPressed = true;
				players.get(0).isMoveBack = true;
			}
			// Rightwards movement
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")
					&& !players.get(0).isMoveRight)
			{
				dPressed = true;
				players.get(0).isMoveRight = true;
			}
			// Turning left
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Left")
					&& !players.get(0).isTurnLeft)
			{
				laPressed = true;
				players.get(0).isTurnLeft = true;
			}
			// Turns right
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Right")
					&& !players.get(0).isTurnRight)
			{
				raPressed = true;
				players.get(0).isTurnRight = true;
			}
			// Closes window if the game is done
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape"))
			{
				output.println("quit");
				output.flush();
				running = false;
			}
			// ALt shoot button 
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Space"))
			{
				if (!players.get(0).isShoot)
				{
					// System.out.println("clicked");
					players.get(0).isShoot = true;
					soundFire();
					currentGun += 5;

				}
			}
		}

		/**
		 * Determines what keys have been released (again part of movement)
		 */
		public void keyReleased(KeyEvent e)
		{
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")
					&& players.get(0).isMoveForward)
			{
				wPressed = false;
				players.get(0).isMoveForward = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")
					&& players.get(0).isMoveLeft)
			{
				aPressed = false;
				players.get(0).isMoveLeft = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")
					&& players.get(0).isMoveBack)
			{
				sPressed = false;
				players.get(0).isMoveBack = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")
					&& players.get(0).isMoveRight)
			{
				dPressed = false;
				players.get(0).isMoveRight = false;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Left")
					&& players.get(0).isTurnLeft)
			{
				laPressed = false;
				players.get(0).isTurnLeft = false;
				// direction+=0.01;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Right")
					&& players.get(0).isTurnRight)
			{
				raPressed = false;
				players.get(0).isTurnRight = false;
				// direction -= 0.01;
			}

			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Space")
					&& players.get(0).isShoot)
			{
				players.get(0).isShoot = false;
				players.get(0).canShoot = true;
				currentGun -= 5;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Q"))
			{
				if (currentGun != 0)
					currentGun--;
				else
					currentGun = 5;

			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("E"))
			{
				if (currentGun != 5)
					currentGun++;
				else
					currentGun = 0;
			}
		}
	}

/*
 * ACTIVATES MOUSE CONTROLS
 */
	public class MouseMovementListener implements MouseMotionListener
	{

		/*
		 * Moves about all cool like whilst hiding the cursor
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		public void mouseDragged(MouseEvent e)
		{
			// TODO Auto-generated method stub
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			mouseX = MouseInfo.getPointerInfo().getLocation().x;
			// if (lastMouseX == 0) lastMouseX = mouseX;
			turnAmount += (mouseX - (int) screenSize.getWidth() / 2.0)
					* mouseSens;

			// System.out.println(turnAmount);
			// lastMouseX = mouseX;

			robot.mouseMove((int) screenSize.getWidth() / 2,
					(int) screenSize.getHeight() / 2);

		}

		/*
		 * Determines how the mouse has been moved
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		public void mouseMoved(MouseEvent e)
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			mouseX = MouseInfo.getPointerInfo().getLocation().x;
			turnAmount += (mouseX - (int) screenSize.getWidth() / 2.0)
					* mouseSens;
			robot.mouseMove((int) screenSize.getWidth() / 2,
					(int) screenSize.getHeight() / 2);
		}

	}

	/**
	 * 
	 * @author Michael
	 *
	 */
	public class MouseClickListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if (SwingUtilities.isLeftMouseButton(e) && !players.get(0).isShoot)
			{
				// System.out.println("clicked");
				players.get(0).isShoot = true;
				soundFire();
				currentGun += 5;

			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if (SwingUtilities.isLeftMouseButton(e) && players.get(0).isShoot)
			{
				players.get(0).isShoot = false;
				players.get(0).canShoot = true;
				currentGun -= 5;
			}
		}

	}

}

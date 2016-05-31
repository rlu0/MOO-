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

class ServerComm2
{

	ServerSocket serverSock;// server socket for connection
	static boolean running = true; // controls if the server is accepting
									// clients
	static ArrayList<ConnectionHandler> clientList = new ArrayList();

	static boolean[] clientsRunning = new boolean[8];
	
	int mapNum = 0;

	public static void main(String[] args)
	{
		new ServerComm2().go(); // start the server
	}

	public void go()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please Enter the port to watch:");
		int port = keyboard.nextInt();

		Socket client = null;// hold the client connection

		try
		{
			serverSock = new ServerSocket(port);
			// serverSock.setSoTimeout(50000); //5 second timeout

			while (running)
			{
				Boolean isSpace = false;
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
					for (int i = 0; i < clientList.size(); i++)
					{
						ConnectionHandler yo = clientList.get(i);
						if (yo != null)
							yo.setPlayerList(clientList);
					}

					Thread t = new Thread(clientHandler);
					t.start(); // start the new thread
				}

			}
		}
		catch (Exception e)
		{
			// System.out.println("Error accepting connection");
			// close all and quit
			try
			{
				client.close();
			}
			catch (Exception e1)
			{
				System.out.println("Failed to close socket");
			}
			System.exit(-1);
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
			output.println(clientList.indexOf(client)+mapNum+clientList.size());
			while (running)
			{ // loop unit a message is received
				try
				{
					if (input.ready())
					{ // check for an incoming message
						data = input.readLine();
						
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

//						for (ConnectionHandler Ch : clientList)
//						{
//
//							output = new PrintWriter(Ch.getClient()
//									.getOutputStream());
//							output.println(data);
//							output.flush();
//
//						}
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
			output.println("01 " + clientList.indexOf(client) + clientList.size());
		}
		

	} // end of inner class
} // end of ChatProgramServer class
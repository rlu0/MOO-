import java.io.*;
import java.net.*;
import java.util.ArrayList;
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
	String ip = "192.168.1.9";
	Player you;
	boolean newMap = true;

	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean

	// Various maps will be designed and put into here
	static int[][] mapNeg1 = {};
	static int[][] map0 = {
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
	static int[][] map1 = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1 } };
	static int[][] map2 = new int[45][45];
	static int[][] map3 = new int[45][45];
	static int[][] map4 = new int[45][45];
	static int[][] map5 = new int[45][45];
	static int[][] map6 = new int[45][45];
	static int[][] map7 = new int[45][45];
	static int[][] map8 = new int[45][45];
	static int[][] map9 = new int[45][45];
	static int[][][] allMaps = { map0, map1, map2, map3, map4, map5, map6,
			map7, map8, map9, mapNeg1 };
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

	// 3D stuff
	static double playerx = 2;
	static double playery = 2;
	static double direction, uberDirection;
	static int sizex = 450;
	static int sizey = 450;
	// Things relating to movement
	static boolean laPressed = false;
	static boolean raPressed = false;

	static int walkrate = 1;

	// List of things:
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Wall> walls = new ArrayList<Wall>();

	/**
	 * Main
	 * 
	 * @param args parameters from command line
	 */
	public static void main(String[] args)
	{
		try
		{
			File f = new File("gridMap1");
			Scanner inFile = new Scanner(f);
			int j = 0;
			while (inFile.hasNext())
			{

				String currentLine = inFile.nextLine();
				String[] split = currentLine.split(" ");

				for (int i = 0; i < split.length; i++)
				{
					int currentInt = Integer.valueOf(split[i]);
					map2[j][i] = currentInt;
					System.out.print(map2[j][i]);
				}
				j++;
				System.out.println("");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		allMaps[2] = map2;

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

	boolean doingStuff = false;

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
				System.out.println("a");
				ip = IPF.getText();
				int port=-1;
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
								mySocket.getInputStream()); // Stream for
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
				allMaps[0] = map0;
				currentMap = allMaps[mapNum];
				numPlayers = Integer.parseInt(message) + 1;

			}
		}
		catch (IOException e)
		{
			// running = false;
			// e.printStackTrace();
		}
		currentMap = allMaps[0];
		System.out.println("Yo mamma1");
		
//		try
//		{
//			if (input.ready())
//			{
//				// Message formatted: playerNum mapNum numEnemies
//				String message = input.readLine();
//
//				playerNum = Integer.parseInt(message.substring(0,
//						message.indexOf(" ")));
//				message = message.substring(message.indexOf(" ") + 1);
//				int mapNum = Integer.parseInt(message.substring(0,
//						message.indexOf(" ")));
//				message = message.substring(message.indexOf(" ") + 1);
//				allMaps[0] = map0;
//				currentMap = allMaps[mapNum];
//				numPlayers = Integer.parseInt(message) + 1;
//
//			}
//		}
//		catch (IOException e)
//		{
//			// running = false;
//			// e.printStackTrace();
//		}
		
		(new Thread(new GameDisp())).run();
		// (new Thread(new GameDisp())).run();
		// System.out.println("Yo mamma2");
		// MyKeyListenerA keyboard = new MyKeyListenerA();
		// Client client = new Client();
		// client.runs();
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
						// running = false;
					}
				}
			}
			catch (IOException e)
			{
				msgArea.append("\n" + "Failed to receive msg from the server!");
				// running = false;
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

	// class gameWindow implements Runnable {
	//
	// @Override
	// public void run() {
	// System.out.println("Yo mamma");
	// JFrame window = new JFrame("MOOD");
	// JPanel bananarama = new GameDisp();
	// window.add(bananarama);
	// window.setVisible(true);
	// window.setSize(470, 470);
	// window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// window.repaint();
	// while (running) {
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// window.repaint();
	// }
	//
	// }

	// class GameDisp extends JPanel
	// {
	// GameDisp()
	// {
	// setSize(450, 450);
	// }
	//
	// public void paintComponent(Graphics g)
	// {
	//
	// g.setColor(Color.BLACK);
	// g.fillRect(0, 0, getWidth(), getHeight());
	//
	//
	//
	// //Apparently re-initializing is necessary since multithreading and all
	// allMaps[0] = map0;
	//
	// currentMap = allMaps[0];
	//
	// ///*
	// int y = 0;
	// for (int j = 0; j < currentMap.length; j++)
	// {
	// int x = 0;
	// for (int i = 0; i < currentMap[j].length; i++)
	// {
	// if (currentMap[j][i] == 1)
	// {
	// g.setColor(Color.RED);
	// g.fillRect(x, y, x + 5, y + 5);
	// }
	// else
	// {
	// g.setColor(Color.BLUE);
	// g.fillRect(x, y, x + 9, y + 9);
	// }
	// //System.out.println("(" + x+","+y+")");
	// x += 10;
	// }
	// y += 10;
	// }//*/
	// if (newMap == true)
	// {
	// double playx = 0;
	// double playy = 0;
	// while (currentMap[(int) (playy)][(int) (playx)] != 0)
	// {
	// playx = Math.random() * 45;
	// playy = (Math.random() * 45);
	// }
	// you = new Player(playx*10, playy*10);
	// }
	// double currentX = you.getX();
	// double currentY = you.getY();
	// g.setColor(Color.BLUE);
	// g.drawOval((int) (currentX - 2.5), (int) (currentY - 2.5),
	// (int) (currentX + 2.5), (int) (currentY + 2.5));
	//
	// }
	// }

	// public class menu extends JPanel implements Runnable
	// {
	// menu()
	// {
	//
	// }
	//
	// @Override
	// public void run() {
	//
	// // window.getContentPane().add(yomamma);
	//
	//// KeyListener keyList = new TypeKeyListener();
	//// window.addKeyListener(keyList);
	//
	// }
	// }

	public class GameDisp extends JPanel implements Runnable
	{
		GameDisp()
		{
			setSize(sizex, sizey);
		}

		public double findAngle(double opp, double adj)
		{
			return Math.atan(opp / adj);
		}

		public double distance(double[] pt)
		{
			return Math.sqrt(Math.pow((pt[0] - playerx), 2)
					+ Math.pow((pt[1] - playery), 2));
		}

		// public double distanceV(double[] pt, double angleAtWall)
		// {
		// return (Math.sqrt(Math.pow((pt[0] - playerx), 2)
		// + Math.pow((pt[1] - playery), 2)))
		// * Math.sin(angleAtWall);
		// }

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
				// System.out.println(i);
				// int uber = 0;
				double tempx;
				double tempy;
				if (angle < Math.PI / 2 && angle > 0)
				{
					tempx = playerx + i * 0.01;
					tempy = playery - (Math.tan(angle) * i * 0.01);
				}
				else if (angle < Math.PI && angle > Math.PI / 2)
				{
					// uber = 1;
					tempx = playerx - i * 0.01;
					tempy = playery - (Math.tan(Math.PI - angle) * i * 0.01);
				}
				else if (angle < 3 * Math.PI / 2 && angle > Math.PI)
				{
					// uber = 2;
					tempx = playerx - i * 0.01;
					tempy = playery + (Math.tan(angle - Math.PI) * i * 0.01);
				}
				else if (angle < 2 * Math.PI && angle > 3 * Math.PI / 2)
				{
					// uber = 3;
					tempx = playerx + i * 0.01;
					tempy = playery
							+ (Math.tan(2 * Math.PI - angle) * i * 0.01);
				}
				else if (angle == 0)
				{
					// uber = 4;
					tempx = playerx + i * 0.01;
					tempy = playery;
				}
				else if (angle == Math.PI / 2)
				{
					// uber = 5;
					tempx = playerx;
					tempy = playery - i * 0.01;
				}
				else if (angle == Math.PI)
				{
					// uber = 6;
					tempx = playerx - i * 0.01;
					tempy = playery;
				}
				else
				{
					// uber = 7;
					tempx = playerx;
					tempy = playery + i * 0.01;
				}
				if (tempx < 0 || tempx > sizex / 10.0 || tempy < 0
						|| tempy > sizey / 10.0)
				{
					i = 0;
					// System.out.println("Doin this thang");
					while (true)
					{
						i++;
						if (angle < Math.PI / 2 && angle > 0)
						{
							tempy = playery - i * 0.01;
							tempx = playerx + ((i * 0.01) / Math.tan(angle));
						}
						else if (angle < Math.PI && angle > Math.PI / 2)
						{
							tempy = playery - i * 0.01;
							tempx = playerx
									- ((i * 0.01) / Math.tan(Math.PI - angle));
						}
						else if (angle < 3 * Math.PI / 2 && angle > Math.PI)
						{
							tempy = playery + i * 0.01;
							tempx = playerx
									- ((i * 0.01) / Math.tan(angle - Math.PI));
						}
						else if (angle < 2 * Math.PI
								&& angle > 3 * Math.PI / 2)
						{
							tempy = playery + i * 0.01;
							tempx = playerx
									+ ((i * 0.01) / Math.tan(2 * Math.PI
											- angle));
						}
						else if (angle == 0)
						{
							tempx = playerx + i * 0.01;
							tempy = playery;
						}
						else if (angle == Math.PI / 2)
						{
							tempx = playerx;
							tempy = playery - i * 0.01;
						}
						else if (angle == Math.PI)
						{
							tempx = playerx - i * 0.01;
							tempy = playery;
						}
						else
						{
							tempx = playerx;
							tempy = playery + i * 0.01;
						}
						// System.out.println(tempx + " " + tempy);
						if (currentMap[(int) tempy][(int) tempx] == 1)
						{
							// System.out.println("shoe");
							// System.out.println("got here");
							newPt = new double[2];
							newPt[0] = tempx;
							newPt[1] = tempy;
							return newPt;
						}
					}
				}
				if (currentMap[(int) tempy][(int) tempx] == 1)
				{
					// System.out.println("got here");
					newPt = new double[2];
					newPt[0] = tempx;
					newPt[1] = tempy;
					return newPt;

				}
				// System.out.println(tempx+" "+tempy);
			}
		}

		public void paintComponent(Graphics g)
		{
			uberDirection = direction;
			g.fillRect(0, 0, sizex, sizey);
			// int gridPlayerx = (int) (playerx / 10);
			// int gridPlayery = (int) (playery / 10);
			double[] centrePt = null;
			centrePt = locatePoint(0);

			// // Only works if facing right

			double dTM = distance(centrePt);
			// System.out.println(dTM);
			double viewInc = (Math.tan(Math.PI * 5 / 18) * dTM) / 225;
			// System.out.println(viewInc);
			double[] newPt = null;
			double angle = 0;
			// increments screen by 1 pixels each time
			for (int i = 0; i < 226; i++)
			{
				/*
				 * if facing right
				 */
				// Left half of Screen
				// if (direction <= Math.PI / 4
				// || direction > Math.PI * 2 - Math.PI / 4) {
				angle = findAngle(i * viewInc, dTM);
				newPt = locatePoint(angle);
				// System.out.println("LS:" + newPt[0] + "," + newPt[1]);

				double distA = distance(newPt);
				int col = (int) (distA);
				Color color;
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
				g.drawLine(sizex / 2 - i,
						(int) ((sizey / 2.0) - 225.0 / distA), sizex / 2 - i,
						(int) ((sizey / 2.0) + 225.0 / distA));

				// }

				// Right half of Screen
				// if (direction <= Math.PI / 4
				// || direction > Math.PI * 2 - Math.PI / 4) {
				angle = findAngle(i * viewInc, dTM);
				newPt = locatePoint(-angle);
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
						(int) ((sizey / 2.0) - 225.0 / distA), sizex / 2 + i,
						(int) ((sizey / 2.0) + 225.0 / distA));

				// }
				// System.out.println("Iteration" + i);
				// System.out.println(distA);
				// System.out.println(angle);
				// System.out.println(newPt[0] + " , " + newPt[1]);
			}
			g.setColor(Color.YELLOW);
			g.drawOval(sizex / 2 - 5, sizey / 2 - 5, 10, 10);

		}

		@Override
		public void run()
		{
			playerx = 2;
			playery = 2;
			direction = 0;
			JFrame window = new JFrame("MOOD");
			JPanel bananarama = new GameDisp();
			// URL url = getClass().getResource("boots.gif");
			// Icon icon = new ImageIcon(url);
			// JLabel yomamma = new JLabel(icon);
			window.add(bananarama);
			window.setResizable(false);
			window.setVisible(true);
			window.setSize(455, 475);
			// window.getContentPane().add(yomamma);

			KeyListener keyList = new MyKeyListener();
			window.addKeyListener(keyList);
			while (running)
			{
				window.requestFocus();
				if (laPressed == true)
				{
					// System.out.println("ye");
					if (direction + 0.2 < 2 * Math.PI)
					{
						direction += 0.2;
					}
					else
					{
						direction = 0.01;
					}
				}
				if (raPressed == true)
				{
					// System.out.println("ye");
					if (direction - 0.2 > 0)
					{
						direction -= 0.2;
					}
					else
					{
						direction = 2 * Math.PI - 0.01;
					}
				}
				double xVal, yVal;
				if (wPressed == true)
				{
					if (direction < Math.PI / 2 && direction > 0)
					{
						xVal = walkrate * (Math.cos(direction));
						if (playerx + xVal < sizex / 10)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.sin(direction));
						if (playery - yVal > 0)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
					}
					else if (direction < Math.PI && direction > Math.PI / 2)
					{
						xVal = walkrate * (Math.cos(Math.PI - direction));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.sin(Math.PI - direction));
						if (playery - yVal > 0)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}

					}
					else if (direction < 3 * Math.PI / 2 && direction > Math.PI)
					{
						xVal = walkrate * (Math.cos(direction - Math.PI));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.sin(direction - Math.PI));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}

					}
					else if (direction < 2 * Math.PI
							&& direction > 3 * Math.PI / 2)
					{
						xVal = walkrate * (Math.cos(2 * Math.PI - direction));
						if (playerx + xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.sin(2 * Math.PI - direction));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}

					}
					else if (direction == 0 || direction == 2 * Math.PI)
					{
						if (playerx + walkrate < sizex / 10)
							if (currentMap[(int) (playery)][(int) (playerx
									+ walkrate)] == 0)
								playerx += walkrate;
					}
					else if (direction == Math.PI / 2)
					{
						if (playery - walkrate > 0)
							if (currentMap[(int) (playery
									- walkrate)][(int) (playerx)] == 0)
								playery -= walkrate;
					}
					else if (direction == Math.PI)
					{
						if (playerx - walkrate > 0)
							if (currentMap[(int) (playery)][(int) (playerx
									- walkrate)] == 0)
								playerx -= walkrate;
					}
					else
					{
						if (playery + walkrate < sizey / 10)
							if (currentMap[(int) (playery
									+ walkrate)][(int) (playerx)] == 0)
								playery += walkrate;
					}
				}
				if (aPressed == true)
				{
					if (direction < Math.PI / 2 && direction > 0)
					{
						xVal = walkrate * (Math.sin(direction));
						if (playerx - xVal < sizex / 10)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.cos(direction));
						if (playery - yVal > 0)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
					}
					else if (direction < Math.PI && direction > Math.PI / 2)
					{
						xVal = walkrate * (Math.sin(Math.PI - direction));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.cos(Math.PI - direction));
						if (playery + yVal > 0)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}
					}
					else if (direction < 3 * Math.PI / 2 && direction > Math.PI)
					{
						xVal = walkrate * (Math.sin(direction - Math.PI));
						if (playerx + xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.cos(direction - Math.PI));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}
					}
					else if (direction < 2 * Math.PI
							&& direction > 3 * Math.PI / 2)
					{
						xVal = walkrate * (Math.sin(2 * Math.PI - direction));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.cos(2 * Math.PI - direction));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
					}
					else if (direction == 0 || direction == 2 * Math.PI)
					{

						if (playery - walkrate > 0)
							if (currentMap[(int) (playery
									- walkrate)][(int) (playerx)] == 0)
								playery -= walkrate;
					}
					else if (direction == Math.PI / 2)
					{
						if (playerx - walkrate > 0)
							if (currentMap[(int) (playery)][(int) (playerx
									- walkrate)] == 0)
								playerx -= walkrate;
					}
					else if (direction == Math.PI)
					{
						if (playery + walkrate < sizey / 10)
							if (currentMap[(int) (playery
									+ walkrate)][(int) (playerx)] == 0)
								playery += walkrate;
					}
					else
					{
						if (playerx + walkrate < sizex / 10)
							if (currentMap[(int) (playery)][(int) (playerx
									+ walkrate)] == 0)
								playerx += walkrate;
					}
				}
				if (sPressed == true)
				{
					if (direction < Math.PI / 2 && direction > 0)
					{
						xVal = walkrate * (Math.cos(direction));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.sin(direction));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}
						// playerx -= walkrate * (Math.cos(direction));
						// playery += walkrate * (Math.sin(direction));
					}
					else if (direction < Math.PI && direction > Math.PI / 2)
					{
						xVal = walkrate * (Math.cos(Math.PI - direction));
						if (playerx + xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.sin(Math.PI - direction));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}
						// playerx += walkrate * (Math.cos(direction - Math.PI /
						// 2));
						// playery += walkrate * (Math.sin(direction - Math.PI /
						// 2));
					}
					else if (direction < 3 * Math.PI / 2 && direction > Math.PI)
					{
						xVal = walkrate * (Math.cos(direction - Math.PI));
						if (playerx + xVal < sizex / 10)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.sin(direction - Math.PI));
						if (playery - yVal > 0)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
						// playerx += walkrate * (Math.cos(direction -
						// Math.PI));
						// playery -= walkrate * (Math.sin(direction -
						// Math.PI));
					}
					else if (direction < 2 * Math.PI
							&& direction > 3 * Math.PI / 2)
					{
						xVal = walkrate * (Math.cos(2 * Math.PI - direction));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.sin(2 * Math.PI - direction));
						if (playery - yVal > 0)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
						// playerx -= walkrate
						// * (Math.cos(direction - 3 * Math.PI / 2));
						// playery -= walkrate
						// * (Math.sin(direction - 3 * Math.PI / 2));
					}
					else if (direction == 0 || direction == 2 * Math.PI)
					{
						if (playerx - walkrate > 0)
							if (currentMap[(int) (playery)][(int) (playerx
									- walkrate)] == 0)
								playerx -= walkrate;
					}
					else if (direction == Math.PI / 2)
					{
						if (playery + walkrate < sizey / 10)
							if (currentMap[(int) (playery
									+ walkrate)][(int) (playerx)] == 0)
								playery += walkrate;
					}
					else if (direction == Math.PI)
					{
						if (playerx + walkrate < sizex / 10)
							if (currentMap[(int) (playery)][(int) (playerx
									+ walkrate)] == 0)
								playerx += walkrate;
					}
					else
					{
						if (playery - walkrate > 0)
							if (currentMap[(int) (playery
									- walkrate)][(int) (playerx)] == 0)
								playery -= walkrate;
					}
				}
				if (dPressed == true)
				{
					if (direction < Math.PI / 2 && direction > 0)
					{
						xVal = walkrate * (Math.sin(direction));
						if (playerx + xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.cos(direction));
						if (playery + yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}
						// playerx += walkrate * (Math.cos(direction));
						// playery += walkrate * (Math.sin(direction));
					}
					else if (direction < Math.PI && direction > Math.PI / 2)
					{
						xVal = walkrate * (Math.sin(Math.PI - direction));
						if (playerx + xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									+ xVal)] == 0)
							{
								playerx += xVal;
							}
						}
						yVal = walkrate * (Math.cos(Math.PI - direction));
						if (playery - yVal < sizey / 10)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
						// playerx -= walkrate * (Math.cos(direction - Math.PI /
						// 2));
						// playery += walkrate * (Math.sin(direction - Math.PI /
						// 2));
					}
					else if (direction < 3 * Math.PI / 2 && direction > Math.PI)
					{
						xVal = walkrate * (Math.sin(direction - Math.PI));
						if (playerx - xVal > 0)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.cos(direction - Math.PI));
						if (playery - yVal > 0)
						{
							if (currentMap[(int) (playery
									- yVal)][(int) (playerx)] == 0)
							{
								playery -= yVal;
							}
						}
						// playerx -= walkrate * (Math.cos(direction -
						// Math.PI));
						// playery -= walkrate * (Math.sin(direction -
						// Math.PI));
					}
					else if (direction < 2 * Math.PI
							&& direction > 3 * Math.PI / 2)
					{
						xVal = walkrate * (Math.sin(2 * Math.PI - direction));
						if (playerx - xVal < sizex / 10)
						{
							if (currentMap[(int) (playery)][(int) (playerx
									- xVal)] == 0)
							{
								playerx -= xVal;
							}
						}
						yVal = walkrate * (Math.cos(2 * Math.PI - direction));
						if (playery + yVal > 0)
						{
							if (currentMap[(int) (playery
									+ yVal)][(int) (playerx)] == 0)
							{
								playery += yVal;
							}
						}
						// playerx += walkrate
						// * (Math.cos(direction - 3 * Math.PI / 2));
						// playery -= walkrate
						// * (Math.sin(direction - 3 * Math.PI / 2));
					}
					else if (direction == 0 || direction == 2 * Math.PI)
					{
						if (playery + walkrate < sizey / 10)
							if (currentMap[(int) (playery
									+ walkrate)][(int) (playerx)] == 0)
								playery += walkrate;
					}
					else if (direction == Math.PI / 2)
					{
						if (playerx + walkrate < sizex / 10)
							if (currentMap[(int) (playery)][(int) (playerx
									+ walkrate)] == 0)
								playerx += walkrate;
					}
					else if (direction == Math.PI)
					{
						if (playery - walkrate > 0)
							if (currentMap[(int) (playery
									- walkrate)][(int) (playerx)] == 0)
								playery -= walkrate;
					}
					else
					{
						if (playerx - walkrate > 0)
							if (currentMap[(int) (playery)][(int) (playerx
									- walkrate)] == 0)
								playerx -= walkrate;
					}
				}
				// System.out.println("Dir:" + direction + " PT: (" + playerx +
				// ","
				// + playery + ")");
				window.repaint();
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
			window.setVisible(false);

		}

	}

	public class TypeKeyListener implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{

		}

	}
	public class MyKeyListener implements KeyListener
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

			// System.out.println("keyPressed="
			// + KeyEvent.getKeyText(e.getKeyCode()));

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
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Left"))
			{
				laPressed = true;
				// System.out.println("Set true");
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Right"))
			{
				raPressed = true;
				// System.out.println("Set true");
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape"))
			{
				running = false;
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
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Left"))
			{
				laPressed = false;
				// direction+=0.01;
			}
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("Right"))
			{
				raPressed = false;
				// direction -= 0.01;
			}

		}
	}

	// public class MyKeyListenerA implements KeyListener {
	//
	// public void keyTyped(KeyEvent e) {
	// // if (KeyEvent.getKeyText(e.getKeyCode()).equals("Enter"))
	// // {
	// //
	// // }
	// }
	//
	// public void keyPressed(KeyEvent e) {
	//
	// System.out.println("keyPressed="
	// + KeyEvent.getKeyText(e.getKeyCode()));
	//
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
	// wPressed = true;
	// }
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
	// aPressed = true;
	// }
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
	// sPressed = true;
	// }
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
	// dPressed = true;
	// }
	// }
	//
	// public void keyReleased(KeyEvent e) {
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
	// wPressed = false;
	// }
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
	// aPressed = false;
	// }
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
	// sPressed = false;
	// }
	// if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
	// dPressed = false;
	// }
	// }
	// }

}

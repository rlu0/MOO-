import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Engine extends JPanel implements Runnable, KeyListener{
	
	Player [] players;
	Wall [] walls;
	
	double forwardAccel = 0.005;
	double sidewaysAccel = 0.003;
	double backwardAccel = 0.003;
	
	int drawScale = 25;
	
	// Current thread
	Thread animator;
	
	int frameTime = 16;
	
	
	Engine (){
		
		// Init GAME VARS
		players = new Player [1];
		players[0] = new Player(4, 4, new CircleHit(5,5,0.5));
		walls = new Wall [4];
		walls[0] = new Wall(0, 0, 10, 1);
		walls[1] = new Wall(9, 1, 1, 8);
		walls[2] = new Wall(0, 9, 10, 1);
		walls[3] = new Wall(0, 1, 1, 8);
		
		// Init Panel
		this.setPreferredSize(new Dimension(600,400));
		this.setVisible(true);
		this.setDoubleBuffered(true);
		
		
		// Init listeners
		addKeyListener(this);
		
	}
	
	public void addNotify()
	{
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		// Draw Floor
		g.setColor(new Color(200,200,200));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Draw Walls
		g.setColor(new Color (40,40,40));
		for (int i=0; i<walls.length; i++){
			g.fillRect((int)Math.round(walls[i].getX()		*  drawScale),
					(int)Math.round(walls[i].getY()			*  drawScale),
					(int)Math.round(walls[i].getWidth()		*  drawScale),
					(int)Math.round(walls[i].getHeight())	*  drawScale);
		}
		
		// Draw Player
		g.setColor(new Color(10, 84, 173));
		for (int i=0; i<players.length; i++){
			g.fillOval((int)Math.round((players[i].hit.getX() - players[i].hit.getR()/2)* drawScale),
					(int)Math.round((players[i].hit.getY() - players[i].hit.getR()/2) 	* drawScale),
					(int)Math.round(players[i].hit.getR() 								* drawScale),
					(int)Math.round(players[i].hit.getR() 								* drawScale));
		}
		g.setColor(Color.WHITE);
		g.drawString(players[0].getX() + " " + players[0].getY() + " " + players[0].isMoveForward,10, 10);
	}
	


	public void run() {
		
		
		
		while (true){
			long startTime = System.currentTimeMillis();
			
			
			// Per player physics
			for(int i=0; i<players.length; i++){
				
				
				// movement vectors
				Vector moveForce = new Vector (0,0,true);
				
				if (players[i].isMoveForward){
					moveForce = moveForce.add(moveForce, new Vector(forwardAccel,players[i].direction,false));
				}
				if (players[i].isMoveBack){
					moveForce = moveForce.add(moveForce, new Vector(backwardAccel,players[i].direction + Math.PI,false));
				}
				if (players[i].isMoveRight){
					moveForce = moveForce.add(moveForce, new Vector(sidewaysAccel,players[i].direction - (Math.PI/2),false));
				}
				if (players[i].isMoveLeft){
					moveForce = moveForce.add(moveForce, new Vector(sidewaysAccel,players[i].direction + (Math.PI/2),false));
				}
				
				// add movement vector to acceleration
				players[i].acceleration = players[i].acceleration.add(players[i].acceleration,moveForce);
				
				// add acceleration to velocity
				players[i].acceleration.calcLengthAngle();
				players[i].velocity = players[i].velocity.add(players[i].velocity, players[i].acceleration);
				players[i].velocity.calcLengthAngle();
				
				// add velocity to position
				players[i].setX(players[i].getX() + players[i].velocity.getX());
				players[i].setY(players[i].getY() + players[i].velocity.getY());
				
				
			}
			
			System.out.println("looping");

			
			
			repaint();
			
			long endTime = System.currentTimeMillis();
			long currentDelay = frameTime - endTime - startTime;
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		//JFrame frame = new JFrame("MOOD_2D");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				JPanel e = new Engine();
				JFrame frame = new JFrame("Mood 2D");
				frame.add(e);
				//frame.setResizable(false);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});

		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		System.out.println("pressed");
		
		if (key == KeyEvent.VK_W){
			players[0].isMoveForward = true;
		}
		if (key == KeyEvent.VK_S){
			players[0].isMoveBack = true;
		}
		if (key == KeyEvent.VK_D){
			players[0].isMoveRight = true;
		}
		if (key == KeyEvent.VK_A){
			players[0].isMoveLeft = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W){
			players[0].isMoveForward = false;
		}
		if (key == KeyEvent.VK_S){
			players[0].isMoveBack = false;
		}
		if (key == KeyEvent.VK_D){
			players[0].isMoveRight = false;
		}
		if (key == KeyEvent.VK_A){
			players[0].isMoveLeft = false;
		}
	}

}

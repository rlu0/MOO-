import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Physics testing purposes
 * @author Tony
 * @version 2016-06-15
 */
public class Engine extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	Player [] players;
	Wall [] walls;
	ArrayList<Hitscan> hitscans = new ArrayList<Hitscan>();
	
	double forwardAccel = 0.025;
	double sidewaysAccel = 0.02;
	double backwardAccel = 0.02;
	double maxAccel = 0.025;
	double quadDrag = 0.05;
	double linearDrag = 0.12;
	double constDrag = 0.006;
	
	int drawScale = 25;
	
	// Current thread
	Thread animator;
	
	int frameTime = 28;
	
	double lastCollisionX = 0;
	double lastCollisionY = 0;
	
	// Mouse movement
	Robot robot;
	int mouseX;
	double turnAmount;
	double mouseSens = 0.01;
	
	
	Engine (){
		
		// Init GAME VARS
		players = new Player [2];
		players[0] = new Player(4, 4, new CircleHit(5,5,0.25));
		players[0].direction = (Math.PI*3)/2;
		players[1] = new Player(7,7,0);
		walls = new Wall [5];
		walls[0] = new Wall(0, 0, 10, 1);
		walls[1] = new Wall(9, 1, 1, 8);
		walls[2] = new Wall(0, 9, 10, 1);
		walls[3] = new Wall(0, 1, 1, 8);
		walls[4] = new Wall(3,4,1,2);
		//testing
		//hitscans.add(new Hitscan(players[0],5,100000000));
		
		// Init Panel
		this.setPreferredSize(new Dimension(600,400));
		this.setVisible(true);
		this.setDoubleBuffered(true);
		
		
		// Init listeners
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void addNotify()
	{
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D graphics2D = (Graphics2D) g;
		
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON); 
		
		// Draw Floor
		g.setColor(new Color(200,200,200));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Draw Walls
		g.setColor(new Color (40,40,40));
		for (int i=0; i<walls.length; i++){
			g.fillRect((int)Math.round(walls[i].hit.getX1()							*  drawScale),
					(int)Math.round(walls[i].hit.getY1()							*  drawScale),
					(int)Math.round((walls[i].hit.getX2() - walls[i].hit.getX1())	*  drawScale),
					(int)Math.round((walls[i].hit.getY2() - walls[i].hit.getY1())	*  drawScale));
		}
		
		// Draw Player
		g.setColor(new Color(10, 84, 173));
		for (int i=0; i<players.length; i++){
			g.fillOval((int)Math.round((players[i].hit.getX() - players[i].hit.getR())* drawScale),
					(int)Math.round((players[i].hit.getY() - players[i].hit.getR()) 	* drawScale),
					(int)Math.round(players[i].hit.getR()*2 							* drawScale),
					(int)Math.round(players[i].hit.getR()*2 							* drawScale));
			
			g.drawLine((int)Math.round(players[i].hit.getX() * drawScale),
					(int)Math.round(players[i].hit.getY() * drawScale),
					(int)Math.round((players[i].hit.getX()+Math.cos(players[i].direction)*0.7) * drawScale),
					(int)Math.round((players[i].hit.getY()+Math.sin(players[i].direction)*0.7) * drawScale));
		}
		
		//ADD TO CLIENT
		// Draw Shots
		g.setColor(new Color(255, 100, 0));
		for (int i=0; i<hitscans.size(); i++){
			g.drawLine((int)Math.round(hitscans.get(i).hit.getX1() * drawScale),
					(int)Math.round(hitscans.get(i).hit.getY1() * drawScale),
					(int)Math.round(hitscans.get(i).hit.getX2() * drawScale),
					(int)Math.round(hitscans.get(i).hit.getY2() * drawScale));
		}
		
		g.setColor(Color.RED);
		g.drawRect((int)Math.round(lastCollisionX*drawScale), (int)Math.round(lastCollisionY*drawScale), 1, 1);
		
		g.setColor(Color.WHITE);
		g.drawString(players[0].getX() + " " + players[0].getY() + " " + players[0].isMoveForward,10, 10);
		
	}
	


	public void run() {
		

		
		while (true){
			long startTime = System.currentTimeMillis();
			
			movePlayers();
			
			// ADD TO CLIENT
			generateShots();
			calcShots();
			checkHits();
			
			repaint();
			
			long endTime = System.currentTimeMillis();
			long currentDelay = frameTime - (endTime - startTime);
			
			try {
				if (currentDelay > 0)
					Thread.sleep(currentDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	void movePlayers(){
		// Per player physics
		for(int i=0; i<1; i++){
			
			
			// movement vectors
			Vector moveForce = new Vector (0,players[0].direction,false);
			
			if (players[i].isMoveForward){
				moveForce.addComponents(new Vector(forwardAccel,players[i].direction,false));
			}
			if (players[i].isMoveBack){
				moveForce.addComponents(new Vector(-backwardAccel,players[i].direction,false));
			}
			if (players[i].isMoveRight){
				moveForce.addComponents(new Vector(sidewaysAccel,players[i].direction + (Math.PI/2),false));
			}
			if (players[i].isMoveLeft){
				moveForce.addComponents(new Vector(sidewaysAccel,players[i].direction - (Math.PI/2),false));
			}
			if (players[i].isTurnLeft){
				players[i].direction -= 0.1;
			}
			if (players[i].isTurnRight){
				players[i].direction += 0.1;
			}
			//if (turnAmount != 0){
			players[i].direction += turnAmount;
			turnAmount = 0;
			//}
			
			// set movement vector to acceleration
			moveForce.calcLengthAngle();
			if (moveForce.length > 0.0001){
				moveForce.length = maxAccel;

			}
			players[i].acceleration = moveForce;
			
			// add acceleration to velocity
			//players[i].acceleration.calcLengthAngle();
			players[i].acceleration.calcComponents();
			players[i].velocity.addComponents(players[i].acceleration);
				
				
	
			// add drag to velocity
			double playerSpeed = players[i].velocity.length;
			players[i].velocity.length -= playerSpeed*playerSpeed*quadDrag;
			players[i].velocity.length -= playerSpeed*linearDrag;
			players[i].velocity.length = Math.max(players[i].velocity.length-constDrag, 0);
			players[i].velocity.calcComponents();
			/*
			players[i].velocity.x -= players[i].velocity.x * linearDrag;
			if(players[i].velocity.x>0)
				players[i].velocity.x = Math.max(players[i].velocity.x-constDrag, 0);
			if(players[i].velocity.x<0)
				players[i].velocity.x = Math.min(players[i].velocity.x+constDrag, 0);
			players[i].velocity.y -= players[i].velocity.y * linearDrag;
			if(players[i].velocity.y>0)
				players[i].velocity.y = Math.max(players[i].velocity.y-constDrag, 0);
			if(players[i].velocity.y<0)
				players[i].velocity.y = Math.min(players[i].velocity.y+constDrag, 0);
			players[i].velocity.calcLengthAngle();*/
			
			//System.out.println(players[i].direction);
			//System.out.println(players[i].velocity.length+" "+Math.toDegrees(players[i].direction));
			//System.out.println(players[i].velocity.getX()+" "+players[i].velocity.getY());


			
			// add velocity to position
			players[i].setX(players[i].getX() + players[i].velocity.getX());
			players[i].setY(players[i].getY() + players[i].velocity.getY());
			
			

			
			// add velocity to position
			//players[i].setX(players[i].getX() + players[i].velocity.getX());
			//players[i].setY(players[i].getY() + players[i].velocity.getY());
			
			// wall collisions
	
			for (int j = 0; j<walls.length; j++){
				double [] coord = players[i].hit.RCIntersect(walls[j].hit, players[i].hit);
				if (coord[0] != Double.MAX_VALUE && coord[1] != Double.MAX_VALUE){
					double xDisplace = players[i].getX()-coord[0];
					double yDisplace = players[i].getY()-coord[1];
					
					//System.out.println(coord[0] + " " + coord[1]);
					
					//System.out.println();
					lastCollisionX = coord[0];
					lastCollisionY = coord[1];
					
					double centerDistance = Math.sqrt(Math.pow(players[i].getX()-coord[0],2)
							+ Math.pow(players[i].getY()-coord[1], 2));
					double displaceDist = players[i].hit.getR() - centerDistance;
					
					Vector displace = new Vector(xDisplace, yDisplace, true);
					displace.length = displaceDist;
					displace.calcComponents();
					
					//Vector bounceVelocity = new Vector(xDisplace, yDisplace, true);
					//players[i].velocity.addComponents(bounceVelocity);
					
					players[i].setX(players[i].getX()+displace.getX());
					players[i].setY(players[i].getY()+displace.getY());
					
				}
			}
			
			// player collisions
			
			for (int j = 1; j<players.length; j++){
				if(i==j){
					continue;
				}
				double [] coord = players[i].hit.CCIntersect(players[j].hit, players[i].hit);
				if (coord[0] != Double.MAX_VALUE && coord[1] != Double.MAX_VALUE){
					double xDisplace = players[i].getX()-coord[0];
					double yDisplace = players[i].getY()-coord[1];
					
					//System.out.println(coord[0] + " " + coord[1]);
					
					//System.out.println();
					lastCollisionX = coord[0];
					lastCollisionY = coord[1];
					
					double centerDistance = Math.sqrt(Math.pow(players[i].getX()-coord[0],2)
							+ Math.pow(players[i].getY()-coord[1], 2));
					double displaceDist = players[i].hit.getR() - centerDistance;
					
					Vector displace = new Vector(xDisplace, yDisplace, true);
					displace.length = displaceDist;
					displace.calcComponents();
					
					//Vector bounceVelocity = new Vector(xDisplace, yDisplace, true);
					//players[i].velocity.addComponents(bounceVelocity);
					
					players[i].setX(players[i].getX()+displace.getX());
					players[i].setY(players[i].getY()+displace.getY());
					
				}
			}
			
		}
	}
	
	
	// ADD TO CLIENT
	
	void generateShots() {
		if (players[0].isShoot && players[0].canShoot){
			hitscans.add(new Hitscan(players[0], 5, 1));
			System.out.println("new shot");
			players[0].canShoot = false;
		}
		
	}
	
	//ADD TO CLIENT
	void calcShots () {
		
		for (int i=0; i<hitscans.size(); i++){
			
			System.out.println(hitscans.get(i).framesLeft);
			
			if (hitscans.get(i).framesLeft == 0){
				hitscans.remove(i);
				i--;
				continue;
			}

			
			hitscans.get(i).update();
			
			System.out.println(": " + hitscans.get(i).hit.getX2() + " " + hitscans.get(i).hit.getY2());
			
			
			double shortestLength = hitscans.get(i).vector.length;
			double [] shortestCoord = new double[2];
			shortestCoord[0] = hitscans.get(i).hit.getX2();
			shortestCoord[1] = hitscans.get(i).hit.getY2();
			
			for (int j=0; j<walls.length; j++){
				
				//System.out.println("hit");
				
				double [] coord = hitscans.get(i).hit.RLIntersect(walls[j].hit, hitscans.get(i).hit);
				
				if (coord[0] != Double.MAX_VALUE && coord[1] != Double.MAX_VALUE){
					System.out.printf("hit: .4%f .4%f%n", coord[0], coord[1]);
					
					double currentLength = Math.sqrt(Math.pow(hitscans.get(i).hit.getX1()-coord[0], 2)  + 
							Math.pow(hitscans.get(i).hit.getY1() - coord[1], 2));
					
					if (currentLength < shortestLength){
						shortestLength = currentLength;
						shortestCoord = coord;
					}
					
				}
			}
			
			hitscans.get(i).hit = new Line (hitscans.get(i).shooter.getX(), hitscans.get(i).shooter.getY(),
					shortestCoord[0], shortestCoord[1]);
			
			//System.out.println(hitscans.get(i).hit.getX2() + " " + hitscans.get(i).hit.getY2());
			hitscans.get(i).framesLeft --;
			
			
		}
	}
	
	void checkHits(){
		for (int i=0; i<hitscans.size(); i++){
			for (int j=0; j<players.length; j++){
				if (players[j].equals(hitscans.get(i).shooter)){
					continue;
				}
				if (hitscans.get(i).hit.CLIntersect(players[j].hit, hitscans.get(i).hit)){
					players[j].setHP(players[j].getHP() - 30);
					System.out.println("PLAYER "+j+" HIT");
				}
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
				e.requestFocusInWindow();
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
		if (key == KeyEvent.VK_E){
			players[0].isTurnRight = true;
		}
		if (key == KeyEvent.VK_Q){
			players[0].isTurnLeft = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W && players[0].isMoveForward){
			players[0].isMoveForward = false;
		}
		if (key == KeyEvent.VK_S && players[0].isMoveBack){
			players[0].isMoveBack = false;
		}
		if (key == KeyEvent.VK_D && players[0].isMoveRight){
			players[0].isMoveRight = false;
		}
		if (key == KeyEvent.VK_A && players[0].isMoveLeft){
			players[0].isMoveLeft = false;
		}
		if (key == KeyEvent.VK_E && players[0].isTurnRight){
			players[0].isTurnRight = false;
		}
		if (key == KeyEvent.VK_Q && players[0].isTurnLeft){
			players[0].isTurnLeft = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		mouseX = MouseInfo.getPointerInfo().getLocation().x;
		//if (lastMouseX == 0) lastMouseX = mouseX;
		turnAmount += (mouseX - (int)screenSize.getWidth()/2.0) * mouseSens;
		
		//System.out.println(turnAmount);
		//lastMouseX = mouseX;
		
		robot.mouseMove((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		mouseX = MouseInfo.getPointerInfo().getLocation().x;
		//if (lastMouseX == 0) lastMouseX = mouseX;
		turnAmount += (mouseX - (int)screenSize.getWidth()/2.0) * mouseSens;
		
		//System.out.println(turnAmount);
		//lastMouseX = mouseX;
		
		robot.mouseMove((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);

	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e) && !players[0].isShoot){
			players[0].isShoot = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e) && players[0].isShoot){
			players[0].isShoot = false;
			players[0].canShoot = true;
		}
		
	}

}

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Engine extends JPanel{
	
	Player [] players;
	
	Wall [] walls;
	
	int drawScale = 20;
	
	
	
	Engine (){
		
		// Init GAME VARS
		players = new Player [1];
		players[0] = new Player(100, 100, new CircleHit(5,5,0.5));
		walls = new Wall [4];
		walls[0] = new Wall(0, 0, 10, 1);
		walls[1] = new Wall(9, 1, 1, 8);
		walls[2] = new Wall(0, 9, 10, 1);
		walls[3] = new Wall(0, 1, 1, 8);
		
		// Init Panel
		this.setSize(600,400);
		this.setVisible(true);
		this.setDoubleBuffered(true);
		
		run();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		// Draw Floor
		g.setColor(Color.LIGHT_GRAY);
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
		g.setColor(Color.ORANGE);
		for (int i=0; i<players.length; i++){
			g.fillOval((int)Math.round((players[i].hit.getX() - players[i].hit.getR()/2)* drawScale),
					(int)Math.round((players[i].hit.getY() - players[i].hit.getR()/2) 	* drawScale),
					(int)Math.round(players[i].hit.getR() 								* drawScale),
					(int)Math.round(players[i].hit.getR() 								* drawScale));
		}
	}
	


	public void run() {
		
		
		while (true){
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("MOOD_2D");
		
		Engine e = new Engine();
		frame.add(e);
		frame.pack();
		
		frame.setVisible(true);
		
	}

}

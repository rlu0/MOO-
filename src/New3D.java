import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class New3D
{
	static int[][] map = {
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1},
		{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1},
		{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,1},
		{1,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} };

	static double playerx, playery;
	static double direction, uberDirection;
	static int sizex = 450;
	static int sizey = 450;
	// Things relating to movement
	static boolean wPressed = false;
	static boolean aPressed = false;
	static boolean sPressed = false;
	static boolean dPressed = false;
	static boolean laPressed = false;
	static boolean raPressed = false;
	static boolean running = true;

	static int walkrate = 1;

	public static void main(String[] args)
	{

		playerx = 2;
		playery = 2;
		direction = 0;

		New3D blah = new New3D();
		blah.run();

	}

	public void run()
	{
		JFrame window = new JFrame("MOOD");
		JPanel bananarama = new GameDisp();
		window.add(bananarama);
		window.setResizable(false);
		window.setVisible(true);
		window.setSize(455, 475);
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
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate * (Math.sin(direction));
					if (playery - yVal > 0)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate * (Math.sin(Math.PI - direction));
					if (playery - yVal > 0)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate * (Math.sin(direction - Math.PI));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
						{
							playery += yVal;
						}
					}


				}
				else if (direction < 2 * Math.PI && direction > 3 * Math.PI / 2)
				{
					xVal = walkrate
							* (Math.cos(2 * Math.PI - direction));
					if (playerx + xVal > 0)
					{
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate
							* (Math.sin(2 * Math.PI - direction));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
						{
							playery += yVal;
						}
					}

				}
				else if (direction == 0 || direction == 2 * Math.PI)
				{
					if (playerx + walkrate < sizex / 10)
						if (map[(int) (playery)][(int) (playerx
								+ walkrate)] == 0)
							playerx += walkrate;
				}
				else if (direction == Math.PI / 2)
				{
					if (playery - walkrate > 0)
						if (map[(int) (playery
								- walkrate)][(int) (playerx)] == 0)
							playery -= walkrate;
				}
				else if (direction == Math.PI)
				{
					if (playerx - walkrate > 0)
						if (map[(int) (playery)][(int) (playerx
								- walkrate)] == 0)
							playerx -= walkrate;
				}
				else
				{
					if (playery + walkrate < sizey / 10)
						if (map[(int) (playery
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
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate * (Math.cos(direction));
					if (playery - yVal > 0)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate * (Math.cos(Math.PI - direction));
					if (playery + yVal > 0)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate * (Math.cos(direction - Math.PI));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
						{
							playery += yVal;
						}
					}
				}
				else if (direction < 2 * Math.PI && direction > 3 * Math.PI / 2)
				{
					xVal = walkrate
							* (Math.sin(2 * Math.PI - direction));
					if (playerx - xVal > 0)
					{
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate
							* (Math.cos(2 * Math.PI - direction));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
						{
							playery -= yVal;
						}
					}
				}
				else if (direction == 0 || direction == 2 * Math.PI)
				{

					if (playery - walkrate > 0)
						if (map[(int) (playery
								- walkrate)][(int) (playerx)] == 0)
							playery -= walkrate;
				}
				else if (direction == Math.PI / 2)
				{
					if (playerx - walkrate > 0)
						if (map[(int) (playery)][(int) (playerx
								- walkrate)] == 0)
							playerx -= walkrate;
				}
				else if (direction == Math.PI)
				{
					if (playery + walkrate < sizey / 10)
						if (map[(int) (playery
								+ walkrate)][(int) (playerx)] == 0)
							playery += walkrate;
				}
				else
				{
					if (playerx + walkrate < sizex / 10)
						if (map[(int) (playery)][(int) (playerx
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
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate * (Math.sin(direction));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
						{
							playery += yVal;
						}
					}
					// playerx -= walkrate * (Math.cos(direction));
					// playery += walkrate * (Math.sin(direction));
				}
				else if (direction < Math.PI && direction > Math.PI / 2)
				{
					xVal = walkrate
							* (Math.cos(Math.PI - direction));
					if (playerx + xVal > 0)
					{
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate
							* (Math.sin(Math.PI - direction));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate * (Math.sin(direction - Math.PI));
					if (playery - yVal > 0)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
						{
							playery -= yVal;
						}
					}
					// playerx += walkrate * (Math.cos(direction - Math.PI));
					// playery -= walkrate * (Math.sin(direction - Math.PI));
				}
				else if (direction < 2 * Math.PI && direction > 3 * Math.PI / 2)
				{
					xVal = walkrate * (Math.cos(2 * Math.PI - direction));
					if (playerx - xVal > 0)
					{
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate
							* (Math.sin(2 * Math.PI - direction));
					if (playery - yVal > 0)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								- walkrate)] == 0)
							playerx -= walkrate;
				}
				else if (direction == Math.PI / 2)
				{
					if (playery + walkrate < sizey / 10)
						if (map[(int) (playery
								+ walkrate)][(int) (playerx)] == 0)
							playery += walkrate;
				}
				else if (direction == Math.PI)
				{
					if (playerx + walkrate < sizex / 10)
						if (map[(int) (playery)][(int) (playerx
								+ walkrate)] == 0)
							playerx += walkrate;
				}
				else
				{
					if (playery - walkrate > 0)
						if (map[(int) (playery
								- walkrate)][(int) (playerx)] == 0)
							playery -= walkrate;
				}
			}
			if (dPressed == true)
			{
				if (direction < Math.PI / 2 && direction > 0)
				{
					xVal = walkrate
							* (Math.sin(direction));
					if (playerx + xVal > 0)
					{
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate
							* (Math.cos(direction));
					if (playery + yVal < sizey / 10)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								+ xVal)] == 0)
						{
							playerx += xVal;
						}
					}
					yVal = walkrate * (Math.cos(Math.PI - direction));
					if (playery - yVal < sizey / 10)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate * (Math.cos(direction - Math.PI));
					if (playery - yVal > 0)
					{
						if (map[(int) (playery - yVal)][(int) (playerx)] == 0)
						{
							playery -= yVal;
						}
					}
					// playerx -= walkrate * (Math.cos(direction - Math.PI));
					// playery -= walkrate * (Math.sin(direction - Math.PI));
				}
				else if (direction < 2 * Math.PI && direction > 3 * Math.PI / 2)
				{
					xVal = walkrate * (Math.sin(2 * Math.PI - direction));
					if (playerx - xVal < sizex / 10)
					{
						if (map[(int) (playery)][(int) (playerx
								- xVal)] == 0)
						{
							playerx -= xVal;
						}
					}
					yVal = walkrate
							* (Math.cos(2 * Math.PI - direction));
					if (playery + yVal > 0)
					{
						if (map[(int) (playery + yVal)][(int) (playerx)] == 0)
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
						if (map[(int) (playery
								+ walkrate)][(int) (playerx)] == 0)
							playery += walkrate;
				}
				else if (direction == Math.PI / 2)
				{
					if (playerx + walkrate < sizex / 10)
						if (map[(int) (playery)][(int) (playerx
								+ walkrate)] == 0)
							playerx += walkrate;
				}
				else if (direction == Math.PI)
				{
					if (playery - walkrate > 0)
						if (map[(int) (playery
								- walkrate)][(int) (playerx)] == 0)
							playery -= walkrate;
				}
				else
				{
					if (playerx - walkrate > 0)
						if (map[(int) (playery)][(int) (playerx
								- walkrate)] == 0)
							playerx -= walkrate;
				}
			}
//			System.out.println("Dir:" + direction + " PT: (" + playerx + ","
//					+ playery + ")");
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

	public class GameDisp extends JPanel
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
					tempy = playery
							- (Math.tan(Math.PI - angle) * i * 0.01);
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
									- ((i * 0.01) / Math
											.tan(angle - Math.PI));
						}
						else if (angle < 2 * Math.PI
								&& angle > 3 * Math.PI / 2)
						{
							tempy = playery + i * 0.01;
							tempx = playerx
									+ ((i * 0.01)
											/ Math.tan(2 * Math.PI - angle));
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
						if (map[(int) tempy][(int) tempx] == 1)
						{
							//System.out.println("shoe");
							// System.out.println("got here");
							newPt = new double[2];
							newPt[0] = tempx;
							newPt[1] = tempy;
							return newPt;
						}
					}
				}
				if (map[(int) tempy][(int) tempx] == 1)
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
						(int) ((sizey / 2.0) - 225.0 / distA), sizex / 2
								- i,
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
				//System.out.println("left");

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
						(int) ((sizey / 2.0) - 225.0 / distA), sizex / 2
								+ i,
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

			// //System.out.println("keyPressed="
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
}

import java.awt.Polygon;
import java.awt.Rectangle;

public class TravelTimeWeapons extends Weapon {

	final int POTATO_GUN_DMG = 1;
	final Rectangle POTATO_RANGE = new Rectangle(x, y, 10, 55555);
	final int POTATO_TYPE = 6;

	final int PLASMA_RIFLE_DMG = 25;
	final Rectangle PLASMA_SIZE = new Rectangle(x, y, 12, 12);
	final int PLASMA_TYPE = 8;

	final int ROCKET_DMG = 100;
	final Rectangle MISSLE_SIZE = new Rectangle(x, y, 12, 12);
	final int ROCKET_TYPE=10;

	TravelTimeWeapons(int type, int ammo) {
		super(type, ammo);
		// TODO Auto-generated constructor stub
	}
	
	private void movePlayer(){
		
		while (true) {

			// ACCELERATION
			p.xSpeed += p.xAccel;
			p.ySpeed += p.yAccel;
			p.x += p.xSpeed;
			p.y += p.ySpeed;

			double constantFriction = 0.05
					;
			double quadraticFriction = 0.01;

			// DRAG
			if (p.xSpeed < 0) {
				p.xSpeed += (quadraticFriction * p.xSpeed * p.xSpeed);
				if (p.xSpeed < -constantFriction) {
					p.xSpeed += constantFriction;
				} else {
					p.xSpeed = 0;
				}
			}
			if (p.xSpeed > 0) {
				p.xSpeed -= (quadraticFriction * p.xSpeed * p.xSpeed);
				if (p.xSpeed > constantFriction) {
					p.xSpeed -= constantFriction;
				} else {
					p.xSpeed = 0;
				}
			}

			if (p.ySpeed < 0) {
				p.ySpeed += (quadraticFriction * p.ySpeed * p.ySpeed);
				if (p.ySpeed < -constantFriction) {
					p.ySpeed += constantFriction;
				} else {
					p.ySpeed = 0;
				}
			}
			if (p.ySpeed > 0) {
				p.ySpeed -= (quadraticFriction * p.ySpeed * p.ySpeed);
				if (p.ySpeed > constantFriction) {
					p.ySpeed -= constantFriction;
				} else {
					p.ySpeed = 0;
				}
			}

			if (p.y < 0) {
				p.y = 0;
				p.ySpeed = -p.ySpeed;
				//p.yAccel = -p.yAccel;
			}
			if (p.y > 900 - 10) {
				p.y = 900 - 10;
				p.ySpeed = -p.ySpeed;
				//p.yAccel = -p.yAccel;
			}
			if (p.x < 0) {
				p.x = 0;
				p.xSpeed = -p.xSpeed;
				//p.xAccel = -p.xAccel;
			}
			if (p.x > 1500 + 10) {
				p.x = 1500 + 10;
				p.xSpeed = -p.xSpeed;
				//p.xAccel = -p.xAccel;
			}

			try {
				Thread.sleep(15);
			} catch (Exception exc) {
			}
			frame.repaint();
		}
	}
}




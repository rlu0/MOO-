public class Projectile {
	int x;
	int y;
	int type;
	final int PLASMA_RIFLE_DMG = 25;
	final int ROCKET_DMG = 100;
	final int POTATO_GUN_DMG = 1;
	int[] dmgs = { PLASMA_RIFLE_DMG, ROCKET_DMG, POTATO_GUN_DMG };

	int xSpeed = 15;
	int ySpeed = 15;
	int xAccel = 10;
	int yAccel = 10;

	Projectile(int x, int y, int psx, int psy) {
		this.x = x;
		this.y = y;
		xSpeed+=psx;
		ySpeed+=psy;
	}

	private void move() {
		x += xSpeed;
		y += ySpeed;
		boolean canMove=true;
		while (canMove) {// Move while not collided

			// ACCELERATION
			xSpeed += xAccel;
			ySpeed += yAccel;
			x += xSpeed;
			y += ySpeed;

		}
		explode();
	}

	int explode() {
		// check if it hits
		if (true) {
			return dmgs[type];
		} else
			return 1;

	}
	
	
}
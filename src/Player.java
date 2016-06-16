/**
 * 
 * @author Tony, Michael, Ray
 * @version 2016-06-15
 */
public class Player extends Thing {
	private double x;
	private double y;
	private int hp;

	final double START_DIRECTION = 0;
	Weapon[] weapons = new Weapon[10];
	Vector velocity;
	Vector acceleration;
	double direction;
	boolean isMoveForward;
	boolean isMoveBack;
	boolean isMoveRight;
	boolean isMoveLeft;
	boolean isTurnRight;
	boolean isTurnLeft;
	boolean isShoot;
	boolean canShoot;

	CircleHit hit;

	final int START_HP = 100;

	/**
	 * constructor given x, y, and circle hitbox
	 * @param x x coord
	 * @param y y coord
	 * @param hit circle hitbox
	 */
	Player(double x, double y, CircleHit hit) {
		super(100);
		hp = START_HP;
		this.x = x;
		this.y = y;
		this.hit = hit;
		acceleration = new Vector(0,0,true);
		velocity = new Vector(0,0,true);
	}

	/**
	 * Constructor given x, y, and initial direction
	 * @param x x coord
	 * @param y y coord
	 * @param d inital direction
	 */
	Player(double x, double y, double d) {
		super(100);
		hp = START_HP;
		this.x = x;
		this.y = y;
		direction = d;
		this.hit = new CircleHit(x, y, 0.3);
		acceleration = new Vector(0,0,true);
		velocity = new Vector(0,0,true);
	}

	void shoot(int i) {

	}

	double getX() {
		return x;
	}
	void setX(double x) {
		this.x = x;
		hit.setX(x);
	}
	double getY() {
		return y;
	}
	void setY(double y) {
		this.y = y;
		hit.setY(y);
	}
	int getHP() {
		return hp;
	}
	void setHP(int hp) {
		this.hp = hp;
	}
	void addHP(int increment) {
		hp += increment;
	}
	void addWeapon(int type, int ammo) {

	}
	void turnLeft() {
		if (getDirection() <= 0) {
			setDirection(2 * Math.PI - .25);
		} else {
			setDirection(getDirection() - .25);
		}
	}
	void turnRight() {
		setDirection(getDirection() + 10);
		if (getDirection() >= 2 * Math.PI) {
			setDirection(.25);
		} else {
			setDirection(getDirection() + .25);
		}
	}

	double getDirection() {
		return direction;
	}

	void setDirection(double direction) {
		this.direction = direction;
		while (this.direction < 0){
			this.direction += Math.PI*2;
		}
		while (this.direction >= Math.PI*2){
			this.direction -= Math.PI*2;
		}
	}

}

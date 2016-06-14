import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javafx.scene.shape.Circle;

public class Player extends Thing {
	private double x;
	private double y;
	private double RADIUS = 2.5;
	private int hp;

	private int id;
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

	CircleHit hit;

	final int START_HP = 100;

	Player(double x, double y, CircleHit hit) {
		super(100);
		hp = START_HP;
		this.x = x;
		this.y = y;
		this.hit = hit;
		acceleration = new Vector(0,0,true);
		velocity = new Vector(0,0,true);
	}

	Player(double x, double y, double d) {
		super(100);
		hp = START_HP;
		this.x = x;
		this.y = y;
		direction = d;
		this.hit = new CircleHit(x, y, 0.5);
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
		if (getDirection() == 0) {
			setDirection(2 * Math.PI - .25);
		} else {
			setDirection(getDirection() - .25);
		}
	}
	void turnRight() {
		setDirection(getDirection() + 10);
		if (getDirection() == 2 * Math.PI) {
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
	}

}


public class CircleHit extends Hitbox{

	private double x;
	private double y;
	private double r;
	
	CircleHit(double x, double y, double r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
	}

	double getX() {
		return x;
	}

	void setX(double x) {
		this.x = x;
	}

	double getY() {
		return y;
	}

	void setY(double y) {
		this.y = y;
	}

	double getR() {
		return r;
	}

	void setR(double r) {
		this.r = r;
	}

}

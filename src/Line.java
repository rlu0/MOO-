/**
 * Line hitbox class
 * @author Tony
 * @version 2016-06-14
 */
public class Line extends Hitbox{
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double angle;
	private double length;
	
	/**
	 * Constructor
	 * @param x1
	 * @param y1 
	 * @param x2 
	 * @param y2 
	 */
	Line (double x1, double y1, double x2, double y2){
		this.setX1(x1);
		this.setY1(y1);
		this.setX2(x2);
		this.setY2(y2);
		this.length = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		this.angle = Math.tan(y2-y1/x2-x1);
	}
	
	void setLengthAngle (double x1, double y1, double length, double angle){
		this.setX1(x1);
		this.setY1(y1);
		this.setAngle(angle);
		this.setLength(length);
		x2 = x1 + length*Math.cos(angle);
		y2 = y1 + length*Math.sin(angle);
	}

	double getAngle() {
		return angle;
	}

	void setAngle(double angle) {
		this.angle = angle;
	}

	double getX1() {
		return x1;
	}

	void setX1(double x1) {
		this.x1 = x1;
	}

	double getY1() {
		return y1;
	}

	void setY1(double y1) {
		this.y1 = y1;
	}

	double getX2() {
		return x2;
	}

	void setX2(double x2) {
		this.x2 = x2;
	}

	double getY2() {
		return y2;
	}

	void setY2(double y2) {
		this.y2 = y2;
	}

	double getLength() {
		return length;
	}

	void setLength(double length) {
		this.length = length;
	}
	
}

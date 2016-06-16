/**
 * Wall class, includes hitbox
 * @author Tony
 * @version 2016-06-15
 */
public class Wall extends Thing{

	private double x;
	private double y;
	private double width;
	private double height;
	RectangleHit hit;
	
	Wall (double x, double y, double width, double height, RectangleHit hit){
		super(1);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.hit = hit;
	}
	
	Wall (double x, double y, double width, double height){
		super(1);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		hit = new RectangleHit(x,y,width,height);
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

	double getWidth() {
		return width;
	}

	void setWidth(double width) {
		this.width = width;
	}

	double getHeight() {
		return height;
	}

	void setHeight(double height) {
		this.height = height;
	}

}

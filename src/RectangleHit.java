
public class RectangleHit extends Hitbox {
	
	private int materialID;
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double width;
	private double height;
	private double angle;
	
	RectangleHit (double x1, double y1, double width, double height){
		this.setX1(x1);
		this.setX2(x2);
		this.setWidth(width);
		this.setHeight(height);
		this.setX2(x1 + width);
		this.setY2(y1 + height);
	}

	double getX1() {
		return x1;
	}

	void setX1(double x) {
		this.x1 = x1;
	}

	double getY1() {
		return y1;
	}

	void setY1(double y) {
		this.y1 = y1;
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

	double getAngle() {
		return angle;
	}

	void setAngle(double angle) {
		this.angle = angle;
	}

	private int getMaterialID() {
		return materialID;
	}

	private void setMaterialID(int materialID) {
		this.materialID = materialID;
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

}

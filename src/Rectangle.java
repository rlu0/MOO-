
public class Rectangle extends Hitbox {
	
	private int materialID;
	private double x;
	private double y;
	private double width;
	private double height;
	private double angle;
	
	Rectangle (int materialID, double x, double y, double width, double height, double angle){
		super(materialID);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
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

}

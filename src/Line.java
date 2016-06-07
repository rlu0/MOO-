
public class Line extends Hitbox{
	
	private double x;
	private double y;
	private double angle;
	
	Line (int materialID, double x, double y, double angle){
		super(materialID);
		this.x = x;
		this.y = y;
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

	double getAngle() {
		return angle;
	}

	void setAngle(double angle) {
		this.angle = angle;
	}
	
	
	
	
}

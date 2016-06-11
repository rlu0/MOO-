
public class Vector {
	
	double x;
	double y;
	double length;
	double angle;
	
	Vector (double xLength, double yAngle, boolean isComponentMode){
		if (isComponentMode){ // x,y component input mode
			this.x = xLength;
			this.y = yAngle;
			calcLengthAngle();
		}
		else { // length/angle input mode
			this.length = xLength;
			setAngle(yAngle);
			calcComponents();
		}
	}
	
	void calcLengthAngle(){
		length = Math.sqrt(x*x + y*y);
		setAngle( Math.tan(y/x));
	}
	
	void calcComponents(){
		this.x = Math.cos(angle) * length;
		this.y = Math.sin(angle) * length;
		if (angle > Math.PI){
			y *= -1;
			x *= -1;
		}
	}
	
	void setX(double x){
		this.x = x;
	}
	
	void setY(double y){
		this.y = y;
	}
	
	double getX(){
		return this.x;
	}
	
	double getY(){
		return this.y;
	}
	
	void setAngle(double angle){
		this.angle = angle;
		while (this.angle < 0){
			this.angle += Math.PI*2;
		}
		while (this.angle > Math.PI*2){
			this.angle -= Math.PI*2;
		}
	}
	
	Vector add(Vector a, Vector b){
		return new Vector(a.x+b.x, a.y+b.y, true);
	}
	

}

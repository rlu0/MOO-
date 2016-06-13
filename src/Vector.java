
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
		double newAngle = 0;
		if (x==0 && y==0){
			newAngle = 0;
			setAngle(newAngle);
			return;
		}
		else if (x==0){
			if (y>0)
				newAngle = Math.PI/2;
			else if (y<0)
				newAngle = (Math.PI*3)/2;
			setAngle(newAngle);
			return;
		}
		else if (y==0){
			if (x>0)
				newAngle = 0;
			else if (x<0)
				newAngle = Math.PI;
			setAngle(newAngle);
			return;
		}
		
		newAngle = Math.atan(y/x);
		
		if(x > 0){
			if(y > 0){
				//nothing
			}
			if(y < 0){
				newAngle += Math.PI*2;
			}
		}
		else if (x < 0){
			if(y > 0){
				newAngle += Math.PI;
			}
			if(y < 0){
				newAngle += Math.PI;
			}
		}
		
		setAngle(newAngle);
	}
	
	void calcComponents(){
		x = Math.cos(angle) * length;
		y = Math.sin(angle) * length;
		if (angle >= Math.PI){
			x *= -1;
			y *= -1;
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
		while (this.angle >= Math.PI*2){
			this.angle -= Math.PI*2;
		}
	}
	
	void addComponents(Vector a){
		setX(getX() + a.x);
		setY(getY() + a.y);
		calcLengthAngle();
	}
	
	//void addLengthAngle(){
	//	setLength();
	//}
	

}

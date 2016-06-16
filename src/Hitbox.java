
public class Hitbox {


	Hitbox() {
	}
	
	// Rectangle Rectangle Collision (unused)
	boolean RRIntersect(RectangleHit a, RectangleHit b){
		
		if ((a.getX2()<b.getX1() || b.getX2()<a.getX1()
				|| a.getY2()<b.getY1() || b.getY2()<a.getY1())){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Detects collision between rectangle and circle
	 * @param a Hitbox of rectangle
	 * @param b Hitbox of circle
	 * @return Point of intersection [0]:x [1]:y
	 */
	double [] RCIntersect(RectangleHit a, CircleHit b){
		
		double [] coord = new double [2];
		coord[0] = Double.MAX_VALUE;
		coord[1] = Double.MAX_VALUE;
		
		
		// Detect rectangle edge collisions
		if (b.getX()>a.getX1() && b.getX()<a.getX2() && b.getY()>a.getY1()-b.getR() && b.getY()<a.getY1()){
			coord[0] = b.getX();
			coord[1] = a.getY1();
			return coord;
		}
		if (b.getX()>a.getX1() && b.getX()<a.getX2() && b.getY()>a.getY2() && b.getY()<a.getY2()+b.getR()){
			coord[0] = b.getX();
			coord[1] = a.getY2();
			return coord;
		}
		if (b.getY()>a.getY1() && b.getY()<a.getY2() && b.getX()>a.getX1()-b.getR() && b.getX()<a.getX1()){
			coord[0] = a.getX1();
			coord[1] = b.getY();
			return coord;
		}
		if (b.getY()>a.getY1() && b.getY()<a.getY2() && b.getX()>a.getX2() && b.getX()<a.getX2()+b.getR()){
			coord[0] = a.getX2();
			coord[1] = b.getY();
			return coord;
		}

		// Detect rectangle vertex collisions
		if (Math.pow(a.getX1()-b.getX(), 2) + Math.pow(a.getY1()-b.getY(), 2)<b.getR()*b.getR()){
			coord[0] = a.getX1();
			coord[1] = a.getY1();
			return coord;
		}
		if (Math.pow(a.getX1()-b.getX(), 2) + Math.pow(a.getY2()-b.getY(), 2)<b.getR()*b.getR()){
			coord[0] = a.getX1();
			coord[1] = a.getY2();
			return coord;
		}
		if (Math.pow(a.getX2()-b.getX(), 2) + Math.pow(a.getY1()-b.getY(), 2)<b.getR()*b.getR()){
			coord[0] = a.getX2();
			coord[1] = a.getY1();
			return coord;
		}
		if (Math.pow(a.getX2()-b.getX(), 2) + Math.pow(a.getY2()-b.getY(), 2)<b.getR()*b.getR()){
			coord[0] = a.getX2();
			coord[1] = a.getY2();
			return coord;
		}
		
		return coord;
	}
	

	/**
	 * Finds intersection point of two line segments
	 * @param x1 x1 of line 1
	 * @param y1 y1 of line 1
	 * @param x2 x2 of line 2
	 * @param y2 y2 of line 2
	 * @param x3 x1 of line 2
	 * @param y3 y1 of line 2
	 * @param x4 x2 of line 2
	 * @param yy y2 of line 2
	 * @return Point of intersection [0]:x [1]:y
	 */
	double []  lineIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double [] coord = new double [2];
		coord [0] = Double.MAX_VALUE;
		coord [1] = Double.MAX_VALUE;
		
		double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (denom == 0.0) {
			// Lines are parallel
			return coord;
		}
		
		double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))/denom;
		double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))/denom;
		if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
			// Get the intersection point
			coord[0] = x1 + ua*(x2 - x1);
			coord[1] = y1 + ua*(y2 - y1);
			return coord;
		}
		return coord;
	}
	
	/**
	 * Finds intersection point of a line segment and rectangle
	 * @param a Hitbox of rectangle
	 * @param b Hitbox of line
	 * @return Point of intersection [0]:x [1]:y
	 */
	double[] RLIntersect(RectangleHit a, Line b){
		
		double [] intersectFinal = new double[2];
		intersectFinal[0] = Double.MAX_VALUE;
		intersectFinal[1] = Double.MAX_VALUE;
		
		double [] intersectsX = new double [4];
		double [] intersectsY = new double [4];
		
		for (int i=0; i<4; i++){
			intersectsX[i] = Double.MAX_VALUE;
			intersectsY[i] = Double.MAX_VALUE;
		}
		
		double [] coord = new double [2];
		
		// Rectangle Top
		coord = lineIntersect(b.getX1(), b.getY1(), b.getX2(), b.getY2(), a.getX1(), a.getY1(), a.getX2(), a.getY1());
		intersectsX[0] = coord[0];
		intersectsY[0] = coord[1];
		
		// Rectangle Bottom
		coord = lineIntersect(b.getX1(), b.getY1(), b.getX2(), b.getY2(), a.getX1(), a.getY2(), a.getX2(), a.getY2());
		intersectsX[1] = coord[0];
		intersectsY[1] = coord[1];
		
		// Rectangle  Left
		coord = lineIntersect(b.getX1(), b.getY1(), b.getX2(), b.getY2(), a.getX1(), a.getY1(), a.getX1(), a.getY2());
		intersectsX[2] = coord[0];
		intersectsY[2] = coord[1];
		
		// Rectangle  Right
		coord = lineIntersect(b.getX1(), b.getY1(), b.getX2(), b.getY2(), a.getX2(), a.getY1(), a.getX2(), a.getY2());
		intersectsX[3] = coord[0];
		intersectsY[3] = coord[1];
		
		
		// Find closest intersection point
		double minDist = Double.MAX_VALUE;
		int minDistIndex = -1;
		
		for (int i=0; i<4; i++){
			
			if (intersectsX[i] == Double.MAX_VALUE && intersectsY[i] == Double.MAX_VALUE){
				continue;
			}
			
			double currentDist = Math.pow(intersectsX[i]-b.getX1(), 2) + Math.pow(intersectsY[i]-b.getY1(), 2);
			if (currentDist < minDist){
				minDist = currentDist;
				minDistIndex = i;
			}
		}
		
		if (minDistIndex != -1){
			intersectFinal[0] = intersectsX[minDistIndex];
			intersectFinal[1] = intersectsY[minDistIndex];
		}
		
		return intersectFinal;
		
	}
	
	/**
	 * Finds intersection point of two circles
	 * @param a Hitbox of circle a
	 * @param b Hitbox of circle b
	 * @return Point of intersection [0]:x [1]:y
	 */
	double[] CCIntersect(CircleHit a, CircleHit b){
		
		double [] intersectFinal = new double[2];
		intersectFinal[0] = Double.MAX_VALUE; // X coordinate
		intersectFinal[1] = Double.MAX_VALUE; // Y coordinate
		
		if (Math.sqrt(Math.pow(b.getX()-a.getX(), 2) + Math.pow(b.getY()-a.getY(), 2)) < a.getR() + b.getR()){
			intersectFinal[0] = (a.getX()*b.getR()+b.getX()*a.getR())/(a.getR()+b.getR());
			intersectFinal[1] = (a.getY()*b.getR()+b.getY()*a.getR())/(a.getR()+b.getR());
		}
		return intersectFinal;
		
	}
	
	/**
	 * Determine distance between the point and the line
	 * @param x1 x1 of line
	 * @param y1 y1 of line
	 * @param x2 x2 of line
	 * @param y2 y2 of line
	 * @param x3 x of point
	 * @param y3 y of point
	 * @return Distance
	 */
	double pointLineDist(double x1, double y1, double x2,double y2, double x3, double y3){
		double px = x2-x1;
		double py = y2-y1;
		
		double dot = px*px + py*py;
		
		double u =  ((x3 - x1) * px + (y3 - y1) * py) / dot;
		
		if( u > 1)
			u = 1;
		else if( u < 0)
			u = 0;
		
		double x = x1 + u * px;
		double y = y1 + u * py;
		
		double dx = x - x3;
		double dy = y - y3;
		
		double dist = Math.sqrt(dx*dx + dy*dy);
		
		return dist;
	}
	
	/**
	 * Detects intersection of a circle and a line
	 * @param a hitbox of circle
	 * @param b hitbox of line
	 * @return Whether circle intersects with line
	 */
	boolean CLIntersect(CircleHit a, Line b){
		
		// distance between line and center of circle
		double distance = pointLineDist(b.getX1(), b.getY1(), b.getX2(), b.getY2(), a.getX(), a.getY());
		
		if (distance < a.getR()){
			return true;
		}
		
		return false;
	}

}

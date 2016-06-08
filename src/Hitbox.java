
public class Hitbox {


	Hitbox() {
	}

	boolean isIntersect(Hitbox a, Hitbox b) {
		if (a instanceof Rectangle) {
			if (b instanceof Rectangle) {
				return RRIntersect((Rectangle)a,(Rectangle)b);
			}
			if (b instanceof Circle) {
				return RCIntersect((Rectangle)a,(Circle)b);
			}
			if (b instanceof Line) {
				return RLIntersect((Rectangle)a,(Line)b);
			}
		}
		if (a instanceof Circle) {
			if (b instanceof Rectangle) {
				return RCIntersect((Rectangle)b, (Circle)a);
			}
			if (b instanceof Circle) {
				return CCIntersect((Circle)a, (Circle)b);
			}
			if (b instanceof Line) {
				return CLIntersect((Circle)a, (Line)b);
			}
		}
		if (a instanceof Line) {
			if (b instanceof Rectangle) {
				return RLIntersect((Rectangle)b, (Line)a);
			}
			if (b instanceof Circle) {
				return CLIntersect((Circle)b, (Line)a);
			}
			if (b instanceof Line) {
				return LLIntersect((Line)a, (Line)b);
			}
		}

		return false;

	}
	
	/*boolean isPolygonsIntersecting(Rectangle a, Rectangle b)
	{
	    for (int x=0; x<2; x++)
	    {
	        Rectangle polygon = (x==0) ? a : b;

	        for (int i1=0; i1<4; i1++)
	        {
	            int i2 = (i1 + 1) % 4;
	            Point p1 = new Point(polygon.getX(), polygon.getY());
	            Point p2 = new Point(polygon.getX(), polygon.getY());

	            Point normal = new Point(p2.y - p1.y, p1.x - p2.x);

	            double minA = Double.POSITIVE_INFINITY;
	            double maxA = Double.NEGATIVE_INFINITY;

	            for (Point p : a.getPoints())
	            {
	                double projected = normal.x * p.x + normal.y * p.y;

	                if (projected < minA)
	                    minA = projected;
	                if (projected > maxA)
	                    maxA = projected;
	            }

	            double minB = Double.POSITIVE_INFINITY;
	            double maxB = Double.NEGATIVE_INFINITY;

	            for (Point p : b.getPoints())
	            {
	                double projected = normal.x * p.x + normal.y * p.y;

	                if (projected < minB)
	                    minB = projected;
	                if (projected > maxB)
	                    maxB = projected;
	            }

	            if (maxA < minB || maxB < minA)
	                return false;
	        }
	    }

	    return true;
	}*/
	
	private boolean RRIntersect(Rectangle a, Rectangle b){
		
		if ((a.getX2()<b.getX1() || b.getX2()<a.getX1()
				|| a.getY2()<b.getY1() || b.getY2()<a.getY1())){
			return true;
		}
		
		return false;
		
	}
	private boolean RCIntersect(Rectangle a, Circle b){
		
		if ((b.getX()>a.getX1() && b.getX()<a.getX2() && 
			(b.getY()+b.getR()>a.getY1() || b.getY()-b.getR()<a.getY2())) ||
			(b.getY()>a.getY1() && b.getY()<a.getY2()&&
			(b.getX()-b.getR()<a.getX2() || b.getX()+b.getR()>a.getX1()))
			){
			return true;
		}
		
		if (Math.sqrt(Math.pow(a.getX1()-b.getX(), 2) + Math.pow(a.getY1()-b.getY(), 2))<b.getR() ||
			Math.sqrt(Math.pow(a.getX2()-b.getX(), 2) + Math.pow(a.getY1()-b.getY(), 2))<b.getR() ||
			Math.sqrt(Math.pow(a.getX2()-b.getX(), 2) + Math.pow(a.getY2()-b.getY(), 2))<b.getR() ||
			Math.sqrt(Math.pow(a.getX1()-b.getX(), 2) + Math.pow(a.getY2()-b.getY(), 2))<b.getR()){
			return true;
		}
		
		return false;
		
	}
	boolean RLIntersect(Rectangle a, Line b){
		
		double [] intersectsX = new double [4];
		double [] intersectsY = new double [4];
		
		for (int i=0; i<4; i++){
			intersectsX[i] = Double.MAX_VALUE;
			intersectsY[i] = Double.MAX_VALUE;
		}
		
		double lineA = b.getY2() - b.getY1();
		double lineB = b.getX1() - b.getX2();
		double lineC = lineA * b.getX1() + lineB * b.getY1();
		
		double upA = 0;
		double upB = a.getX1()-a.getX2();
		double upC = upB * a.getY1();
		
		double det = lineA*upB - upA*lineB;
		if(det == 0){
			//Lines are parallel
		}else{
			double x = (upB*lineC - lineB*upC)/det;
			double y = (lineA*upC - upA*lineC)/det;
			if (x>a.getX1() && x<a.getX2()){
				intersectsX[0] = x;
				intersectsY[0] = y;
			}
		}
		
		double downA = 0;
		double downB = a.getX1()-a.getX2();
		double downC = downB * a.getY2();
		
		det = lineA*upB - upA*lineB;
		if(det == 0){
			//Lines are parallel
		}else{
			double x = (downB*lineC - lineB*downC)/det;
			double y = (lineA*downC - downA*lineC)/det;
			if (x>a.getX1() && x<a.getX2()){
				intersectsX[1] = x;
				intersectsY[1] = y;
			}
		}
		
		double leftA = a.getY2()-a.getY1();
		double leftB = 0;
		double leftC = leftA * a.getX1();
		
		det = lineA*leftB - leftA*lineB;
		if(det == 0){
			//Lines are parallel
		}else{
			double x = (leftB*lineC - lineB*leftC)/det;
			double y = (lineA*leftC - leftA*lineC)/det;
			if (y>a.getY1() && y<a.getY2()){
				intersectsX[2] = x;
				intersectsY[2] = y;
			}
		}
		
		double rightA = a.getY2()-a.getY1();
		double rightB = 0;
		double rightC = leftA * a.getX2();
		
		det = rightA*leftB - rightA*lineB;
		if(det == 0){
			//Lines are parallel
		}else{
			double x = (rightB*lineC - lineB*rightC)/det;
			double y = (lineA*rightC - rightA*lineC)/det;
			if (y>a.getY1() && y<a.getY2()){
				intersectsX[3] = x;
				intersectsY[3] = y;
			}
		}
		
		double minDist = Double.MAX_VALUE;
		double minDistIndex = 0;
		
		for (int i=0; i<4; i++){
			if (intersectsX[i] == Double.MAX_VALUE || intersectsY[i] == Double.MAX_VALUE){
				break;
			}
			double currentDist = Math.sqrt(Math.pow(intersectsX[i]-b.getX1(), 2) + Math.pow(intersectsY[i]-b.getY1(), 2));
			if (currentDist < minDist){
				minDist = currentDist;
				minDistIndex = i;
			}
		}
		
		if (minDist != Double.MAX_VALUE){
			return true;
		}
		
		
		
	}
	
	
	private boolean CCIntersect(Circle a, Circle b){
		if (Math.sqrt(Math.pow(b.getX()-a.getX(), 2) + Math.pow(b.getY()-a.getY(), 2)) < a.getR() + b.getR()){
			return true;
		}
		return false;
		
	}
	private boolean CLIntersect(Circle a, Line b){
		return false;
		
	}
	private boolean LLIntersect(Line a, Line b){
		return false;
		
	}

}

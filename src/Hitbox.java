
public class Hitbox {

	int materialID;

	Hitbox(int materialID) {
		this.materialID = materialID;
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
		
		if ((a.getX()+a.getWidth()<b.getX() || b.getX()+b.getWidth()<a.getX()
				|| a.getY()+a.getHeight()<b.getY() || b.getY()+b.getHeight()<a.getY())){
			return true;
		}
		
		return false;
		
	}
	private boolean RCIntersect(Rectangle a, Circle b){
		
		if ((b.getX()>a.getX() && b.getX()<a.getX()+a.getWidth() && 
			(b.getY()+b.getR()>a.getY() || b.getY()-b.getR()<a.getY()+a.getHeight())) ||
			(b.getY()>a.getY() && b.getY()<a.getY()+a.getHeight() &&
			(b.getX()-b.getR()<a.getX()+a.getWidth() || b.getX()+b.getR()>a.getX()))
			){
			return true;
		}
		
		if (Math.sqrt(Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY()-b.getY(), 2))<b.getR() ||
			Math.sqrt(Math.pow(a.getX()+a.getWidth()-b.getX(), 2) + Math.pow(a.getY()-b.getY(), 2))<b.getR() ||
			Math.sqrt(Math.pow(a.getX()+a.getWidth()-b.getX(), 2) + Math.pow(a.getY()+a.getHeight()-b.getY(), 2))<b.getR() ||
			Math.sqrt(Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY()+a.getHeight()-b.getY(), 2))<b.getR()){
			return true;
		}
		
		return false;
		
	}
	private boolean RLIntersect(Rectangle a, Line b){
		return false;
		
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

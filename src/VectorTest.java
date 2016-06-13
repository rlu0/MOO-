
public class VectorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector a = new Vector (3,-2,true);
		System.out.println(a.length + " " + Math.toDegrees(a.angle));
		System.out.println(a.x + " " + a.y);
		Vector b = new Vector (1,1, true);
		Vector c = new Vector (-4,1, true);
		c.add(b);
		System.out.println(c.length + " " + Math.toDegrees(c.angle));
		System.out.println(c.x + " " + c.y);
		
		
	}

}

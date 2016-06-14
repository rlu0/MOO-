
public class VectorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector a = new Vector (1,-Math.PI/4,false);
		System.out.println(a.length + " " + Math.toDegrees(a.angle));
		System.out.println(a.x + " " + a.y);
		Vector b = new Vector (1,Math.PI/2, false);
		Vector c = new Vector (1,b.angle, false);
		c.addComponents(b);
		System.out.println();
		System.out.println(c.length + " " + Math.toDegrees(c.angle));
		System.out.println(c.x + " " + c.y);
		
		
	}

}

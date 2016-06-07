
public class Wall extends Thing{

	double x, y, width, height;
	
	Wall (double x, double y, double width, double height, Hitbox hit){
		super(1,hit);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}

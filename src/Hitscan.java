
public class Hitscan {

	Player shooter;
	double range;
	Vector vector;
	Line hit;
	int framesLeft;
	
	Hitscan(Player shooter, double range, int framesLeft){
		this.shooter = shooter;
		this.range = range;
		vector = new Vector (this.range, this.shooter.direction, false);
		hit = new Line(this.shooter.getX(), this.shooter.getY(), this.shooter.getX() + this.vector.getX(), this.shooter.getY() + this.vector.getY());
	}
	
	void update(){
		vector = new Vector (this.range, this.shooter.direction, false);
		hit = new Line(this.shooter.getX(), this.shooter.getY(), this.shooter.getX() + this.vector.getX(), this.shooter.getY() + this.vector.getY());
	}
	

}

/**
 * Hitscan projectile class
 * @author Tony
 * @version 2016-06-15
 */
public class Hitscan {

	Player shooter;
	double range;
	Vector vector;
	Line hit;
	int framesLeft;
	
	/**
	 * Constructor given shooter player, range of weapon, and length of existence
	 * @param shooter The origin player
	 * @param range The max range of projectile
	 * @param framesLeft Number of frames left to exist
	 */
	Hitscan(Player shooter, double range, int framesLeft){
		this.shooter = shooter;
		this.range = range;
		this.framesLeft = framesLeft;
		vector = new Vector (this.range, this.shooter.direction, false);
		hit = new Line(this.shooter.getX(), this.shooter.getY(), this.shooter.getX() + this.vector.getX(), this.shooter.getY() + this.vector.getY());
	}
	
	/**
	 * Update variables from shooter player
	 */
	void update(){
		vector = new Vector (this.range, this.shooter.direction, false);
		hit = new Line(this.shooter.getX(), this.shooter.getY(), this.shooter.getX() + this.vector.getX(), this.shooter.getY() + this.vector.getY());
	}
	

}

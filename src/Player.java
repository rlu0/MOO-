import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javafx.scene.shape.Circle;

public class Player extends Thing {
private double x;
private double y;
private double RADIUS = 2.5;
private int hp;
private double direction;
final double START_DIRECTION= 0; 
Weapon [] weapons= new Weapon[10];




final int START_HP=100;

Player(double sx, double sy, Hitbox hit){
	super(, hit);
	hp=START_HP;
	x=sx;
	y=sy;
	direction = START_DIRECTION;
}

void shoot(int i){
	

	
}


double getX(){
	return x;
}
void setX(int nx){
	x=nx;
}
double getY(){
	return y;
}
void setY(int ny){
	y=ny;
}
int getHP(){
	return hp;
}
void setHP(int nHP){
	hp=nHP;
}
void addHP(int increment){
	hp+=increment;
}
void addWeapon(int type, int ammo){

}
void turnLeft(){
	if (direction==0)
	{
		direction = 2*Math.PI -.25;
	}
	else
	{
		direction-=.25;
	}
}
void turnRight(){
	direction +=10;
	if (direction==2*Math.PI)
	{
		direction =.25;
	}
	else
	{
		direction+=.25;
	}
}

}


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
private int id;
final double START_DIRECTION= 0; 
Weapon [] weapons= new Weapon[10];

CircleHit hit;


final int START_HP=100;

Player(double x, double y, CircleHit hit){
	super(100);
	hp=START_HP;
	this.x=x;
	this.y=y;
	setDirection(START_DIRECTION);
	this.hit = hit;
}

Player(double x, double y){
	super(100);
	hp=START_HP;
	this.x=x;
	this.y=y;
	setDirection(START_DIRECTION);
	this.hit = new CircleHit(x,y,0.5);
}

void shoot(int i){
	
	
	
}


double getX(){
	return x;
}
void setX(int x){
	this.x = x;
	hit.setX(x);
}
double getY(){
	return y;
}
void setY(int y){
	this.y = y;
	hit.setY(y);
}
int getHP(){
	return hp;
}
void setHP(int hp){
	this.hp = hp;
}
void addHP(int increment){
	hp+=increment;
}
void addWeapon(int type, int ammo){

}
void turnLeft(){
	if (getDirection()==0)
	{
		setDirection(2*Math.PI -.25);
	}
	else
	{
		setDirection(getDirection() - .25);
	}
}
void turnRight(){
	setDirection(getDirection() + 10);
	if (getDirection()==2*Math.PI)
	{
		setDirection(.25);
	}
	else
	{
		setDirection(getDirection() + .25);
	}
}

double getDirection() {
	return direction;
}

void setDirection(double direction) {
	this.direction = direction;
}

}


import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Player{
private int x;
private int y;
private int hp;
private double direction;
final double START_DIRECTION= 0; 
int [] weapons= new int[10];

final int BOOT_DAMAGE=34; final Rectangle BOOT_RANGE = new Rectangle(x,y,30,30);
final int PISTOL_DAMAGE=10; final Rectangle PISTOL_RANGE = new Rectangle(x,y,10,55555);
final int SHOTGUN_DAMAGE= 50; final Polygon SHOTGUN_RANGE = new Polygon( new int[]{x,x+50,x-50}, new int[]{y,y+50,y-50},3 ); final int SUPER_SHOTGUN_DMG = 140;
final int ASSAULT_RIFLE_DMG= 15; final Rectangle ASSAULT_RIFLE_RANGE = new Rectangle(x,y,10,55555);
final int CHAINGUN_DMG= 20; final Rectangle CHAINGUN_RANGE = new Rectangle(x,y,10,55555);
final int POTATO_GUN_DMG=1; final Rectangle POTATO_RANGE = new Rectangle(x,y,10,55555);
final int MANGATGUN_DMG =9999; final Rectangle MANGAT_RANGE = new Rectangle(0,0,55555,55555);
final int PLASMA_RIFLE_DMG = 25; final Rectangle PLASMA_RANGE = new Rectangle(x,y,20,55555);


final int START_HP=100;

Player(int sx, int sy){
	hp=START_HP;
	x=sx;
	y=sy;
	direction = START_DIRECTION;
}

void shoot(int i){
	
	range(i);
	dmg(i);
	
}
Rectangle range(int i){
	AffineTransform transform = new AffineTransform();
	transform.rotate(direction);
return BOOT_RANGE;
}

int dmg(int i){
	
	return 100; 
			
}

int getX(){
	return x;
}
void setX(int nx){
	x=nx;
}
int getY(){
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
	weapons[type]=ammo;
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


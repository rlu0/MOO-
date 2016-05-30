
public class Player {
private int x;
private int y;
private int hp;
private double direction;
int [] weapons; 
final int START_HP=666;

Player(int sx, int sy){
	hp=START_HP;
	x=sx;
	y=sy;

}

void getX(){
	
}
void setX(int nx){
	x=nx;
}
void getY(){
	
}
void setY(int ny){
	y=ny;
}
void getHP(){
	
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

}


import java.awt.Polygon;
import java.awt.Rectangle;

public class InstantFireWeapons extends Weapon {

	final int BOOT_DAMAGE=34; final Rectangle BOOT_RANGE = new Rectangle(x,y,30,30); final int BOOT_TYPE=0;
	final int PISTOL_DAMAGE=10; final Rectangle PISTOL_RANGE = new Rectangle(x,y,10,55555); final int PISTOL_TYPE =2;
	final int SHOTGUN_DAMAGE= 50; final Polygon SHOTGUN_RANGE = new Polygon( new int[]{x,x+50,x-50}, new int[]{y,y+50,y-50},3 ); final int SUPER_SHOTGUN_DMG = 140; final int SG_TYPE=3; final int SSG_TYPE=3; 
	final int ASSAULT_RIFLE_DMG= 15; final Rectangle ASSAULT_RIFLE_RANGE = new Rectangle(x,y,10,55555); final int AR_TYPE=4;
	final int CHAINGUN_DMG= 20; final Rectangle CHAINGUN_RANGE = new Rectangle(x,y,10,55555); final int CG_TYPE=5;
	final int FLAME_THROWER_DMG = 15; final Polygon FLAME_RANGE = new Polygon( new int[]{x,x+50,x-50}, new int[]{y,y+50,y-50},3 ); final int BURN_DOT = 10; final int FTHRW_TYPE =9;
	final int MANGATGUN_DMG =9999; final Rectangle MANGAT_RANGE = new Rectangle(0,0,55555,55555); final int MANGAT_TYPE =7;
	

	InstantFireWeapons(int type, int ammo) {
		super(type, ammo);
		
	}
}

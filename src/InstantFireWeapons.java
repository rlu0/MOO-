import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import javafx.scene.shape.Circle;

public class InstantFireWeapons extends Weapon {
	final int BOOT_DMG = 34;
	//final Rectangle BOOT_RANGE;
	final int BOOT_TYPE = 0;
	final int PISTOL_DMG = 10;
	//final Rectangle PISTOL_RANGE;
	final int PISTOL_TYPE = 1;
	final int SHOTGUN_DMG = 50;
	//final Polygon SHOTGUN_RANGE = 0;
	final int SUPER_SHOTGUN_DMG = 140;
	final int SG_TYPE = 2;
	final int SSG_TYPE = 3;
	final int ASSAULT_RIFLE_DMG = 15;
	//final Rectangle ASSAULT_RIFLE_RANGE;
	final int AR_TYPE = 4;
	final int CHAINGUN_DMG = 20;
	//final Rectangle CHAINGUN_RANGE;
	final int CG_TYPE = 5;
	final int FLAME_THROWER_DMG = 10;
	//final Polygon FLAME_RANGE;
	final int BURN_DOT = 10;
	final int FTHRW_TYPE = 6;
	final int MANGATGUN_DMG = 9999;
	//final Rectangle MANGAT_RANGE;
	final int MANGAT_TYPE = 7;

	int[] dmgArray = { BOOT_DMG, PISTOL_DMG, SHOTGUN_DMG, SUPER_SHOTGUN_DMG, ASSAULT_RIFLE_DMG, CHAINGUN_DMG,
			FLAME_THROWER_DMG, MANGATGUN_DMG };


	public static Polygon rotate(Polygon base, double angle, double x, double y) {
		if (base == null) {
			return null;
		}
		// Rotates around a point
		AffineTransform rotate = AffineTransform.getRotateInstance(angle, x, y);

		Polygon result = (Polygon) rotate.createTransformedShape(base);
		return result;
	}

	InstantFireWeapons(int type, int ammo) {
		super(type, ammo);

	}

	public boolean intersects(Polygon a, Circle b)
	{
		 Area areaA = new Area(a);
		   areaA.intersect(new Area((Shape) b));
		   return !areaA.isEmpty();
	}
	
	int getDmg() {

		return dmgArray[type];
	}


//	Polygon triangle() {
//
//		return FLAME_RANGE;
//	}

//	boolean hitScan( double direction, int eX, int eY, Circle eR) {
//
//		if (type != 3 || type != 4 || type != 7) {
//			Rectangle range = rotate(rekt(), x, y, direction);
//		} else {
//			Polygon range = rotate(triangle(), x, y, direction);
//		}
//		
//		//if ()
//
//		return false;
//
//	}

}

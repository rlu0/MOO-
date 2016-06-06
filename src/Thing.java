public class Thing {
	Hitbox hit;
	
	int materialId;
	
	Thing(int materialId, Hitbox hit){
		this.materialId = materialId;
		this.hit = hit;
	}
	
	/*Material ID index
	0 = air
	1 = wall
	100 = player
	*/
	
	
}

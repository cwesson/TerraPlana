package terraplana.Projectile;

import terraplana.Board;
import terraplana.Direction;
import terraplana.Actor.Actor;

public class SlimeBall extends Projectile {
	public SlimeBall(Board brd, Direction dir){
		super(brd, dir);
		setInterval(200);
		setRange(5);
	}
	
	public boolean onConflict(Actor act){
		if(act.isPlayer()){
			act.setHealth(-25);
		}
		return true;
	}
}

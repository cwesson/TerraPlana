package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Projectile.Projectile;

public class Glass extends Landscape {
	private final int MAX_HEALTH;
	private int health;

	public Glass(Tile place, String[] args){
		super(place);
		if(args.length > 0) {
			MAX_HEALTH = Integer.parseInt(args[0]);
		}else{
			MAX_HEALTH = 6;
		}
		health = MAX_HEALTH;
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		if(health > 0){
			return false;
		}else{
			return super.onEnter(actor, dir);
		}
	}

	@Override
	public boolean onEntered(Projectile proj, Direction dir, Tile last){
		super.onEntered(proj, dir, last);
		if(health > 0){
			--health;
		}
		return false;
	}
	
	@Override
	public boolean onExit(Projectile proj, Direction dir, Tile next){
		super.onExit(proj, dir, next);
		tile.getBoard().removeProjectile(proj);
		return false;
	}

	@Override
	public int spriteX(){
		return MAX_HEALTH-health;
	}
}

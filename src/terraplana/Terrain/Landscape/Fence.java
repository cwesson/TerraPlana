package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Projectile.Projectile;

public class Fence extends Landscape {

	public Fence(Tile place, String[] args) {
		super(place);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		return false;
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		super.onEntered(actor, dir, last);
		return false;
	}
	
	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		super.onExit(actor, dir, next);
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		super.onExited(actor, dir, next);
		return true;
	}

	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		return false;
	}

	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		super.onEntered(move, dir, last);
		return false;
	}

	@Override
	public boolean onExit(Movable move, Direction dir, Tile next){
		super.onExit(move, dir, next);
		return true;
	}

	@Override
	public boolean onExited(Movable move, Direction dir, Tile next){
		super.onExited(move, dir, next);
		return true;
	}

	public boolean onEnter(Projectile proj, Direction dir){
		super.onEnter(proj, dir);
		return true;
	}

	public boolean onEntered(Projectile proj, Direction dir, Tile last){
		super.onEntered(proj, dir, last);
		return true;
	}

	public boolean onExit(Projectile proj, Direction dir, Tile next){
		super.onExit(proj, dir, next);
		return true;
	}

	public boolean onExited(Projectile proj, Direction dir, Tile next){
		super.onExited(proj, dir, next);
		return true;
	}

}

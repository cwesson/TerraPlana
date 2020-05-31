/**
 * @file Fire.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Fire extends Landscape {
	public Fire(Tile place, String[] args) {
		super(place);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return super.onEnter(actor, dir);
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		super.onEnter(actor, dir);
		actor.setHealth(-100);
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return super.onEnter(move, dir);
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		super.onEnter(move, dir);
		tile.removeMovable();
		return false;
	}
}

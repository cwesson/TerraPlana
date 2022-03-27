/**
 * @file TrapdoorOpen.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class DoorOpen extends Landscape {
	public DoorOpen(Tile place, String[] args){
		super(place);
	}
	
	public DoorOpen(Tile place){
		super(place);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return super.onEnter(actor, dir);
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return super.onEnter(move, dir);
	}
}

/**
 * @file Trapdoor.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Trapdoor extends Landscape {
	public Trapdoor(Tile place, String[] args){
		super(place);
	}
	
	public Trapdoor(Tile place){
		super(place);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return false;
	}
}

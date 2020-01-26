/**
 * @file Cactus.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Cactus extends Landscape {
	public Cactus(Tile place) {
		super(place);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		if(actor.isPlayer()){
			actor.setHealth(-5);
		}
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		return false;
	}
}

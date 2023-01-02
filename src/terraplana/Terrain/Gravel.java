/**
 * @file Gravel.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Gravel extends Terrain{

	public Gravel(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return !actor.isPlayer();
	}

	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}
	
	public String toString(){
		return ":";
	}
}

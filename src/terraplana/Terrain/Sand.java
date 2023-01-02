/**
 * @file Sand.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Sand extends Terrain{

	public Sand(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return actor.hasAttribute("movement.walk");
	}

	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer() && !actor.hasAttribute("movement.sand.safe")){
			actor.setHealth(-1);
		}
		return true;
	}

	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return true;
	}
	
	@Override
	public Direction onExit(Movable move, Direction dir, Tile next){
		return Direction.NONE;
	}
	
	public String toString(){
		return ".";
	}
}

/**
 * @file Star.java
 * @author Conlan Wesson
 */

package terraplana.mod.space.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Star extends Terrain {
	public Star(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return true;
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		player.setHealth(-100);
		return false;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return false;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		return false;
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
		return "*";
	}

}

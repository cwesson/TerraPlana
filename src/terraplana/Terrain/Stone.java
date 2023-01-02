/**
 * @file Stone.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Stone extends Terrain{

	public Stone(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return player.hasAttribute("movement.climb.safe");
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return false;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return true;
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
		return dir;
	}
	
	public String toString(){
		return "&";
	}
}

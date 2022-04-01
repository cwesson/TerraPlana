/**
 * @file Igloo.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Debug;
import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Corner extends Landscape {
	private int corner;
	public Corner(Tile place, String[] args) {
		super(place);
		if(args[0].equals("south-east")){
			corner = 0;
		}else if(args[0].equals("south-west")){
			corner = 1;
		}else if(args[0].equals("north-west")){
			corner = 2;
		}else if(args[0].equals("north-east")){
			corner = 3;
		}
		Debug.error("direction "+corner);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		switch(corner){
			case 0:
				return (dir == Direction.NORTH || dir == Direction.WEST);
			case 1:
				return (dir == Direction.NORTH || dir == Direction.EAST);
			case 2:
				return (dir == Direction.SOUTH || dir == Direction.EAST);
			case 3:
				return (dir == Direction.SOUTH || dir == Direction.WEST);
		}
		return false;
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		Debug.error("entered "+dir);
		switch(corner){
			case 0:
				if(dir == Direction.NORTH){
					dir = Direction.EAST;
				}else if(dir == Direction.WEST){
					dir = Direction.SOUTH;
				}
				break;
			case 1:
				if(dir == Direction.NORTH){
					dir = Direction.WEST;
				}else if(dir == Direction.EAST){
					dir = Direction.SOUTH;
				}
				break;
			case 2:
				if(dir == Direction.SOUTH){
					dir = Direction.WEST;
				}else if(dir == Direction.EAST){
					dir = Direction.NORTH;
				}
				break;
			case 3:
				if(dir == Direction.SOUTH){
					dir = Direction.EAST;
				}else if(dir == Direction.WEST){
					dir = Direction.NORTH;
				}
				break;
		}
		super.onEntered(actor, dir, last);
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		switch(corner){
			case 0:
				return (dir == Direction.NORTH || dir == Direction.WEST);
			case 1:
				return (dir == Direction.NORTH || dir == Direction.EAST);
			case 2:
				return (dir == Direction.SOUTH || dir == Direction.EAST);
			case 3:
				return (dir == Direction.SOUTH || dir == Direction.WEST);
		}
		return false;
	}

	@Override
	public int spriteX(){
		return corner;
	}
}

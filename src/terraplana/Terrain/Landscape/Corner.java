/**
 * @file Corner.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Corner extends Landscape {
	enum CornerDirection {
		SOUTH_EAST(0),
		SOUTH_WEST(1),
		NORTH_WEST(2),
		NORTH_EAST(3);
		
		private final int dir;
		
		private CornerDirection(int dir){
			this.dir = dir;
		}
		
		public int toInt(){
			return dir;
		}
	}
	private CornerDirection corner;
	
	public Corner(Tile place, String[] args) {
		super(place);
		if(args[0].equals("south-east")){
			corner = CornerDirection.SOUTH_EAST;
		}else if(args[0].equals("south-west")){
			corner = CornerDirection.SOUTH_WEST;
		}else if(args[0].equals("north-west")){
			corner = CornerDirection.NORTH_WEST;
		}else if(args[0].equals("north-east")){
			corner = CornerDirection.NORTH_EAST;
		}
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		switch(corner){
			case SOUTH_EAST:
				return (dir == Direction.NORTH || dir == Direction.WEST);
			case SOUTH_WEST:
				return (dir == Direction.NORTH || dir == Direction.EAST);
			case NORTH_WEST:
				return (dir == Direction.SOUTH || dir == Direction.EAST);
			case NORTH_EAST:
				return (dir == Direction.SOUTH || dir == Direction.WEST);
			default:
				break;
		}
		return false;
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		switch(corner){
			case SOUTH_EAST:
				if(dir == Direction.NORTH){
					dir = Direction.EAST;
				}else if(dir == Direction.WEST){
					dir = Direction.SOUTH;
				}
				break;
			case SOUTH_WEST:
				if(dir == Direction.NORTH){
					dir = Direction.WEST;
				}else if(dir == Direction.EAST){
					dir = Direction.SOUTH;
				}
				break;
			case NORTH_WEST:
				if(dir == Direction.SOUTH){
					dir = Direction.WEST;
				}else if(dir == Direction.EAST){
					dir = Direction.NORTH;
				}
				break;
			case NORTH_EAST:
				if(dir == Direction.SOUTH){
					dir = Direction.EAST;
				}else if(dir == Direction.WEST){
					dir = Direction.NORTH;
				}
				break;
			default:
				break;
		}
		super.onEntered(actor, dir, last);
		return false;
	}
	
	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		if(super.onExit(actor, dir, next)){
			switch(corner){
				case SOUTH_EAST:
					return (dir == Direction.SOUTH || dir == Direction.EAST);
				case SOUTH_WEST:
					return (dir == Direction.SOUTH || dir == Direction.WEST);
				case NORTH_WEST:
					return (dir == Direction.NORTH || dir == Direction.WEST);
				case NORTH_EAST:
					return (dir == Direction.NORTH || dir == Direction.EAST);
				default:
					break;
			}
		}
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		switch(corner){
			case SOUTH_EAST:
				return (dir == Direction.NORTH || dir == Direction.WEST);
			case SOUTH_WEST:
				return (dir == Direction.NORTH || dir == Direction.EAST);
			case NORTH_WEST:
				return (dir == Direction.SOUTH || dir == Direction.EAST);
			case NORTH_EAST:
				return (dir == Direction.SOUTH || dir == Direction.WEST);
			default:
				break;
		}
		return false;
	}
	
	@Override
	public boolean onExit(Movable move, Direction dir, Tile next){
		if(super.onExit(move, dir, next)){
			switch(corner){
				case SOUTH_EAST:
					return (dir == Direction.SOUTH || dir == Direction.EAST);
				case SOUTH_WEST:
					return (dir == Direction.SOUTH || dir == Direction.WEST);
				case NORTH_WEST:
					return (dir == Direction.NORTH || dir == Direction.WEST);
				case NORTH_EAST:
					return (dir == Direction.NORTH || dir == Direction.EAST);
				default:
					break;
			}
		}
		return false;
	}

	@Override
	public int spriteX(){
		return corner.toInt();
	}
}

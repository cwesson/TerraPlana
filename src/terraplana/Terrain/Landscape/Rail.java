/**
 * @file Rail.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Debug;
import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Rail extends Landscape {
	enum RailDirection {
		WEST_EAST(0),
		NORTH_SOUTH(1),
		NORTH_EAST(2),
		NORTH_WEST(3),
		SOUTH_WEST(4),
		SOUTH_EAST(5);
		
		private final int dir;
		
		private RailDirection(int dir){
			this.dir = dir;
		}
		
		public int toInt(){
			return dir;
		}
	}
	private RailDirection active;
	private RailDirection inactive;
	
	public Rail(Tile place, String[] args) {
		super(place);
		
		if(args[0].equals("west-east")){
			active = RailDirection.WEST_EAST;
		}else if(args[0].equals("north-south")){
			active = RailDirection.NORTH_SOUTH;
		}else if(args[0].equals("south-east")){
			active = RailDirection.SOUTH_EAST;
		}else if(args[0].equals("south-west")){
			active = RailDirection.SOUTH_WEST;
		}else if(args[0].equals("north-west")){
			active = RailDirection.NORTH_WEST;
		}else if(args[0].equals("north-east")){
			active = RailDirection.NORTH_EAST;
		}
		if(args.length > 1){
			if(args[1].equals("west-east")){
				inactive = RailDirection.WEST_EAST;
			}else if(args[1].equals("north-south")){
				inactive = RailDirection.NORTH_SOUTH;
			}else if(args[1].equals("south-east")){
				inactive = RailDirection.SOUTH_EAST;
			}else if(args[1].equals("south-west")){
				inactive = RailDirection.SOUTH_WEST;
			}else if(args[1].equals("north-west")){
				inactive = RailDirection.NORTH_WEST;
			}else if(args[1].equals("north-east")){
				inactive = RailDirection.NORTH_EAST;
			}
		}else{
			inactive = active;
		}
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		switch(active){
			case NORTH_SOUTH:
				return (dir == Direction.NORTH || dir == Direction.SOUTH);
			case WEST_EAST:
				return (dir == Direction.WEST || dir == Direction.EAST);
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
	public boolean onExit(Actor actor, Direction dir, Tile next){
		super.onExit(actor, dir, next);
		switch(active){
			case NORTH_SOUTH:
				return (dir == Direction.NORTH || dir == Direction.SOUTH);
			case WEST_EAST:
				return (dir == Direction.WEST || dir == Direction.EAST);
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
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		switch(active){
			case NORTH_SOUTH:
				return (dir == Direction.NORTH || dir == Direction.SOUTH);
			case WEST_EAST:
				return (dir == Direction.WEST || dir == Direction.EAST);
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
	public Direction onExit(Movable move, Direction dir, Tile next){
		super.onExit(move, dir, next);
		Direction redirect = Direction.NONE;
		switch(active){
			case NORTH_SOUTH:
				if(dir == Direction.NORTH || dir == Direction.SOUTH){
					redirect = dir;
				}
				break;
			case WEST_EAST:
				if(dir == Direction.WEST || dir == Direction.EAST){
					redirect = dir;
				}
				break;
			case SOUTH_EAST:
				if(dir == Direction.NORTH) {
					redirect = Direction.EAST;
				}else if(dir == Direction.WEST){
					redirect = Direction.SOUTH;
				}
				break;
			case SOUTH_WEST:
				if(dir == Direction.NORTH) {
					redirect = Direction.WEST;
				}else if(dir == Direction.EAST){
					redirect = Direction.SOUTH;
				}
				break;
			case NORTH_WEST:
				if(dir == Direction.SOUTH) {
					redirect = Direction.WEST;
				}else if(dir == Direction.EAST){
					redirect = Direction.NORTH;
				}
				break;
			case NORTH_EAST:
				Debug.error("NE");
				if(dir == Direction.SOUTH) {
					redirect = Direction.EAST;
					Debug.error("east");
				}else if(dir == Direction.WEST){
					redirect = Direction.NORTH;
					Debug.error("north");
				}
				break;
			default:
				break;
		}
		return redirect;
	}
	
	@Override
	public void activate(){
		RailDirection temp = active;
		active = inactive;
		inactive = temp;
	}
	
	@Override
	public void deactivate(){
		RailDirection temp = active;
		active = inactive;
		inactive = temp;
	}

	@Override
	public int spriteX(){
		return inactive.toInt();
	}

	@Override
	public int spriteY(){
		return active.toInt();
	}
}

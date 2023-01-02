/**
 * @file Edge.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Edge extends Landscape {

	private Direction edge;
	
	public Edge(Tile place, String[] args) {
		super(place);
		
		if(args[0].equals("north")){
			edge = Direction.NORTH;
		}else if(args[0].equals("east")){
			edge = Direction.EAST;
		}else if(args[0].equals("south")){
			edge = Direction.SOUTH;
		}else if(args[0].equals("west")){
			edge = Direction.WEST;
		}
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		switch(edge){
			case NORTH:
				return (dir != Direction.SOUTH);
			case EAST:
				return (dir != Direction.WEST);
			case SOUTH:
				return (dir != Direction.NORTH);
			case WEST:
				return (dir != Direction.EAST);
			default:
				break;
		}
		return false;
	}
	
	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		super.onExit(actor, dir, next);
		switch(edge){
			case NORTH:
				return (dir != Direction.NORTH);
			case EAST:
				return (dir != Direction.EAST);
			case SOUTH:
				return (dir != Direction.SOUTH);
			case WEST:
				return (dir != Direction.WEST);
			default:
				break;
		}
		return false;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		switch(edge){
			case NORTH:
				return (dir != Direction.SOUTH);
			case EAST:
				return (dir != Direction.WEST);
			case SOUTH:
				return (dir != Direction.NORTH);
			case WEST:
				return (dir != Direction.EAST);
			default:
				break;
		}
		return false;
	}
	
	@Override
	public Direction onExit(Movable move, Direction dir, Tile next){
		super.onExit(move, dir, next);
		if(dir != edge){
			return dir;
		}else {
			return Direction.NONE;
		}
	}

	@Override
	public int spriteX(){
		return edge.index();
	}
}

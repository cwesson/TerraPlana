/**
 * @file Trapdoor.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Door extends Landscape {
	protected int state = 0;
	public Door(Tile place, String[] args){
		super(place);
		if(args.length > 0) {
			state = Integer.parseInt(args[0]);
		}
	}
	
	public Door(Tile place){
		super(place);
	}
	
	@Override
	public void activate() {
		state = 1;
	}
	
	@Override
	public void deactivate() {
		state = 0;
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return (state > 0);
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return (state > 0);
	}

	@Override
	public int spriteX(){
		return state;
	}
}

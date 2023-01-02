/**
 * @file RailSwitch.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class RailSwitch extends RailButton {
	public RailSwitch(Tile place, String[] args) {
		super(place, args);
	}
	
	protected void toggle(){
		if(state == 0){
			activate();
		}else{
			deactivate();
		}
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		toggle();
		return terrain.onEntered(actor, dir, last);
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		toggle();
		return terrain.onEntered(move, dir, last);
	}
	
	@Override
	public boolean onExited(Actor actor, Direction dir, Tile last){
		return terrain.onExited(actor, dir, last);
	}
	
	@Override
	public boolean onExited(Movable move, Direction dir, Tile last){
		return terrain.onExited(move, dir, last);
	}
}

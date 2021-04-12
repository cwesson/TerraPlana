/**
 * @file Button.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Landscape.Toggle;

public class Button extends Toggle {

	public Button(Tile place, String[] args) {
		super(place, args);
	}
	
	public Button(Tile place, Position td) {
		super(place, td);
	}
	
	@Override
	protected void swapButton() {
	}
	
	@Override
	public boolean onExited(Actor actor, Direction dir, Tile last){
		swapDoor();
		return true;
	}
	
	@Override
	public boolean onExited(Movable move, Direction dir, Tile last){
		swapDoor();
		return true;
	}
}

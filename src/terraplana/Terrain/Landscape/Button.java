/**
 * @file B.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Debug;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Button extends Landscape {
	protected Position td;
	protected int state = 0;

	public Button(Tile place, String[] args) {
		super(place);
		td = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		if(args.length > 2) {
			state = Integer.parseInt(args[2]);
		}
	}
	
	public Button(Tile place, Position td) {
		super(place);
		this.td = td;
	}
	
	protected void swapButton() {
		Tile cell = tile.getBoard().at(td);
		Terrain terr = cell.getTerrain();
		if(Landscape.class.isAssignableFrom(terr.getClass())){
			Landscape scape = (Landscape)terr;
			if(state == 0){
				scape.deactivate();
			}else{
				scape.activate();
			}
		}
	}
	
	public void activate(){
		state = 1;
		swapButton();
	}
	
	public void deactivate(){
		state = 0;
		swapButton();
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		activate();
		return super.onEntered(actor, dir, last);
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		activate();
		return super.onEntered(move, dir, last);
	}
	
	@Override
	public boolean onExited(Actor actor, Direction dir, Tile last){
		deactivate();
		return super.onExited(actor, dir, last);
	}
	
	@Override
	public boolean onExited(Movable move, Direction dir, Tile last){
		deactivate();
		return super.onExited(move, dir, last);
	}

	@Override
	public int spriteX(){
		return state;
	}
}

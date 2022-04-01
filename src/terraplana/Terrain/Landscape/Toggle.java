/**
 * @file Toggle.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Toggle extends Landscape {
	protected Position td;
	protected int state = 0;

	public Toggle(Tile place, String[] args) {
		super(place);
		td = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		if(args.length > 2) {
			state = Integer.parseInt(args[2]);
		}
	}
	
	public Toggle(Tile place, Position td) {
		super(place);
		this.td = td;
	}
	
	protected void swapButton() {
		state = 1-state;
		
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
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		return true;
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		swapButton();
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		return true;
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		swapButton();
		return true;
	}

	@Override
	public int spriteX(){
		return state;
	}
}

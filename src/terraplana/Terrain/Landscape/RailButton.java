/**
 * @file RailButton.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class RailButton extends Rail {
	protected Position td;
	protected int state = 0;

	public RailButton(Tile place, String[] args) {
		super(place);
		
		if(args[0].equals("west-east")){
			active = RailDirection.WEST_EAST;
		}else if(args[0].equals("north-south")){
			active = RailDirection.NORTH_SOUTH;
		}
		inactive = active;
		
		td = new Position(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		if(args.length > 3) {
			state = Integer.parseInt(args[3]);
		}
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

	@Override
	public void activate(){
		state = 1;
		swapButton();
	}

	@Override
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
	public int spriteY(){
		return state;
	}
}

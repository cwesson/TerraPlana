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

	public Toggle(Tile place, String[] args) {
		super(place);
		td = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}
	
	public Toggle(Tile place, Position td) {
		super(place);
		this.td = td;
	}
	
	protected void swapButton() {
		tile.setTerrain(getTerrain());
		tile.setTerrain(new TogglePressed(tile, td));
	}
	
	protected void swapDoor(){
		Tile cell = tile.getBoard().at(td);
		Terrain door = cell.getTerrain();
		if(Door.class.isAssignableFrom(door.getClass())){
			Landscape scape = (Door)door;
			Terrain terra = scape.getTerrain();
			cell.setTerrain(terra);
			cell.setTerrain(new DoorOpen(cell));
		}else if(DoorOpen.class.isAssignableFrom(door.getClass())){
			Landscape scape = (DoorOpen)door;
			Terrain terra = scape.getTerrain();
			cell.setTerrain(terra);
			cell.setTerrain(new Door(cell));
		}
		swapButton();
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		return true;
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		swapDoor();
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		return true;
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		swapDoor();
		return true;
	}

}

/**
 * @file Teleport.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Teleport extends Landscape {
	private Position ep;

	public Teleport(Tile place, String[] args) {
		super(place);
		ep = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer()){
			Position pos = ep.clone();
			pos.move(dir);
			tile.getBoard().moveActor(actor, dir, pos);
		}
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		return true;
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		Position pos = ep.clone();
		pos.move(dir);
		Tile next = tile.getBoard().at(pos);
		if(next.addMovable(move)){
			tile.removeMovable();
		}
		return true;
	}
}

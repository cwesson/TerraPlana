/**
 * @file Portal.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Landscape.Landscape;
import terraplana.mod.portal.PortalManager;

public abstract class Portal extends Landscape {
	private PortalManager manager;
	
	public static Portal factory(Tile place, Position pos, String color) {
		Portal port;
		if(color.equals("Blue")){
			port = new PortalBlue(place, pos);
		}else{
			port = new PortalOrange(place, pos);
		}
		return port;
	}

	protected Portal(Tile place, Position pos) {
		super(place);
		manager = PortalManager.getInstance();
		String color = this.getColor();
		if(color.equals("Blue")) {
			manager.setBlue(place, pos);
		}else{
			manager.setOrange(place, pos);
		}
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer()){
			String color = this.getColor();
			Position pos;
			if(color.equals("Blue")) {
				pos = manager.getOrange();
			}else{
				pos = manager.getBlue();
			}
			if(pos != null) {
				pos.move(dir);
				tile.getBoard().moveActor(actor, dir, pos);
			}
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
		String color = this.getColor();
		Position pos;
		if(color.equals("Blue")) {
			pos = manager.getOrange();
		}else{
			pos = manager.getBlue();
		}
		if(pos != null) {
			pos.move(dir);
			Tile next = tile.getBoard().at(pos);
			if(next.addMovable(move)){
				tile.removeMovable();
			}
		}
		return true;
	}

	protected abstract String getColor();

}

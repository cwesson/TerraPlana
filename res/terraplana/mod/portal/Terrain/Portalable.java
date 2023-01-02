/**
 * @file Portalable.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Portalable extends Terrain {
	public Portalable(Tile place){
		super(place);
		attributes.add("movement.walk");
		attributes.add("portal.portalable");
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return player.hasAttribute("movement.walk");
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return true;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return true;
	}
	
	@Override
	public Direction onExit(Movable move, Direction dir, Tile next){
		return dir;
	}
	
	public String toString(){
		return "[";
	}

}

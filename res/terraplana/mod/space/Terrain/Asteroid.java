/**
 * @file Asteroid.java
 * @author Conlan Wesson
 */

package terraplana.mod.space.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Asteroid extends Terrain {

	public Asteroid(Tile place){
		super(place);
		attributes.add("movement.walk");
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
	public boolean onExit(Movable move, Direction dir, Tile next){
		return true;
	}
	
	public String toString(){
		return "o";
	}

}

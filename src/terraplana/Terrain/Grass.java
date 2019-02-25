/**
 * @file Grass.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Grass extends Terrain{

	public Grass(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return actor.hasAttribute("movement.walk");
	}

	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer()){
			tile.setTerrain(new Path(tile));
		}
		return false;
	}

	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		return false;
	}
	
	public String toString(){
		return "y";
	}
}

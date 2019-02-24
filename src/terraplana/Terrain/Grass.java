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
	public boolean onEnter(Actor player, Direction dir){
		return true;
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		tile.setTerrain(new Path(tile));
		return false;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		return false;
	}
	
	public String toString(){
		return "y";
	}
}

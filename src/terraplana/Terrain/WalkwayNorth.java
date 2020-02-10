/**
 * @file WalkwayNorth.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;

public class WalkwayNorth extends Walkway {
	public WalkwayNorth(Tile place) {
		super(place, Direction.NORTH);
	}
	
	public String toString(){
		return "^";
	}
}

/**
 * @file WalkwayWest.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;

public class WalkwayWest extends Walkway {
	public WalkwayWest(Tile place) {
		super(place, Direction.WEST);
	}
	
	public String toString(){
		return "<";
	}
}

/**
 * @file WalkwayEast.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;

public class WalkwayEast extends Walkway {
	public WalkwayEast(Tile place) {
		super(place, Direction.EAST);
	}
	
	public String toString(){
		return ">";
	}
}

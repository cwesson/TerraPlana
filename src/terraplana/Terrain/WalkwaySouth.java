/**
 * @file WalkwaySouth.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;

public class WalkwaySouth extends Walkway {
	public WalkwaySouth(Tile place) {
		super(place, Direction.SOUTH);
	}
	
	public String toString(){
		return "v";
	}
}

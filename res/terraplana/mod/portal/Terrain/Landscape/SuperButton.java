/**
 * @file SuperButton.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Position;
import terraplana.Tile;
import terraplana.Terrain.Landscape.Button;

public class SuperButton extends Button {

	public SuperButton(Tile place, String[] args) {
		super(place, args);
	}
	
	public SuperButton(Tile place, Position td) {
		super(place, td);
	}
}

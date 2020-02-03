/**
 * @file TogglePressed.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import terraplana.Position;
import terraplana.Tile;

public class TogglePressed extends Toggle {
	public TogglePressed(Tile place, String[] args){
		super(place, args);
	}
	
	public TogglePressed(Tile place, Position td){
		super(place, td);
	}
	
	@Override
	protected void swapButton() {
		tile.setTerrain(getTerrain());
		tile.setTerrain(new Toggle(tile, td));
	}
}

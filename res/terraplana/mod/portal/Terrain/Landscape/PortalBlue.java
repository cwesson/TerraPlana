/**
 * @file PortalBlue.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Position;
import terraplana.Tile;

public class PortalBlue extends Portal {
	protected PortalBlue(Tile place, Position pos) {
		super(place, pos);
	}
	
	@Override
	protected String getColor() {
		return "Blue";
	}
}

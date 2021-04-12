/**
 * @file PortalOrange.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Position;
import terraplana.Tile;

public class PortalOrange extends Portal {
	public PortalOrange(Tile place, String[] args) {
		super(place, args);
	}
	
	protected PortalOrange(Tile place, Position pos) {
		super(place, pos);
	}
	
	@Override
	protected String getColor() {
		return "Orange";
	}
}

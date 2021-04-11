/**
 * @file PortalManager.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal;

import terraplana.Position;
import terraplana.Tile;
import terraplana.Terrain.Terrain;
import terraplana.mod.portal.Terrain.Landscape.PortalBlue;
import terraplana.mod.portal.Terrain.Landscape.PortalOrange;

public class PortalManager {
	private static PortalManager inst = null;

	private Position bluePos = null;
	private Tile blueTile = null;
	private Position orangePos = null;
	private Tile orangeTile = null;
	
	private PortalManager() {
		
	}
	
	public static PortalManager getInstance() {
		if(inst == null) {
			inst = new PortalManager();
		}
		return inst;
	}
	
	public void setBlue(Tile tile, Position pos) {
		if(blueTile != null) {
			Terrain terra = blueTile.getTerrain();
			if(PortalBlue.class.isAssignableFrom(terra.getClass())){
				PortalBlue scape = (PortalBlue)terra;
				terra = scape.getTerrain();
				blueTile.setTerrain(terra);
			}
		}
		blueTile = tile;
		bluePos = pos.clone();
	}
	
	public void setOrange(Tile tile, Position pos) {
		if(orangeTile != null) {
			Terrain terra = orangeTile.getTerrain();
			if(PortalOrange.class.isAssignableFrom(terra.getClass())){
				PortalOrange scape = (PortalOrange)terra;
				terra = scape.getTerrain();
				orangeTile.setTerrain(terra);
			}
		}
		orangeTile = tile;
		orangePos = pos.clone();
	}
	
	public Position getBlue() {
		return bluePos.clone();
	}
	
	public Position getOrange() {
		return orangePos.clone();
	}

}

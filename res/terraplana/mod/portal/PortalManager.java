/**
 * @file PortalManager.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal;

import terraplana.Debug;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Terrain.Terrain;
import terraplana.mod.portal.Terrain.Landscape.Portal;
import terraplana.mod.portal.Terrain.Landscape.PortalBlue;

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
	
	private void clearTile(Tile tile) {
		Terrain terra = tile.getTerrain();
		if(Portal.class.isAssignableFrom(terra.getClass())){
			if(PortalBlue.class.isAssignableFrom(terra.getClass())){
				blueTile = null;
				bluePos = null;
			}else{
				orangeTile = null;
				orangePos = null;
			}
			Portal scape = (Portal)terra;
			terra = scape.getTerrain();
			tile.setTerrain(terra);
		}
	}
	
	public void setBlue(Tile tile, Position pos) {
		clearTile(tile);
		if(blueTile != null) {
			Terrain terra = blueTile.getTerrain();
			if(Portal.class.isAssignableFrom(terra.getClass())){
				Portal scape = (Portal)terra;
				terra = scape.getTerrain();
				blueTile.setTerrain(terra);
				Debug.info("cleared blue");
			}
		}
		blueTile = tile;
		bluePos = pos.clone();
		tile.setTerrain(Portal.factory(tile, pos, "Blue"));
		Debug.info("Blue at " + bluePos);
	}
	
	public void setOrange(Tile tile, Position pos) {
		clearTile(tile);
		if(orangeTile != null) {
			Terrain terra = orangeTile.getTerrain();
			Debug.info("orange tile "+ terra.getClass().getName());
			if(Portal.class.isAssignableFrom(terra.getClass())){
				Portal scape = (Portal)terra;
				terra = scape.getTerrain();
				orangeTile.setTerrain(terra);
				Debug.info("cleared orange");
			}
		}
		orangeTile = tile;
		orangePos = pos.clone();
		tile.setTerrain(Portal.factory(tile, pos, "Orange"));
		Debug.info("Orange at " + orangePos);
	}
	
	public Position getBlue() {
		return bluePos.clone();
	}
	
	public Position getOrange() {
		return orangePos.clone();
	}

}

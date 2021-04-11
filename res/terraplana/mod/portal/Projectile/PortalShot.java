/**
 * @file PortalShot.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Projectile;

import terraplana.Board;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Projectile.Projectile;
import terraplana.Terrain.Landscape.Landscape;
import terraplana.mod.portal.Terrain.Landscape.Portal;

public abstract class PortalShot extends Projectile {
	protected PortalShot(Board brd, Direction dir){
		super(brd, dir);
		setInterval(100);
		setRange(15);
	}
	
	public static PortalShot factory(Board board, Direction dir, String color) {
		PortalShot proj;
		if(color.equals("Blue")){
			proj = new PortalShotBlue(board, dir);
		}else{
			proj = new PortalShotOrange(board, dir);
		}
		return proj;
	}

	protected abstract String getColor();

	@Override
	public boolean onImpact(){
		super.onImpact();
		Position pos = board.getPosition(this);
		Tile tile = board.at(pos);
		Landscape port = Portal.factory(tile, pos, this.getColor());
		tile.setTerrain(port);
		return true;
	}

	@Override
	public boolean onConflict(Actor act){
		super.onConflict(act);
		return true;
	}

}

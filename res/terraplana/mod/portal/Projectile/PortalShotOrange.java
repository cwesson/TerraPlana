/**
 * @file PortalShotOrange.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Projectile;

import terraplana.Board;
import terraplana.Direction;

public class PortalShotOrange extends PortalShot {
	protected PortalShotOrange(Board brd, Direction dir){
		super(brd, dir);
	}
	
	@Override
	protected String getColor() {
		return "Orange";
	}

}

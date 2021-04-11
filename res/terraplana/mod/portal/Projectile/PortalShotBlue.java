/**
 * @file PortalShotBlue.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Projectile;

import terraplana.Board;
import terraplana.Direction;

public class PortalShotBlue extends PortalShot {
	protected PortalShotBlue(Board brd, Direction dir){
		super(brd, dir);
	}
	
	@Override
	protected String getColor() {
		return "Blue";
	}

}

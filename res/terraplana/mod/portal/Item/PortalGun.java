/**
 * @file PortalGun.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Item;

import terraplana.Board;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;
import terraplana.Item.Item;
import terraplana.Projectile.Projectile;
import terraplana.mod.portal.Projectile.PortalShot;

public abstract class PortalGun extends Item {
	private boolean pickup = true;
	
	protected PortalGun(String[] args){
		super(args);
	}

	@Override
	protected void parseArgs(String[] args){
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onPickup(Actor player){
		pickup = true;
		for(Item it : player.getInventory()){
			if(it.getClass().equals(this.getClass())){
				pickup = false;
			}
		}
		return pickup;
	}

	@Override
	public boolean onPickedup(Actor player){
		return pickup;
	}

	@Override
	public boolean onUse(Actor player){
		return true;
	}
	
	protected abstract String getColor();

	@Override
	public boolean onUsed(Actor player){
		Board board = player.getTile().getBoard();
		Position pos = board.getPosition(player);
		Direction dir = player.getDirection();
		Projectile proj = PortalShot.factory(board, dir, this.getColor());
		board.addProjectile(proj, pos, dir);
		return true;
	}

}

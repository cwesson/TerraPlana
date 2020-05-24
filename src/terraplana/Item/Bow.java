/**
 * @file Key.java
 * @author Conlan Wesson
 */

package terraplana.Item;

import terraplana.Board;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;
import terraplana.Projectile.Projectile;

public class Bow extends Item{
	private boolean pickup = true;
	private Item arrow = null;
	
	public Bow(String[] args){
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
		for(Item it : player.getInventory()) {
			if(it.getClass().equals(terraplana.Item.Arrow.class)){
				arrow = it;
				return true;
			}
		}
		arrow = null;
		return false;
	}

	@Override
	public boolean onUsed(Actor player){
		if(arrow != null){
			arrow.decCount();
			if(arrow.getCount() <= 0){
				player.removeItem(arrow);
			}
			Board board = player.getTile().getBoard();
			Position pos = board.getPosition(player);
			Direction dir = player.getDirection();
			Projectile proj = new terraplana.Projectile.Arrow(board, dir);
			board.addProjectile(proj, pos, dir);
		}
		return true;
	}

}

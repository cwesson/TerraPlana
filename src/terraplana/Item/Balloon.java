/**
 * @file Balloon.java
 * @author Conlan Wesson
 */

package terraplana.Item;

import terraplana.Actor.Player;

public class Balloon extends Item{
	public Balloon(String[] args){
		super(args);
	}

	@Override
	protected void parseArgs(String[] args){
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onPickup(Player player){
		boolean add = true;
		for(Item it : player.getInventory()){
			if(it.getClass().equals(this.getClass())){
				it.incCount();
				add = false;
			}
		}
		return add;
	}

	@Override
	public boolean onPickedup(Player player){
		return true;
	}

	@Override
	public boolean onUse(Player player){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onUsed(Player player){
		// TODO Auto-generated method stub
		return false;
	}

}

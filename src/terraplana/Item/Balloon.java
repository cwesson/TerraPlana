/**
 * @file Balloon.java
 * @author Conlan Wesson
 */

package terraplana.Item;

import terraplana.Actor.Actor;

public class Balloon extends Item{
	public Balloon(String[] args){
		super(args);
	}

	@Override
	protected void parseArgs(String[] args){
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onPickup(Actor player){
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
	public boolean onPickedup(Actor player){
		return true;
	}

	@Override
	public boolean onUse(Actor player){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onUsed(Actor player){
		// TODO Auto-generated method stub
		return false;
	}

}

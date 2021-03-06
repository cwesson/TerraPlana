/**
 * @file Carabiner.java
 * @author Conlan Wesson
 */

package terraplana.Item;

import terraplana.Actor.Actor;

public class Carabiner extends Item{
	private boolean pickup = true;
	
	public Carabiner(String[] args){
		super(args);
		attributes.add("movement.climb");
		attributes.add("movement.climb.safe");
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onUsed(Actor player){
		// TODO Auto-generated method stub
		return false;
	}

}

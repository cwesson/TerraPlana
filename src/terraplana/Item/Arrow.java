/**
 * @file Arrow.java
 * @author Conlan Wesson
 */

package terraplana.Item;

import terraplana.Actor.Actor;

public class Arrow extends Item{
	protected int amount;
	protected boolean add = true;
	
	public Arrow(String[] args){
		super(args);
	}

	@Override
	protected void parseArgs(String[] args){
		amount = Integer.parseInt(args[0]);
	}

	@Override
	public boolean onPickup(Actor player){
		for(Item it : player.getInventory()){
			if(it.getClass().equals(this.getClass())){
				add = false;
				break;
			}
		}
		return add;
	}

	@Override
	public boolean onPickedup(Actor player){
		for(Item it : player.getInventory()){
			if(it.getClass().equals(this.getClass())){
				if(add){
					it.addCount(amount-1);
				}else {
					it.incCount();
				}
				break;
			}
		}
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

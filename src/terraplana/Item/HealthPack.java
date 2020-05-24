/**
 * @file HealthPack.java
 * @author Conlan Wesson
 */

package terraplana.Item;

import terraplana.Actor.Actor;

public class HealthPack extends Item{
	protected int amount;
	
	public HealthPack(String[] args){
		super(args);
	}
	
	protected void parseArgs(String[] args){
		amount = Integer.parseInt(args[0]);
	}

	@Override
	public boolean onPickup(Actor player){
		amount -= player.setHealth(amount);
		return false;
	}

	@Override
	public boolean onPickedup(Actor player){
		return !(amount > 0);
	}

	@Override
	public boolean onUse(Actor player){
		return false;
	}

	@Override
	public boolean onUsed(Actor player){
		return false;
	}

}

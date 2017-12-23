package terraplana.Item;

import terraplana.Actor.Player;

public class HealthPack extends Item{
	protected int amount;
	
	public HealthPack(String[] args){
		super(args);
	}
	
	protected void parseArgs(String[] args){
		amount = Integer.parseInt(args[0]);
	}

	@Override
	public boolean onPickup(Player player){
		amount -= player.setHealth(amount);
		return false;
	}

	@Override
	public boolean onPickedup(Player player){
		return !(amount > 0);
	}

	@Override
	public boolean onUse(Player player){
		return false;
	}

	@Override
	public boolean onUsed(Player player){
		return false;
	}

}

package terraplana.Item;

import java.util.ArrayList;
import java.util.List;

import terraplana.Attributes;
import terraplana.Position;
import terraplana.Actor.Player;

public abstract class Item implements Attributes{
	protected List<String> attributes = new ArrayList<String>();
	private Position pos;
	protected int count = 1;
	
	public Item(String args[]){
		parseArgs(args);
	}
	
	protected abstract void parseArgs(String[] args);

	/**
	 * Called when the Player lands on the Item.
	 * @param player The Player.
	 * @return true to add the Item to the Player's inventory.
	 */
	public abstract boolean onPickup(Player player);
	
	/**
	 * Called after onPickup().
	 * @param player The Player.
	 * @return true to remove the Item from the Board.
	 */
	public abstract boolean onPickedup(Player player);
	
	public abstract boolean onUse(Player player);
	public abstract boolean onUsed(Player player);
	
	public void setPosition(Position pos){
		this.pos = pos;
	}
	
	public Position getPosition(){
		return pos;
	}
	
	public int getCount(){
		return count;
	}
	
	public void incCount(){
		count++;
	}
	
	public void decCount(){
		count--;
	}

	@Override
	public boolean hasAttribute(String attr){
		if(attributes.contains(attr)){
			return true;
		}
		return false;
	}
}

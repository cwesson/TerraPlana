package terraplana.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Actor.Player;
import terraplana.Item.Balloon;
import terraplana.Item.Item;
import terraplana.Movable.Movable;

public class Gate extends Landscape {
	protected int threshold = 0;
	public Gate(Tile place, String[] args) {
		super(place);
		threshold = Integer.parseInt(args[0]);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		super.onEnter(actor, dir);
		boolean ok = false;
		if(actor.isPlayer()){
			for(Item it : ((Player)actor).getInventory()){
				if(it.getClass().equals(Balloon.class)){
					if(it.getCount() >= threshold){
						ok = true;
						break;
					}
				}
			}
		}
		return ok;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		super.onEnter(move, dir);
		return false;
	}
}

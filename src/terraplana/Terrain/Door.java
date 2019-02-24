/**
 * @file Door.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Actor.Player;
import terraplana.Item.Item;
import terraplana.Item.Key;

public class Door extends Terrain{
	private Direction enter = null;

	public Door(Tile place){
		super(place);
	}

	public boolean onEnter(Actor player, Direction dir){
		boolean ok = false;
		if(player.getClass().equals(Player.class)){
			for(Item it : ((Player)player).getInventory()){
				if(it.getClass().equals(Key.class)){
					ok = true;
					enter = dir;
					break;
				}
			}
		}
		return ok;
	}

	public boolean onEntered(Actor player, Direction dir, Tile last){
		return false;
	}

	public boolean onExit(Actor player, Direction dir, Tile next){
		return true;
	}

	public boolean onExited(Actor player, Direction dir, Tile next){
		if(enter != null && player.getClass().equals(Player.class)){
			if(!enter.invert().equals(dir)){
				for(Item it : ((Player)player).getInventory()){
					if(it.getClass().equals(Key.class)){
						((Player)player).getInventory().remove(it);
						tile.setTerrain(new Path(tile));
						break;
					}
				}
			}
		}
		return false;
	}
	
	public String toString(){
		return "#";
	}
}

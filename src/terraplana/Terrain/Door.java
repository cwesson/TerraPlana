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
import terraplana.Projectile.Projectile;

public class Door extends Terrain{
	private Direction enter = null;

	public Door(Tile place){
		super(place);
	}

	public boolean onEnter(Actor actor, Direction dir){
		boolean ok = false;
		if(actor.isPlayer()){
			for(Item it : ((Player)actor).getInventory()){
				if(it.getClass().equals(Key.class)){
					ok = true;
					enter = dir;
					break;
				}
			}
		}
		return ok;
	}

	public boolean onEntered(Actor actor, Direction dir, Tile last){
		return false;
	}

	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}

	public boolean onExited(Actor actor, Direction dir, Tile next){
		if(enter != null && actor.isPlayer()){
			if(!enter.invert().equals(dir)){
				for(Item it : ((Player)actor).getInventory()){
					if(it.getClass().equals(Key.class)){
						((Player)actor).getInventory().remove(it);
						tile.setTerrain(new Path(tile));
						break;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onEnter(Projectile proj, Direction dir){
		return false;
	}
	
	public String toString(){
		return "#";
	}
}

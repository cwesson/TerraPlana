/**
 * @file Tile.java
 * @author Conlan Wesson
 */

package terraplana;

import java.util.ArrayList;
import java.util.List;

import terraplana.Actor.Actor;
import terraplana.Item.Item;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Tile{
	private Terrain terrain = Terrain.VOID;
	private List<Item> items = new ArrayList<Item>();
	private Movable movable = null;
	private List<Actor> actors = new ArrayList<Actor>();
	private Board board;
	
	public Tile(Board map, Terrain terr){
		board = map;
		terrain = terr;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void addActor(Actor act){
		synchronized(actors){
			for(Actor other : actors){
				other.onConflict(act);
				act.onConflict(other);
			}
			actors.add(act);
		}
	}
	
	public void removeActor(Actor act){
		synchronized(actors){
			actors.remove(act);
		}
	}
	
	public void addItem(Item it){
		items.add(it);
	}
	
	public void removeItem(Item it){
		items.remove(it);
	}
	
	public List<Item> getItems(){
		List<Item> list = new ArrayList<Item>();
		for(Item it : items){
			list.add(it);
		}
		return list;
	}
	
	public void addMovable(Movable mv){
		movable = mv;
	}
	
	public void removeMovable(){
		movable = null;
	}
	
	public Movable getMovable(){
		return movable;
	}
	
	public boolean hasMovable(){
		return movable != null;
	}
	
	public Terrain getTerrain(){
		return terrain;
	}
	
	public void setTerrain(Terrain terr){
		terrain = terr;
	}
	
	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public boolean onEnter(Actor player, Direction dir){
		return terrain.onEnter(player, dir);
	}
	
	/**
	 * Called after the player enters the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return terrain.onEntered(player, dir, last);
	}
	
	/**
	 * Called when the Player attempts to move out of the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	public boolean onExit(Actor player, Direction dir, Tile next){
		return terrain.onExit(player, dir, next);
	}

	/**
	 * Called after the player exits the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Actor player, Direction dir, Tile next){
		return terrain.onExited(player, dir, next);
	}
	
	/**
	 * Called when the Movable attempts to move onto the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to enter the cell.
	 */
	public boolean onEnter(Movable move, Direction dir){
		return terrain.onEnter(move, dir);
	}
	
	/**
	 * Called after the Movable enters the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Movable move, Direction dir, Tile last){
		return terrain.onEntered(move, dir, last);
	}
	
	/**
	 * Called when the Movable attempts to move out of the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to exit the cell.
	 */
	public boolean onExit(Movable move, Direction dir, Tile next){
		return terrain.onExit(move, dir, next);
	}
	
	/**
	 * Called after the Movable exits the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Movable move, Direction dir, Tile next){
		return terrain.onExited(move, dir, next);
	}
	
	public boolean hasAttribute(String attr){
		return terrain.hasAttribute(attr);
	}
}

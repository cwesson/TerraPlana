/**
 * @file Tile.java
 * @author Conlan Wesson
 */

package terraplana;

import java.util.ArrayList;
import java.util.List;

import terraplana.Actor.Actor;
import terraplana.Item.Item;
import terraplana.Landscape.Landscape;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Tile{
	private Terrain terrain = Terrain.VOID;
	private Landscape landscape = Landscape.NONE;
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
	
	public void setLandscape(Landscape scape){
		landscape = scape;
	}
	
	public Landscape getLandscape(){
		return landscape;
	}
	
	public boolean hasLandscape(){
		return landscape != Landscape.NONE;
	}
	
	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public boolean onEnter(Actor player, Direction dir){
		boolean allow = terrain.onEnter(player, dir);
		if(allow && hasLandscape()) {
			allow = landscape.onEnter(player, dir);
		}
		return allow;
	}
	
	/**
	 * Called after the player enters the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Actor player, Direction dir, Tile last){
		boolean allow = terrain.onEntered(player, dir, last);
		if(hasLandscape()) {
			allow = landscape.onEntered(player, dir, last) && allow;
		}
		return allow;
	}
	
	/**
	 * Called when the Player attempts to move out of the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	public boolean onExit(Actor player, Direction dir, Tile next){
		boolean allow =  terrain.onExit(player, dir, next);
		if(allow && hasLandscape()) {
			allow = landscape.onExit(player, dir, next);
		}
		return allow;
	}

	/**
	 * Called after the player exits the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Actor player, Direction dir, Tile next){
		boolean allow =  terrain.onExited(player, dir, next);
		if(hasLandscape()) {
			allow = landscape.onExited(player, dir, next) && allow;
		}
		return allow;
	}
	
	/**
	 * Called when the Movable attempts to move onto the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to enter the cell.
	 */
	public boolean onEnter(Movable move, Direction dir){
		boolean allow = terrain.onEnter(move, dir);
		if(allow && hasLandscape()) {
			allow = landscape.onEnter(move, dir);
		}
		return allow;
	}
	
	/**
	 * Called after the Movable enters the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Movable move, Direction dir, Tile last){
		boolean allow = terrain.onEntered(move, dir, last);
		if(hasLandscape()) {
			allow = landscape.onEntered(move, dir, last) && allow;
		}
		return allow;
	}
	
	/**
	 * Called when the Movable attempts to move out of the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to exit the cell.
	 */
	public boolean onExit(Movable move, Direction dir, Tile next){
		boolean allow = terrain.onExit(move, dir, next);
		if(allow && hasLandscape()) {
			allow = landscape.onExit(move, dir, next);
		}
		return allow;
	}

	/**
	 * Called after the Movable exits the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Movable move, Direction dir, Tile next){
		boolean allow = terrain.onExited(move, dir, next);
		if(hasLandscape()) {
			allow = landscape.onExited(move, dir, next) && allow;
		}
		return allow;
	}
	public boolean hasAttribute(String attr){
		boolean has = terrain.hasAttribute(attr);
		if(!has && hasLandscape()) {
			has = landscape.hasAttribute(attr);
		}
		return has;
	}
}

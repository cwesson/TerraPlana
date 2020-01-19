/**
 * @file Landscape.java
 * @author Conlan Wesson
 */

package terraplana.Landscape;

import java.util.ArrayList;
import java.util.List;

import terraplana.Attributes;
import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public class Landscape implements Attributes{
	public static final Landscape NONE = null;
	
	protected List<String> attributes = new ArrayList<String>();
	protected Tile tile;
	
	public Landscape(Tile place){
		tile = place;
	}
	
	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public boolean onEnter(Actor actor, Direction dir){
		return true;
	}
	
	/**
	 * Called after the player enters the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		return true;
	}
	
	/**
	 * Called when the Player attempts to move out of the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}

	/**
	 * Called after the player exits the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Actor actor, Direction dir, Tile next){
		return true;
	}
	
	/**
	 * Called when the Movable attempts to move onto the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to enter the cell.
	 */
	public boolean onEnter(Movable move, Direction dir){
		return false;
	}
	
	/**
	 * Called after the Movable enters the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Movable move, Direction dir, Tile last){
		return false;
	}
	
	/**
	 * Called when the Movable attempts to move out of the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to exit the cell.
	 */
	public boolean onExit(Movable move, Direction dir, Tile next){
		return false;
	}

	/**
	 * Called after the Movable exits the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Movable move, Direction dir, Tile next){
		return false;
	}

	@Override
	public boolean hasAttribute(String attr){
		if(attributes.contains(attr)){
			return true;
		}
		return false;
	}
}
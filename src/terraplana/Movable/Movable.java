/**
 * @file Movable.java
 * @author Conlan Wesson
 */

package terraplana.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Player;

public abstract class Movable{

	public Movable(String[] args){
		parseArgs(args);
	}
	
	protected abstract void parseArgs(String[] args);

	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public abstract boolean onPush(Player player, Direction dir, Tile next);
	
	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public abstract boolean onPushed(Player player, Direction dir, Tile next);

}

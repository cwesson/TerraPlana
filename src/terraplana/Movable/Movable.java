/**
 * @file Movable.java
 * @author Conlan Wesson
 */

package terraplana.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Projectile.Projectile;

public abstract class Movable{

	public Movable(String[] args){
		parseArgs(args);
	}
	
	protected abstract void parseArgs(String[] args);

	/**
	 * Called when the Actor attempts to move onto the cell.
	 * @param act The Actor.
	 * @param dir The Direction the Actor moved.
	 * @return true if the Actor should be allowed to enter the cell.
	 */
	public abstract boolean onPush(Actor act, Direction dir, Tile next);
	
	/**
	 * Called when an Actor attempts to move onto the cell.
	 * @param act The Actor.
	 * @param dir The Direction the Actor moved.
	 * @return Currently ignored.
	 */
	public abstract boolean onPushed(Actor act, Direction dir, Tile next);

	/**
	 * Called when a Projectile attempts to move onto the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Projectile moved.
	 * @return true if the Projectile should be allowed to enter the cell.
	 */
	public boolean onEnter(Projectile proj, Direction dir) {
		return false;
	}

	/**
	 * Called when a Projectile attempts to move onto the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Projectile moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Projectile proj, Direction dir) {
		return false;
	}

	/**
	 * Called when a Projectile attempts to move out of the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Projectile moved.
	 * @return true if the Projectile should be allowed to exit the cell.
	 */
	public boolean onExit(Projectile proj, Direction dir) {
		return false;
	}

	/**
	 * Called when a Projectile attempts to move out of the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Projectile moved.
	 * @return true if the Projectile should be allowed to exit the cell.
	 * @return Currently ignored.
	 */
	public boolean onExited(Projectile proj, Direction dir) {
		return false;
	}

}

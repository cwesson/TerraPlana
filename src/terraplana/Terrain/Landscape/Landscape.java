/**
 * @file Landscape.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import java.util.ArrayList;
import java.util.List;

import terraplana.Attributes;
import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Projectile.Projectile;
import terraplana.Terrain.Terrain;

public abstract class Landscape extends Terrain implements Attributes{
	protected List<String> attributes = new ArrayList<String>();
	protected Terrain terrain;
	
	public Landscape(Tile place){
		super(place);
		terrain = tile.getTerrain();
	}
	
	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return terrain.onEnter(actor, dir);
	}
	
	/**
	 * Called after the player enters the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		return terrain.onEntered(actor, dir, last);
	}
	
	/**
	 * Called when the Player attempts to move out of the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return terrain.onExit(actor, dir, next);
	}

	/**
	 * Called after the player exits the cell.
	 * @param actor The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		return terrain.onExited(actor, dir, next);
	}
	
	/**
	 * Called when the Movable attempts to move onto the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to enter the cell.
	 */
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return terrain.onEnter(move, dir);
	}
	
	/**
	 * Called after the Movable enters the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		return terrain.onEntered(move, dir, last);
	}
	
	/**
	 * Called when the Movable attempts to move out of the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return true if the Movable should be allowed to exit the cell.
	 */
	@Override
	public boolean onExit(Movable move, Direction dir, Tile next){
		return terrain.onExit(move, dir, next);
	}

	/**
	 * Called after the Movable exits the cell.
	 * @param move The Movable.
	 * @param dir The Direction the Movable moved.
	 * @return Currently ignored.
	 */
	@Override
	public boolean onExited(Movable move, Direction dir, Tile next){
		return terrain.onExited(move, dir, next);
	}
	
	/**
	 * Called when a Projectile attempts to move onto the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public boolean onEnter(Projectile proj, Direction dir){
		return terrain.onEnter(proj, dir);
	}
	
	/**
	 * Called after a Projectile enters the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Projectile proj, Direction dir, Tile last){
		return terrain.onEntered(proj, dir, last);
	}
	
	/**
	 * Called when a Projectile attempts to move out of the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	public boolean onExit(Projectile proj, Direction dir, Tile next){
		return terrain.onExit(proj, dir, next);
	}

	/**
	 * Called after a Projectile exits the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Projectile proj, Direction dir, Tile next){
		return terrain.onExited(proj, dir, next);
	}

	@Override
	public boolean hasAttribute(String attr){
		return (attributes.contains(attr) || terrain.hasAttribute(attr));
	}
	
	public Terrain getTerrain(){
		return terrain;
	}
}

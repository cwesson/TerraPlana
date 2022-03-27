/**
 * @file Terrain.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import java.util.ArrayList;
import java.util.List;

import terraplana.Attributes;
import terraplana.ContentLoader;
import terraplana.Direction;
import terraplana.Sprite;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Projectile.Projectile;

public abstract class Terrain implements Attributes, Sprite{
	private static class Sky extends Terrain {
		public Sky() {
			super(null);
		}
	}

	public static final Terrain VOID = new Sky();
	
	protected List<String> attributes = new ArrayList<String>();
	protected Tile tile;
	
	public Terrain(Tile place){
		tile = place;
	}
	
	/**
	 * Called when the Player attempts to move onto the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public boolean onEnter(Actor player, Direction dir){
		return false;
	}
	
	/**
	 * Called after the player enters the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return false;
	}
	
	/**
	 * Called when the Player attempts to move out of the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	public boolean onExit(Actor player, Direction dir, Tile next){
		return false;
	}

	/**
	 * Called after the player exits the cell.
	 * @param player The Player.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Actor player, Direction dir, Tile next){
		return false;
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
		return true;
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
	
	/**
	 * Called when a Projectile attempts to move onto the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to enter the cell.
	 */
	public boolean onEnter(Projectile proj, Direction dir){
		return true;
	}
	
	/**
	 * Called after a Projectile enters the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onEntered(Projectile proj, Direction dir, Tile last){
		return true;
	}
	
	/**
	 * Called when a Projectile attempts to move out of the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return true if the Player should be allowed to exit the cell.
	 */
	public boolean onExit(Projectile proj, Direction dir, Tile next){
		return true;
	}

	/**
	 * Called after a Projectile exits the cell.
	 * @param proj The Projectile.
	 * @param dir The Direction the Player moved.
	 * @return Currently ignored.
	 */
	public boolean onExited(Projectile proj, Direction dir, Tile next){
		return true;
	}

	public void finalize(){
		
	}

	@Override
	public boolean hasAttribute(String attr){
		if(attributes.contains(attr)){
			return true;
		}
		return false;
	}

	@Override
	public int spriteWidth(){
		return SPRITE_SIZE;
	}

	@Override
	public int spriteHeight(){
		return SPRITE_SIZE;
	}

	@Override
	public int spriteX(){
		return 0;
	}

	@Override
	public int spriteY(){
		return 0;
	}
	
	@Override
	public String spritePath(){
		return ContentLoader.getInstance().imagePath(this.getClass().getName());
	}
}

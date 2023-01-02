/**
 * @file CompanionCube.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Player;
import terraplana.Movable.Movable;
import terraplana.Projectile.Projectile;
import terraplana.Terrain.Lava;
import terraplana.mod.portal.Terrain.Acid;

public class CompanionCube extends Movable {

	public CompanionCube(String[] args){
		super(args);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void parseArgs(String[] args){
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onPush(Player player, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onPushed(Player player, Direction dir, Tile next){
		// TODO Auto-generated method stub
		if(next.getTerrain().getClass().equals(Acid.class) || next.getTerrain().getClass().equals(Lava.class)){
			next.removeMovable();
		}
		return false;
	}

	@Override
	public boolean onEnter(Projectile proj, Direction dir){
		return false;
	}

	@Override
	public boolean onExit(Projectile proj, Direction dir){
		return false;
	}
}

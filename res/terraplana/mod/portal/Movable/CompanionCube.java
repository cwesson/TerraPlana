/**
 * @file CompanionCube.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Projectile.Projectile;

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
	public boolean onPush(Actor act, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onPushed(Actor act, Direction dir, Tile next){
		if(next.getTerrain().hasAttribute("hazard")){
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

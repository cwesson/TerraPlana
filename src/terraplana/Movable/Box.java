/**
 * @file Box.java
 * @author Conlan Wesson
 */

package terraplana.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Projectile.Projectile;
import terraplana.Terrain.Lava;
import terraplana.Terrain.Path;
import terraplana.Terrain.Water;

public class Box extends Movable{

	public Box(String[] args){
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
		// TODO Auto-generated method stub
		if(next.getTerrain().getClass().equals(Water.class)){
			next.setTerrain(new Path(next));
			next.removeMovable();
		}else if(next.getTerrain().getClass().equals(Lava.class)){
			next.removeMovable();
		}
		return false;
	}

	@Override
	public boolean onEnter(Projectile proj, Direction dir){
		return true;
	}

	@Override
	public boolean onExit(Projectile proj, Direction dir){
		return false;
	}
}

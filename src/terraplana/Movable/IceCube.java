/**
 * @file IceCube.java
 * @author Conlan Wesson
 */

package terraplana.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Terrain.Ice;
import terraplana.Terrain.Lava;
import terraplana.Terrain.Stone;

public class IceCube extends Movable{

	public IceCube(String[] args){
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
		if(next.hasAttribute("element.water")){
			next.setTerrain(new Ice(next));
			next.removeMovable();
		}else if(next.getTerrain().getClass().equals(Lava.class)){
			next.setTerrain(new Stone(next));
			next.removeMovable();
		}
		return false;
	}
	
}

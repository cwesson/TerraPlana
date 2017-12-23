package terraplana.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Player;
import terraplana.Terrain.Ice;
import terraplana.Terrain.Lava;
import terraplana.Terrain.Stone;
import terraplana.Terrain.Water;

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
	public boolean onPush(Player player, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onPushed(Player player, Direction dir, Tile next){
		if(next.getTerrain().getClass().equals(Water.class)){
			next.setTerrain(new Ice(next));
			next.removeMovable(this);
		}else if(next.getTerrain().getClass().equals(Lava.class)){
			next.setTerrain(new Stone(next));
			next.removeMovable(this);
		}
		return false;
	}
	
}

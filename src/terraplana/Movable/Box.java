package terraplana.Movable;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Player;
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
	public boolean onPush(Player player, Direction dir, Tile next){
		if(next.getTerrain().getClass().equals(player.getTile().getTerrain().getClass())){
			return true;
		}else if(next.getTerrain().getClass().equals(Water.class)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean onPushed(Player player, Direction dir, Tile next){
		// TODO Auto-generated method stub
		if(next.getTerrain().getClass().equals(Water.class)){
			next.setTerrain(new Path(next));
			next.removeMovable();
		}
		return false;
	}
	
}

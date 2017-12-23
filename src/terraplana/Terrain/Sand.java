package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Sand extends Terrain{

	public Sand(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString(){
		return ".";
	}
}

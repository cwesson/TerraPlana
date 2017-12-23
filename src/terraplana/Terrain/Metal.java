package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Metal extends Terrain{

	public Metal(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return false;
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return false;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return false;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		return false;
	}
	
	public String toString(){
		return "=";
	}
}

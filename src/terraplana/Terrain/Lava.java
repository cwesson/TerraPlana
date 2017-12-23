package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Lava extends Terrain{

	public Lava(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return true;
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		player.setHealth(-100);
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
		return "%";
	}
}

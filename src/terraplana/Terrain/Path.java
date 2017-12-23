package terraplana.Terrain;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Path extends Terrain{

	public Path(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return player.hasAttribute("movement.walk");
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return true;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		return true;
	}
	
	public String toString(){
		return "+";
	}
}

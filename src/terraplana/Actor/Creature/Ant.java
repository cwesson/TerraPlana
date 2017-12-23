package terraplana.Actor.Creature;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;
import terraplana.Terrain.Terrain;

public class Ant extends Creature{
	
	public Ant(String[] args){
		super(args);
		attributes.add("movement.walk");
		setInterval(200);
	}
	
	@Override
	protected void parseArgs(String[] args){
		direction = Direction.parse(args[0]);
	}
	
	@Override
	protected Direction tick(int time){
		Position pos = getTile().getBoard().getPosition(this);
		Position newPos = pos.clone();
		newPos.move(direction);
		Terrain next = getTile().getBoard().at(newPos).getTerrain();
		Terrain curr = getTile().getBoard().at(pos).getTerrain();
		if(curr.getClass().equals(next.getClass())){
			return direction;
		}else{
			return direction.invert();
		}
	}

	@Override
	public void onConflict(Actor act){
		// TODO Auto-generated method stub
		
	}
}

/**
 * @file Ant.java
 * @author Conlan Wesson
 */

package terraplana.Actor.Creature;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;

public class Ant extends Creature{
	
	public Ant(String[] args){
		super(args);
		attributes.put("movement.walk", 1);
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
		return direction;
	}
	
	@Override
	protected boolean onStopped(){
		direction = direction.invert();
		return true;
	}

	@Override
	public void onConflict(Actor act){
		// TODO Auto-generated method stub
		
	}
}

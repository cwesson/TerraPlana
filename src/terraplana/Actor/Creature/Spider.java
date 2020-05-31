/**
 * @file Spider.java
 * @author Conlan Wesson
 */

package terraplana.Actor.Creature;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;

public class Spider extends Creature{
	
	public Spider(String[] args){
		super(args);
		attributes.put("movement.walk", 1);
		attributes.put("movement.climb", 1);
		attributes.put("movement.climb.safe", 1);
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
	public void onConflict(Actor actor){
		if(actor.isPlayer()){
			actor.setHealth(-25);
		}
	}
	
	@Override
	protected boolean onStopped(){
		direction = direction.turnLeft();
		return true;
	}
}

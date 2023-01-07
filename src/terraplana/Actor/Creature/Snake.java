/**
 * @file Snake.java
 * @author Conlan Wesson
 */

package terraplana.Actor.Creature;

import terraplana.Debug;
import terraplana.Direction;
import terraplana.Actor.Actor;

public class Snake extends Creature{
	private int state = 0;
	
	public Snake(String[] args){
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
		state = 1 - state;
		return direction;
	}
	
	@Override
	protected boolean onStopped(int count){
		Debug.error(""+count);
		switch(count){
			case 0:
				setDirection(direction.turnLeft());
				break;
			case 1:
				setDirection(direction.turnRight().turnRight());
				break;
			case 2:
				setDirection(direction.turnLeft());
				break;
			default:
				setDirection(direction.invert());
				count = 0;
				break;
		}
		return true;
	}

	@Override
	public void onConflict(Actor act){
		//act.setHealth(-50);
	}

	@Override
	public int spriteX(){
		return state;
	}
}

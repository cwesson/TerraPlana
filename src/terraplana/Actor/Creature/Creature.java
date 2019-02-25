/**
 * @file Creature.java
 * @author Conlan Wesson
 */

package terraplana.Actor.Creature;

import terraplana.Direction;
import terraplana.Actor.Actor;

public abstract class Creature extends Actor{
	public Creature(String args[]){
		parseArgs(args);
	}
	
	abstract protected void parseArgs(String[] args);
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setDirection(Direction dir){
		direction = dir;
	}

	@Override
	public boolean hasAttribute(String attr){
		if(attributes.contains(attr)){
			return true;
		}
		return false;
	}
}

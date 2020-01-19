/**
 * @file Cactus.java
 * @author Conlan Wesson
 */

package terraplana.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Cactus extends Landscape {
	public Cactus(Tile place) {
		super(place);
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		if(actor.isPlayer()){
			actor.setHealth(-5);
		}
		return false;
	}
}

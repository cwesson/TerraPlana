/**
 * @file Igloo.java
 * @author Conlan Wesson
 */

package terraplana.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Landscape.Landscape;

public class Igloo extends Landscape {
	public Igloo(Tile place) {
		super(place);
		attributes.add("movement.skate.safe");
	}
	
	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return true;
	}
}

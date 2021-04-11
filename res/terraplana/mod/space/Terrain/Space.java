/**
 * @file Space.java
 * @author Conlan Wesson
 */

package terraplana.mod.space.Terrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Space extends Terrain {
	private static Map<Actor, SpaceTimer> timers = new HashMap<Actor, SpaceTimer>();

	public Space(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return true;
	}

	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer()){
			actor.blockInput();
			timers.put(actor, new SpaceTimer(actor, dir));
		}
		return true;
	}

	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		if(actor.isPlayer()){
			Position pos = actor.getTile().getBoard().getPosition(actor);
			pos.move(dir);
			Terrain terr = next.getTerrain();
			if(terr.getClass() != this.getClass()){
				actor.allowInput();
			}
		}
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		if(timers.get(actor) != null){
			timers.get(actor).cancel();
			timers.remove(actor);
		}
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return true;
	}
	
	@Override
	public boolean onExit(Movable move, Direction dir, Tile next){
		return true;
	}
	
	public String toString(){
		return ":";
	}
	
	private class SpaceTimer extends TimerTask{
		private Timer timer = new Timer();
		private Actor actor;
		private boolean moved;
		private Direction dir;
		
		public SpaceTimer(Actor actor, Direction dir){
			this.actor = actor;
			this.dir = dir;
			this.moved = false;
			timer.scheduleAtFixedRate(this, 100, 100);
		}

		@Override
		public void run(){
			if(!moved) {
				moved = actor.getTile().getBoard().moveActor(actor, dir);
				if(!moved) {
					actor.allowInput();
				}
			}
			actor.setHealth(-2);
		}
	}

}

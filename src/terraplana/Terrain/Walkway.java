/**
 * @file Walkway.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import java.util.Timer;
import java.util.TimerTask;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;

public abstract class Walkway extends Terrain{
	protected Direction dir;
	protected WalkwayTimer timer = null;

	public Walkway(Tile place, Direction dir){
		super(place);
		this.dir = dir;
	}

	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return actor.hasAttribute("movement.skate");
	}

	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer()){
			if(!actor.hasAttribute("movement.skate.safe") && !tile.hasAttribute("movement.skate.safe")){
				timer = new WalkwayTimer(actor, this.dir);
			}
		}
		return true;
	}

	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		if(timer != null) {
			timer.cancel();
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
	
	private class WalkwayTimer extends TimerTask{
		private Timer timer = new Timer();
		private Actor actor;
		private Direction dir;
		
		public WalkwayTimer(Actor actor, Direction dir){
			this.actor = actor;
			this.dir = dir;
			timer.schedule(this, 100);
		}

		@Override
		public void run(){
			actor.getTile().getBoard().moveActor(actor, dir);
		}
	}
}

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
	protected TimerTask timer = null;

	public Walkway(Tile place, Direction dir){
		super(place);
		this.dir = dir;
	}

	@Override
	public boolean onEnter(Actor actor, Direction d){
		return actor.hasAttribute("movement.skate");
	}

	@Override
	public boolean onEntered(Actor actor, Direction d, Tile last){
		if(actor.isPlayer()){
			if(!actor.hasAttribute("movement.drag.safe") && !tile.hasAttribute("movement.drag.safe")){
				timer = new WalkwayTimer(actor, this.dir);
			}
		}
		return true;
	}

	@Override
	public boolean onExit(Actor actor, Direction d, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction d, Tile next){
		if(timer != null) {
			timer.cancel();
		}
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction d){
		return true;
	}
	
	@Override
	public boolean onEntered(Movable move, Direction d, Tile last){
		timer = new WalkwayMover(move, this.dir);
		return true;
	}
	
	@Override
	public Direction onExit(Movable move, Direction d, Tile next){
		return d;
	}
	
	@Override
	public boolean onExited(Movable move, Direction d, Tile next){
		if(timer != null) {
			timer.cancel();
		}
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
	
	private class WalkwayMover extends TimerTask{
		private Timer timer = new Timer();
		private Movable move;
		private Direction dir;
		
		public WalkwayMover(Movable mv, Direction dir){
			this.move = mv;
			this.dir = dir;
			timer.schedule(this, 100);
		}

		@Override
		public void run(){
			tile.getBoard().moveMovable(move, dir);
		}
	}
}

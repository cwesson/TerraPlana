/**
 * @file Ice.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import java.util.Timer;
import java.util.TimerTask;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Actor.Player;
import terraplana.Movable.Movable;
import terraplana.Terrain.Landscape.Landscape;

public class Ice extends Terrain{

	public Ice(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor actor, Direction dir){
		return actor.hasAttribute("movement.skate");
	}

	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		if(actor.isPlayer()){
			if(!actor.hasAttribute("movement.skate.safe") && !tile.hasAttribute("movement.skate.safe")){
				actor.blockInput();
				new IceTimer(actor, dir);
			}else{
				actor.allowInput();
			}
		}
		return true;
	}

	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		if(actor.isPlayer()){
			if(!((Player)actor).hasAttribute("movement.skate.safe") && !next.hasAttribute("movement.skate.safe")){
				Position pos = actor.getTile().getBoard().getPosition(actor);
				pos.move(dir);
				Terrain terr = next.getTerrain();
				if(Landscape.class.isAssignableFrom(terr.getClass())){
					terr = ((Landscape)terr).getTerrain();
				}
				if(!terr.getClass().isAssignableFrom(this.getClass())){
					actor.allowInput();
				}
			}
		}
		return true;
	}

	@Override
	public boolean onExited(Actor actor, Direction dir, Tile next){
		return true;
	}
	
	@Override
	public boolean onEnter(Movable move, Direction dir){
		return true;
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		new IceMover(move, dir);
		return true;
	}
	
	@Override
	public Direction onExit(Movable move, Direction dir, Tile next){
		return dir;
	}
	
	public String toString(){
		return "/";
	}
	
	private class IceTimer extends TimerTask{
		private Timer timer = new Timer();
		private Actor actor;
		private Direction dir;
		
		public IceTimer(Actor actor, Direction dir){
			this.actor = actor;
			this.dir = dir;
			timer.schedule(this, 100);
		}

		@Override
		public void run(){
			boolean moved = false;
			while(!moved) {
				moved = actor.getTile().getBoard().moveActor(actor, dir);
				if(!moved) {
					this.dir = this.dir.invert();
				}
			}
		}
	}
	
	private class IceMover extends TimerTask{
		private Timer timer = new Timer();
		private Movable move;
		private Direction dir;
		
		public IceMover(Movable mv, Direction dir){
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

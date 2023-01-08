/**
 * @file Spawner.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import java.util.Timer;
import java.util.TimerTask;

import terraplana.Board;
import terraplana.ContentLoader;
import terraplana.Debug;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Actor.Creature.Creature;

public class Spawner extends Landscape {
	private Board board;
	private Position outPos;
	private int interval;
	private String type;
	private String[] spawnArgs;
	private Creature spawn = null;
	private Creature blocked = null;
	private SpawnerTimer timer = null;
	
	public Spawner (Tile place, String[] args) {
		super(place);
		board = place.getBoard();
		outPos = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		interval = Integer.parseInt(args[2]);
		type = args[3];
		spawnArgs = new String[args.length-4];

		// Creature specific
		System.arraycopy(args, 4, spawnArgs, 0, args.length-4);
		
		if(interval > 0) {
			timer = new SpawnerTimer(this, interval);
		}
	}
	
	@Override
	public synchronized void activate(){
		if(blocked == null) {
			try {
				spawn = ContentLoader.getInstance().loadCreature(type, spawnArgs);
				Debug.info("Creature " + type);
				board.addCreature(spawn, outPos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public synchronized boolean onEntered(Actor actor, Direction dir, Tile next){
		if(actor == spawn){
			blocked = spawn;
			spawn = null;
		}
		return super.onEntered(actor, dir, next);
	}
	
	@Override
	public synchronized boolean onExited(Actor actor, Direction dir, Tile next){
		if(actor == blocked){
			blocked = null;
		}
		return true;
	}
	
	public void finalize(){
		if(timer != null) {
			timer.cancel();
		}
	}
	
	private class SpawnerTimer extends TimerTask{
		private Timer timer = new Timer();
		private Spawner spawner;
		
		public SpawnerTimer(Spawner spawn, int interval){
			spawner = spawn;
			timer.scheduleAtFixedRate(this, 0, interval);
		}

		@Override
		public void run(){
			spawner.activate();
		}
	}
}

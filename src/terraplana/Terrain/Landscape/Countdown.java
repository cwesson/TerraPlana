/**
 * @file Countdown.java
 * @author Conlan Wesson
 */

package terraplana.Terrain.Landscape;

import java.util.Timer;
import java.util.TimerTask;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Countdown extends Landscape {
	protected Position td;
	protected int duration = 0;
	protected int remaining = 0;
	protected CountdownTimer timer = null;
	protected final int frames = 8;
	protected boolean polarity = false;

	public Countdown(Tile place, String[] args) {
		super(place);
		td = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		duration = Integer.parseInt(args[2]) * 1000;
		if(args.length > 3){
			polarity = (Integer.parseInt(args[3]) != 0);
		}
	}
	
	public Countdown(Tile place, Position td) {
		super(place);
		this.td = td;
	}
	
	protected void trigger(Landscape scape, boolean pole){
		if(pole){
			scape.activate();
		}else{
			scape.deactivate();
		}
	}
	
	protected void trigger(){
		Tile cell = tile.getBoard().at(td);
		Terrain terr = cell.getTerrain();
		if(Landscape.class.isAssignableFrom(terr.getClass())){
			Landscape scape = (Landscape)terr;
			if(remaining == 0){
				trigger(scape, polarity);
			}else{
				trigger(scape, !polarity);
			}
		}
	}

	@Override
	public void activate(){
		if(timer == null){
			remaining = frames;
			timer = new CountdownTimer(this, duration/frames);
			trigger();
		}
	}
	
	@Override
	public boolean onEntered(Actor actor, Direction dir, Tile last){
		activate();
		return super.onEntered(actor, dir, last);
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		activate();
		return super.onEntered(move, dir, last);
	}

	@Override
	public int spriteX(){
		return remaining;
	}
	
	private void tick(){
		remaining--;
		if(remaining == 0){
			timer.cancel();
			timer = null;
			trigger();
		}
	}
	
	private class CountdownTimer extends TimerTask{
		private Timer timer = new Timer();
		private Countdown counter;
		
		public CountdownTimer(Countdown count, int interval){
			counter = count;
			timer.scheduleAtFixedRate(this, interval, interval);
		}

		@Override
		public void run(){
			counter.tick();
		}
	}
}

package terraplana.Actor.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import terraplana.Direction;
import terraplana.Actor.Actor;

public abstract class Creature extends Actor{
	protected List<String> attributes = new ArrayList<String>();
	private CreatureTimer timer;
	protected Direction direction;

	public Creature(String args[]){
		parseArgs(args);
	}
	
	abstract protected void parseArgs(String[] args);

	protected void setInterval(int time){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		if(time > 0){
			timer = new CreatureTimer(this, time);
		}
	}
	
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
	
	public void finalize(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}
	
	abstract protected Direction tick(int time);
	
	private class CreatureTimer extends TimerTask{
		private Timer timer = new Timer();
		private Creature crea;
		private int time;
		
		public CreatureTimer(Creature creature, int interval){
			crea = creature;
			time = interval;
			timer.scheduleAtFixedRate(this, interval, interval);
		}

		@Override
		public void run(){
			Direction dir = crea.tick(time);
			crea.getTile().getBoard().moveActor(crea, dir);
		}
	}
}

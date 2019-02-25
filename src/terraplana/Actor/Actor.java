/**
 * @file Actor.java
 * @author Conlan Wesson
 */

package terraplana.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import terraplana.Attributes;
import terraplana.Debug;
import terraplana.Direction;
import terraplana.Tile;
import terraplana.Item.Item;

public abstract class Actor implements Attributes{
	public enum Status {
		STATUS_OK,
		STATUS_DEAD,
		STATUS_DONE
	};

	protected List<String> attributes = new ArrayList<String>();
	protected List<Item> inventory = new ArrayList<Item>();
	protected Tile tile = null;
	protected Direction direction = Direction.EAST;
	protected int health = 100;
	protected int lives = 1;
	protected boolean input = true;
	protected Status status = Status.STATUS_OK;
	private ActorTimer timer;

	public abstract void onConflict(Actor act);
	
	protected void setInterval(int time){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		if(time > 0){
			timer = new ActorTimer(this, time);
		}
	}
	
	public void setTile(Tile place){
		if(tile != null){
			tile.removeActor(this);
		}
		tile = place;
		place.addActor(this);
	}
	
	public Tile getTile(){
		return tile;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int setHealth(int amount){
		int prev = health;
		if(status != Status.STATUS_DONE){
			health += amount;
			if(health > 100){
				health = 100;
			}else if(health <= 0){
				health = 0;
				lives--;
			}
			Debug.out.println(this);
			if(lives <= 0){
				status = Status.STATUS_DEAD;
				Debug.out.println("Status = " + status);
			}else{
				status = Status.STATUS_OK;
			}
		}
		return health - prev;
	}
	
	public int getLives(){
		return lives;
	}

	public void allowInput(){
		input = true;
	}
	
	public void blockInput(){
		input = false;
	}
	
	public boolean inputAllowed(){
		return input;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public boolean isPlayer(){
		return false;
	}
	
	public void done(){
		status = Status.STATUS_DONE;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setDirection(Direction dir){
		direction = dir;
	}


	public List<Item> getInventory(){
		return inventory;
	}
	
	public boolean hasItem(Class<?> item){
		for(Item it : inventory){
			if(it.getClass().equals(item)){
				return true;
			}
		}
		return false;
	}
	
	public boolean addItem(Item it){
		inventory.add(it);
		return true;
	}
	
	public boolean removeItem(Item it){
		inventory.remove(it);
		return true;
	}
	
	public boolean hasAttribute(String attr){
		if(attributes.contains(attr)){
			return true;
		}
		for(Item it : inventory){
			if(it.hasAttribute(attr)){
				return true;
			}
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
	
	protected boolean onStopped(){
		return false;
	}
	
	private class ActorTimer extends TimerTask{
		private Timer timer = new Timer();
		private Actor act;
		private int time;
		
		public ActorTimer(Actor actor, int interval){
			act = actor;
			time = interval;
			timer.scheduleAtFixedRate(this, interval, interval);
		}

		@Override
		public void run(){
			if(act.inputAllowed() && act.getStatus() == Actor.Status.STATUS_OK){
				boolean moved = false;
				boolean again = true;
				while(!moved && again) {
					Direction dir = act.tick(time);
					if(dir != Direction.NONE){
						moved = act.getTile().getBoard().moveActor(act, dir);
						if(!moved){
							again = act.onStopped();
						}
					}else{
						again = false;
					}
				}
			}
		}
	}
}

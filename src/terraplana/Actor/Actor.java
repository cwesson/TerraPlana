/**
 * @file Actor.java
 * @author Conlan Wesson
 */

package terraplana.Actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import terraplana.Attributes;
import terraplana.ContentLoader;
import terraplana.Debug;
import terraplana.Direction;
import terraplana.Sprite;
import terraplana.Tile;
import terraplana.Item.Item;
import terraplana.Projectile.Projectile;

public abstract class Actor implements Attributes, Sprite{
	public enum Status {
		STATUS_OK,
		STATUS_DEAD,
		STATUS_DONE
	};

	protected Map<String, Integer> attributes = new HashMap<String, Integer>();
	protected List<Item> inventory = new ArrayList<Item>();
	protected int selected = 0;
	protected Tile tile = null;
	protected Direction direction = Direction.EAST;
	protected boolean input = true;
	protected Status status = Status.STATUS_OK;
	private ActorTimer timer;

	public abstract void onConflict(Actor act);
	public abstract void onConflict(Projectile proj);
	
	protected Actor(){
		attributes.put("health", 100);
		attributes.put("lives", 1);
	}
	
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
		return getAttribute("health");
	}
	
	public int setHealth(int amount){
		int health = getAttribute("health");
		if(status != Status.STATUS_DONE){
			health += amount;
			if(health > 100){
				health = 100;
			}else if(health <= 0){
				health = 0;
				setAttribute("lives", -1);
			}
			Debug.info(this.toString());
			if(getAttribute("lives") <= 0){
				status = Status.STATUS_DEAD;
				Debug.info("Status = " + status);
				tile.getBoard().removeActor(this);
			}else{
				status = Status.STATUS_OK;
			}
		}
		attributes.put("health", health);
		return health;
	}
	
	public int getLives(){
		return getAttribute("lives");
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
	
	private Item getSelectedItem(){
		if(inventory.size() > selected){
			return inventory.get(selected);
		}else{
			return null;
		}
	}
	
	public int getSelected(){
		return selected;
	}
	
	public void setSelected(int sel){
		if(sel >= 0){
			selected = sel;
		}
	}
	
	public void useSelected(){
		Item it = getSelectedItem();
		if(it != null){
			if(it.onUse(this)){
				it.onUsed(this);
			}
		}
	}
	
	public boolean hasAttribute(String attr){
		if(attributes.containsKey(attr)){
			return true;
		}
		for(Item it : inventory){
			if(it.hasAttribute(attr)){
				return true;
			}
		}
		return false;
	}
	
	public void setAttribute(String attr, int inc){
		int value = attributes.get(attr);
		value += inc;
		attributes.put(attr, value);
	}
	
	public int getAttribute(String attr){
		return attributes.get(attr);
	}

	@Override
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

	@Override
	public int spriteWidth(){
		return SPRITE_SIZE;
	}

	@Override
	public int spriteHeight(){
		return SPRITE_SIZE;
	}

	@Override
	public int spriteX(){
		return 0;
	}

	@Override
	public int spriteY(){
		return direction.index();
	}
	
	@Override
	public String spritePath(){
		return ContentLoader.getInstance().imagePath(this.getClass().getName());
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
				int tries = 4;
				while(!moved && tries > 0) {
					Direction dir = act.tick(time);
					if(dir != Direction.NONE){
						moved = act.getTile().getBoard().moveActor(act, dir);
						if(!moved){
							if(act.onStopped()){
								--tries;
							}else{
								tries = 0;
							}
						}
					}else{
						tries = 0;
					}
				}
			}
		}
	}
}

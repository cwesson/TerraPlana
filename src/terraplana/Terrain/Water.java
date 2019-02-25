/**
 * @file Water.java
 * @author Conlan Wesson
 */

package terraplana.Terrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Actor.Actor;

public class Water extends Terrain{
	
	public Water(Tile place){
		super(place);
		attributes.add("element.water");
	}

	private static Map<Actor, WaterTimer> timers = new HashMap<Actor, WaterTimer>();
	
	@Override
	public boolean onEnter(Actor player, Direction dir){
		return player.hasAttribute("movement.swim");
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		Position pos = player.getTile().getBoard().getPosition(player);
		pos.move(dir, false);
		
		if(player.hasAttribute("movement.swim.safe")){
			if(timers.get(player) != null){
				timers.get(player).cancel();
				timers.remove(player);
			}
		}else{
			Terrain terr = last.getTerrain();
			if(terr.getClass() != this.getClass()){
				if(timers.get(player) == null){
					timers.put(player, new WaterTimer(player));
				}
			}
		}
		return true;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		return true;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		if(next != null){
			Terrain terr = next.getTerrain();
			if(terr.getClass() != this.getClass() || player.hasAttribute("movement.swim.safe")){
				if(timers.get(player) != null){
					timers.get(player).cancel();
					timers.remove(player);
				}
			}
		}else{
			if(timers.get(player) != null){
				timers.get(player).cancel();
				timers.remove(player);
			}
		}
		return true;
	}
	
	public String toString(){
		return "~";
	}
	
	private class WaterTimer extends TimerTask{
		private Timer timer = new Timer();
		private Actor player;
		
		public WaterTimer(Actor player){
			this.player = player;
			timer.scheduleAtFixedRate(this, 250, 250);
		}

		@Override
		public void run(){
			player.setHealth(-2);
		}
	}
}

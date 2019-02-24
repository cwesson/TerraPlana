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
import terraplana.Item.IceSkates;

public class Ice extends Terrain{

	public Ice(Tile place){
		super(place);
	}

	@Override
	public boolean onEnter(Actor player, Direction dir){
		return true;
	}

	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		if(player.getClass().equals(Player.class)){
			if(!((Player)player).hasItem(IceSkates.class)){
				player.blockInput();
				new IceTimer(player, dir);
			}else{
				player.allowInput();
			}
		}
		return true;
	}

	@Override
	public boolean onExit(Actor player, Direction dir, Tile next){
		if(player.getClass().equals(Player.class)){
			if(!((Player)player).hasItem(IceSkates.class)){
				Position pos = player.getTile().getBoard().getPosition(player);
				pos.move(dir);
				Terrain terr = next.getTerrain();
				if(terr.getClass() != this.getClass()){
					player.allowInput();
				}
			}
		}
		return true;
	}

	@Override
	public boolean onExited(Actor player, Direction dir, Tile next){
		return true;
	}
	
	public String toString(){
		return "/";
	}
	
	private class IceTimer extends TimerTask{
		private Timer timer = new Timer();
		private Actor player;
		private Direction dir;
		
		public IceTimer(Actor player, Direction dir){
			this.player = player;
			this.dir = dir;
			timer.schedule(this, 100);
		}

		@Override
		public void run(){
			player.getTile().getBoard().moveActor(player, dir);
		}
	}
}

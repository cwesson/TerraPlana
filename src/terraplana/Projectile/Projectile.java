package terraplana.Projectile;

import java.util.Timer;
import java.util.TimerTask;

import terraplana.Board;
import terraplana.ContentLoader;
import terraplana.Direction;
import terraplana.Sprite;
import terraplana.Actor.Actor;

public abstract class Projectile implements Sprite {
	protected Direction direction = Direction.NONE;
	protected Board board = null;
	private ProjectileTimer timer = null;
	private int range = 0;
	
	protected Projectile(Board brd, Direction dir) {
		board = brd;
		direction = dir;
	}

	protected void setInterval(int time){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		if(time > 0){
			timer = new ProjectileTimer(this, time);
		}
	}
	
	protected void setRange(int ran){
		range = ran;
	}

	public Board getBoard(){
		return board;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public boolean onImpact(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		return true;
	}
	
	public boolean onConflict(Actor act){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		return true;
	}
	
	@Override
	public void finalize(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
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
	
	private class ProjectileTimer extends TimerTask{
		private Timer timer = new Timer();
		private Projectile proj;
		
		public ProjectileTimer(Projectile projectile, int interval){
			proj = projectile;
			timer.scheduleAtFixedRate(this, interval, interval);
		}

		@Override
		public void run(){
			if(proj.getBoard().moveProjectile(proj, proj.getDirection())){
				--proj.range;
				if(range == 0) {
					this.cancel();
					proj.onImpact();
					proj.getBoard().removeProjectile(proj);
				}
			}else {
				this.cancel();
			}
		}
	}
}

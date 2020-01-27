package terraplana.Projectile;

import java.util.Timer;
import java.util.TimerTask;

import terraplana.Board;
import terraplana.Direction;
import terraplana.Actor.Actor;

public abstract class Projectile {
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
		return true;
	}
	
	@Override
	public void finalize(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
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
			proj.getBoard().moveProjectile(proj, proj.getDirection());
			--proj.range;
			if(range == 0) {
				this.cancel();
				proj.onImpact();
				proj.getBoard().removeProjectile(proj);
			}
		}
	}
}

package terraplana.Projectile;

import terraplana.Board;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;

public class Arrow extends Projectile {
	public Arrow(Board brd, Direction dir){
		super(brd, dir);
		setInterval(100);
		setRange(15);
	}
	
	@Override
	public boolean onImpact(){
		super.onImpact();
		Position pos = board.getPosition(this);
		String args[] = new String[] {"1"};
		board.at(pos).addItem(new terraplana.Item.Arrow(args));
		return true;
	}

	@Override
	public boolean onConflict(Actor act){
		super.onConflict(act);
		act.setHealth(-25);
		return true;
	}
}

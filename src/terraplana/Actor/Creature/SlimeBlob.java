/**
 * @file SlimeBlob.java
 * @author Conlan Wesson
 */

package terraplana.Actor.Creature;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;
import terraplana.Projectile.Projectile;
import terraplana.Projectile.SlimeBall;
import terraplana.Terrain.Grass;
import terraplana.Terrain.Path;
import terraplana.Terrain.Terrain;

public class SlimeBlob extends Creature{
	
	public SlimeBlob(String[] args){
		super(args);
		attributes.add("movement.walk");
		setInterval(500);
	}
	
	private Direction turn(Direction dir){
		int num = (int)(Math.random() * 2);
		switch(num){
			case 0:
				dir = dir.turnLeft();
				break;
			case 1:
				dir = dir.turnRight();
				break;
			default:
				break;
		}
		return dir;
	}
	
	private boolean movable(Position pos, Direction dir){
		Position newPos = pos.clone();
		newPos.move(dir);
		Terrain terra = getTile().getBoard().at(newPos).getTerrain();
		String name = terra.getClass().getName();
		return (name.equals(Path.class.getName()) || name.equals(Grass.class.getName()));
	}
	
	@Override
	protected void parseArgs(String[] args){
		direction = Direction.parse(args[0]);
	}
	
	@Override
	protected Direction tick(int time){
		Position pos = getTile().getBoard().getPosition(this);
		Direction dir = direction;
		int num = (int)(Math.random() * 4);
		switch(num){
			case 0:
			case 1:
			case 2:
				if(!movable(pos, dir)){
					// Start by turning randomly
					dir = turn(dir);
					if(!movable(pos, dir)){
						// Try the other direction
						dir = dir.invert();
						if(!movable(pos, dir)) {
							// Last resort, turn around
							dir = direction.invert();
						}
					}
				}
				if(dir != direction) {
					SlimeBall slime = new SlimeBall(getTile().getBoard(), dir);
					getTile().getBoard().addProjectile(slime, pos, dir);
				}
				break;
			case 3:
			default:
				dir = Direction.NONE;
				break;
		}
		return dir;
	}
	
	@Override
	protected boolean onStopped(){
		direction = turn(direction);
		return true;
	}

	@Override
	public void onConflict(Actor act) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConflict(Projectile proj) {
		// TODO Auto-generated method stub
		
	}
}

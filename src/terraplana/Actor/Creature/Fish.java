/**
 * @file Fish.java
 * @author Conlan Wesson
 */

package terraplana.Actor.Creature;

import terraplana.Debug;
import terraplana.Direction;
import terraplana.Position;
import terraplana.Actor.Actor;
import terraplana.Terrain.Terrain;

public class Fish extends Creature{
	private int count = 0;
	private int conflict = 0;
	
	public Fish(String[] args){
		super(args);
		attributes.add("movement.swim");
		attributes.add("movement.swim.safe");
		attributes.add("creature.peaceful");
		setInterval(400);
	}
	
	private void turn(){
		Direction dir = direction;
		while(dir.equals(direction)){
			int num = (int)(Math.random() * 4);
			switch(num){
				case 0:
					dir = Direction.NORTH;
					break;
				case 1:
					dir = Direction.SOUTH;
					break;
				case 2:
					dir = Direction.EAST;
					break;
				case 3:
					dir = Direction.WEST;
					break;
				default:
					break;
			}
		}
		setDirection(dir);
	}
	
	@Override
	protected void parseArgs(String[] args){
		direction = Direction.parse(args[0]);
	}
	
	@Override
	protected Direction tick(int time){
		Position newPos = getTile().getBoard().getPosition(this);
		newPos.move(direction);
		Terrain next = getTile().getBoard().at(newPos).getTerrain();
		Debug.out.println("conflict="+conflict+"    count="+count);
		if(next.hasAttribute("element.water") && (count <= 4 || conflict > 0)){
			count++;
			if(conflict > 0){
				conflict--;
			}else{
				setInterval(400);
			}
		}else{
			turn();
			count = 0;
			conflict = 0;
		}
		return direction;
	}

	@Override
	public void onConflict(Actor act){
		conflict = 6;
		setInterval(100);
	}
}

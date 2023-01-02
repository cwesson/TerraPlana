/**
 * @file Direction.java
 * @author Conlan Wesson
 */

package terraplana;

public enum Direction{
	NONE(0),
	NORTH(0),
	EAST(1),
	SOUTH(2),
	WEST(3);
	
	private final int dir;
	
	private Direction(int dir){
		this.dir = dir;
	}
	
	public String toString(){
		switch(this){
			case NORTH:
				return "North";
			case EAST:
				return "East";
			case SOUTH:
				return "South";
			case WEST:
				return "West";
			case NONE:
				return "None";
			default:
				return "";
		}
	}

	public static Direction parse(String string){
		string = string.toLowerCase();
		Direction dir = null;
		if(string.equals("north")){
			dir = NORTH;
		}else if(string.equals("south")){
			dir = SOUTH;
		}else if(string.equals("east")){
			dir = EAST;
		}else if(string.equals("west")){
			dir = WEST;
		}
		return dir;
	}
	
	public Direction invert(){
		switch(this){
			case NORTH:
				return SOUTH;
			case EAST:
				return WEST;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			default:
				return null;
		}
	}
	
	public Direction turnLeft(){
		switch(this){
			case NORTH:
				return WEST;
			case EAST:
				return NORTH;
			case SOUTH:
				return EAST;
			case WEST:
				return SOUTH;
			default:
				return null;
		}
	}
	
	public Direction turnRight(){
		switch(this){
			case NORTH:
				return EAST;
			case EAST:
				return SOUTH;
			case SOUTH:
				return WEST;
			case WEST:
				return NORTH;
			default:
				return null;
		}
	}
	
	public int index(){
		return dir;
	}
}

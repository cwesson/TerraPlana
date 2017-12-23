package terraplana;

public class Direction{
	public static final Direction NORTH = new Direction(8);
	public static final Direction SOUTH = new Direction(2);
	public static final Direction EAST = new Direction(6);
	public static final Direction WEST = new Direction(4);
	
	private int dir;
	
	private Direction(int dir){
		this.dir = dir;
	}
	
	public String toString(){
		switch(dir){
			case 8:
				return "North";
			case 2:
				return "South";
			case 6:
				return "East";
			case 4:
				return "West";
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
		switch(dir){
			case 8:
				return SOUTH;
			case 2:
				return NORTH;
			case 6:
				return WEST;
			case 4:
				return EAST;
			default:
				return null;
		}
	}
}

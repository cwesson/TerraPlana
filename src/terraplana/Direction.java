package terraplana;

public enum Direction{
	NORTH(0),
	SOUTH(180),
	EAST(90),
	WEST(270);
	
	private final int dir;
	
	private Direction(int dir){
		this.dir = dir;
	}
	
	public String toString(){
		switch(this){
			case NORTH:
				return "North";
			case SOUTH:
				return "South";
			case EAST:
				return "East";
			case WEST:
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
		switch(this){
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case EAST:
				return WEST;
			case WEST:
				return EAST;
			default:
				return null;
		}
	}
}

/**
 * @file Position.java
 * @author Conlan Wesson
 */

package terraplana;

public class Position{
	private int x, y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void move(Direction dir){
		move(dir, true);
	}
		
	public void move(Direction dir, boolean fwd){
		if(fwd){
			if(dir == Direction.NORTH){
				y--;
			}else if(dir == Direction.SOUTH){
				y++;
			}else if(dir == Direction.EAST){
				x++;
			}else if(dir == Direction.WEST){
				x--;
			}
		}else{
			if(dir == Direction.NORTH){
				y++;
			}else if(dir == Direction.SOUTH){
				y--;
			}else if(dir == Direction.EAST){
				x--;
			}else if(dir == Direction.WEST){
				x++;
			}
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Position clone(){
		return new Position(x, y);
	}
	
	public boolean equals(Object other){
		if(other.getClass() == this.getClass()){
			Position pos = (Position)other;
			return pos.x == x && pos.y == y;
		}
		return false;
	}
	
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
}

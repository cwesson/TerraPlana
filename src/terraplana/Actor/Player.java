/**
 * @file Player.java
 * @author Conlan Wesson
 */

package terraplana.Actor;

public class Player extends Actor{
	private static int sequence = 0;

	private int id;
	private String name;
	
	public Player(String name){
		attributes.add("movement.walk");
		attributes.add("movement.swim");
		attributes.add("movement.skate");
		id = sequence;
		sequence++;
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	public String toString(){
		return "Player" + id + ": " + name + " (" + health + "%)";
	}
	
	@Override
	public boolean isPlayer(){
		return true;
	}
	
	public boolean equals(Object other){
		if(other.getClass().equals(this.getClass())){
			return this.id == ((Player)other).id;
		}
		return false;
	}

	@Override
	public void onConflict(Actor act){
		// TODO Auto-generated method stub
		
	}
}

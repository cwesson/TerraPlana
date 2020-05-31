/**
 * @file Player.java
 * @author Conlan Wesson
 */

package terraplana.Actor;

import terraplana.Direction;
import terraplana.Display;
import terraplana.Projectile.Projectile;

public class Player extends Actor{
	private static int sequence = 0;

	private int id;
	private String name;
	private Display display;
	
	public Player(String name){
		attributes.put("movement.walk", 1);
		attributes.put("movement.swim", 1);
		attributes.put("movement.skate", 1);
		id = sequence;
		sequence++;
		this.name = name;
		setInterval(200);
	}

	public String getName(){
		return name;
	}
	
	public String toString(){
		return "Player" + id + ": " + name + " (" + getHealth() + "%)";
	}
	
	public void setDisplay(Display dis){
		display = dis;
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

	@Override
	public void onConflict(Projectile proj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected Direction tick(int time){
		if(display != null){
			return display.nextMove();
		}else{
			return Direction.NONE;
		}
	}
}

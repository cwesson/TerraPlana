package terraplana.Actor;

public class Player extends Actor{
	private static int sequence = 0;

	private int id;
	private String name;
	
	public Player(String name){
		attributes.add("movement.walk");
		attributes.add("movement.swim");
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
	
	public boolean equals(Object other){
		if(other.getClass() == this.getClass()){
			return this.id == ((Player)other).id;
		}
		return false;
	}

	@Override
	public void onConflict(Actor act){
		// TODO Auto-generated method stub
		
	}
}

package terraplana;

import java.util.ArrayList;
import java.util.List;

import terraplana.Actor.Actor;
import terraplana.Item.Item;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;

public class Tile{
	private Terrain terrain = Terrain.VOID;
	private List<Item> items = new ArrayList<Item>();
	private List<Movable> movables = new ArrayList<Movable>();
	private List<Actor> actors = new ArrayList<Actor>();
	private Board board;
	
	public Tile(Board map, Terrain terr){
		board = map;
		terrain = terr;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void addActor(Actor act){
		for(Actor other : actors){
			other.onConflict(act);
			act.onConflict(other);
		}
		actors.add(act);
	}
	
	public void removeActor(Actor act){
		actors.remove(act);
	}
	
	public void addItem(Item it){
		items.add(it);
	}
	
	public void removeItem(Item it){
		items.remove(it);
	}
	
	public List<Item> getItems(){
		List<Item> list = new ArrayList<Item>();
		for(Item it : items){
			list.add(it);
		}
		return list;
	}
	
	public void addMovable(Movable mv){
		movables.add(mv);
	}
	
	public void removeMovable(Movable mv){
		movables.remove(mv);
	}
	
	public List<Movable> getMovables(){
		List<Movable> list = new ArrayList<Movable>();
		for(Movable mv : movables){
			list.add(mv);
		}
		return list;
	}
	
	public Terrain getTerrain(){
		return terrain;
	}
	
	public void setTerrain(Terrain terr){
		terrain = terr;
	}
}

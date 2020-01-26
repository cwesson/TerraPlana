/**
 * @file Board.java
 * @author Conlan Wesson
 */

package terraplana;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import terraplana.Actor.Actor;
import terraplana.Actor.Player;
import terraplana.Actor.Creature.Creature;
import terraplana.Item.Item;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;
import terraplana.Terrain.Landscape.Landscape;

public class Board{
	private List<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
	private List<Player> players = new ArrayList<Player>();
	private Map<Actor, Position> actors = new HashMap<Actor, Position>();
	private List<Creature> creatures = new ArrayList<Creature>();
	private Map<Character, String> terrainmap = new HashMap<Character, String>();
	private int level = 0;
	private Game game = null;
	private Position start;
	private Position end;
	private boolean local = true;
	private URL url = null;
	private String file = null;
	private List<String> cmdQueue = new ArrayList<String>();
	private String title = "";
	private String desc = "";
	
	public Board(String path, Game game, int num) throws Exception{
		local = true;
		this.file = path;
		this.url = null;
		this.game = game;
		level = num;
		load();
	}
	
	public Board(URL url, Game game, int num) throws Exception{
		local = false;
		this.url = url;
		this.file = null;
		this.game = game;
		level = num;
		load();
	}
	
	private void load() throws Exception{
		if(local){
			parseFile(getClass().getResourceAsStream("/"+file), file.substring(0, file.lastIndexOf('/')));
		}else{
			String parent = url.toString().substring(0, url.toString().lastIndexOf('/'));
			parseFile(url.openStream(), parent);
		}
		for(String line : cmdQueue){
			String cmd[] = line.split("\\s+");
			if(cmd[0].equals("item")){
				parseItem(cmd);
			}else if(cmd[0].equals("movable")){
				parseMovable(cmd);
			}else if(cmd[0].equals("creature")){
				parseCreature(cmd);
			}else if(cmd[0].equals("landscape")){
				parseLandscape(cmd);
			}
		}
	}
	
	private void parseFile(InputStream file, String parent) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(file));
		String line = "";
		while((line = in.readLine()) != null){
			line = line.trim();
			if(line.length() == 0) {
				continue;
			}
			String cmd[] = line.split("\\s+");
			if(cmd[0].equals("terrain")){
				parseTerrain(cmd);
			}else if(cmd[0].equals("item")){
				cmdQueue.add(line);
			}else if(cmd[0].equals("movable")){
				cmdQueue.add(line);
			}else if(cmd[0].equals("creature")){
				cmdQueue.add(line);
			}else if(cmd[0].equals("landscape")){
				cmdQueue.add(line);
			}else if(cmd[0].equals("start")){
				start = new Position(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
			}else if(cmd[0].equals("end")){
				end = new Position(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
			}else if(cmd[0].equals("map")){
				parseMap(in);
			}else if(cmd[0].equals("include")){
				String path = parent;
				if(local){
					parseFile(getClass().getResourceAsStream("/"+path + "/" + cmd[1]), parent);
				}else{
					parseFile(new URL(parent + "/" + cmd[1]).openStream(), parent);
				}
			}else if(cmd[0].equals("title")){
				title = String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length));
			}else if(cmd[0].equals("desc")){
				desc = String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length));
			}else{
				Debug.info("Unknown map command " + cmd[0]);
			}
		}
	}

	private void parseCreature(String[] cmd) throws Exception{
		String type = cmd[1];
		String[] args = new String[cmd.length-4];
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Class<?> cls = Class.forName("terraplana.Actor.Creature." + type);
		Constructor<?> con = cls.getConstructor(args.getClass());
		Creature creature = (Creature)con.newInstance((Object)args);
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		
		Debug.info("Creature " + type);
		creature.setTile(this.at(pos));
		actors.put(creature, pos);
		creatures.add(creature);
	}

	private void parseItem(String[] cmd) throws Exception{
		// Item type
		String type = cmd[1];
		// Item position
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		
		// Item specific
		String[] args = new String[cmd.length-4];
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Class<?> cls = Class.forName("terraplana.Item." + type);
		Constructor<?> con = cls.getConstructor(args.getClass());
		Item item = (Item)con.newInstance((Object)args);
		item.setPosition(pos);

		Debug.info("Item " + type);
		at(pos).addItem(item);
	}

	private void parseMovable(String[] cmd) throws Exception{
		// Item type
		String type = cmd[1];
		// Item position
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		
		// Item specific
		String[] args = new String[cmd.length-4];
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Class<?> cls = Class.forName("terraplana.Movable." + type);
		Constructor<?> con = cls.getConstructor(args.getClass());
		Movable mv = (Movable)con.newInstance((Object)args);

		Debug.info("Movable " + type);
		at(pos).addMovable(mv);
	}
	
	private void parseLandscape(String[] cmd) throws Exception{
		// Landscape type
		String type = cmd[1];
		// Landscape position
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		
		// Landscape specific
		String[] args = new String[cmd.length-4];
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Class<?> cls = Class.forName("terraplana.Terrain.Landscape." + type);
		Constructor<?> con = cls.getConstructor(Tile.class, args.getClass());
		Landscape scape = (Landscape)con.newInstance(at(pos), args);
		
		Debug.info("Landscape " + type);
		at(pos).setTerrain(scape);
	}

	private void parseMap(BufferedReader in) throws Exception{
		String line = "";
		while((line = in.readLine()) != null){
			ArrayList<Tile> row = new ArrayList<Tile>();
			map.add(row);
			for(int i = 0; i < line.length(); i++){
				Terrain terr = Terrain.VOID;
				Character c = line.charAt(i);
				String type = terrainmap.get(c);
				Tile cell = new Tile(this, terr);
				if(c != null){
					Class<?> cls = Class.forName("terraplana.Terrain." + type);
					Constructor<?> con = cls.getConstructor(Tile.class);
					terr = (Terrain)con.newInstance(cell);
					cell.setTerrain(terr);
				}
				row.add(cell);
			}
		}
	}

	private void parseTerrain(String[] cmd){
		String type = cmd[1];
		String cell = cmd[2];
		terrainmap.put(new Character(cell.charAt(0)), type);
	}

	public void addPlayer(Player player){
		players.add(player);
		actors.put(player, start);
		player.setTile(this.at(start));
		player.setHealth(100);
		player.getInventory().removeAll(player.getInventory());
	}
	
	public String toString(){
		String str = "";
		for(int i = 0; i < map.size(); i++){
			ArrayList<Tile> row = map.get(i);
			for(int j = 0; j < row.size(); j++){
				Terrain cell = row.get(j).getTerrain();
				if(cell != Terrain.VOID){
					str += cell.toString();
				}else{
					str += " ";
				}
			}
			str += "\n";
		}
		return str;
	}

	public Tile at(Position pos){
		int x = pos.getX();
		int y = pos.getY();
		if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
		{
			return map.get(y).get(x);
		}else{
			return null;
		}
	}
	
	public void set(Position pos, Terrain ter){
		map.get(pos.getY()).get(pos.getX()).setTerrain(ter);
	}
	
	public int getWidth(){
		return map.get(0).size();
	}
	
	public int getHeight(){
		return map.size();
	}
	
	public Position getStart(){
		return start;
	}
	
	public Position getEnd(){
		return end;
	}

	public boolean next(Player player){
		try{
			Board next = game.getBoard(level+1);
			if(next != null){
				next.addPlayer(player);
				players.remove(player);
				actors.remove(player);
				for(Creature crea : creatures){
					crea.finalize();
				}
				creatures.removeAll(creatures);
				return true;
			}else{
				player.done();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public List<Creature> getCreatures(){
		return creatures;
	}

	public boolean moveActor(Actor actor, Direction dir){
		Position pos = actors.get(actor);
		Position newPos = pos.clone();
		newPos.move(dir);
		Position pushPos = newPos.clone();
		pushPos.move(dir);
		Tile oldTile = this.at(pos);
		Tile newTile = this.at(newPos);
		Tile pushTile = this.at(pushPos);
		if(oldTile.onExit(actor, dir, newTile)){
			if(newTile.onEnter(actor, dir)){
				boolean pushed = true;
				if(actor.isPlayer() && newTile.hasMovable() && pushTile != null){
					if(!pushTile.hasMovable()){
						Movable mv = newTile.getMovable();
						if(newTile.onExit(mv, dir, pushTile)){
							if(pushTile.onEnter(mv, dir)){
								if(mv.onPush((Player)actor, dir, pushTile)){
									newTile.removeMovable();
									pushTile.addMovable(mv);
									newTile.onExited(mv, dir, pushTile);
									mv.onPushed((Player)actor, dir, pushTile);
									pushTile.onEntered(mv, dir, newTile);
								}else {
									pushed = false;
								}
							}else{
								pushed = false;
							}
						}else{
							pushed = false;
						}
					}else{
						pushed = false;
					}
				}
				if(pushed){
					actors.put(actor, newPos);
					actor.setTile(newTile);
					actor.setDirection(dir);
					oldTile.onExited(actor, dir, newTile);
					if(actor.isPlayer()){
						List<Item> items = newTile.getItems();
						for(Item it : items){
							if(it.onPickup((Player)actor)){
								actor.addItem(it);
								it.setPosition(null);
							}
							if(it.onPickedup((Player)actor)){
								newTile.removeItem(it);
							}
						}
					}
					newTile.onEntered(actor, dir, oldTile);
					if(actor.isPlayer()){
						if(newPos.equals(this.getEnd())){
							newTile.onExit(actor, dir, null);
							newTile.onExited(actor, dir, null);
							Debug.info("End of level.");
							this.next((Player)actor);
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	public Position getPosition(Actor player){
		return actors.get(player).clone();
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return desc;
	}
}

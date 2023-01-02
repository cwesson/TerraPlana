/**
 * @file Board.java
 * @author Conlan Wesson
 */

package terraplana;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import terraplana.Projectile.Projectile;
import terraplana.Terrain.Terrain;
import terraplana.Terrain.Landscape.Landscape;

public class Board{
	private List<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
	private List<Player> players = new ArrayList<Player>();
	private Map<Actor, Position> actors = new HashMap<Actor, Position>();
	private Map<Actor, Position> removed = new HashMap<Actor, Position>();
	private List<Creature> creatures = new ArrayList<Creature>();
	private Map<Character, String> terrainmap = new HashMap<Character, String>();
	private Map<Projectile, Position> projectiles = new HashMap<Projectile, Position>();
	private Map<Movable, Position> movables = new HashMap<Movable, Position>();
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
			}else if(cmd[0].equals("mod")){
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
				Debug.info("Include " + cmd[1]);
				if(local){
					parseFile(getClass().getResourceAsStream("/"+path + "/" + cmd[1]), parent);
				}else{
					parseFile(new URL(parent + "/" + cmd[1]).openStream(), parent);
				}
			}else if(cmd[0].equals("title")){
				title = String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length));
			}else if(cmd[0].equals("desc")){
				desc = String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length));
			}else if(cmd[0].equals("mod")){
				ContentLoader.getInstance().addMod(cmd[1]);
				Debug.info("Loaded mod " + cmd[1]);
			}else{
				Debug.info("Unknown map command " + cmd[0]);
			}
		}
	}

	private void parseCreature(String[] cmd) throws Exception{
		// Item type
		String type = cmd[1];
		// Item position
		String[] args = new String[cmd.length-4];

		// Item specific
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		Creature creature = ContentLoader.getInstance().loadCreature(type, args);
		
		Debug.info("Creature " + type);
		addCreature(creature, pos);
	}

	private void parseItem(String[] cmd) throws Exception{
		// Item type
		String type = cmd[1];
		// Item position
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		
		// Item specific
		String[] args = new String[cmd.length-4];
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Item item = ContentLoader.getInstance().loadItem(type, args);
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
		Movable mv = ContentLoader.getInstance().loadMovable(type, args);

		Debug.info("Movable " + type);
		addMovable(mv, pos);
	}
	
	private void parseLandscape(String[] cmd) throws Exception{
		// Landscape type
		String type = cmd[1];
		// Landscape position
		Position pos = new Position(Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
		
		// Landscape specific
		String[] args = new String[cmd.length-4];
		System.arraycopy(cmd, 4, args, 0, cmd.length-4);
		Landscape scape = ContentLoader.getInstance().loadLandscape(type, args, at(pos));
		
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
					terr = ContentLoader.getInstance().loadTerrain(type, cell);
					cell.setTerrain(terr);
				}
				row.add(cell);
			}
		}
	}

	private void parseTerrain(String[] cmd){
		String type = cmd[1];
		String cell = cmd[2];
		terrainmap.put(Character.valueOf(cell.charAt(0)), type);
	}

	public void addPlayer(Player player){
		players.add(player);
		actors.put(player, start);
		player.setTile(this.at(start));
		player.setLives(1);
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

	private boolean next(Player player, int level){
		try{
			Board next = game.getBoard(level);
			if(next != null){
				next.addPlayer(player);
				players.remove(player);
				actors.remove(player);
				for(Creature crea : creatures){
					crea.finalize();
				}
				creatures.removeAll(creatures);
				for(List<Tile> row : map) {
					for(Tile tile : row) {
						tile.finalize();
					}
				}
				return true;
			}else{
				player.done();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean next(Player player){
		return next(player, level+1);
	}

	public boolean reset(Player player){
		return next(player, level);
	}

	public List<Creature> getCreatures(){
		return creatures;
	}
	
	public Map<Projectile, Position> getProjectiles(){
		return projectiles;
	}

	public synchronized boolean moveActor(Actor actor, Direction dir){
		Position pos = actors.get(actor);
		if(pos != null) {
		Position newPos = pos.clone();
		newPos.move(dir);
		return moveActor(actor, dir, newPos);
		}else {
			return false;
	}
	}

	public synchronized boolean moveMovable(Movable move, Direction dir){
		Position pos = movables.get(move);
		Position newPos = pos.clone();
		newPos.move(dir);
		return moveMovable(move, null, dir, pos, newPos);
	}

	public synchronized boolean moveActor(Actor actor, Direction dir, Position newPos){
		Position pos = actors.get(actor);
		Position pushPos = newPos.clone();
		pushPos.move(dir);
		Tile oldTile = this.at(pos);
		Tile newTile = this.at(newPos);
		Tile pushTile = this.at(pushPos);
		if(newTile == null){
			return false;
		}
		actor.setDirection(dir);
		// Make sure we can leave the current tile
		if(oldTile.onExit(actor, dir, newTile)){
			// Make sure we can enter the new tile
			if(newTile.onEnter(actor, dir)){
				// Check if something needs to be pushed out of the way
				boolean clear = false;
				if(actor.isPlayer() && newTile.hasMovable() && pushTile != null){
					Movable mv = newTile.getMovable();
					clear = moveMovable(mv, actor, dir, newPos, pushPos);
				}else{
					clear = true;
				}
				// If the new tile is not blocked
				if(clear){
					actors.put(actor, newPos);
					actor.setTile(newTile);
					// Leave the old tile
					oldTile.onExited(actor, dir, newTile);
					if(actor.isPlayer()){
						// Pickup any items
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
					// Enter the new tile
					newTile.onEntered(actor, dir, oldTile);
					if(actor.isPlayer()){
						// Check if its the exit
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
	

	public synchronized boolean moveMovable(Movable move, Actor act, Direction dir, Position oldPos, Position newPos){
		Tile pushTile = this.at(newPos);
		Tile oldTile = this.at(oldPos);
		// Check if it can be pushed
		Direction redirect = oldTile.onExit(move, dir, pushTile);
		boolean clear = false;
		if(redirect != Direction.NONE){
			Position pushPos = oldPos.clone();
			pushPos.move(redirect);
			pushTile = this.at(pushPos);
			if(pushTile != null && !pushTile.hasMovable()){
				if(pushTile.onEnter(move, redirect)){
					oldTile.onExited(move, redirect, pushTile);
					oldTile.removeMovable();
					movables.replace(move, pushPos);
					pushTile.addMovable(move);
					pushTile.onEntered(move, redirect, oldTile);
					move.onPushed(act, dir, pushTile);
					clear = true;
				}
			}
		}
		return clear;
	}

	public synchronized void removeActor(Actor act) {
		Position pos = actors.get(act);
		actors.remove(act);
		at(pos).removeActor(act);
		creatures.remove(act);
		removed.put(act, pos);
	}
	
	public synchronized void addCreature(Creature creature, Position pos) {
		Tile tile = this.at(pos);
		if(tile.onEnter(creature, creature.getDirection())) {
			tile.onEntered(creature, creature.getDirection(), null);
			creature.setTile(tile);
			actors.put(creature, pos);
			creatures.add(creature);
		}
	}
	
	public synchronized void addMovable(Movable move, Position pos) {
		Tile tile = this.at(pos);
		movables.put(move, pos);
		tile.addMovable(move);
	}

	public synchronized Position getPosition(Actor player){
		if(actors.containsKey(player)){
			return actors.get(player).clone();
		}else if(removed.containsKey(player)){
			return removed.get(player).clone();
		}else{
			return null;
		}
	}

	public synchronized Position getPosition(Projectile proj){
		Position pos = projectiles.get(proj);
		if(pos != null){
			return pos.clone();
		}
		return null;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return desc;
	}

	public synchronized boolean moveProjectile(Projectile proj, Direction dir){
		Position pos = projectiles.get(proj);
		Position newPos = pos.clone();
		newPos.move(dir);
		Tile oldTile = this.at(pos);
		Tile newTile = this.at(newPos);
		if(oldTile.onExit(proj, dir, newTile)){
			if(oldTile.hasMovable()){
				Movable mv = oldTile.getMovable();
				if(!mv.onExit(proj, dir)){
					removeProjectile(proj);
					return false;
				}
			}
			if(newTile.onEnter(proj, dir)){
				if(newTile.hasMovable()){
					Movable mv = newTile.getMovable();
					if(!mv.onEnter(proj, dir)){
						proj.onImpact();
						removeProjectile(proj);
						return false;
					}
				}
				oldTile.onExited(proj, dir, newTile);
				oldTile.removeProjectile(proj);
				projectiles.replace(proj, newPos);
				if(newTile.addProjectile(proj)){
					newTile.onEntered(proj, dir, oldTile);
					return true;
				}else{
					return false;
				}
			}
		}
		proj.onImpact();
		removeProjectile(proj);
		return false;
	}

	public synchronized void addProjectile(Projectile proj, Position pos, Direction dir){
		projectiles.put(proj, pos);
		moveProjectile(proj, dir);
	}

	public synchronized void removeProjectile(Projectile proj) {
		proj.finalize();
		projectiles.remove(proj);
	}
}

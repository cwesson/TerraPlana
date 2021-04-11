/**
 * @file ContentLoader.java
 * @author Conlan Wesson
 */

package terraplana;

import java.awt.Image;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;

import terraplana.Actor.Creature.Creature;
import terraplana.Item.Item;
import terraplana.Movable.Movable;
import terraplana.Terrain.Terrain;
import terraplana.Terrain.Landscape.Landscape;

public class ContentLoader {
	private static ContentLoader inst = null;
	
	private ArrayList<String> mods = new ArrayList<String>();
	private ImageCache icache = ImageCache.getInstance();
	
	private ContentLoader() {
		
	}
	
	public static ContentLoader getInstance() {
		if(inst == null) {
			inst = new ContentLoader();
		}
		return inst;
	}
	
	public boolean addMod(String name) {
		mods.add(name);
		return true;
	}
	
	private String getFullType(String type, String cat) {
		String fulltype = "terraplana." + cat + "." + type;
		if(type.contains(".")) {
			String[] parts = type.split("[.]");
			if(mods.contains(parts[0])) {
				fulltype = "terraplana.mod." + parts[0] + "." + cat + "." + String.join(".", Arrays.copyOfRange(parts, 1, parts.length));
			}
		}
		return fulltype;
	}
	
	public Item loadItem(String type, String[] args) throws Exception {
		Class<?> cls = Class.forName(getFullType(type, "Item"));
		Constructor<?> con = cls.getConstructor(args.getClass());
		Item item = (Item)con.newInstance((Object)args);
		return item;
	}
	
	public Creature loadCreature(String type, String[] args) throws Exception {
		Class<?> cls = Class.forName(getFullType(type, "Actor.Creature"));
		Constructor<?> con = cls.getConstructor(args.getClass());
		Creature creature = (Creature)con.newInstance((Object)args);
		return creature;
	}
	
	public Movable loadMovable(String type, String[] args) throws Exception {
		Class<?> cls = Class.forName(getFullType(type, "Movable"));
		Constructor<?> con = cls.getConstructor(args.getClass());
		Movable mv = (Movable)con.newInstance((Object)args);
		return mv;
	}
	
	public Terrain loadTerrain(String type, Tile tile) throws Exception {
		Class<?> cls = Class.forName(getFullType(type, "Terrain"));
		Constructor<?> con = cls.getConstructor(Tile.class);
		Terrain terr = (Terrain)con.newInstance(tile);
		return terr;
	}
	
	public Landscape loadLandscape(String type, String[] args, Tile tile) throws Exception {
		Class<?> cls = Class.forName(getFullType(type, "Terrain.Landscape"));
		Constructor<?> con = cls.getConstructor(Tile.class, args.getClass());
		Landscape scape = (Landscape)con.newInstance(tile, args);
		return scape;
	}
	
	private String parseType(String type) {
		String path = "img";
		String[] parts = type.split("[.]");
		int i = 0;
		if(parts[0].equals("terraplana")) {
			++i;
		}
		if(parts[i].equals("mod")) {
			path = "terraplana/mod/" + parts[i+1] + "/" + path;
			i += 2;
		}
		if(parts[i].equals("Terrain")) {
			++i;
			if(parts[i].equals("Landscape")) {
				++i;
				path += "/Landscape";
			}else {
				path += "/Terrain";
			}
		}else if(parts[i].equals("Actor")) {
			++i;
			if(parts[i].equals("Creature")) {
				++i;
				path += "/Creature";
			}else {
				path += "/Actor";
			}
		}else{
			path += "/" + parts[i];
			++i;
		}
		path += "/" + parts[i] + ".png";
		return path;
	}
	
	public Image getImage(String type) {
		Image img = icache.request(parseType(type));
		return img;
	}
	
	public Image getImage(String type, Direction dir) {
		type += "-" + dir;
		Image img = icache.request(parseType(type));
		return img;
	}
}

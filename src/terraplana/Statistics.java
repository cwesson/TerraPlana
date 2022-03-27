/**
 * @file Statistics.java
 * @author Conlan Wesson
 */

package terraplana;

import java.util.HashMap;

import terraplana.Actor.Actor;

public class Statistics {
	private static HashMap<Actor, Statistics> playerStats = new HashMap<Actor, Statistics>();
	
	private HashMap<String, Integer> stats;
	
	public static Statistics getStats(Actor player) {
		if(!playerStats.containsKey(player)) {
			playerStats.put(player, new Statistics());
		}
		return playerStats.get(player);
	}
	
	private Statistics() {
		stats = new HashMap<String, Integer>();
	}
	
	public int increment(String key, int amount) {
		int current = 0;
		if(stats.containsKey(key)) {
			current = stats.get(key);
		}
		current += amount;
		stats.put(key, current);
		Debug.error(key + "=" + current);
		return current;
	}
	
	public int set(String key, int amount) {
		stats.put(key, amount);
		return amount;
	}
	
	public int get(String key) {
		int current = 0;
		if(stats.containsKey(key)) {
			current = stats.get(key);
		}
		return current;
	}

}

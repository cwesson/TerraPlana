/**
 * @file Achievements.java
 * @author Conlan Wesson
 */

package terraplana;

import java.util.ArrayList;
import java.util.HashMap;

import terraplana.Actor.Player;

public class Achievements {
	private static HashMap<Player, Achievements> map = new HashMap<Player, Achievements>();
	
	private ArrayList<String> achievements;
	
	public static Achievements getAchievements(Player player) {
		if(!map.containsKey(player)) {
			map.put(player, new Achievements());
		}
		return map.get(player);
	}
	
	private Achievements() {
		achievements = new ArrayList<String>();
	}
	
	public void giveAchievement(String name) {
		achievements.add(name);
	}
}

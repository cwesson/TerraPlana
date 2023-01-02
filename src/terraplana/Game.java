/**
 * @file Game.java
 * @author Conlan Wesson
 */

package terraplana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import terraplana.Actor.Player;

public class Game{
	private List<String> files = new ArrayList<String>();
	private Map<String, Integer> codes = new HashMap<String, Integer>();
	private String title = "";
	private String desc = "";
	private String path = null;
	private URL url = null;
	private boolean local = true;
	
	public Game(String file) throws Exception{
		local = true;
		this.path = file;
		this.url = null;
		load();
	}
	
	public Game(URL url) throws Exception{
		local = false;
		this.url = url;
		this.path = null;
		load();
	}
	
	private void load() throws Exception{
		if(local){
			parseFile(getClass().getResourceAsStream("/"+path), path.substring(0, path.lastIndexOf('/')));
		}else{
			String parent = url.toString().substring(0, url.toString().lastIndexOf('/'));
			parseFile(url.openStream(), parent);
		}
	}
	
	private void parseFile(InputStream file, String parent) throws IOException{
		int level = 0;
		BufferedReader in = new BufferedReader(new InputStreamReader(file));
		String line = "";
		while((line = in.readLine()) != null){
			line = line.trim();
			if(line.length() == 0) {
				continue;
			}
			String cmd[] = line.split("\\s+");
			if(cmd[0].equals("title")){
				title = cmd[1];
			}else if(cmd[0].equals("desc")){
				desc = line.substring(4);
			}else if(cmd[0].equals("level")){
				files.add(parent + "/" + cmd[1]);
				if(cmd.length > 2){
					codes.put(cmd[2], level);
				}
				level++;
			}else if(cmd[0].equals("title")){
				title = String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length));
			}else if(cmd[0].equals("desc")){
				desc = String.join(" ", Arrays.copyOfRange(cmd, 1, cmd.length));
			}else{
				Debug.warning("Unknown game command " + cmd[0]);
			}
		}
	}

	public void addPlayer(Player play) throws Exception{
		getBoard(0).addPlayer(play);
	}
	
	public void addPlayer(Player play, String code) throws Exception{
		try{
			getBoard(codes.get(code)).addPlayer(play);
		}catch(Exception e){
			Debug.warning("No code " + code);
			getBoard(0).addPlayer(play);
		}
	}
	
	public Board getBoard(int num) throws Exception{
		if(files.size() > num){
			Board board;
			if(local){
				board = new Board(files.get(num), this, num);
			}else{
				board = new Board(new URL(files.get(num)), this, num);
			}
			return board;
		}else{
			return null;
		}
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDesc(){
		return desc;
	}
}

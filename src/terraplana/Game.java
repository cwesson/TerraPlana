package terraplana;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import terraplana.Actor.Player;

public class Game{
	private Map<Integer, Board> levels = new HashMap<Integer, Board>();
	private List<String> files = new ArrayList<String>();
	private Map<String, Integer> codes = new HashMap<String, Integer>();
	private String title = "";
	private String desc = "";
	private File file = null;
	private URL url = null;
	private boolean local = true;
	
	public Game(File file) throws Exception{
		local = true;
		this.file = file;
		this.url = null;
		load();
	}
	
	public Game(URL url) throws Exception{
		local = false;
		this.url = url;
		this.file = null;
		load();
	}
	
	private void load() throws Exception{
		if(local){
			parseFile(getClass().getResourceAsStream("/"+file.getPath()), file.getParent());
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
			getBoard(0).addPlayer(play);
		}
	}
	
	public Board getBoard(int num) throws Exception{
		if(levels.containsKey(num)){
			return levels.get(num);
		}else{
			if(files.size() > num){
				Board board;
				if(local){
					board = new Board(new File(files.get(num)), this, num);
				}else{
					board = new Board(new URL(files.get(num)), this, num);
				}
				levels.put(num, board);
				return board;
			}else{
				return null;
			}
		}
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDesc(){
		return desc;
	}
}

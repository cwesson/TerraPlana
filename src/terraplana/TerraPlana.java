/**
 * @file TerraPlana.java
 * @author Conlan Wesson
 */

package terraplana;

import terraplana.Actor.Player;

public class TerraPlana{

	/**
	 * @param args
	 */
	public static void main(String[] args){
		String map = null;
		String code = "";
		// Parse command line arguments.
		for(int i = 0; i < args.length; i++){
			String arg = args[i];
			if(arg.substring(0, 2).equals("--")){
				arg = arg.substring(2);
				if(arg.equals("debug")){
					Debug.enable();
				}else if(arg.equals("code")){
					i++;
					code = args[i];
				}
			}else if(arg.charAt(0) == '-'){
				arg = arg.substring(1);
				for(int j = 0; j < arg.length(); j++){
					char c = arg.charAt(j);
					System.out.println(c);
				}
			}else if(i == args.length-1){
				map = args[i];
			}
		}

		try{
			Game game = new Game(map);
			Player player = new Player("Player 1");
			if(code.equals("")){
				game.addPlayer(player);
			}else{
				game.addPlayer(player, code);
			}
			new Display(player);
		}catch(Exception e) {
			Debug.err.println("Could not open game file " + map);
			e.printStackTrace();
		}
	}
}

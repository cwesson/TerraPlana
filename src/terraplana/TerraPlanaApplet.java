package terraplana;

import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import terraplana.Actor.Player;

public class TerraPlanaApplet extends JApplet{
	private static final long serialVersionUID = -6296289803778845184L;

	//Called when this applet is loaded into the browser.
	public void init(){
		//Execute a job on the event-dispatching thread; creating this applet's GUI.
		try{
			URL url = new URL("http://www.cwesson.net/projects/TerraPlanus/map/test.gam");
			ImageCache.getInstance().setURL("http://www.cwesson.net/projects/TerraPlanus/");
			Game game = new Game(url);
			Player player = new Player("Player 1");
			game.addPlayer(player);
			new Display(player, this);
			requestFocus();
		}catch(Exception e){
			e.printStackTrace();
			StackTraceElement[] stack = e.getStackTrace();
			JTextPane pane = new JTextPane();
			pane.setText(e.toString());
			StyledDocument doc = pane.getStyledDocument();
			for(StackTraceElement trace : stack){
				try{
					doc.insertString(doc.getLength(), "\n"+trace.toString(), null);
				}catch(BadLocationException e1){
					e1.printStackTrace();
				}
			}
			add(pane);
		}
	}
}

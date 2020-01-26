/**
 * @file Debug.java
 * @author Conlan Wesson
 */

package terraplana;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Debug{
	private static Logger logger = Logger.getLogger("");

	public static void enable(){
		logger.setLevel(Level.INFO);
	}
	
	public static void disable(){
		logger.setLevel(Level.WARNING);
	}
	
	public static void error(String msg) {
		logger.severe(msg);
	}
	
	public static void warning(String msg) {
		logger.warning(msg);
	}
	
	public static void info(String msg) {
		logger.info(msg);
	}
}

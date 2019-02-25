/**
 * @file Debug.java
 * @author Conlan Wesson
 */

package terraplana;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Debug{
	public static PrintStream out = new PrintStream(new NullOutput());
	public static PrintStream err = System.err;

	public static void enable(){
		out = System.out;
	}
	
	public static void disable(){
		out = new PrintStream(new NullOutput());
	}
	
	private static class NullOutput extends OutputStream{
		@Override
		public void write(int b) throws IOException{
			// ignore.
		}
	}
}

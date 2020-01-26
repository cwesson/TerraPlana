/**
 * @file ImageCache.java
 * @author Conlan Wesson
 */

package terraplana;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageCache{
	private static ImageCache instance = new ImageCache();
	
	private Map<String, Image> cache = new HashMap<String, Image>();
	private String path = ".";
	private boolean local = true;
	
	public static ImageCache getInstance(){
		return instance;
	}
	
	private ImageCache(){}
	
	public void setURL(String url){
		path = url;
		local = false;
	}
	
	public void setPath(String path){
		this.path = path;
		local = true;
	}
	
	/**
	 * Request an image from the cache.
	 * @param file The image to retrieve.
	 * @return The cached image.
	 */
	public Image request(String file){
		Image img = null;
		if(cache.containsKey(file)){
			img = cache.get(file);
		}else{
			try{
				if(local){
					img = ImageIO.read(getClass().getResourceAsStream("/"+file));
				}else{
					img = ImageIO.read(new URL(path+"/"+file));
				}
				cache.put(file, img);
			}catch(Exception e){
				Debug.error("Cannot open image " + file);
			}
		}
		return img;
	}
}

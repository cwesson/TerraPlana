/**
 * @file Display.java
 * @author Conlan Wesson
 */

package terraplana;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import terraplana.Actor.Actor;
import terraplana.Actor.Player;
import terraplana.Actor.Creature.Creature;
import terraplana.Item.Item;
import terraplana.Movable.Movable;
import terraplana.Terrain.Landscape.Landscape;
import terraplana.Terrain.Terrain;

public class Display extends JPanel implements KeyListener{
	private static final long serialVersionUID = 9023014814223737117L;
	
	private static final int IMG_SIZE = 32;
	private static final int DRAW_HEIGHT = 640;
	private static final int DRAW_WIDTH = 640;
	private JFrame win;
	protected Player player;
	protected Direction direction = Direction.NONE;
	protected int code = KeyEvent.VK_UNDEFINED;
	private ImageCache icache = ImageCache.getInstance();
	
	public Display(Player player) throws Exception{
		this(player, null);
		win = new JFrame("TerraPlana - " + player.getName());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setIconImage(icache.request("img/icon.png"));
		win.add(this);
		win.pack();
		win.setLocationRelativeTo(null);
		win.setVisible(true);
	}
	
	protected Display(Player player, JApplet applet){
		this.player = player;
		player.setDisplay(this);
		this.setPreferredSize(new Dimension(DRAW_WIDTH, DRAW_HEIGHT));
		if(applet != null){
			applet.add(this);
		}
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		new DisplayTimer();
	}
	
	@Override
	public void paint(Graphics graph){
		Board board = player.getTile().getBoard();
		
		Image offscreen = createImage(getWidth(), getHeight());
		Graphics buffer = offscreen.getGraphics();
		
		BufferedImage uiscreen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics uibuffer = uiscreen.getGraphics();
		
		BufferedImage fgscreen = new BufferedImage(DRAW_WIDTH, DRAW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics foreground = fgscreen.getGraphics();
		
		Actor.Status status = player.getStatus();
		
		// Draw background image.
		{
			Image bg = icache.request("img/background.jpg");
			for(int i = 0; i < getHeight(); i+= bg.getHeight(this)){
				for(int j = 0; j < getWidth(); j += bg.getWidth(this)){
					buffer.drawImage(bg, j, i, this);
				}
			}
		}
		
		int px = player.getTile().getBoard().getPosition(player).getX();
		int py = player.getTile().getBoard().getPosition(player).getY();
		int offx = (int)(DRAW_WIDTH/2.0-16);
		int offy = (int)(DRAW_HEIGHT/2.0-16);
		
		// Draw the terrain.
		{
			for(int i = 0; i < board.getHeight(); i++){
				for(int j = 0; j < board.getWidth(); j++){
					Tile cell = board.at(new Position(j, i));
					if(cell != null){
						Terrain terra = cell.getTerrain();
						if(terra != Terrain.VOID){
							Landscape scape = null;
							if(Landscape.class.isAssignableFrom(terra.getClass())){
								scape = (Landscape)terra;
								terra = scape.getTerrain();
							}
							// Draw terrain.
							String type = terra.getClass().getName();
							type = type.substring(type.lastIndexOf(".")+1);
							Image img = icache.request("img/Terrain/" + type + ".png");
							foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
							
							// Draw Landscape.
							if(scape != null){
								type = scape.getClass().getName();
								type = type.substring(type.lastIndexOf(".")+1);
								img = icache.request("img/Landscape/" + type + ".png");
								foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
							}
						}
						
						// Draw items.
						for(Item it : cell.getItems()){
							String type = it.getClass().getName();
							type = type.substring(type.lastIndexOf(".")+1);
							Image img = icache.request("img/Item/" + type + ".png");
							foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
							if(it.getCount() > 1){
								foreground.drawString(it.getCount()+"", ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy);
							}
						}
						
						// Draw movable.
						if(cell.hasMovable()){
							Movable mv = cell.getMovable();
							String type = mv.getClass().getName();
							type = type.substring(type.lastIndexOf(".")+1);
							Image img = icache.request("img/Movable/" + type + ".png");
							foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
						}
					}
				}
			}
		}

		// Draw the start flag.
		{
			int x = board.getStart().getX();
			int y = board.getStart().getY();
			Image img = icache.request("img/Start.png");
			foreground.drawImage(img, ((x-px)*IMG_SIZE)+offx, ((y-py)*IMG_SIZE)+offy, this);
		}

		// Draw the end flag.
		{
			int x = board.getEnd().getX();
			int y = board.getEnd().getY();
			Image img = icache.request("img/End.png");
			foreground.drawImage(img, ((x-px)*IMG_SIZE)+offx, ((y-py)*IMG_SIZE)+offy, this);
		}
		
		// Draw creatures.
		{
			for(Creature creature : board.getCreatures()){
				String type = creature.getClass().getName();
				type = type.substring(type.lastIndexOf(".")+1);
				int ix = creature.getTile().getBoard().getPosition(creature).getX();
				int iy = creature.getTile().getBoard().getPosition(creature).getY();
				Direction dir = creature.getDirection();
				Image img = icache.request("img/Creature/" + type + "-" + dir + ".png");
				foreground.drawImage(img, ((ix-px)*IMG_SIZE)+offx, ((iy-py)*IMG_SIZE)+offy, this);
			}
		}
		
		// Draw foreground border.
		foreground.setColor(Color.BLACK);
		foreground.drawRect(0, 0, DRAW_WIDTH-1, DRAW_HEIGHT-1);
		
		// Draw the player.
		{
			Direction dir = player.getDirection();
			Image img = icache.request("img/Player" + "-" + dir + ".png");
			foreground.drawImage(img, offx, offy, this);
		}

		double centerX = getWidth()/2.0;
		double centerY = getHeight()/2.0;
		double scale = Math.min((double)getWidth()/(double)DRAW_WIDTH, (double)getHeight()/(double)DRAW_HEIGHT);
		double uiscale = scale;
		if(scale > 2){
			uiscale = scale/2.0 + 1.0;
		}
		int uiCenterLeft = (int)(centerX-(DRAW_WIDTH/2.0)*uiscale);
		int uiWidth = (int)(DRAW_WIDTH*uiscale);
		int uiImgSize = (int)(IMG_SIZE*uiscale);
		
		// Draw the health bar.
		{
			int health = player.getHealth();
			uibuffer.setColor(Color.GREEN);
			uibuffer.fill3DRect(uiCenterLeft, 0, uiWidth, (int)(uiImgSize*uiscale), false);
			uibuffer.setColor(Color.RED);
			int width = (int)(((100.0-(double)health)/100.0)*DRAW_WIDTH);
			uibuffer.fill3DRect((int)(uiCenterLeft+(DRAW_WIDTH - width )*uiscale), 0, (int)(width*uiscale), (int)(uiImgSize*uiscale), true);
			
			int lives = player.getLives();
			Image img = icache.request("img/Item/Life.png");
			for(int i = 0; i < lives; i++){
				uibuffer.drawImage(img, uiCenterLeft+(i*uiImgSize), 0, (int)(uiImgSize*uiscale), (int)(uiImgSize*uiscale), this);
			}
		}
		
		// Draw inventory.
		{
			uibuffer.setColor(Color.GRAY);
			uibuffer.fill3DRect(uiCenterLeft, (int)(getHeight()-uiImgSize), uiWidth, uiImgSize,true);
			uibuffer.fill3DRect(uiCenterLeft+2, getHeight()-uiImgSize+2, uiImgSize-5, uiImgSize-5, false);
			uibuffer.setColor(Color.BLACK);
			int count = 0;
			for(Item it : player.getInventory()){
				String type = it.getClass().getName();
				type = type.substring(type.lastIndexOf(".")+1);
				Image img = icache.request("img/Item/" + type + ".png");
				uibuffer.drawImage(img, uiCenterLeft+(count*uiImgSize), getHeight()-uiImgSize, uiImgSize, uiImgSize, this);
				if(it.getCount() > 1){
					uibuffer.drawString(it.getCount()+"", uiCenterLeft+(count*uiImgSize), getHeight()-2);
				}
				count++;
			}
		}
		
		// Draw message box.
		if(status != Player.Status.STATUS_OK){
			uibuffer.setColor(Color.GRAY);
			int boxTop = (int)(centerY-uiImgSize*4/3);
			uibuffer.fill3DRect((int)(centerX-uiWidth/3), boxTop, uiWidth*2/3, uiImgSize*3, true);
			uibuffer.setColor(Color.BLACK);
			
			Font font = uibuffer.getFont();
			Font fontsm = font.deriveFont(font.getSize()*(float)uiscale);
			Font fontlg = font.deriveFont(font.getSize()*(float)uiscale*2);
			
			{
				String str = "Game Over";
				uibuffer.setFont(fontlg);
				int strwidth = (int)uibuffer.getFontMetrics().stringWidth(str);
				int strheight = (int)uibuffer.getFontMetrics().getHeight();
				uibuffer.drawString(str, (int)centerX-strwidth/2, boxTop+strheight);
			}
			
			if(status == Player.Status.STATUS_DEAD){
				String msg = "You died.";
				uibuffer.setFont(fontsm);
				int strwidth = (int)uibuffer.getFontMetrics().stringWidth(msg);
				int strheight = (int)uibuffer.getFontMetrics().getHeight();
				uibuffer.drawString(msg, (int)centerX-strwidth/2, (int)centerY+strheight);
			}else if(status == Player.Status.STATUS_DONE){
				String msg = "You Win!";
				uibuffer.setFont(fontsm);
				int strwidth = (int)uibuffer.getFontMetrics().stringWidth(msg);
				int strheight = (int)uibuffer.getFontMetrics().getHeight();
				uibuffer.drawString(msg, (int)centerX-strwidth/2, (int)centerY+strheight);
			}
		}

		int fgCenterLeft = (int)(centerX-(DRAW_WIDTH/2.0)*scale);
		int fgWidth = (int)(DRAW_WIDTH*scale);
		buffer.drawImage(fgscreen, fgCenterLeft, (int)((getHeight()/2)-(DRAW_HEIGHT/2)*scale), fgWidth, (int)(scale*DRAW_HEIGHT), this);
		buffer.drawImage(uiscreen, 0, 0, this);
		graph.drawImage(offscreen, 0, 0, this);
	}

	@Override
	public void keyPressed(KeyEvent key){
		Debug.info("keyPressed");
		code = key.getKeyCode();
	}
	
	public Direction nextMove(){
		Direction dir = Direction.NONE;
		switch(code){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				dir = Direction.NORTH;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				dir = Direction.SOUTH;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				dir = Direction.WEST;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				dir = Direction.EAST;
				break;
		}
		code = KeyEvent.VK_UNDEFINED;
		return dir;
	}

	@Override
	public void keyReleased(KeyEvent key){
		// ignore.
	}

	@Override
	public void keyTyped(KeyEvent key){
		// ignore.
	}

	private class DisplayTimer extends TimerTask{
		private Timer timer = new Timer();
		
		public DisplayTimer(){
			timer.scheduleAtFixedRate(this, 0, 30);
		}
		
		@Override
		public void run(){
			repaint();
		}
	}
}

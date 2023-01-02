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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import terraplana.Actor.Actor;
import terraplana.Actor.Player;
import terraplana.Actor.Creature.Creature;
import terraplana.Item.Item;
import terraplana.Movable.Movable;
import terraplana.Projectile.Projectile;
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
	private ContentLoader loader = ContentLoader.getInstance();
	
	public Display(Player player) throws Exception{
		this.player = player;
		player.setDisplay(this);
		this.setPreferredSize(new Dimension(DRAW_WIDTH, DRAW_HEIGHT));

		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		new DisplayTimer();
		
		win = new JFrame("TerraPlana - " + player.getName());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setIconImage(icache.request("img/icon.png"));
		win.add(this);
		win.pack();
		win.setLocationRelativeTo(null);
		win.setVisible(true);
	}
	
	BufferedImage drawBoard(Board board, BufferedImage fgscreen){
		Graphics foreground = fgscreen.getGraphics();
		
		Position pos = board.getPosition(player);
		if(pos == null) {
			return fgscreen;
		}
		
		int px = pos.getX();
		int py = pos.getY();
		int offx = (int)(DRAW_WIDTH/2.0-16);
		int offy = (int)(DRAW_HEIGHT/2.0-16);
		
		// Draw the terrain.
		{
			for(int i = 0; i < board.getHeight(); i++){
				for(int j = 0; j < board.getWidth(); j++){
					Position loc = new Position(j, i);
					Tile cell = board.at(loc);
					if(cell != null){
						Terrain terra = cell.getTerrain();
						if(terra != Terrain.VOID){
							ArrayList<Landscape> scapes = new ArrayList<Landscape>();
							while(Landscape.class.isAssignableFrom(terra.getClass())){
								Landscape ls = (Landscape)terra;
								scapes.add(ls);
								terra = ls.getTerrain();
							}
							// Draw terrain.
							Image img = icache.request((Sprite)terra);
							foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
							
							// Draw Landscape.
							for(Landscape scape : scapes){
								img = icache.request((Sprite)scape);
								foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
							}
						}
						
						// Draw items.
						for(Item it : cell.getItems()){
							String type = it.getClass().getName();
							Image img = loader.getImage(type);
							foreground.drawImage(img, ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy, this);
							if(it.getCount() > 1){
								foreground.drawString(it.getCount()+"", ((j-px)*IMG_SIZE)+offx, ((i-py)*IMG_SIZE)+offy);
							}
						}
						
						// Draw movable.
						if(cell.hasMovable()){
							Movable mv = cell.getMovable();
							String type = mv.getClass().getName();
							Image img = loader.getImage(type);
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
		synchronized(board){
			for(Creature creature : board.getCreatures()){
				int ix = creature.getTile().getBoard().getPosition(creature).getX();
				int iy = creature.getTile().getBoard().getPosition(creature).getY();
				Image img = icache.request((Sprite)creature);
				foreground.drawImage(img, ((ix-px)*IMG_SIZE)+offx, ((iy-py)*IMG_SIZE)+offy, this);
			}
		}
		
		// Draw Projectiles.
		{
			for(Projectile proj : board.getProjectiles().keySet()) {
				int ix = board.getPosition(proj).getX();
				int iy = board.getPosition(proj).getY();
				Image img = icache.request((Sprite)proj);
				foreground.drawImage(img, ((ix-px)*IMG_SIZE)+offx, ((iy-py)*IMG_SIZE)+offy, this);
			}
		}
		
		// Draw the player.
		{
			Image img = icache.request((Sprite)player);
			foreground.drawImage(img, offx, offy, this);
		}
		
		// Draw foreground border.
		foreground.setColor(Color.BLACK);
		foreground.drawRect(0, 0, DRAW_WIDTH-1, DRAW_HEIGHT-1);
		
		return fgscreen;
	}
	
	@Override
	public void paint(Graphics graph){
		Board board = player.getTile().getBoard();
		
		Image offscreen = createImage(getWidth(), getHeight());
		Graphics buffer = offscreen.getGraphics();
		
		BufferedImage uiscreen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics uibuffer = uiscreen.getGraphics();
		
		BufferedImage fgscreen = new BufferedImage(DRAW_WIDTH, DRAW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		if(board != null) {
			drawBoard(board, fgscreen);
		}
		
		// Draw background image.
		{
			Image bg = icache.request("img/background.jpg");
			for(int i = 0; i < getHeight(); i+= bg.getHeight(this)){
				for(int j = 0; j < getWidth(); j += bg.getWidth(this)){
					buffer.drawImage(bg, j, i, this);
				}
			}
		}

		double centerX = getWidth()/2.0;
		double centerY = getHeight()/2.0;
		double scale = Math.round(Math.min((double)getWidth()/(double)DRAW_WIDTH, (double)getHeight()/(double)DRAW_HEIGHT));
		int uiCenterLeft = (int)(centerX-(DRAW_WIDTH/2.0)*scale);
		int uiWidth = (int)(DRAW_WIDTH*scale);
		int uiImgSize = (int)(IMG_SIZE*scale);
		
		// Draw the health bar.
		{
			int health = player.getHealth();
			uibuffer.setColor(Color.GREEN);
			uibuffer.fill3DRect(uiCenterLeft, 0, uiWidth, uiImgSize, false);
			uibuffer.setColor(Color.RED);
			int width = (int)(((100.0-(double)health)/100.0)*DRAW_WIDTH);
			uibuffer.fill3DRect((int)(uiCenterLeft+(DRAW_WIDTH - width )*scale), 0, (int)(width*scale), uiImgSize, true);
			
			int lives = player.getLives();
			Image img = icache.request("img/Item/Life.png");
			for(int i = 0; i < lives; i++){
				uibuffer.drawImage(img, uiCenterLeft+(i*uiImgSize), 0, uiImgSize, uiImgSize, this);
			}
		}
		
		// Draw inventory.
		{
			uibuffer.setColor(Color.GRAY);
			uibuffer.fill3DRect(uiCenterLeft, (int)(getHeight()-uiImgSize), uiWidth, uiImgSize,true);
			int selected = player.getSelected();
			int count = 0;
			for(Item it : player.getInventory()){
				String type = it.getClass().getName();
				Image img = loader.getImage(type);
				if(selected == count){
					uibuffer.setColor(Color.GRAY);
					uibuffer.fill3DRect(uiCenterLeft+(count*uiImgSize)+2, getHeight()-uiImgSize+2, uiImgSize-5, uiImgSize-5, false);
				}
				uibuffer.drawImage(img, uiCenterLeft+(count*uiImgSize), getHeight()-uiImgSize, uiImgSize, uiImgSize, this);
				if(it.getCount() > 1){
					uibuffer.setColor(Color.BLACK);
					uibuffer.drawString(it.getCount()+"", uiCenterLeft+(count*uiImgSize), getHeight()-2);
				}
				count++;
			}
		}

		Actor.Status status = player.getStatus();
		// Draw message box.
		if(status != Player.Status.STATUS_OK){
			uibuffer.setColor(Color.GRAY);
			int boxTop = (int)(centerY-uiImgSize*4/3);
			uibuffer.fill3DRect((int)(centerX-uiWidth/3), boxTop, uiWidth*2/3, uiImgSize*3, true);
			uibuffer.setColor(Color.BLACK);
			
			Font font = uibuffer.getFont();
			Font fontsm = font.deriveFont(font.getSize()*(float)scale);
			Font fontlg = font.deriveFont(font.getSize()*(float)scale*2);
			
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
			case KeyEvent.VK_E:
				player.useSelected();
				break;
			case KeyEvent.VK_1:
				player.setSelected(0);
				break;
			case KeyEvent.VK_2:
				player.setSelected(1);
				break;
			case KeyEvent.VK_3:
				player.setSelected(2);
				break;
			case KeyEvent.VK_4:
				player.setSelected(3);
				break;
			case KeyEvent.VK_5:
				player.setSelected(4);
				break;
			case KeyEvent.VK_6:
				player.setSelected(5);
				break;
			case KeyEvent.VK_7:
				player.setSelected(6);
				break;
			case KeyEvent.VK_8:
				player.setSelected(7);
				break;
			case KeyEvent.VK_9:
				player.setSelected(8);
				break;
			case KeyEvent.VK_0:
				player.setSelected(9);
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

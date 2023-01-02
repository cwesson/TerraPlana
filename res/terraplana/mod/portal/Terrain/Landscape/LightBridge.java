/**
 * @file LightBridge.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Tile;
import terraplana.Actor.Actor;
import terraplana.Movable.Movable;
import terraplana.Terrain.Landscape.Landscape;

public class LightBridge extends Landscape {
	enum BridgeDirection {
		NORTH_SOUTH(0),
		WEST_EAST(1);
		
		private final int dir;
		
		private BridgeDirection(int dir){
			this.dir = dir;
		}
		
		public int index(){
			return dir;
		}
	}
	private final BridgeDirection dir;

	public LightBridge(Tile place, String[] args) {
		super(place);
		
		if(args[0].equals("north-south")){
			dir = BridgeDirection.NORTH_SOUTH;
		}else if(args[0].equals("west-east")){
			dir = BridgeDirection.WEST_EAST;
		}else{
			dir = BridgeDirection.NORTH_SOUTH;
		}
	}
	
	public LightBridge(Tile place, BridgeDirection d) {
		super(place);
		
		dir = d;
	}

	@Override
	public void deactivate(){
		if(BridgeProjector.class.isAssignableFrom(this.getTerrain().getClass())) {
			((Landscape)this.getTerrain()).deactivate();
		}
	}
	
	@Override
	public boolean onEntered(Actor player, Direction dir, Tile last){
		return true;
	}
	
	@Override
	public boolean onEntered(Movable move, Direction dir, Tile last){
		return true;
	}
	
	@Override
	public boolean onExit(Actor actor, Direction dir, Tile next){
		return true;
	}
	
	@Override
	public Direction onExit(Movable move, Direction dir, Tile next){
		return dir;
	}

	@Override
	public int spriteX(){
		return dir.index();
	}

}

/**
 * @file BridgeProjector.java
 * @author Conlan Wesson
 */

package terraplana.mod.portal.Terrain.Landscape;

import terraplana.Direction;
import terraplana.Position;
import terraplana.Tile;
import terraplana.Terrain.Terrain;
import terraplana.Terrain.Landscape.Landscape;

public class BridgeProjector extends Landscape {
	private final Direction edge;
	private final Position pos;
	
	public BridgeProjector(Tile place, String[] args) {
		super(place);

		if(args[0].equals("north")){
			edge = Direction.NORTH;
		}else if(args[0].equals("east")){
			edge = Direction.EAST;
		}else if(args[0].equals("south")){
			edge = Direction.SOUTH;
		}else if(args[0].equals("west")){
			edge = Direction.WEST;
		}else{
			edge = Direction.NORTH;
		}

		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		pos = new Position(x, y);
	}
	
	@Override
	public void activate(){
		LightBridge.BridgeDirection dir;
		if(edge == Direction.NORTH || edge == Direction.SOUTH) {
			dir = LightBridge.BridgeDirection.NORTH_SOUTH;
		}else {
			dir = LightBridge.BridgeDirection.WEST_EAST;
		}

		Position scan = pos.clone();
		for(Tile place = tile.getBoard().at(scan);
				place.getTerrain().hasAttribute("hazard");
				place = place.getBoard().at(scan)
		){
			place.setTerrain(new LightBridge(place, dir));
			scan.move(edge.invert());
		}
	}
	
	@Override
	public void deactivate(){
		Position scan = pos.clone();
		for(Tile place = tile.getBoard().at(scan);
				LightBridge.class.isAssignableFrom(place.getTerrain().getClass());
				place = place.getBoard().at(scan)
		){
			Terrain terra = ((Landscape)place.getTerrain()).getTerrain();
			place.setTerrain(terra);
			scan.move(edge.invert());
		}
	}

	@Override
	public int spriteY(){
		return edge.index();
	}

}

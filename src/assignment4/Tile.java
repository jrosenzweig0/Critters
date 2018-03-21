package assignment4;

import java.util.*;

/**
 * This class stores the list of critters that are on a particular tile.
 * At the end of each time step, we loop through each tile and if there is more than one critter on a particuler
 * tile, then we will deal with encounters
 * @author jonathan
 *
 */
public class Tile {
	private int x; // the x position of the tile
	private int y; // the y position of the tile
	private ArrayList<Critter> filled; // A list of the critters on a particular tile
	//private boolean hasAlgae;
	
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.filled = new ArrayList<Critter>();
	}
	/**
	 * This method gets the position of a specific tile. I am not sure if we will need it or not
	 * @return returns an array that contains the position in the form [x,y]
	 */
	public int[] getPosition() {
		int[] position = new int[]{this.x,this.y};
		return position;
	}
	
	/**
	 * This method returns the list of critters on a particular tile.
	 * @return returns list of critters on a particular tile. If there are no critters than it will return an 
	 * empty ArrayList
	 */
	public ArrayList<Critter> crittersOnTile() {
		return this.filled;
	}
	
	/**
	 * This method adds a critter to a tile
	 * @param filled is the critter objsct that we are adding to the list of critters that are on the Tile
	 */
	public void setFilled(Critter filled) {
		this.filled.add(filled);
	}

	/**
	 * This method removes a critter from the tile
	 * @param dead is the critter object that we are removing from the list of critters that are on the Tile
	 */
	public void remove(Critter dead){
		this.filled.remove(dead);
	}
	/**
	 * This is a getter method that returns true if there is Algae on the tile
	 * @return retunrs true or false depending if there is Algae on the tile
	 */
//	public boolean getAlgae() {
//		return this.hasAlgae;
//	}
	
	/**
	 * This function sets the value of hasAlgae to a boolean value that you input
	 * @param z
	 */
//	public void setAlgae(boolean z) {
//		this.hasAlgae = z;
//	}
}

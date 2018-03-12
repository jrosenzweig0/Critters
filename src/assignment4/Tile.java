package assignment4;

import java.util.*;

public class Tile {
	private int x;
	private int y;
	private ArrayList<Critter> filled - new ArrayList<Critter>;
	
	public int[] getPosition() {
		int[] position = new int[]{this.x,this.y};
		return position;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int[] position) {
		this.x = position[0];
		this.y = position[1];
	}
	
	public ArrayList<Critter> isFilled() {
		return this.filled;
	}
	
	public void setFilled(Critter filled) {
		this.filled.add(filled);
	}
}

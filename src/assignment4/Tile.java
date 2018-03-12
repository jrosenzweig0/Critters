package assignment4;

public class Tile {
	private int x;
	private int y;
	private boolean filled;
	
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
	
	public boolean isFilled() {
		return this.filled;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
}

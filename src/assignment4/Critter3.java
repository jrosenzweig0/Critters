package assignment4;
/* CRITTERS Critter3.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Jonathan Rosenzweig>
 * <JJR3349>
 * <15466>
 * <Student2 Zach Sisti>
 * <Student2 zes279>
 * <15495>
 * Slip days used: <0>
 * Fall 2016
 */

import assignment4.Critter.TestCritter;

public class Critter3 extends TestCritter {
	private int reproduceLimit;
	private double algae;
	private boolean hasmoved;
	public int direction;
	public Critter3() {
		if(getRandomInt(2) == 0) {
			reproduceLimit=50;
		}
		else {
			reproduceLimit = 200;
		}
		direction = getRandomInt(8);
	}
	
	@Override
	public void doTimeStep() {
		hasmoved = false;
		try {
			algae = ((double)Critter.getInstances("assignment4.Algae").size())/((double)(Params.world_height*Params.world_width));
		} catch (InvalidCritterException e) {
			algae = 1.0;
		}
		if(algae > 0.06) {
			walk(direction);
			hasmoved = true;
		}
		
		if (getEnergy() > reproduceLimit) {
			Critter3 child = new Critter3();
			reproduce(child, Critter.getRandomInt(8));
		}
		


	}

	@Override
	public boolean fight(String opponent) {
		if(hasmoved) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString () {
		if(reproduceLimit == 50)
			return "b";
		else
			return "B";
	}
}

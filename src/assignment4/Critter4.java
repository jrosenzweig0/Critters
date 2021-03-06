package assignment4;

import assignment4.Critter.TestCritter;

/**
 * This critter has a 5% chance of reproducing an impotent critter (A critter that does not reproduce)
 * this critter runs if is high on energy and walks if it is low on energy
 * This critter waits untill the percentage of algae on the screen is greater than 4%
 * @author jonathan
 *
 */
public class Critter4 extends TestCritter {
	private int reproduceLimit; //the energy it needs to have for it to reproduce 
	private double algae; // the percentage of algae in the world. a number between 0 and 1
	private boolean hasmoved; //says whether or not a critter has moved
	public boolean reproduce; //true if it can reproduce 
	
	/**
	 * This is the constructor for the critter it sets the constants
	 */
	public Critter4() {
		if(getRandomInt(20) == 0) {
			reproduce = false;
		}
		else{
			reproduce = true; // sets the reproduce flag to true
		}
		reproduceLimit = 120; //set reproduce energy to 120


	}
	  
	@Override
	/**
	 * This method does the time step for the critter.
	 */
	public void doTimeStep() {
		hasmoved = false;
		try {
			algae = ((double)Critter.getInstances("assignment4.Algae").size())/((double)(Params.world_height*Params.world_width));
		} catch (InvalidCritterException e) {
			algae = 1.0;
		}
		if(algae > 0.04) { //it only moves if there is more than 4% algae in the world
			if(this.getEnergy()>50) run(getRandomInt(8)); //runs if energy is high
			else walk(getRandomInt(8)); //walks if energy is low
			hasmoved = true; // sets the hasmoved flag to true so that it does not try and run in the fight
		}
		
		if (getEnergy() > reproduceLimit && reproduce) {
			Critter3 child = new Critter3();
			reproduce(child, Critter.getRandomInt(8));
		}
		


	}

	@Override
	/**
	 * this method determines if the critter is going to fight or run if it encounters another critter
	 * if it returns true it will fight otherwise 
	 * @return returns a boolean for if it will fight or try and run
	 */
	public boolean fight(String opponent) {
		if(hasmoved) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	/**
	 * this is the to string method for the critter
	 * @return returns a character representing what it will look like on the grid
	 */
	public String toString () {
		return "A";
	}
	/**
	 * This method displays some stats about the critter
	 * It prints the number of critters that can reproduce and the number of critters that are impotent
	 * @param My1s is the list of critter4s in the population list
	 */
	public static void runStats(java.util.List<Critter> My1s) {
		int total=0;
		for (int i=0; i<My1s.size(); i++){
			if(((Critter4)My1s.get(i)).reproduce)
				total += 1;
		}
		int total1 = My1s.size() - total;
		System.out.print("" + My1s.size() + " total Critter1s    ");
		System.out.println(" with " + total + " reproducers and " + total1 + " impotants");
	}

}
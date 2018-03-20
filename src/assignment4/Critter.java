package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.*;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Map<Integer, Map<Integer, Tile>> world; //2D map (Since we have 2 keys: x,y) holding the tile objects
	private static boolean firstTime = true;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return "%"; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	/**
	 * This method simulates a critter walking
	 * @param direction is the direction the critter moves. it is an integer between 0 and 7 where 
	 * 0 represents moving right 1 represents moving right and up and so on
	 */
	protected final void walk(int direction) {
		if (this.energy > Params.walk_energy_cost) {
			this.energy -= Params.walk_energy_cost;
			this.move(direction);
		}
		else {
			this.energy = 0;
			this.death();
		}
	}
	
	/**
	 * This method simulates a critter running
	 * @param direction is the direction the critter moves. it is an integer between 0 and 7 where 0 
	 * represents moving right 1 represents moving right and up and so on
	 */
	protected final void run(int direction) {
		if (this.energy > Params.run_energy_cost) {
			this.energy -= Params.run_energy_cost;
			this.move(direction);
			this.move(direction);
		}
		else {
			this.energy = 0;
			this.death();
		}
	}
	/**
	 * This method moves the critter one tile in the specified direction
	 * @param direction is an integer between 0 and 7 that specifies the direction the critter moves
	 */
	private void move(int direction) {
		if (population.contains(this)) world.get(y_coord).get(x_coord).remove(this);

		switch (direction) {
		case 0: x_coord += 1; break;
		case 1: x_coord += 1; y_coord -= 1; break;
		case 2: y_coord -= 1; break;
		case 3: y_coord -= 1; x_coord -= 1; break;
		case 4: x_coord -= 1; break;
		case 5: x_coord -= 1; y_coord += 1; break;
		case 6: y_coord += 1; break;
		case 7: y_coord += 1; x_coord += 1; break;
		}

		if (population.contains(this)) world.get(y_coord).get(x_coord).setFilled(this);
	}
	
	/**
	 * This method takes the offspring that another critter produced and initialized it giving
	 * it half of the parents energy rounded down and putting it in a position next to 
	 * the parent indicated by the direction parameter
	 * @param offspring is the reference to the critter that the parent created
	 * @param direction is an integer between 0 and 7 that represents the direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = this.energy/2;
		this.energy = this.energy - offspring.energy;
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.move(direction);
		babies.add(offspring);
	}

	/**
	 * This method effectively kills a critter
	 */
	private void death(){
		population.remove(this);
		(world.get(this.y_coord).get(this.x_coord)).remove(this);
	}

	/**
	 * Determine the result of an encounter between 2 Critters
	 * @param A Critter encountering B
	 * @param B Critter encountering A
	 */
	private static void encounter(Critter A, Critter B){
		if(!A.fight(B.toString())) {				//Determine if A will run or fight
			A.run(getRandomInt(8));
			return;
		}
		if(!B.fight(A.toString())) {				//Determine if B will run or fight
			B.run(getRandomInt(8));
			return;
		}
		int aAttack = getRandomInt(A.energy+1);	//Determine attack value of A and B based on energy and RNG
		int bAttack = getRandomInt(B.energy+1);
		if (aAttack > bAttack){						//The one with the higher attack gains half the other's energy and lives
			A.energy += (B.energy)/2;
			B.death();
		}
		else if (bAttack > aAttack){
			B.energy += (A.energy)/2;
			A.death();
		}
		else {
			int winner = getRandomInt(2);		//If they have the same attack value, randomly decide winner
			if (winner == 0) {
				A.energy += (B.energy)/2;
				B.death();
			}
			else {
				B.energy += (A.energy)/2;
				A.death();
			}
		}
	}


	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		try {
			Class c = Class.forName(critter_class_name);
			//(c.getClasses()) c.newInstance();
		} 
		catch (ClassNotFoundException e){
			
		}

	}

	/**
	 * Fills the world Map with Tiles
	 */
	private static void createWorld(){
		world = new HashMap<Integer, Map<Integer, Tile>>(); 		//Map holding the Maps of each row
		for (int i=0; i<Params.world_height; i++){  				//i represents the y coordinate
			Map<Integer, Tile> row = new HashMap<Integer, Tile>();  //Map of all of the tiles with y coordinate i
			for (int j=0; j<Params.world_width; j++){ 				//j represents the x coordinate
				Tile newTile = new Tile(j,i);
				row.put(j, newTile);
			}
			world.put(i, row);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
	}

	/**
	 * Calls doTimeStep for each critter in the world, then resolves Tiles with more than one critter
	 */
	public static void worldTimeStep() {
		if (firstTime) {												//if this is the first worldTimeStep create world
			createWorld();
			firstTime = false;
		}
		for (int i=0; i<population.size(); i++){						//call each Critter's doTimeStep
			population.get(i).doTimeStep();
		}
		for (int i=0; i<Params.world_height; i++){						//find any Tile with more than one critter, and have them fight
			for(int j=0; j<Params.world_width; j++){
				ArrayList<Critter> crits = world.get(i).get(j).crittersOnTile();
				while(crits.size()>1){
					crits.get(0).fight(crits.get(1).toString());
				}
			}
		}
		//do encounters
		for(Critter c: population) {
			c.energy -= Params.rest_energy_cost;
			if(c.energy <= 0)
				c.death();
		}
		//generate algae
		for(Critter baby: babies) {
			population.add(baby);
		}
		babies.clear();
		
	}
	
	public static void displayWorld() {
		// Complete this method.
	}
}

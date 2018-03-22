package assignment4;

import assignment4.Critter.TestCritter;

public class MyCritter7 extends TestCritter {
	public int reproduceLimit;

	public int direction;
	public MyCritter7() {
		reproduceLimit = getRandomInt(250);
		direction = getRandomInt(8);
	}
	
	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(direction);
		
		if (getEnergy() > reproduceLimit) {
			MyCritter7 child = new MyCritter7();
			reproduce(child, Critter.getRandomInt(8));
		}
		


	}

	@Override
	public boolean fight(String opponent) {

		return true;
	}

	@Override
	public String toString () {
		return "J";
	}
}

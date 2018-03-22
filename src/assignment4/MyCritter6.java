package assignment4;

import assignment4.Critter.TestCritter;

import java.util.*;

import static java.lang.Math.*;

public class MyCritter6 extends TestCritter {
	/**
	 * construct sets fought to 0
	 */

	public MyCritter6(){
		fought=0;
	}
	private int fought;		//energy to be deducted at the beginning of the next time step (70% of energy at the time of the previous turn's fight)

	/**
	 * Determines if and how MyCritter6 will walk and reproduce (and lose energy if fought last turn) each timestep
	 */
	@Override
	public void doTimeStep() {
		if (fought>0) {						//if fought previous turn deduct fought
			setEnergy(getEnergy()-fought);
			fought=0;
		}
		if (getEnergy()<25){				//only move if energy is below threshold (25)
			walk(getRandomInt(8));
		}
		if (getEnergy()>=60){				//only reproduce if energy is above threshold (120)
			MyCritter6 child = new MyCritter6();
			reproduce(child, getRandomInt(8));
		}
	}

	/**
	 * Determines if MyCritter6 will fight
	 * @param opponent String representing opponent Class symbol
	 * @return boolean indicating if MyCritter6 will fight
	 */
	@Override
	public boolean fight(String opponent) {
		if (getEnergy()>20){						//only fight if energy is above threshold (20)
			fought = (int)(getEnergy()*(0.7));		//When entering a fight, increase energy by 50%, but lose 70% after fight ends
			setEnergy(getEnergy()+(getEnergy()/2));
			return true;
		}
		return false;
	}

	/**
	 * gets symbol of MyCritter6
	 * @return String representing symbol of MyCritter6
	 */
	@Override
	public String toString () {
		return "!";
	}

	public static void runStats(java.util.List<Critter> My6s) {
		int g100 = 0;
		int l30 = 0;
		for (int i=0; i<My6s.size(); i++){
			if (My6s.get(i).getEnergy()>=100) g100++;
			if (My6s.get(i).getEnergy()<30) l30++;
		}
		System.out.print("" + My6s.size() + " total Critter6s    ");
		System.out.println("" + g100 + " thriving, and " + l30 + " on death's door");
	}
}

package assignment4;

import java.util.*;

import static java.lang.Math.*;

public class MyCritter1 extends Critter.TestCritter{

	/**
	 * gets the distance to another Critter using the distance formula
	 * @param friend Critter whose distance we are solving for
	 * @return double representing distance to friend
	 */
	public double getDist(Critter friend){
		MyCritter1 fr = (MyCritter1)friend;							//cast to MyCritter1
		double distance = sqrt((fr.getX_coord()-this.getX_coord())*(fr.getX_coord()-this.getX_coord())+	//distance formula
				(fr.getY_coord()-this.getY_coord())*(fr.getY_coord()-this.getY_coord()));
		return distance;
	}

	/**
	 * gets the direction from 0-8 that this Critter would have to walk to get to friend
	 * @param friend Critter whose direction we are interested in
	 * @return int from 0-8 representing direction that friend is in
	 */
	public int getDirec(Critter friend){
		MyCritter1 fr = (MyCritter1)friend;							//cast to MyCritter1
		double x = (double)(fr.getX_coord()-this.getX_coord());		//+ means to the right, - means to the left
		double y = (double)(this.getY_coord()-this.getY_coord());		//+ means up, - means down
		if (x==0 && y==0) return getRandomInt(8); //if in the same tile go in a random direcion
		if (x==0) return (y>0) ? 2 : 6;				//if x is the same go up or down
		if (y==0) return (x>0) ? 0 : 4;				//if y is the same go left or right
		double angle=0.0;				 				//get angle
		double frac=0.0;
		if (x>0 && y>0) {		 				//first quad tan(y/x)
			frac = y/x;
			angle = Math.atan(frac);
		}
		else if (x<0 && y>0) {					//second quad 180-tan(y/(-x))
			frac = y/((-1.0)*x);
			angle = (Math.PI)-Math.atan(frac);
		}
		else if (x<0 && y<0) {					//third quad 180+tan(y/x)
			frac = y/x;
			angle = (Math.PI)+Math.atan(frac);
		}
		else {									//fourth quad 360-tan((-y)/x)
			frac = ((-1.0)*y)/x;
			angle = ((2.0)*Math.PI)-Math.atan(frac);
		}
		double direc = (angle/((2.0)*Math.PI))*(8.0);		//convert angle to int from 0 to 7
		int dir = (int)Math.round(direc);
		if (dir == 8) dir = 0;
		return dir;
	}

	/**
	 * determines if and how MyCritter1 will walk and reproduce each time step
	 */
	@Override
	public void doTimeStep() {
		try {                                                                //gets list of MyCritter1s
				List<Critter> friends = Critter.getInstances("assignment4.MyCritter1");
				double bestDistance = getDist(friends.get(0));					//double representing distance to closest MyCritter1
				int bestFriend = 0;												//index of closest MyCritter1 in friends
				if ((friends.get(bestFriend) == this)&&(friends.size()>1)) {	//makes start Critter!=this
					bestFriend = 1;
					bestDistance = getDist( friends.get(bestFriend));
				}
				for (int i = 0; i < friends.size(); i++) {						//checks all MyCritter1s to find closest
					if (getDist(friends.get(i)) < bestDistance && friends.get(i) != this) {
						bestDistance = getDist(friends.get(i));
						bestFriend = i;
					}
				}
				Critter bFriend = friends.get(bestFriend);						//Critter representing closest MyCritter1
				if (bFriend==this) walk(getRandomInt(8));					//if this is the only MyCritter1, walk in random direction
				else walk(getDirec(bFriend));									//if not walk toward nearest Critter
			} catch (InvalidCritterException e) {                                //if InvalidCritterException print it
				System.out.println(e);
			} catch (Exception e) {                                            //if other exception print stack trace
				e.printStackTrace();
			}
		if (this.getEnergy()>=100) {											//if energy is above threshold (120) reproduce
			MyCritter1 baby = new MyCritter1();
			reproduce(baby,getRandomInt(8));
		}
	}

	/**
	 * Determines if MyCritter1 will fight
	 * @param opponent String representing opponents Class symbol
	 * @return boolean representing MyCritter1's willingness to fight
	 */
	@Override
	public boolean fight(String opponent) {
		if ((getEnergy() > 10) && (!opponent.equals("Z"))) return true;			//if energy is above threshold (10) and opponent is not a MyCritter1 fight
		return false;
	}

	public static void runStats(java.util.List<Critter> My1s) {
		int total=0;
		for (int i=0; i<My1s.size(); i++){
			total+=My1s.get(i).getEnergy();
		}
		System.out.print("" + My1s.size() + " total Critter1s    ");
		System.out.println(" with an average of " + total/(My1s.size()) + "  energy");
	}

	/**
	 * gets symbol of MyCritter1
	 * @return String representing symbol of MyCritter1
	 */
	public String toString() {
		return "Z";
	}

}

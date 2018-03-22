package assignment4;

import java.util.*;

import static java.lang.Math.*;

public class MyCritter1 extends Critter.TestCritter{
	public MyCritter1() {
	}

	private double getDist(Critter friend){
		MyCritter1 fr = (MyCritter1)friend;
		double distance = sqrt((fr.getX_coord()-this.getX_coord())*(fr.getX_coord()-this.getX_coord())+
				(fr.getY_coord()-this.getY_coord())*(fr.getY_coord()-this.getY_coord()));
		return distance;
	}

	private int getDirec(Critter friend){
		MyCritter1 fr = (MyCritter1)friend;
		int x = fr.getX_coord()-this.getX_coord();		//+ means to the right, - means to the left
		int y = this.getY_coord()-fr.getY_coord();		//+ means up, - means down
		if (x==0 && y==0) return getRandomInt(8); //if in the same tile go in a random direcion
		if (x==0) return (y>0) ? 2 : 6;				//if x is the same go up or down
		if (y==0) return (x>0) ? 0 : 4;				//if y is the same go left or right
		double angle;								//get angle
		if (x>0 && y>0) angle = Math.tan(y/x);		//first quad tan(y/x)
		else if (x<0 && y>0) angle = Math.tan(y/(-1*x)); //second quad 180-tan(y/(-x))
		else if (x<0 && y<0) angle = Math.tan((-1*y)/(-1*x)); //third quad 180+tan((-y)/(-x))
		else angle = Math.tan((-1*y)/x);			//fourth quad 360-tan((-y)/x)
		double direc = (angle/(2*Math.PI))*8;		//convert angle to int from 0 to 7
		int dir = (int)Math.round(direc);
		if (dir == 8) dir = 0;
		return dir;
	}

	@Override
	public void doTimeStep() {
		if (this.getEnergy()>=25) {
			try {																//gets list of My
				List<Critter> friends = Critter.getInstances("assignment4.MyCritter1");
				double bestDistance = getDist(friends.get(0));
				int bestFriend = 0;
				for (int i = 0; i < friends.size(); i++) {
					if (getDist(friends.get(i)) < bestDistance) {
						bestDistance = getDist(friends.get(i));
						bestFriend = i;
					}
				}
				Critter bFriend = friends.get(bestFriend);
				walk(getDirec(bFriend));
			} catch (InvalidCritterException e) {                                //if InvalidCritterException print it
				System.out.println(e);
			} catch (Exception e) {                                            //if other exception print stack trace
				e.printStackTrace();
			}
		}
		else if (this.getEnergy()>=120) {
			MyCritter1 baby = new MyCritter1();
			reproduce(baby,getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		if ((getEnergy() > 10) && (!opponent.equals("z"))) return true;
		return false;
	}
	
	public String toString() {
		return "z";
	}

}

package assignment4;
/* CRITTERS Main.java
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.lang.Integer;
import java.util.concurrent.TimeUnit;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }
        /* Do not alter the code above for your submission. */


        //parse and make input command
        boolean quitted = false;
        String command;
        String[] arguments;

        while (true) { 													//An infinite loop so that you can enter multiple commands
            command = kb.nextLine();									//Reads the next line on the keyboard
            arguments = command.split(" ");								//parses the input
            arguments[0] = arguments[0].toLowerCase();					
            if(!commands.contains(arguments[0])) {						//error handling if the command is not a valid one 
            	System.out.println("invalid command: " + command);		
            	continue;
            }
            if(arguments.length <= 3) {									// just in case they are entering junk at the end. no valid command is larger than 3 words
	            switch (arguments[0]) {
	            	//Terminates the program
	                case "quit":
	                	try {
	                		if(arguments.length>1) throw new Exception();
		                    Critter.clearWorld();
		                    quitted = true;
		                    break;
	                	}
	                	catch(Exception e){
	                		System.out.println("error processing: " + command);
	                		break;
	                	}
	                //displays the world on the console
	                case "show":
	                	try {
	                		if(arguments.length>1) throw new Exception();
		                    Critter.displayWorld();
		                    break;
	                	}
	                	catch(Exception e) {
	                		System.out.println("error processing: " + command);
	                		break;
	                	}
	                //Performs one world time step if no number is entered after step. if there is a number it will perform that many time steps
	                case "step":
	                    try {
	                        try {
	                            for (int i = 0; i < Integer.parseInt(arguments[1]); i++) {
	                                Critter.worldTimeStep();
	                            }
	                            break;
	                        } catch (ArrayIndexOutOfBoundsException e) {
	                            Critter.worldTimeStep();
	                            break;
	                        }
	                        catch(NumberFormatException e){
	                        	System.out.println("error processing: " + command);
	                        	break;
	                        }
	                    } catch (InvalidCritterException e) {
	                        System.out.println("error");
	                        break;
	                    }
	                //specifies the seed for the random number generator
	                case "seed":
	                	try {
		                    Critter.setSeed(Long.parseLong(arguments[1]));
		                    break;
	                	}
		                catch(NumberFormatException e) {
		                	System.out.println("error processing: " + command);
		                	break;
		                }
	                //creates a critter 
	                //must specify the type of critter it makes after the word make
	                //optionally you can ad an int after to say how many of them you want to make if no int is specified it will make 1
	                case "make":
	                    try {
	                        try {
	                            for (int i = 0; i < Integer.parseInt(arguments[2]); i++) {
	                                Critter.makeCritter("assignment4." + arguments[1]);
	                            }
	                            break;
	                        }
	                        catch (ArrayIndexOutOfBoundsException e) {
	                            Critter.makeCritter("assignment4." + arguments[1]);
	                            break;
	                        }
	                        catch(NumberFormatException e) {
	                        	System.out.println("error processing: " + command);
	                        	break;
	                        }
	
	                    }
	                    catch (InvalidCritterException e) {
                            System.out.println("error processing: " + command);
	                        break;
	                    }
	                //Shows the stats for a specific critter
	                //must enter the name of the critter after the word stats
	                case "stats":
	                    try {
	                        if (!(critterTypes.contains("assignment4." + arguments[1]))) {//if invalid type throw invalid critter exception
	                            throw new InvalidCritterException(arguments[1]);
	                        }
	                        List<Critter> crits = Critter.getInstances("assignment4." + arguments[1]);
	                        Class<?> critterClass = Class.forName("assignment4." + arguments[1]); //get class corresponding to critter_class_name
	                        Method m[] = critterClass.getDeclaredMethods();
	                        for (int i=0; i<m.length; i++){
	                            if (m[i].getName().equals("runStats")){
	                                try{
	                                    m[i].invoke(critterClass, crits);
	                                }
	                                catch(InvocationTargetException e){
	    	                            System.out.println("error processing: " + command);
	                                }
	                            }
	                        }
	
	                    } catch (InvalidCritterException e) {
                            System.out.println("error processing: " + command);
	                    } catch (Exception e) {                                            //if other exception print stack trace
                            System.out.println("error processing: " + command);
	                    }
	                    break;
	                //this command animates the world
	                //it performs one time step and one show world every second
	                case "animate":
	                    while (true) {
	                        try {
	                            TimeUnit.SECONDS.sleep(1);
	                        }
	                        catch (InterruptedException e1) {
	                            System.out.println("error processing: " + command);
	                        }
	                        try {
	                            Critter.worldTimeStep();
	                            Critter.displayWorld();
	                        }
	                        catch (InvalidCritterException e) {
	                            System.out.println("error processing: " + command);
	                        }
	                    }
	            }
            }
            else {
                System.out.println("error processing: " + command);
            }
            if (quitted) { //breaks out of the while loop if the command quit was entered
            	break;
            }
        }
    }
    private static HashSet<String> critterTypes = new HashSet<String>() {{add("assignment4.Craig"); add("assignment4.Algae"); //a set of all the different critter types
        add("assignment4.Critter1"); add("assignment4.Critter2"); add("assignment4.Critter3"); add("assignment4.Critter4");}}; //a set of all possible commands
    private static HashSet<String> commands = new HashSet<String>() {{add("make"); add("stats");
        add("seed"); add("step"); add("animate"); add("show");add("quit");}};

}

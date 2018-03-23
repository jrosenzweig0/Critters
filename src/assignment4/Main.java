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

        //need to fix but kind of works
        //need to change popuation to private and remake stats
        while (true) {
            command = kb.nextLine();
            arguments = command.split(" ");
            arguments[0] = arguments[0].toLowerCase();
            switch (arguments[0]) {
                case "quit":
                    Critter.clearWorld();
                    quitted = true;
                    break;
                case "show":
                    Critter.displayWorld();
                    break;
                case "step":
                    try {
                        try {
                            for (int i = 0; i < Integer.parseInt(arguments[1]); i++) {
                                if (i % 1000 == 0) System.out.println(i);
                                Critter.worldTimeStep();
                            }
                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Critter.worldTimeStep();
                            break;
                        }
                        catch(NumberFormatException e){
                            System.out.println("error processing: "+ command);
                            break;
                        }
                    } catch (InvalidCritterException e) {
                        System.out.println("error");
                        break;
                    }
                case "seed":
                    Critter.setSeed(Long.parseLong(arguments[1]));
                    break;
                case "make":
                    try {
                        try {
                            for (int i = 0; i < Integer.parseInt(arguments[2]); i++) {
                                Critter.makeCritter("assignment4." + arguments[1]);
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                            Critter.makeCritter("assignment4." + arguments[1]);
                        }
                        break;
                    }
                    catch (InvalidCritterException e) {
                        System.out.println(e);
                        break;
                    }
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
                                    System.out.println(e);
                                }
                            }
                        }

                    } catch (InvalidCritterException e) {
                        System.out.println("Invalid Critter: " + arguments[1]);
                    } catch (Exception e) {                                            //if other exception print stack trace
                        e.printStackTrace();
                    }
                    break;
                case "animate":
                    while (true) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            Critter.worldTimeStep();
                            Critter.displayWorld();
                        }
                        catch (InvalidCritterException e) {
                            System.out.println("error");
                        }
                    }
            }
            if (quitted) {
                    break;
            }
        }
    }
    private static HashSet<String> critterTypes = new HashSet<String>() {{add("assignment4.Craig"); add("assignment4.Algae");
        add("assignment4.Critter1"); add("assignment4.Critter2"); add("assignment4.Critter3");}};

}

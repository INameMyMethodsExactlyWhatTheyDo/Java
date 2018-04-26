package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * <Kyle Zhou>
 * <KZ3528>
 */

import java.util.Scanner;
import java.io.*;


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
    private static boolean run = true;  // turn into false for program to terminate

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
        /* Write your code below. */
        while(run){
            command(kb.nextLine());
        }
        /* Write your code above */
        System.out.flush();

    }
    /**
     * This method is the flow of the program. It is 
     * Controled by external commands
     * It can make Critters
     * Show map
     * Clean map
     * step though 
     * seed the RNG
     * and show stats of a Critter
     * @param A string command such as "make assignment4.Critter1 100"
     */
    public static void command(String input) {
        String[] in = input.split(" ");
        //System.out.println("input is: " + in);
        try{
            switch(in[0]){
                case "make":
                    //System.out.println("input is spawn");
                    if(in.length != 2){
                        for(int i = 0; i < Integer.parseInt(in[2]); i++){
                        //System.out.println("i");
                            Critter.makeCritter(in[1]);
                        }
                    }else if (in.length == 2){
                        Critter.makeCritter(in[1]);   
                    }
                    break;
                case "show":
                    if(!input.equals("show")){
                        System.out.println("invalid command: " + input);
                        break;
                    }
                    System.out.println("input is show");
                    Critter.displayWorld();
                    break;

                case "step":
                    //System.out.println("input is step");
                    if(in.length == 2 ){
                        for(int i = 0 ; i < Integer.parseInt(in[1]);i ++){
                            Critter.worldTimeStep();
                        }
                    }else{
                        Critter.worldTimeStep();
                    }
                    break;

                case "clear":
                    if(!input.equals("clear")){
                        System.out.println("invalid command: " + input);
                        break;
                    }
                    //System.out.println("input is clear");
                    Critter.clearWorld();
                    break;

                case "stats":
                    //System.out.println("input is stats");
                    Critter.runStats(Critter.getInstances(in[1]));
                    break;

                case "seed":
                    //System.out.println("input is seed");
                    Critter.setSeed(Integer.parseInt(in[1]));
                    break;
                case "quit":
                    //System.out.println("input is quit");
                    if(!input.equals("quit")){
                        System.out.println("invalid command: " + input);
                        break;
                    }
                    run = false;
                    break;
                case "s":
                    Critter.worldTimeStep();
                    Critter.displayWorldDebug();
                    break;
                case "a":
                    Critter.makeCritter("assignment4.Critter1");
                    break;
                default:
                    System.out.println("invalid command: " + input);
                    break;
            }
        }catch(InvalidCritterException | 
                NumberFormatException |
                ArrayIndexOutOfBoundsException e){
            System.out.println("error processing: " + input);
        }
    }

    public static void init(){
        command("make assignment4.Algae 1");
        command("make assignment4.Critter1 1");
        command("make assignment4.Critter2 1");
    }
}

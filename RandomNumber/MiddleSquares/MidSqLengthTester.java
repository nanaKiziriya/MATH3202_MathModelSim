/*
OVERVIEW:
For Middle-Square method of generating pseudo-random numbers
This program priorities time efficiency over space efficiency: Avoids iterating ANY previously iterated elements
For each seed, it iterates through the orbit and calculates important info. After each completed orbit, it stores info and restarts.

Terminology:
seed - the initial value of a sequence
tail - the longest acyclic sequence of the orbit

This program:
Calculates the tail length of each seed
Finds the longest tail length and its seeds

Built-in functionality:
Keeps track of seed & tailLength in both directions -> seedTail, tailSeed

USER INPUTS starting at line ??:
firstSeed - first seed value to calculate sequ of; program will continue to calculate until largest seed of given seedLength
seedLength - controls length of the elements; Note: for middle-square, odd-lengths favor the left side of the middle
printSequ, firstRoot - booleans to det. what info you want to calcluate/display

*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;

class Main {
    
    // USER INPUTS
    static int firstSeed=0, seedLength=3;
    static boolean printTailOfEachSeed = true; // during calculations
    static boolean printSeedsOfMaxTailL = false; // after calculations
    static boolean printTailLOfEachSeed = false; // after calculations
    
    public static void main(String[] args) {

        /* DATAFIELDS */
        
        int lastSeed = (int)Math.pow(10,seedLength)-1;
        
        // Containers
        HashMap<Integer,TreeSet<Integer>> tailSeed = new HashMap<>(); // <tailLength,seedSet>
        HashMap<Integer,Integer> seedTail = new HashMap<>(); // <seed,tailLength>, keySet() holds all previous elements, helps avoid unnecessary repeats
        ArrayList<Integer> orbitHolder = new ArrayList<>(); // for each seed, holds orbit in order, helps calc (seed,tailL) pair quicker, reset empty per seed
        HashSet<Integer> cycleChecker = new HashSet<Integer>(); // for each seed, for quickly checking cycle, reset empty per seed

        int tailLength=0; // for each seed, holds its tail length
        int maxTailLength=0; // holds largest tail length

        // For time efficiency: avoid iterating over ALL previously iterated elements
        int tempTail=0, tempIndex=0, tailMin=0; // works with tempSeed, tempIndex instead of ArrayList.indexOf() for time efficiency
        boolean cycleCheckerFlag, seedTailFlag; // flags where the element was seen before, current orbit vs. previous orbit, affects tailLength calculations of same-cycle elements
        
        
        /* ALGORITHM */

        // execute for each seed from firstSeed (user input) to lastSeed (999..9)
        for(int i=firstSeed, j=i;i<=lastSeed;i++){
            
            // reset vals/prep for new seed orbit
            j=i; tailLength=tempIndex=0;
            cycleChecker.clear();
            orbitHolder.clear();
            cycleCheckerFlag=seedTailFlag=false;
            
            while(true){ // for each root, continues orbit calc until hits repeat in either current orbit or some previous orbit
                cycleCheckerFlag=cycleChecker.contains(j); // if current orbit has repeat
                seedTailFlag=seedTail.keySet().contains(j); // if we've calculated and stored this as a seed before
                if(!(cycleCheckerFlag||seedTailFlag)){
                    /*printTailOfEachSeed*/
                    if(printTailOfEachSeed) System.out.print(j+" ");
                    
                    cycleChecker.add(j);
                    orbitHolder.add(j);
                    tailLength++;
                } else{
                    // if cycleChecker is flagged (instead of seedTailFlag) that means its a NEW periodic point, thus every subsequent element has the same period/tailLength, and the tempTail of the element that flagged cycleChecker is the sharp lower bound for tailLengths of all elements in the current orbit
                    if(seedTailFlag) tailLength+=seedTail.get(j);
                    /*printTailOfEachSeed*/
                    if(printTailOfEachSeed) System.out.print(j+"... ");
                    break; // break this seed sequ
                }
                j=fnct(j);
            }
            // while loop break guaranteed
            
            /*printTailOfEachSeed*/
            if(printTailOfEachSeed) System.out.println("["+tailLength+"]");
            
            // update tailSeed, seedTail, time efficient
            // NOTE: If a non-fixed periodic point is hit (flagged cycleChecker in loop) every subsequent element has the exact same period/tailLength
            tailMin = (cycleCheckerFlag)? tailLength-orbitHolder.indexOf(j):0;
            for(int tempSeed:orbitHolder){
                if(!seedTail.keySet().contains(tempSeed)){ // if this seed hasn't been calculated and stored b4
                    tempTail = Math.max(tailLength-tempIndex,tailMin);
                    seedTail.put(tempSeed,tempTail);
                    if(!tailSeed.keySet().contains(tempTail)) tailSeed.put(tempTail,new TreeSet<>());
                    tailSeed.get(tempTail).add(tempSeed);
                }
                tempIndex++;
            }
            
            // update maxTailLength
            if(maxTailLength<tailLength) maxTailLength=tailLength;
            
            // restart for loop again
        }
        
        // END
        
        // Print final information
        System.out.println("Largest tail length is "+maxTailLength);
        
        /*printSeedsOfMaxTailL*/
        if(printSeedsOfMaxTailL){
            System.out.println("With seed(s): ");
            for(int n:tailSeed.get(maxTailLength)){
                System.out.print(n+" ");
            }
            System.out.println();
        }
        
        /*printTailLOfEachSeed*/
        if(printTailLOfEachSeed){
            System.out.println("All tail lengths in order:");
            for(int i=firstRoot;i<=lastRoot;i++){
                System.out.print(seedTail.get(i)+" ");
            }
        }
        
        
    }
    
    public static int fnct(int n){
        int squareLength = 2*seedLength;
        StringBuilder s = new StringBuilder(""+n*n);
        while(s.length()<squareLength){
            s.insert(0,"0");
        }
        
        int startIndex = (seedLength+1)/2;
        n=Integer.parseInt(s.substring(startIndex,startIndex+seedLength));
        return n;
    }
}

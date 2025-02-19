/*
OVERVIEW:
For Middle-Square method of generating pseudo-random numbers
Calculates for each initial value (seed) the length of its non-cyclic sequence (tail) -> seed, tailLength, maxTailLength
Keeps track of seed & tailLength in both directions -> seedTail, tailSeed
This program priorities TIME efficiency over space efficiency

USER INPUTS starting at line 21 -> printSequ, firstRoot, halfSeedLength
halfSeedLength - controls upper limit for length of the elements; Note: for middle-square, length must be even, thus HALF seed length is an integer

*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        
        // USER INPUTS
        boolean printSequ = false;
        int firstRoot=0, halfSeedLength=1;

        int lastRoot = (int)Math.pow(10,2*halfSeedLength)-1;
        
        // Containers
        HashMap<Integer,TreeSet<Integer>> tailSeed = new HashMap<>(); // <tailLength,seedSet>
        HashMap<Integer,Integer> seedTail = new HashMap<>(); // <seed,tailLength>, keySet() holds all previous elements, helps avoid unnecessary repeats
        ArrayList<Integer> cycleHolder = new ArrayList<>(); // reset empty per seed, holds cycle in order, helps calc seed-tailL pair quicker
        HashSet<Integer> cycleChecker = new HashSet<Integer>(); // reset empty per seed, for quickly checking cycle
        int tailLength=0, maxTailLength=0;
        int tempTail=0, tempIndex=0; // works with tempSeed, tempIndex instead of ArrayList.indexOf() for time efficiency
        
        
        // efficient calc for each root
        for(int i=firstRoot, j=i;i<=lastRoot;i++){
            // reset vals/prep for next root
            j=i; tailLength=0; tempIndex=0;
            cycleChecker.clear();
            cycleHolder.clear();
            
            while(true){ // for each root, continues sequ calc until hits repeat in either root's cycleChecker or rootChecker
                if(!(cycleChecker.contains(j)||seedTail.keySet().contains(j))){
                    if(printSequ) System.out.print(j+" ");
                    cycleChecker.add(j);
                    cycleHolder.add(j);
                    tailLength++;
                } else{
                    if(seedTail.keySet().contains(j)) tailLength+=seedTail.get(j);
                    if(printSequ) System.out.print(j+"... ");
                    break; // break this root sequ
                }
                j=fnct(j);
            }
            // while loop break guaranteed
            
            if(printSequ) System.out.println("["+tailLength+"]");
            
            // update tailSeed, seedTail, time efficient
            for(int tempSeed:cycleHolder){
                if(!seedTail.keySet().contains(tempSeed)){ // if this seed hasn't been calculated and stored b4
                    tempTail = tailLength-tempIndex;
                    seedTail.put(tempSeed,tempTail);
                    if(!tailSeed.keySet().contains(tempTail)) tailSeed.put(tempTail,new TreeSet<>());
                    tailSeed.get(tempTail).add(tempSeed);
                }
                tempIndex++;
            }
            
            // update maxTailLength
            if(maxTailLength<tailLength) maxTailLength=tailLength;
        }
        
        // Print final information
        System.out.println("Largest tail length is "+maxTailLength);
        System.out.print("With seed(s): ");
        for(int n:tailSeed.get(maxTailLength)){
            System.out.print(n+" ");
        }
        
    }
    
    public static int fnct(int n){ // works good
        String s = String.format("%04d",n*n);
        s=s.substring(1,3);
        n=Integer.parseInt(s);
        return n;
    }
}

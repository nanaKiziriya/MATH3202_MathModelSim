import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        
        boolean printSequ = false;
        int firstRoot=1, lastRoot=99;
        
        HashMap<Integer,TreeSet<Integer>> tailSeed = new HashMap<>(); // <tailLength,seedSet>
        HashMap<Integer,Integer> seedTail = New HashMap<>(); // <seed,tailLength>, keySet() holds all previous elements to avoid unnecessary repeats
        ArrayList<Integer> cycleHolder = new ArrayList<>(); // reset empty per seed, holds cycle in order
        HashSet<Integer> cycleChecker = new HashSet<Integer>(); // reset empty per seed, for quickly checking cycle
        int tailLength=0, maxTailLength=1;
        
        for(int i=j=firstRoot;i<=lastRoot;i++){
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
            if(!(tailSeed.keySet().contains(tailLength))){
                tailSeed.put(tailLength,new TreeSet<Integer>());
            }
            tailSeed.get(tailLength).add(i);
            maxTailLength=(maxTailLength<tailLength)?tailLength:maxTailLength;
            j=i; tailLength=0;
            cycleChecker.clear();
        }
        System.out.println("Largest tail length is "+maxTailLength);
        System.out.print("With seed(s): ");
        for(int n:tailSeed.get(maxTailLength)){
            System.out.print(n+", ");
        }
    }
    
    public static int fnct(int n){ // works good
        String s = String.format("%04d",n*n);
        s=s.substring(1,3);
        n=Integer.parseInt(s);
        return n;
    }
}

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) {
        
        boolean printSequ = false;
        
        
        HashMap<Integer,TreeSet<Integer>> sequAdd = new HashMap<>();
        HashSet<Integer> cycleChecker = new HashSet<>();
        int j=1, tailLength=0, maxTailLength=1;
        for(int i=1;i<=100;i++){
            while(true){
                if(!(cycleChecker.contains(j)||sequAdd.keySet().contains(j))){
                    if(printSequ) System.out.print(j+" ");
                    cycleChecker.add(j);
                    tailLength++;
                } else{
                    if(sequAdd.keySet().contains(j))tailLength+=sequAdd.get(j).size();
                    break;
                }
                j=fnct(j);
            }
            if(printSequ) System.out.println("["+tailLength+"]");
            if(!(sequAdd.keySet().contains(tailLength))){
                sequAdd.put(tailLength,new TreeSet<Integer>());
            }
            sequAdd.get(tailLength).add(i);
            maxTailLength=(maxTailLength<tailLength)?tailLength:maxTailLength;
            j=i; tailLength=0;
            cycleChecker.clear();
        }
        System.out.println("Largest tail length is "+maxTailLength);
        System.out.print("With seed(s): ");
        for(int n:sequAdd.get(maxTailLength)){
            System.out.print(n+", ");
        }
    }
    
    public static int fnct(int n){
        String s = String.format("%04d",n*n);
        s=s.substring(1,3);
        return Integer.parseInt(s);
    }
}

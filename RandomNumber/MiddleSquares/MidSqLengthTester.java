class MidSqLengthTester {
    public static void main(String[] args) {
        HashMap<Integer,TreeSet<Integer>> sequAdd = new HashMap<>();
        HashSet<Integer> cycleChecker = new HashSet<>();
        int j=1, tailLength=0;
        for(int i=1;i<=100;i++){
            while(true){
                System.out.print(j+" ")
                if(!(cycleChecker.contains(j)||sequAdd.keySet().contains(j))){
                    cycleChecker.add(j);
                    tailLength++;
                } else break;
                j=fnct(j);
            }
            System.out.println("["+tailLength+"]");
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
        String s = String.format(%4d,n*n);
        s=s.substring(1,3);
        return Integer.parseInt(s);
    }
}

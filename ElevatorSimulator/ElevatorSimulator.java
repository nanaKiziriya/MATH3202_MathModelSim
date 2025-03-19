// Given passenger arrival interval 25, elevator departure timer 20, elevator return interval 180, elevator max capacity 8
// Calculate max & avg wait time
// How sensitive is the model to change in passenger arrival rate? e.g. 25->24

// Elevator sim with datafields, incl. Queue<Passenger>
// tick() method updates waitTime for Queue<P...>

class ElevatorSimulator {
    final static int PAI = 25, /* Passenger arrival interval*/
        EDT = 20, /*Elevator departure timer, reset at new entry INTO elevator*/
        ERI = 180, /*Elevator return interval*/
        cap = 8; /*max Elevator capacity*/
    private static time=0, eCountdown=EDT;
    private static Queue<Passenger> queue = new Queue<>();
    
    public static void main(String[] args) {
        while(true){ // each elevator cycle
            //elevator arrives
            
            while(eCountdown>0){
                tick(20); // updates queue; if new person arrives at 0, eCountdown restarts
            }
            
            
            //elevator finally departs
            tick(ERI);
        }
    }
    
    private static tick(){ tick(1); }
    private static tick(final int T){
        for(int i=0;i<T;i++){
            time++;
            if(queue.size()>0){
                eCountdown--;
            }
            
            if()
        }
    }
}

class Passenger{
    private int waitTime=0;
    
}

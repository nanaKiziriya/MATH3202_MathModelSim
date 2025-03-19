// Given passenger arrival interval 25, elevator departure timer 20, elevator return interval 180, elevator max capacity 8
// Calculate max & avg wait time
// How sensitive is the model to change in passenger arrival rate? e.g. 25->24

// Elevator sim with datafields, incl. Queue<Passenger>
// tick() method updates waitTime for Queue<P...>

class ElevatorSimulator {
    
    // initial conditions
    final static int PAI = 25, /* Passenger arrival interval*/
        EDT = 20, /*Elevator departure timer, reset at new entry INTO elevator*/
        ERI = 180, /*Elevator return interval*/
        capacity = 8, /*max Elevator capacity*/
        runtime = Integer.MAX_VALUE/ERI*PAI; /*simulation runtime, max Integer.MAX_VALUE/ERI; avoid overflow sumWaitTime < rt*ERI/PAI/2 < INT.MAX*/

    // elevator datafields
    private static int time=0, eCountdown=EDT;
    private static Queue<Passenger> queue = new Queue<>();

    // statistics datafields
    private static int maxWaitTime=0, sumWaitTime=0, totalDeparted=0;

    public static void main(String[] args) {
        while(time<runtime){ // 1 loop ~ 1 elevator arrival/departure cycle
            
            // elevator arrives
            eCountdown=EDT;
            
            while(eCountdown>0){
                // waiting for elevator to leave
                tick(true); // updates elevator datafields; if new person arrives at 0 and not at capacity, eCountdown restarts
            }

            // elevator finally departs
            departureStats(); // updates statistics datafields, then shortens queue accordingly

            // waiting for elevator to return
            tick(false); // updates elevator datafields EXCEPT eCountdown
        }
    }

    private static void tick(boolean elevatorIsPresent){ // one minute passes in the lobby
        time++;
        waiting(); // waittime for ppl present goes up
        if(elevatorIsPresent && queue.size()>0) eCountdown--; // timer to leave is ticking
        if(time%PAI==0){
            queue.add(new Passenger()); // new person arrives every PAI minutes
            if(elevatorIsPresent && queue.size()<capacity) eCountdown=EDT; // if new person has space to enter elevator
        }
    }
    
    private static void waiting(){ for(Passenger p:queue) p.incrWaitTime(); }

    private static void departureStats(){
        int numDeparted = min(queue.size(),capacity), inspectWaitTime;
        for(int i=0;i<numDeparted;i++){
            inspectWaitTime = queue.remove().getWaitTime();
            totalDeparted++;
            maxWaitTime = Math.max(maxWaitTime,inspectWaitTime);
            sumWaitTime+=inspectWaitTime;   
        }
    }
    
}

class Passenger{
    private int waitTime=0;
    public void incrWaitTime(){ waitTime++; }
    public int getWaitTime(){ return waitTime; }
}

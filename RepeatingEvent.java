/**
 * A RepeatingEvent is a DelayedEvent that will run an action multiple times.
 * 
 * @author Freeman Wang 
 * @version 2024-02-27
 */
public class RepeatingEvent extends Event 
{
    int iterations, count; // The amount of required executions and the number of executions, respectively.
    int delay; // The number of acts between executions.
    int act; // a counter for # of acts.
    boolean executeInstantly; // Whether the first execution will be instant.
    /**
     * Creates a RepeatingEvent that will run a function with [delay] acts between
     * executions [count] times. WILL run on the first Act.
     * @param function The action to run when the event is triggered.
     * @param delay The number of acts between executions.
     * @param count The number of times to run the action. A value of -1 will run infinitely.
     */
    public RepeatingEvent(Runnable function, int delay, int count)
    {
        this(function, delay, count, true);
    }
    /**
     * Creates a RepeatingEvent that will run a function with [delay] acts between
     * executions [count] times
     * @param function The action to run when the event is triggered.
     * @param delay The number of acts between executions.
     * @param count the Number of times to run the action. A value of -1 will run infinitely.
     * @param executeInstantly Whether to run the event on the first Act.
     */
    public RepeatingEvent(Runnable function, int delay, int count, boolean executeInstantly){
        super(function);
        iterations = 0;
        act = 0;
        this.count = count;
        this.delay = delay;
        this.executeInstantly = executeInstantly;
    }

    public void act() {
        // The reason I did not put (act==0 && executeInstantly) is so that the if else if would skip the
        // else if portion during the first act, since if (act==0) evaluated to true.
        
        if (act == 0) { // If in the first act,
            if (executeInstantly){ // If I should run instantly, do it.
                event.run();
                iterations++;
            }
        }
        else if (act % delay == 0) { // If I should act right now,
            event.run(); // Run the event.
            iterations++; // Increase the number of triggers that have occured.
        }
        
        if (count != -1 && iterations >= count) { // If not -1, and I have executed enought times,
            getWorld().removeObject(this); // Finished execution. remove.
        }
        
        act++;
    }
    public RepeatingEvent copy() {
        return new RepeatingEvent(event, delay, count);
    }
}

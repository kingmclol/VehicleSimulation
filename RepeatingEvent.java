/**
 * A RepeatingEvent is a DelayedEvent that will run an action multiple times.
 * 
 * @author Freeman Wang 
 * @version 2024-02-27
 */
public class RepeatingEvent extends DelayedEvent 
{
    int iterations, count; // The amount of required executions and the number of executions, respectively.
    /**
     * Creates a RepeatingEvent that will run a function with [delay] acts between
     * executions [count] times.
     * @param function The action to run when the event is triggered.
     * @param delay The number of acts between executions.
     * @param count The number of times to run the action.
     */
    public RepeatingEvent(Runnable function, int delay, int count)
    {
        super(function, delay);
        iterations = 0;
        this.count = count;
    }

    public void act() {
        if (act++ % delay == 0) { // If I should act right now,
            event.run(); // Run the event.
            iterations++; // Increase the number of triggers that have occured.
        }
        if (iterations >= count) getWorld().removeObject(this); // Finished execution. remove.
    }
    public RepeatingEvent copy() {
        return new RepeatingEvent(event, delay, count);
    }
}

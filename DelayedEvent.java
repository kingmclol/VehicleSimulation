import greenfoot.*;
/**
 * A DelayedEvent is a Event that is delayed by a specified amount of acts.
 * When the amount of acts that this DelayedEvent has existed is greater or equal to
 * the required delay amount of acts, call the given action.
 * 
 * @author Freeman Wang 
 * @version 2024-02-27
 */
public class DelayedEvent extends Event
{
    int delay; // The amount of frames to wait before running.
    /**
     * Creates a DelayedEvent that would run an [action] after [delay] acts of existence.
     * @param action The action to run when the event is triggered.
     * @param delay How many actions to wait before running the action.
     */
    public DelayedEvent(Runnable action, int delay) {
        super(action);
        this.delay = delay;
    }
    public void act() {
        if (++act >= delay) { // Check if the amount of acts I have existed is greater or equal to the required delay.
            event.run(); // Run the event.
            getWorld().removeObject(this); // Remove this from the world, as the event has been run.
        }
    }
    public void reset(){
        act = 0;
    }
    public DelayedEvent copy() {
        return new DelayedEvent(event, delay);
    }
}

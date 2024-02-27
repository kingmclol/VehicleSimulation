import greenfoot.*;
/**
 * Event is abstract. It will should a given Runnable (function) when a condition is satisfied.
 * @author Freeman Wang
 * @version 2024-02-27
 */
public abstract class Event extends Actor 
{
    protected int act; // The number of acts this has existed.
    protected Runnable event; // What to do when triggered.
    /**
     * Create an Event.
     * @param Action The action to run when the Event is triggered.
     */
    public Event(Runnable action) {
        act = 0; // Current acts of existence is 0.
        event = action; // Set the event to run as the given action.
    }
    public abstract Event copy();
}

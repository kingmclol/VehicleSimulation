/**
 * Write a description of class InstantEvent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstantEvent extends Event 
{
    /**
     * Constructor for objects of class InstantEvent
     */
    // not useful.
    public InstantEvent(Runnable action)
    {
        super(action);
    }
    public void act() {
        event.run();
    }
    public InstantEvent copy() {
        return new InstantEvent(event);
    }
}

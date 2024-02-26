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
    public InstantEvent(Runnable action)
    {
        super(action);
    }
    public void update() {
        event.run();
        executed = true;
    }
    public InstantEvent copy() {
        return new InstantEvent(event);
    }
}

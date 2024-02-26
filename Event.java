/**
 * Write a description of class Event here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Event  
{
    protected int act;
    protected boolean executed;
    protected Runnable event;
    public Event(Runnable action) {
        act = 0;
        executed = false;
        event = action;
    }
    public abstract void update();
    public abstract Event copy();
    public boolean isExecuted() {
        return executed;
    }
}

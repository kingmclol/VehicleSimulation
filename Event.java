import greenfoot.*;
/**
 * Write a description of class Event here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Event extends Actor 
{
    protected int act;
    protected Runnable event;
    public Event(Runnable action) {
        act = 0;
        event = action;
    }
    public abstract Event copy();
}

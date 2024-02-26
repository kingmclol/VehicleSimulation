import greenfoot.*;
/**
 * Write a description of class DelayedEvent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DelayedEvent extends Event
{
    int delay;
    public DelayedEvent(Runnable action, int delay) {
        super(action);
        this.delay = delay;
    }
    public void act() {
        if (act++ >= delay) {
            event.run();
            getWorld().removeObject(this);
        }
    }
    public DelayedEvent copy() {
        return new DelayedEvent(event, delay);
    }
}

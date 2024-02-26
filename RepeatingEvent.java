/**
 * Write a description of class RepeatingEvent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RepeatingEvent extends DelayedEvent 
{
    /**
     * Constructor for objects of class RepeatingEvent
     */
    int iterations, count;
    public RepeatingEvent(Runnable function, int delay, int count)
    {
        super(function, delay);
        iterations = 0;
        this.count = count;
    }

    public void update() {
        if (act++ % delay == 0) {
            event.run();
            iterations++;
        }
        if (iterations >= count) executed = true;
    }
    public RepeatingEvent copy() {
        return new RepeatingEvent(event, delay, count);
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Stage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Stage extends World
{
    protected List<Event> events;
    /**
     * Constructor for objects of class Stage.
     * 
     */
    public Stage (int a, int b, int c) {
        this(a,b,c,false);
    }
    public Stage(int a, int b, int c, boolean d)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(a, b, c, d); 
        events = new ArrayList<>();
    }
    public void act() {
        List<Event> executed = new ArrayList<Event>();
        for (Event e : events) {
                if (e!=null) {
                    e.update();
                    if (e.isExecuted()) executed.add(e);
            }
        }
        events.removeAll(executed);
        //System.out.println(events);
    }
    public void addEvent(Event e) {
        events.add(e);
    }
}

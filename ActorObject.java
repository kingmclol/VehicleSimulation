import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ActorObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ActorObject extends Actor
{
    /**
     * Act - do whatever the ActorObject wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected void createEvent (Event e) {
        ((Stage)getWorld()).addEvent(e);
    }
}

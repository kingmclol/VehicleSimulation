import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Grunt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Civilian extends Human
{
    /**
     * Act - do whatever the Grunt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Civilian(int direction) {
        super(direction);
    }
    public void act() {
        if (awake){
            moveToOtherSide();
            if (atEdge()) getWorld().removeObject(this);
        }
    }
}

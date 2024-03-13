import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Civilian has nothing special. It just wants to get the heck out of here,
 * either through getting on a bus or moving off screen. It has no means of defending
 * itself or helping others.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
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
            if (atEdge()) removeMe();
        }
    }
}

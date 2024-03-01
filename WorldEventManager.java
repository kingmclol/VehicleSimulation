import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WorldEvent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldEventManager extends Actor
{
    /**
     * Act - do whatever the WorldEvent wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int count=0;
    //WorldEvent worldEvent;
    int possibleEvents;
    public WorldEventManager(){
        //worldEvent = null;
        possibleEvents = 2;
    }
    public void act(){
        if (count++ >= 600) {
            if (Greenfoot.getRandomNumber(600) ==0) {
                startRandomEvent();
            }
        }
    }
    private void startRandomEvent() {
        count = 0;
        switch(Greenfoot.getRandomNumber(possibleEvents)) {
            case 0:
                ((VehicleWorld) getWorld()).progressDayCycle();
                break;
            case 1:
                // getWorld().addObject(new BombingEvent(),0,0);
                break;
        }
    }
    public boolean isEventRunning(){
        //return (worldEvent.getWorld()!=null);
        return false;
    }
    
    
}

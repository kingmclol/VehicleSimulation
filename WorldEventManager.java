import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The WorldEventManager manages when World events should occur. Not to be cused with World Events that would affect
 * all instances of a class in the World.
 *
 * The WorldEventManager should be added to the World, preferably at a side away location such as (0,0) to allow it to act.
 * Upon a set amount of time passing, there is a chance that a random event is executed.
 * 
 * @author Freeman Wang
 * @version 2024-03-04
 */
public class WorldEventManager extends Actor
{
    /**
     * Act - do whatever the WorldEvent wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int act;
    private static final int NUM_EVENTS = 2;
    private static final int WAIT_BETWEEN_EVENTS=600;
    public WorldEventManager(){
        act = 0;
    }
    public void act(){
        if (act++ >= WAIT_BETWEEN_EVENTS) {
            if (Greenfoot.getRandomNumber(300) == 0) {
                startRandomEvent();
            }
        }
    }
    /**
     * Starts a random possible event.
     */
    private void startRandomEvent() {
        act = 0;
        VehicleWorld w = (VehicleWorld) getWorld();
        switch(Greenfoot.getRandomNumber(NUM_EVENTS)) {
            case 0:
                w.startBombing();
                break;
            case 1:
                w.spawnZombieWave();
                break;
        }
    }
}

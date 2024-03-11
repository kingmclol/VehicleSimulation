import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The WorldEventManager manages when World events should occur. Not to be confused with World effects.
 * World Events are random events such as the zombie wave, and car rush.
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
    private int eventAct, nightAct;
    private static final int NUM_EVENTS = 3;
    private static final int WAIT_BETWEEN_EVENTS=600;
    private static final int WAIT_BETWEEN_NIGHTTIME=2400;
    public WorldEventManager(){
        eventAct = 0;
        nightAct = 0;
    }
    public void act(){
        if (++nightAct >= WAIT_BETWEEN_NIGHTTIME) {
            nightAct = 0;
            getWorld().addObject(new Nighttime(), 0, 0);
        }
        
        if (++eventAct >= WAIT_BETWEEN_EVENTS) {
            if (Greenfoot.getRandomNumber(300) == 0) {
                startRandomEvent();
            }
        }
    }
    /**
     * Starts a random possible event.
     */
    private void startRandomEvent() {
        eventAct = 0;
        VehicleWorld w = (VehicleWorld) getWorld();
        switch(Greenfoot.getRandomNumber(NUM_EVENTS)) {
            case 0:
                w.startBombing();
                announce("BOMBING");
                break;
            case 1:
                w.spawnZombieWave();
                announce("ZOMBIE RUSH");
                break;
            case 2:
                w.spawnManyVehicles();
                announce("TRAFFIC RUSH");
                break;
        }
    }
    /**
     * Draws some text onto the world to announce what event is occuring.
     * @param text The text to draw on the World.
     */
    private void announce(String text) {
        World w = getWorld();
        w.addObject(new AnnounceText(text), w.getWidth()/2, 100);
    }
}

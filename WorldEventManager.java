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
    private int act;
    private static int NUM_EVENTS = 2;
    public WorldEventManager(){
        act = 0;
    }
    public void act(){
        if (act++ >= 600) {
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

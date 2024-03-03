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
    int act;
    int possibleEvents;
    public WorldEventManager(){
        act = 0;
        possibleEvents = 2;
    }
    public void act(){
        if (act++ >= 600) {
            if (Greenfoot.getRandomNumber(300) == 0) {
                startRandomEvent();
            }
        }
    }
    /**
     * Progresses the World's time.
     */
    private void progressWorldTime(){
        act = 0;
        ((VehicleWorld) getWorld()).progressDayCycle();
    }
    /**
     * Starts a random possible event.
     */
    private void startRandomEvent() {
        act = 0;
        VehicleWorld w = (VehicleWorld) getWorld();
        switch(Greenfoot.getRandomNumber(possibleEvents)) {
            case 0:
                w.startBombing();
                break;
            case 1:
                w.spawnZombieWave();
                break;
        }
    }
}

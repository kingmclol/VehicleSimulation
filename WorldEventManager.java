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
    int counterEvent, counterWorldTime;
    int possibleEvents;
    public WorldEventManager(){
        counterEvent = 0;
        counterWorldTime = 0;
        possibleEvents = 1;
    }
    public void act(){
        // if (counterWorldTime++ >= 900) {
            // progressWorldTime();
        // }
        
        if (counterEvent++ >= 600) {
            if (Greenfoot.getRandomNumber(300) == 0) {
                startRandomEvent();
            }
        }
    }
    /**
     * Progresses the World's time.
     */
    private void progressWorldTime(){
        counterWorldTime = 0;
        ((VehicleWorld) getWorld()).progressDayCycle();
    }
    /**
     * Starts a random possible event.
     */
    private void startRandomEvent() {
        counterEvent = 0;
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

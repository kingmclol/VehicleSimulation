import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Nighttime is an effect for when the World is night. Yes, it is a very creative name.
 * When the effect starts, all pedestrians will have their view ranges (aggro radius) modified
 * to their nighttime counterparts, and Zombies will also speed up quite a bit.
 * 
 * <p>Obviously, on removal, all the changes made will be reverted, and Pedestrians will be
 * of their normal view ranges and speeds for daytime.</p>
 * 
 * @author Freeman Wang
 * @version 2024-03-09
 */
public class Nighttime extends Effect
{
    private static SuperSound nightAmbience;
    /**
     * Creates an effect that lasts for 1200 acts, and targets 100 transparency.
     */
    public Nighttime(){
        super(1200, 100);
        nightAmbience = new SuperSound("Night Ambience.mp3", 1, 40);
    }
    public void addedToWorld(World w){
        if (!initialAct) return;
        super.addedToWorld(w);
        
        // Setup the image.
        GreenfootImage img = new GreenfootImage(w.getWidth(), w.getHeight());
        img.setColor(Color.BLACK);
        img.fill();
        img.setTransparency(0);
        setImage(img);
        
        // Center on screen.
        setLocation(w.getWidth()/2, w.getHeight()/2);
    }
    /**
     * Resumes playing the ambience
     */
    public static void resumeAmbience() {
        nightAmbience.play();
    }
    /**
     * Pauses the ambience
     */
    public static void pauseAmbience() {
        nightAmbience.pause();
    }
    /**
     * Starts the effect, which is to change view ranges and Zombie speeds.
     */
    public void startEffect() {
        // Start night ambience.
        nightAmbience.play();
        setPedestrianStats(false); // Set the pedestrian stats to nighttime counterparts
    }
    /**
     * Stops the effect, which is to revert view ranges Zombies speeds to normal.
     */
    public void stopEffect() {
        // Stop night ambience.
        nightAmbience.stop();
        setPedestrianStats(true); // Set the pedestrian stats to daytime counterparts
    }
    /**
     * Sets the stats for all Pedestrians in the World for the stats that they should have at the
     * current time of day.
     * @param daytime Whether it is currently daytime.
     */
    private void setPedestrianStats(boolean daytime) {
        world.setTime(daytime); // Update time in the world, too.

        // Change all Pedestrians to their respective stats, depending on the time of day.
        ArrayList<Pedestrian> pedestrians = (ArrayList<Pedestrian>)world.getObjects(Pedestrian.class);
        for (Pedestrian p : pedestrians) p.setStats(daytime);
    }
}

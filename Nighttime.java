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
    public Nighttime(){
        super(1200, 100);
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
     * Starts the effect, which is to change view ranges and Zombie speeds.
     */
    public void startEffect() {
        setPedestrianStats(false); // Set the pedestrian stats to nighttime counterparts
    }
    /**
     * Stops the effect, which is to revert view ranges Zombies speeds to normal.
     */
    public void stopEffect() {
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

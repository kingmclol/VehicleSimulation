import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The Car is being driven by a Civilian that really wants to get out as fast as possible.
 * In their rush, they don't care about anything that may be in their way, running over
 * Humans and Zombies alike.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Car extends Vehicle
{
    public Car(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 30)/5);
        speed = maxSpeed;
        yOffset = 4;
        int z;
        followingDistance = 6;
    }

    public void act()
    {
        super.act();
        // if(getWorld()!= null) shoot();
    }
    /**
     * Returns true if hits a Pedestrian, and knocks it over.
     * @return true if a Pedestrian was hit, false otherwise.
     */
    public boolean checkHitPedestrian () {
        // Get the pedestrian
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null) // If it exists,
        {
            p.knockDown(); // Knock it down.
            return true;
        }
        return false;
        
    }
}

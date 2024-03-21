import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The Car is being driven by a person that really wants to get out as fast as possible.
 * In their rush, they don't care about anything that may be in their way, running over
 * Humans and Zombies alike.
 * 
 * @author Jordan Cohen
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Car extends Vehicle
{
    private static SuperSound hitSound = new SuperSound("Car Rev.mp3", 15, 60);
    public Car(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 2.5 + ((Math.random() * 30)/4);
        speed = maxSpeed;
        yOffset = 4;
        followingDistance = 6;
    }

    public void act()
    {
        super.act();
    }
    /**
     * Returns true if hits a Pedestrian, and knocks it over.
     * @return Whether a Pedestrian was hit.
     */
    public boolean checkHitPedestrian () {
        // Get the pedestrian
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null && p.isAwake() || p instanceof Zombie) // If it exists and is awake, or is Zombie,
        {
            p.knockDown(); // Knock it down.
            hitSound.play(); // The car celebrates its victory.
            return true;
        }
        return false;
        
    }
}

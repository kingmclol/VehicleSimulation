import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Medic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Medic extends Human
{
    private Human target;
    private ArrayList<Human> humans;
    //private SuperStatBar energyBar;
    private int hp = 111;
    private int maxHp;
    private double speed = 3.0;

    /**
     * Primary constructor for Bug - creates a new Bug with full HP.
     * This is called by the Spawn button, and used for creating the 
     * first bug at the beginning of the simulation.
     */
    public Medic (int direction)
    {
        super(direction);
    }

    /**
     * Act - do whatever the Bug wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (getWorld() != null && isAwake()) {
            if (target != null && target.getWorld() == null){ // target does not exist anymore
                target = null; // no more target
            }
            if (target == null || target.isAwake() || Utility.getDistance (getPosition(), target.getPosition()) > 40){ // too far or no target
                findTarget(); // find new target
            }
            
            if (target != null) moveTowardOrHelpTarget(); // Go towards the target, if one exists.
            else moveToOtherSide(); // if it does not, move normally
        }
        
        if (atEdge()) getWorld().removeObject(this);
    }    

    /**
     * Private method, called by act(), that constantly checks for closer targets
     */
    private void findTarget ()
    {
        double closestTargetDistance = 0;
        double distanceToActor;
        // Get a list of all Humans in the World, cast it to ArrayList
        // for easy management

        humans = (ArrayList<Human>)getObjectsInRange(40, Human.class); // Get nearby humans
        humans.removeIf(p -> p.isAwake()); // Remove any that are alive (they don't need to be healed)
        if (humans.size() == 0){ // None found, expand search to 140 radius.
            humans = (ArrayList<Human>)getObjectsInRange(140, Human.class);
            humans.removeIf(p -> p.isAwake());
        } 
        if (humans.size() == 0){ // Still none found? Expand search again to 350 radius.
            humans = (ArrayList<Human>)getObjectsInRange(350, Human.class);
            humans.removeIf(p -> p.isAwake());
        } 

        if (humans.size() > 0) // One or more downed humans found.
        {
            // set the first one as my target
            target = humans.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDistance = distanceFrom(target);

            // Loop through the objects in the ArrayList to find the closest target
            for (Human c : humans)
            {
                distanceToActor = distanceFrom(c);
                // If I find a Human closer than my current target, I will change
                // targets
                if (distanceToActor < closestTargetDistance)
                {
                    target = c;
                    closestTargetDistance = distanceToActor;
                }
            }
        }
    }

    /**
     * Private method, called by act(), that moves toward the target,
     * or tries to revive it if within range.
     */
    private void moveTowardOrHelpTarget ()
    {
        if (distanceFrom(target) < 18)
        {
            healHuman(target);
            target=null;
        }
        else
        {
            // If the next position I will move two is not obstructed by a vehicle, move there.
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
    }
    private void healHuman(Human c) {
        speed = 0; // the medic cannot move while healing.
        
        // Creates a delayed event that would happen 30 acts later which heals the target, and lets the medic move again.
        createEvent(new DelayedEvent(() -> {
                                        if (isAwake()) {
                                            c.healMe(); // heal the dead citizen
                                            target=null; // no more target
                                            speed = maxSpeed; // can move again
                                        }}, 30));
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * The Medic is very helpful, moving towards and healing any Humans that are knocked down.
 * However, it is defenseless, and it takes time to heal a knocked down person,
 * making it very vulnerable out there on the street.
 * 
 * @author Freeman Wang
 * @version (a version number or a date)
 */
public class Medic extends Human
{
    private Human target;
    private ArrayList<Human> humans;
    private double speed = 3.0;

    public Medic (int direction)
    {
        super(direction);
    }

    /**
     * Act - do whatever the Medic wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (getWorld() != null && isAwake()) {
            if (target != null && !target.exists()){ // target does not exist anymore
                target = null; // no more target
            }
            if (target == null || target.isAwake() || distanceFrom(target) > 40){ // too far or no target, or target is now awake
                findTarget(); // find new target
            }
            
            if (target != null) moveTowardOrHelpTarget(); // Go towards the target, if one exists.
            else moveToOtherSide(); // if it does not, move normally
        }
        
        if (atEdge()) getWorld().removeObject(this);
    }    

    /**
     * Checks for any nearby downed humans.
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
     * Moves toward, or helps the target Human.
     */
    private void moveTowardOrHelpTarget ()
    {
        if (distanceFrom(target) < 18) // Close enough. Heal them!
        {
            healHuman(target);
        }
        else // Too far, go closer.
        {
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
    }
    /**
     * Makes the medic begin healing the downed human, making it unable to move for a moment.
     */
    private void healHuman(Human target) {
        speed = 0; // the medic cannot move while healing.
        // Create a delayed event that would happen 30 acts later which finishes the healing.
        createEvent(new DelayedEvent(() -> finishHealing(target), 30));
    }
    /**
     * The medic has finished healing the human and can move again.
     * Used in the DelayedEvent, but made seperate for readability.
     */
    private void finishHealing(Human target) {
        if (isAwake()) { // if I didn't die while I was healing...
            target.healMe(); // heal the dead human
            System.out.println("healed");
            target = null;
            speed = maxSpeed; // can move again
        }
    }
}

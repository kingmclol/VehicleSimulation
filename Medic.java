import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * The Medic is very helpful, moving towards and healing any Humans that are knocked down.
 * However, it is defenseless, and it takes time to heal a knocked down person,
 * making it very vulnerable out there on the street. They're also TOO nice, trying to heal a downed Human
 * no matter how dangerous it is.
 * 
 * If the Medic has nothing to do, it will try to cross the street normally.
 * 
 * @author Freeman Wang
 * @version (a version number or a date)
 */
public class Medic extends Human
{
    private Human target;
    private ArrayList<Human> humans;
    public Medic (int direction)
    {
        super(direction);
        visionRangeDay = 350;
        visionRangeNight = 150;
        visionRange = visionRangeDay;
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
        
        if (atEdge()) removeMe();
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

        humans = (ArrayList<Human>)getObjectsInRange(visionRange/4, Human.class); // Get nearby humans
        humans.removeIf(p -> p.isAwake()); // Remove any that are alive (they don't need to be healed)
        if (humans.size() == 0){ // None found, expand search.
            humans = (ArrayList<Human>)getObjectsInRange(visionRange/2, Human.class);
            humans.removeIf(p -> p.isAwake());
        } 
        if (humans.size() == 0){ // Still none found? Expand search again to maximum radius.
            humans = (ArrayList<Human>)getObjectsInRange(visionRange, Human.class);
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
            target=null;
        }
        else // Too far, go closer.
        {
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
    }
    /**
     * Heals the human. The medic is vulnerable for 30 acts (it cannot move while healing.)
     */
    private void healHuman(Human c) {
        speed = 0; // the medic cannot move while healing.
        
        // Creates a delayed event that would happen 30 acts later which heals the target, and lets the medic move again.
        // createEvent(new DelayedEvent(() -> {
                                        // if (isAwake()) {
                                            // c.healMe(); // heal the dead citizen
                                            // target=null; // no more target, need to find a new one
                                            // speed = maxSpeed; // can move again
                                        // }}, 30));
        
    }
    private void finishHealing(Human c) {
        
    }
}

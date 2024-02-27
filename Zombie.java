import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Zombie extends Pedestrian
{

    // Instance variables - Class variables
    private Human target;
    private ArrayList<Human> humans;
    private double speed = 2.0;

    /**
     * Primary constructor for Bug - creates a new Bug with full HP.
     * This is called by the Spawn button, and used for creating the 
     * first bug at the beginning of the simulation.
     */
    public Zombie (int direction)
    {
        super(direction);
    }

    /**
     * Act - do whatever the Bug wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (getWorld() != null) {
            if (target != null && target.getWorld() == null){ // target does not exist in world anymore
                target = null; // no more target
            }
            if (target == null || !target.isAwake() || distanceFrom(target) > 40){ // too far, no target, or target is not awake
                findTarget(); // find new target
            }
    
            // If my current target Human exists, move toward or attack it
            if (target != null)
            {
                moveTowardOrKillTarget();
            }
            else moveToOtherSide(); // if it does not, move normally
        }
        
        if (atEdge()) getWorld().removeObject(this);
        else if (!isAwake()) { // run over, in this case.
            killMe();
        }
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

        humans = (ArrayList<Human>)getObjectsInRange(40, Human.class);
        humans.removeIf(p -> !p.isAwake());
        if (humans.size() == 0){
            humans = (ArrayList<Human>)getObjectsInRange(140, Human.class);
            humans.removeIf(p -> !p.isAwake());
        } 
        if (humans.size() == 0){
            humans = (ArrayList<Human>)getObjectsInRange(350, Human.class);
            humans.removeIf(p -> !p.isAwake());
        } 
        // if (Humans.size() == 0){
            // //Humans = (ArrayList<Human>)getWorld().getObjects(Human.class);
        // } 

        if (humans.size() > 0)
        {
            // set the first one as my target
            target = humans.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDistance = distanceFrom(target);

            // Loop through the objects in the ArrayList to find the closest target
            for (Human h : humans)
            {
                distanceToActor = distanceFrom(h);
                // If I find a Human closer than my current target, I will change
                // targets
                if (distanceToActor < closestTargetDistance)
                {
                    target = h;
                    closestTargetDistance = distanceToActor;
                }
            }
        }
    }

    /**
     * Private method, called by act(), that moves toward the target,
     * or knocks it down if within range.
     */
    private void moveTowardOrKillTarget ()
    {
        if (distanceFrom(target) < 18)
        {
            target.knockDownAndInfect();
            target=null;
        }
        else
        {
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
    }
}

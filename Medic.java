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
    private Civilian target;
    private ArrayList<Civilian> civilians;
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
        if (getWorld() != null) {
            if (target != null && target.getWorld() == null){ // target does not exist anymore
                target = null; // no more target
            }
    
            if (target == null || target.isAwake() || Utility.getDistance (getPosition(), target.getPosition()) > 40){ // too far or no target
                findTarget(); // find new target
            }
            if (target != null)
            {
                moveTowardOrHelpTarget();
            }
            else if (!obstructedPath()) setLocation (getX(), getY() + (speed*direction)); // if it does not, move normally
        }
        if (atEdge()) getWorld().removeObject(this);
        else if (!isAwake()) { // run over.
            getWorld().addObject(new DeathParticle(), getX(), getY()); 
            getWorld().removeObject(this);
        }
    }    

    /**
     * Private method, called by act(), that constantly checks for closer targets
     */
    private void findTarget ()
    {
        double closestTargetDistance = 0;
        double distanceToActor;
        // Get a list of all Civilians in the World, cast it to ArrayList
        // for easy management

        civilians = (ArrayList<Civilian>)getObjectsInRange(40, Civilian.class);
        civilians.removeIf(p -> p.isAwake());
        if (civilians.size() == 0){

            civilians = (ArrayList<Civilian>)getObjectsInRange(140, Civilian.class);
            civilians.removeIf(p -> p.isAwake());
        } 
        if (civilians.size() == 0){

            civilians = (ArrayList<Civilian>)getObjectsInRange(350, Civilian.class);
            civilians.removeIf(p -> p.isAwake());
        } 
        // if (Civilians.size() == 0){
            // //Civilians = (ArrayList<Civilian>)getWorld().getObjects(Civilian.class);
        // } 

        if (civilians.size() > 0)
        {
            // set the first one as my target
            target = civilians.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDistance = Utility.getDistance (this, target);

            // Loop through the objects in the ArrayList to find the closest target
            for (Civilian c : civilians)
            {
                distanceToActor = Utility.getDistance(this, c);
                // If I find a Civilian closer than my current target, I will change
                // targets
                if (distanceToActor < closestTargetDistance)
                {
                    target = c;
                    closestTargetDistance = distanceToActor;
                }
            }
            //turnTowards(target.getX(), target.getY());
        }
    }

    /**
     * Private method, called by act(), that moves toward the target,
     * or eats it if within range.
     */
    private void moveTowardOrHelpTarget ()
    {
        if (Utility.getDistance(getPosition(), target.getPosition()) < 18)
        {
            healCivilian(target);
            target=null;
        }
        else
        {
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
    }
    private void healCivilian(Civilian c) {
        speed = 0;
        createEvent(new DelayedEvent(() -> {
                                        if (getWorld()!=null && c.getWorld()!=null) {
                                            c.healMe();
                                            target=null;
                                            speed = maxSpeed;
                                        }}, 30));
    }
    /**
     * A method to be used for moving randomly if no target is found. Will mostly
     * just move in its current direction, occasionally turning to face a new, random
     * direction.
     */
    private void moveRandomly ()
    {
        if (Greenfoot.getRandomNumber (100) == 50)
        {
            turn (Greenfoot.getRandomNumber(360));
        }
        else
            move (speed);
    }
}

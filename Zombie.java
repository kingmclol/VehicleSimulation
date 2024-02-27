import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Zombie extends Pedestrian
{

    // Instance variables - Class variables
    private Human target;
    private ArrayList<Human> humans;
    //private SuperStatBar energyBar;
    private int hp = 111;
    private int maxHp;
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
            if (target != null && target.getWorld() == null){ // target does not exist anymore
                target = null; // no more target
            }
    
            if (target == null || !target.isAwake() || Utility.getDistance (getPosition(), target.getPosition()) > 40){ // too far or no target
                findTarget(); // find new target
            }
    
            // If my current target Human exists, move toward it
            if (target != null)
            {
                moveTowardOrKillTarget();
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
            closestTargetDistance = Utility.getDistance (this, target);

            // Loop through the objects in the ArrayList to find the closest target
            for (Human c : humans)
            {
                distanceToActor = Utility.getDistance(this, c);
                // If I find a Human closer than my current target, I will change
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
    private void moveTowardOrKillTarget ()
    {
        if (Utility.getDistance(getPosition(), target.getPosition()) < 18)
        {
            target.knockDown();
            target=null;
        }
        else
        {
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
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

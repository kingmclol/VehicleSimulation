import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Zombies are Pedestrians that attempt to chase and infect any Humans roaming around.
 * While posing only a small threat when alone, things may get problmeatic really quickly
 * if not handled properly...
 * 
 * If no Humans are found, it moves normally to get to the other side.
 * 
 * @author Freeman Wang
 * @version 2024-02=27
 */
public class Zombie extends Pedestrian
{
    private Human target;
    private ArrayList<Human> humans;
    public Zombie (int direction)
    {
        super(direction);
        awake = false; // They're dead, thus, not awake, and can be cured by ambulances.
        visionRangeDay = 250;
        visionRangeNight = 400;
    }
    /**
     * Act - do whatever the Zombie wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (getWorld()!= null) {
            if (target != null && !target.exists()){ // target does not exist in world anymore
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
        
        if (atEdge()) removeMe();
    }
    /**
     * Tries to find the closest Human that is still awake.
     */
    private void findTarget ()
    {
        double closestTargetDistance = 0;
        double distanceToActor;
        // Get a list of all Humans in the World, cast it to ArrayList
        // for easy management

        humans = (ArrayList<Human>)getObjectsInRange(visionRange/4, Human.class);
        humans.removeIf(p -> !p.isAwake()); // Remove any not awake humans, as they're not important to me.
        if (humans.size() == 0){
            humans = (ArrayList<Human>)getObjectsInRange(visionRange/2, Human.class);
            humans.removeIf(p -> !p.isAwake());
        } 
        if (humans.size() == 0){
            humans = (ArrayList<Human>)getObjectsInRange(visionRange, Human.class);
            humans.removeIf(p -> !p.isAwake());
        } 
        if (humans.size() > 0) // Found one or more awake humans
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
     * Move toward, or infect the target Human.
     */
    private void moveTowardOrKillTarget ()
    {
        if (distanceFrom(target) < 18) // Close enough in distance--attack.
        {
            target.knockDownAndInfect();
            target = null;
        }
        else // To far. Move closer.
        {
            if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        }
    }
    /**
     * Zombies cannot be knocked down. They just die, since they don't die twice.
     */
    @Override
    public void knockDown(){
        killMe();
    }
    /**
     * Turns the zombie back into a civilian. Hopefully they don't die again, as
     * that wouldn't be fun. Better run!
     */
    @Override
    public void healMe(){
        getWorld().addObject(new Civilian(direction),getX(),getY());
        removeMe();
    }
}

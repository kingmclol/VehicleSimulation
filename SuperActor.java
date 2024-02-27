import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * A SuperActor is an Actor that provides new functionality, namely compatability with using Vectors.
 * SuperActor should extend SuperSmoothMover by Mr. Cohen.
 * I intended for SuperActor to make my life easier while developing. It did not.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class SuperActor extends SuperSmoothMover
{
    /** 
     * Creates a given event by adding it into the world.
     */
    public void createEvent(Event e){
        getWorld().addObject(e, 0 ,0 );
    }
    /**
     * Returns the current position as a Vector.
     */
    public Vector getPosition() {
        return new Vector(getX(), getY());
    }
    /** 
     * Moves [distance] units toward a SuperActor.
     */
    public void moveTowards(SuperActor a, double distance) {
        displace(getPosition().distanceFrom(a.getPosition()).scaleTo(distance));
    }
    /**
     * Moves [distance] units towards a position.
     */
    public void moveTowards(Vector v, double distance) {
        displace(getPosition().distanceFrom(v).scaleTo(distance));
    }
    /**
     * Apply the given [displacement] vector to the SuperActor's current position.
     */
    protected void displace(Vector displacement) {
        setLocation(getX()+displacement.getX(), getY()+displacement.getY());
    }
    /**
     * Returns the displacement Vector pointing from the current position to the SuperActor's
     * position.
     * @param target The Actor to go to.
     * @param distance The amount of distance to move.
     * @return The potential displacement Vector this SuperActor would move by.
     */
    protected Vector getDisplacement(SuperActor target, double distance) {
        return getDisplacement(target.getPosition(), distance);
    }
    /**
     * Returns the displacement Vector pointing from the current position to a target position.
     * @param target The position to go to.
     * @param distance The amount of distance to move.
     * @return The potential displacement Vector this SuperActor would move by.
     */
    protected Vector getDisplacement(Vector target, double distance) {
        return getPosition().distanceFrom(target).scaleTo(distance);
    }
    /**
     * Turns the SuperActor towards the given position.
     */
    protected void turnTowards(Vector position) {
        turnTowards(Utility.round(position.getX()), Utility.round(position.getY()));
    }
    /**
     * Returns the magnitude of the distance from this SuperActor and the target SuperActor.
     * @param other The target SuperActor.
     * @return The distance between this SuperActor and the target SuperActor.
     */
    protected double distanceFrom(SuperActor other) {
        return getPosition().distanceFrom(other.getPosition()).getMagnitude();
    }
    /**
     * Returns true if the this SuperActor exists.
     * @return Whether this SuperActor exists in the world.
     */
    public boolean exists() {
        return (getWorld() != null);
    }
    /**
     * Get the closest SuperActor of a given class and radius. Inspired by Mr. Cohen's
     * implementation.
     * @param c The Class of the SuperActor to look for.
     * @param range The radius around to search in.
     * @return The closest SuperActor found. Returns null if none found.
     */
    protected SuperActor getClosestInRange(Class c, int range) {
        ArrayList<SuperActor> targets = (ArrayList<SuperActor>)getObjectsInRange(range, c);
        return getClosest(targets);
    }
    /** 
     * Get the closest SuperActor in a given ArrayList of SuperActors. Inspired
     * by Mr.Cohen's implementation.
     * @param targets The ArrayList to find the closest SuperActor from.
     * @return The closest SuperActor found. Returns null if none found.
     */
    protected SuperActor getClosest(ArrayList<SuperActor> targets) {
        double closestTargetDistance = 0;
        double distanceToActor;
        SuperActor target = null;
        if (targets.size() > 0) {
            target = targets.get(0);
            closestTargetDistance = distanceFrom(target);
            for (SuperActor a : targets) {
                distanceToActor = distanceFrom(a);
                if (distanceToActor < closestTargetDistance)
                {
                    target = a;
                    closestTargetDistance = distanceToActor;
                }
            }
        }
        return target;
    }
    // I honestly tried, but I'm just digging myself into a deeper hole if I try to complete this.
    // I have no idea why I am doing all these unnecessary things, but god damn
    // my code is edible spaghetti at this point.
    /*
    protected SuperActor getClosestActor(Class c, int range, Callable condition){
        double closestTargetDistance = 0;
        double distanceToActor;
        SuperActor target;
        ArrayList<SuperActor> targets = (ArrayList<SuperActor>)getObjectsInRange(range, c);
        targets.removeIf(condition);
        if (targets.size() > 0) {
            target = targets.get(0);
            closestTargetDistance = distanceFrom(target);
            for (SuperActor a : targets) {
                distanceToActor = distanceFrom(a);
                if (distanceToActor < closestTargetDistance)
                {
                    target = a;
                    closestTargetDistance = distanceToActor;
                }
            }
        }
        return target;
    }
    */
}

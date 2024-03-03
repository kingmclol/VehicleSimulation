import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * <strong>SuperActor should extend SuperSmoothMover by Mr. Cohen.</strong>  
 * 
 * <p>A SuperActor is an Actor that provides new functionality, namely compatability with using Vectors as positions. 
 * SuperActors can do more than normal Actors, such as move towards a point while keeping its rotation unchanged.
 * Well, that was before I learned about the existence of static rotation from SuperSmoothMover... whatever. I guess
 * I'm just reinventing the wheel at this point. Should've looked at the Library of Code first...</p>
 * 
 * <blockquote>"I intended for SuperActor to make my life easier while working with Greenfoot. It did not."</blockquote>
 * 
 * TODO: make speed as instance variable, add constructor for SuperActor. Will likely not do during this project.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */

public class SuperActor extends SuperSmoothMover
{
    /* Not to be added during the vehicle simulator, but here so I won't forget.
    ArrayList <Vector> path;
    private double speed;
    private boolean staticRotation;
    public SuperActor(double speed) {
        this.speed = speed;
    }
    Remove all "double distance" parameters in the methods, as will be managed 
    by instance variable speed.
    */
    /** 
     * Creates a given event by adding it into the world.
     */
    protected void createEvent(Event e){
        getWorld().addObject(e, 0 ,0 );
    }
    /**
     * Returns the current position as a Vector.
     */
    public Vector getPosition() {
        return new Vector(getX(), getY());
    }
    /** 
     * Move towards a SuperActor.
     * @param target The SuperActor to move towards.
     * @param The distance the SuperActor should travel. Also can be seen as the "speed."
     */
    protected void moveTowards(SuperActor target, double distance) {
        //displace(getPosition().distanceFrom(a.getPosition()).scaleTo(distance));
        displace(getDisplacement(target, distance));
    }
    /**
     * Moves towards towards a position.
     * @param targetPos The target position to head towards. 
     * @param distance The distance the SuperActor should travel. Also can be seen as the "speed."
     */
    protected void moveTowards(Vector targetPos, double distance) {
        //displace(getPosition().distanceFrom(v).scaleTo(distance));
        displace(getDisplacement(targetPos, distance));
    }
    /**
     * Apply the given displacement vector to the SuperActor's current position. Can be thought
     * of as "moving" the SuperActor.
     * @param displacement The displacement the SuperActor should have.
     */
    protected void displace(Vector displacement) {
        setLocation(getX()+displacement.getX(), getY()+displacement.getY());
    }
    /**
     * Returns the displacement Vector pointing from the current position to the SuperActor's
     * position.
     * @param target The SuperActor to go to.
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
        /*
        First, gets the Vector pointing from this SuperActor to the target position, and scale to the
        speed of the SuperActor (in this case, it is equal to the distance travelled, as time = 1 act)
        Then, limit (cap) the magnitude of the displacement to the ACTUAL distance from the target, so it would
        not go past the target position, in case that scenario will occur.
        */
        return getPosition().distanceFrom(target).scaleTo(distance).limitMagnitude(distanceFrom(target));
    }
    /**
     * Turns the SuperActor towards the given position.
     * @param position The position the SuperActor should turn towards.
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
        //return getPosition().distanceFrom(other.getPosition()).getMagnitude();
        return distanceFrom(other.getPosition());
    }
    /**
     * Returns the magnitude of the distance from this SuperActor to the target position.
     * @param targetPos The target position.
     * @return the distance between this SuperActor and the target position.
     */
    protected double distanceFrom(Vector targetPos) {
        return getPosition().distanceFrom(targetPos).getMagnitude();
    }
    /**
     * Returns true if the this SuperActor exists in a World.
     * @return Whether this SuperActor exists in a World.
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
    // my code overall in this project is edible spaghetti at this point.
    /*
    protected SuperActor getClosestActor(Class c, int range, ____ condition){
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

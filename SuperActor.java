import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuperActor extends SuperSmoothMover
{
    /**
     * Act - do whatever the SuperActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void createEvent(Event e){
        getWorld().addObject(e, 0 ,0 );
    }
    public Vector getPosition() {
        return new Vector(getX(), getY());
    }
    public void moveTowards(SuperActor a, double distance) {
        displace(getPosition().distanceFrom(a.getPosition()).scaleTo(distance));
    }
    public void moveTowards(Vector v, double distance) {
        displace(getPosition().distanceFrom(v).scaleTo(distance));
    }
    protected void displace(Vector displacement) {
        setLocation(getX()+displacement.getX(), getY()+displacement.getY());
    }
    protected Vector getDisplacement(SuperActor a, double distance) {
        return getDisplacement(a.getPosition(), distance);
    }
    protected Vector getDisplacement(Vector v, double distance) {
        return getPosition().distanceFrom(v).scaleTo(distance);
    }
    protected void turnTowards(Vector position) {
        turnTowards(Utility.round(position.getX()), Utility.round(position.getY()));
    }
}

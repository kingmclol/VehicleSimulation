import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Pedestrian that tries to walk across the street.
 */
public abstract class Pedestrian extends SuperActor
{
    protected double speed;
    protected double maxSpeed;
    protected int direction; // direction is always -1 or 1, for moving down or up, respectively
    protected  boolean awake, entering;
    protected int visionRangeDay, visionRangeNight;
    protected int visionRange;
    private boolean initialAct; // To get addedToWorld working...
    public Pedestrian(int direction) {
        // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
        // start as awake 
        awake = true;
        entering = true;
        this.direction = direction;
        initialAct = true;
    }
    public void addedToWorld(World w) {
        //System.out.println(this);
        if (!initialAct) return;
        VehicleWorld world = (VehicleWorld) w;
        if (world.isDaytime()) visionRange = visionRangeDay;
        else visionRange = visionRangeNight;
        initialAct = false;
    }
    /**
     * Moves the pedestrian where it tries to get to the other side it spawned from.
     */
    protected void moveToOtherSide() {
        if (!obstructedPath()) setLocation(getX(), getY() + (speed*direction));
    }
    // /**
     // * Act - do whatever the Pedestrian wants to do. This method is called whenever
     // * the 'Act' or 'Run' button gets pressed in the environment.
     // */
    // public void act()
    // {
        // // // Awake is false if the Pedestrian is "knocked down"
        // // if (awake){
            // // // Check in the direction I'm moving vertically for a Vehicle -- and only move if there is no Vehicle in front of me.
            // // if (!obstructedPath()){
                // // setLocation (getX(), getY() + (speed*direction));
            // // }
            // // // if (direction == -1 && getY() < 100){
                // // // getWorld().removeObject(this);
            // // // } else if (direction == 1 && getY() > getWorld().getHeight() - 30){
                // // // getWorld().removeObject(this);
            // // // }

        // // }
    // }
    /**
     * Kills the Pedestrian.
     */
    public void killMe() {
        getWorld().addObject(new DeathParticle(), getX(), getY()); 
        getWorld().removeObject(this);
    }
    /**
     * Returns whether the Pedestrian's movement would be obstructed by a Vehicle.
     * @return If the movement would be obstructed.
     */
    protected boolean obstructedPath() {
        return !(getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null);
    }
    /**
     * Returns whether the target position would be obstructed by a Vehicle.
     * @return If the target position is obstructed.
     */
    protected boolean obstructedAt(Vector displacement) {
        double dx = displacement.getX();
        double dy = displacement.getY();
        return !(getOneObjectAtOffset(
                            Utility.round(dx + getImage().getWidth()/2 * -1 * Utility.getSign(dx)),
                            Utility.round(dy + getImage().getHeight()/2 * Utility.getSign(dy)), 
                            Vehicle.class) == null);
    }
    /**
     * Returns whether the Pedestrian is at the edge of the main area. Not to be confused with
     * the World's edge.
     * @return Whether the pedestrian is at the edge of the main zone.
     */
    public boolean atEdge() {
        if (direction == -1 && getY() < 100){
            return true;
        } else if (direction == 1 && getY() > getWorld().getHeight() - 30){
            return true;
        }
        return false;
    }
    /**
     * Method to cause this Pedestrian to become knocked down - stop moving, turn onto side.
     */
    public void knockDown () {
        speed = 0;
        setRotation (direction * 90);
        awake = false;
    }
    /**
     * Method to allow a downed Pedestrian to be healed
     */
    public void healMe () {
        speed = maxSpeed;
        setRotation (0);
        awake = true;
    }
    /**
     * Returns whether the Pedestrian is awake.
     * @return true if awake.
     */
    public boolean isAwake () {
        return awake;
    }
    /**
     * Returns the speed of the Pedestrian.
     * @return The speed of the Pedestrian.
     */
    public double getSpeed() {
        return speed;
    }
    /**
     * Changes the Pedestrian's vision range to their respective vision values for the
     * time of day.
     * @param daytime Whether the current world time is day.
     */
    public void setVisionRange(boolean daytime){
        visionRange = (daytime) ? visionRangeDay : visionRangeNight;
    }
}

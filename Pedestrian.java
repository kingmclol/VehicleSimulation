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
    private static final double MAX_ZOMBIE_SPEED_BOOST = 0.75;
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
    /**
     * Determines the visionRange the Pedestrian should have, depending on
     * the current time of the World.
     */
    protected void addedToWorld(World w) {
        //System.out.println(this);
        if (!initialAct) return;
        VehicleWorld world = (VehicleWorld) w;
        setStats(world.isDaytime()); // Update stats to respective stats for world time.
        initialAct = false;
        world.addToCount(this);
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
        removeMe();
    }
    /**
     * Returns whether the Pedestrian's movement would be obstructed by a Vehicle.
     * @return If the movement would be obstructed.
     */
    protected boolean obstructedPath() {
        int halfHeight = getImage().getHeight()/2;
        int halfWidth = getImage().getWidth()/2;
        if (getOneObjectAtOffset(0, (int)(direction * halfHeight + (int)(direction * speed)), Vehicle.class) != null){
            return true;
        }
        else if (getOneObjectAtOffset(halfHeight, (int)(direction * halfHeight + (int)(direction * speed)), Vehicle.class) != null) {
            return true;
        }
        else if(getOneObjectAtOffset(halfHeight, (int)(direction * halfHeight + (int)(direction * speed)), Vehicle.class) != null) {
            return true;
        }
        return false;
        
        //return !(getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null);
    }
    /**
     * Returns whether the target position would be obstructed by a Vehicle.
     * @return If the target position is obstructed.
     */
    protected boolean obstructedAt(Vector displacement) {
        double dx = displacement.getX();
        double dy = displacement.getY();
        int collisionPoints = 0;
        return !(getOneObjectAtOffset(
                            Utility.round(dx + getImage().getWidth()/2 * -1 * Utility.getSign(dx)),
                            Utility.round(dy + getImage().getHeight()/2 * Utility.getSign(dy)),
                            Vehicle.class) == null);
        /*
        It seems like checking all corners is too excessive. Old implementation may be not as *proper*
        but works because it is much less restrictive... slightly better than having no checks at all.
        
        Vehicle[] corners = new Vehicle[4];
        //    Corner      i    x   y
        // Bottom Right | 0 | +1, +1
        // Bottom Left  | 1 | -1, +1
        // Top Right    | 2 | +1, -1
        // Top Left     | 3 | -1, -1
        for (int i = 0; i < 4; i++) {
            int xSign = (i%2 == 0) ? 1 : -1;
            int ySign = (i < 2) ? 1 : -1;
            corners[i] = (Vehicle) getOneObjectAtOffset(
                                        Utility.round(dx + getImage().getWidth()/2*xSign),
                                        Utility.round(dy + getImage().getHeight()/2*ySign),
                                        Vehicle.class);
        }
        for (Vehicle v : corners) {
            if (v!=null) collisionPoints++;
        }
        return collisionPoints > 1;
        */
    }
    public void removeMe() {
        VehicleWorld v = (VehicleWorld) getWorld();
        v.removeFromCount(this);
        v.removeObject(this);
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
     * Changes the Pedestrian's stats (vision range and speed) to their respective values for the
     * time of day.
     * @param daytime Whether the current world time is day.
     */
    public void setStats(boolean daytime){
        visionRange = (daytime) ? visionRangeDay : visionRangeNight; // Update vision
        if (this instanceof Zombie) { // Update speed, if is a zombie.
            speed = daytime ? maxSpeed : maxSpeed + Math.random()*MAX_ZOMBIE_SPEED_BOOST;
        }
    }
}

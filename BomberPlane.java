import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * BomberPlanes drop... Bombs on the city below. The last resort used to (inaccurately) kill zombies
 * when things get out of hand. But hey, it's fun to watch no matter <i>what</i> it is killing!
 * 
 * <p>Image sourced from Adobe Stock</p>
 * 
 * @author Freeman Wang
 * @version 2024-03-03
 */
public class BomberPlane extends SuperActor
{
    /**
     * Act - do whatever the BomberPlane wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    double speed;
    boolean initialAct;
    int numLanes;
    public BomberPlane(){
        initialAct = true;
        speed = 3.0 + Greenfoot.getRandomNumber(2);
    }
    protected void addedToWorld(World w) {
        if (!initialAct) return;
        VehicleWorld v = (VehicleWorld) w;
        numLanes = v.getNumLanes();
        initialAct = false;
    }
    public void act()
    {
        move(speed);
        if (getX() >= 50 && getX() <= getWorld().getWidth()-50){ // Don't drop bombs too close to the side.
            if (Greenfoot.getRandomNumber(20) == 0){ // Randomly drop bombs.
                dropBomb();
            }
        }
        if (isAtEdge()){
            getWorld().removeObject(this);
        }
    }
    private void dropBomb(){
        // Get the Y value of the random target lane the bomb should land in.
        int laneY = ((VehicleWorld)getWorld()).getLaneY(Greenfoot.getRandomNumber(numLanes));
        getWorld().addObject(new Bomb(laneY), getX(), getY());
    }
}

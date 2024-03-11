import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Bullet is a projectile that would move towards a target position. It will move at a straight line.
 * If the Bullet hits a Zombie, the Zombie will be killed.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Bullet extends SuperActor
{
    private double speed; // The speed of the bullet.
    private Vector targetPos; // The position of the target.
    private Vector velocity; // The velocity of the bullet (has direction)
    boolean initialAct; // addedToWorld() is running infinitely due to zSorting. This variable is to counter that.
    /**
     * Creates a Bullet.
     * @param target The SuperActor to target.
     */
    public Bullet(SuperActor target) {
        speed = 6.0;
        targetPos = target.getPosition(); // get the position of the target.
        initialAct = true;
    }
    public void addedToWorld(World w) {
        if (!initialAct) return; // Stop this method from running infinitely...
        initialAct = false;
        velocity = getDisplacement(targetPos, speed); // get the distance travelled per act to move towards the target
        turnTowards(targetPos); // turn the image in the direction of the target.
    }
    public void act() {
        displace(velocity); // move
        if(isTouching(Zombie.class)) { // If hit a zombie, kill it.
            Zombie z = (Zombie)getOneIntersectingObject(Zombie.class);
            z.knockDown();
            getWorld().removeObject(this); // remove the bullet.
        }
        else if(isAtEdge()) {
            getWorld().removeObject(this); // If at the edge of world, remove this.
        }
    }
}

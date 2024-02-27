import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Bullet is a projectile that would move towards a target zombie. If it hits a zombie, kill it.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Bullet extends SuperActor
{
    private double speed; // The speed of the bullet.
    private Vector targetPos; // The position of the target.
    private Vector velocity; // The velocity of the bullet (has direction)
    int count; // Okay, so for some reason addedToWorld() is running infinitely. This count variable is to counter that.
    /**
     * Creates a Bullet.
     * @param target The SuperActor to target.
     */
    public Bullet(SuperActor target) {
        speed = 6.0;
        targetPos = target.getPosition(); // get the position of the target.
    }
    public void addedToWorld(World w) {
        if (count++ > 0) return; // Stop this method from running infinitely...
        velocity = getDisplacement(targetPos, speed); // get the distance travelled per act to move towards the target
        turnTowards(targetPos); // turn the image in the direction of the target.
    }
    public void act() {
        displace(velocity); // move
        if(isTouching(Zombie.class)) { // If hit a zombie, kill it.
            Zombie z = (Zombie)getOneIntersectingObject(Zombie.class);
            z.killMe();
            getWorld().removeObject(this); // remove the bullet.
        }
        else if(isAtEdge()) getWorld().removeObject(this); // If at the edge of world, remove this.
    }
}

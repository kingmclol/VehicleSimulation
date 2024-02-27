import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends SuperActor
{
    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private double speed;
    private Vector targetPos;
    private Vector velocity;
    int count;
    public Bullet(SuperActor target) {
        speed = 4.0;
        targetPos = target.getPosition();
    }
    public Bullet(Vector target){
        speed = 4.0;
        targetPos = target;
    }
    public void addedToWorld(World w) {
        if (count > 0) return;
        velocity = getPosition().distanceFrom(targetPos).scaleTo(speed);
        turnTowards(targetPos);
        System.out.println(this + " | " + count++);
    }
    public void act() {
        displace(velocity);
        if(isTouching(Zombie.class) || isAtEdge()) {
            Zombie z = (Zombie)getOneIntersectingObject(Zombie.class);
            getWorld().removeObject(z);
            getWorld().removeObject(this);
        }
    }
}

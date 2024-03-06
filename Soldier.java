import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The Soldier is a brave person who fights against the Zombies by charing at them with their trusty gun. 
 * However, they don't know how to count, so they may be overwhelmed by trying to fight a group
 * with only one bullet left! Reloading takes a while, so it's not going to look good for them...
 * In a group of Soldiers, however, that's a different story.
 * 
 * If the Soldier has nothing to do, it will try to cross the street normally.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Soldier extends Human
{
    /**
     * Act - do whatever the Soldier wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private static final int RELOAD_TIME = 60; // in acts
    private static final int MAX_BULLETS = 4;
    private int currentBullets;
    private boolean onCooldown;
    private Zombie target;
    public Soldier(int direction) {
        super(direction);
        currentBullets = MAX_BULLETS;
        onCooldown = false;
        visionRangeDay = 300;
        visionRangeNight = 100;
        visionRange = visionRangeDay;
    }
    public void act()
    {
        if (getWorld() != null && isAwake()) {
            if (target != null && !target.exists()){ // target does not exist anymore
                    target = null; // no more target
                }
            if (target == null || distanceFrom(target) > 50){ // too far or no target
                scanForTargets(); // find new target
            }
            if (target != null) { // target exists, move towards or shoot
                moveTowardsOrShootTarget();
            }
            else moveToOtherSide(); // if it does not, move normally
        }

        if (atEdge()) removeMe();
    }
    private void moveTowardsOrShootTarget() {
        if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        if (canShoot()) shoot();
    }
    private void scanForTargets() {
        target = (Zombie)getClosestInRange(Zombie.class, visionRange/2);
        if (target == null) target = (Zombie) getClosestInRange(Zombie.class, visionRange);
    }
    private boolean canShoot() {
        return currentBullets > 0 && !onCooldown;
    }
    private void shoot() {
        currentBullets--; // decrease bullets by 1
        onCooldown = true; // now on cooldown between shots
        getWorld().addObject(new Bullet(target), getX(), getY());
        createEvent(new DelayedEvent(() -> onCooldown = false, 10)); // manage cooldown between shots
        if (currentBullets == 0) { // if no bullets left
            createEvent(new DelayedEvent(() -> currentBullets = MAX_BULLETS, 60)); // reloading
        }
    }
}

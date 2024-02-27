import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Soldier here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Soldier extends Human
{
    /**
     * Act - do whatever the Soldier wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int maxBullets, currentBullets;
    private boolean onCooldown;
    private Zombie target;
    public Soldier(int direction) {
        super(direction);
        maxBullets = 1;
        currentBullets = 1;
        onCooldown = false;
    }
    public void act()
    {
        if (getWorld() != null && isAwake()) {
            if (target != null && target.getWorld() == null){ // target does not exist anymore
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

        if (atEdge()) getWorld().removeObject(this);
    }
    private void moveTowardsOrShootTarget() {
        if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
        if (canShoot()) shoot();
    }
    private void scanForTargets() {
        target = (Zombie)getClosest(Zombie.class, 140);
        if (target == null) target = (Zombie) getClosest(Zombie.class, 300);
        // double closestTargetDistance = 0;
        // double distanceToActor;
        // ArrayList<Zombie> zombies = (ArrayList<Zombie>) getObjectsInRange(140, Zombie.class); // Search for a zombie around me
        // if (zombies.size() == 0){ // If none found within 140 radius,
            // zombies = (ArrayList<Zombie>)getObjectsInRange(300, Zombie.class); // Expand search to 300 radius
        // } 
        // if (zombies.size() > 0) // If a zombie (or more) is found
        // {
            // target = zombies.get(0);
            // closestTargetDistance = distanceFrom(target);
            // for (Zombie z : zombies)
            // {
                // distanceToActor = distanceFrom(z);
                // // If I find a zombie closer than my current target, I will change
                // // targets
                // if (distanceToActor < closestTargetDistance)
                // {
                    // target = z;
                    // closestTargetDistance = distanceToActor;
                // }
            // }
        // }
    }
    private boolean canShoot() {
        return currentBullets > 0 && !onCooldown;
    }
    private void shoot() {
        currentBullets--; // decrease bullets by 1
        onCooldown = true; // now on cooldown between shots
        getWorld().addObject(new Bullet(target), getX(), getY());
        createEvent(new DelayedEvent(() -> onCooldown = false, 10)); // cooldown will finish after 10 acts
        if (currentBullets == 0) { // if no bullets left
            createEvent(new DelayedEvent(() -> currentBullets = maxBullets, 180)); // reload after 180 acts
        }
    }
}

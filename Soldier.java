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
        maxBullets = 5;
        currentBullets = 5;
        onCooldown = false;
    }
    public void act()
    {
        // if (target == null || target.getWorld() == null || distanceFrom(target) > 50) {
            // scanForTargets();
        // }
        if (getWorld()!=null){
            if (target != null && target.getWorld() == null){ // target does not exist anymore
                    target = null; // no more target
                }
            if (target == null || distanceFrom(target) > 40){ // too far or no target
                scanForTargets(); // find new target
            }
            if (target != null) {
                if (!obstructedAt(getDisplacement(target, speed))) moveTowards(target, speed);
                if (canShoot()) shoot();
            }
            else if (!obstructedPath()) setLocation (getX(), getY() + (speed*direction)); // if it does not, move normally
        }
        if (atEdge()) getWorld().removeObject(this);
        else if (!isAwake()) { // killed.
            getWorld().addObject(new DeathParticle(), getX(), getY()); 
            getWorld().removeObject(this);
        }
    }
    private void scanForTargets() {
        double closestTargetDistance = 0;
        double distanceToActor;
        ArrayList<Zombie> zombies = (ArrayList<Zombie>) getObjectsInRange(300, Zombie.class);
        if (zombies.size() > 0)
        {
            target = zombies.get(0);
            closestTargetDistance = distanceFrom(target);
            for (Zombie z : zombies)
            {
                distanceToActor = distanceFrom(z);
                // If I find a Civilian closer than my current target, I will change
                // targets
                if (distanceToActor < closestTargetDistance)
                {
                    target = z;
                    closestTargetDistance = distanceToActor;
                }
            }
            //turnTowards(target.getX(), target.getY());
        }
    }
    private boolean canShoot() {
        return currentBullets > 0 && !onCooldown;
    }
    private void shoot() {
        currentBullets--;
        onCooldown = true;
        getWorld().addObject(new Bullet(target.getPosition()), getX(), getY());
        System.out.println("aaa");
        createEvent(new DelayedEvent(() -> onCooldown = false, 60));
        if (currentBullets == 0) {
            createEvent(new DelayedEvent(() -> currentBullets = maxBullets, 300));
        }
    }
}

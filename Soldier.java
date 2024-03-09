import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * <p>The Soldier is a brave person who fights against the Zombies by charging at them with their trusty gun. 
 * With godlike reaction time but mediocre accuracy, the Soldier can be relied on by others to keep them safe</p>
 * 
 * <p>However, with their relatively low firerate, lone Soldiers are easily overwhelmed when fighting a group
 * of Zombies. A group of Soldiers, however, is a different story.</p>
 * 
 * <p>If the Soldier has nothing to do, it will try to cross the street normally.</p>
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
    private static final int RELOAD_TIME = 90; // in acts
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
        createEvent(new DelayedEvent(() -> onCooldown = false, 15)); // manage cooldown between shots
        if (currentBullets == 0) { // if no bullets left
            createEvent(new DelayedEvent(() -> currentBullets = MAX_BULLETS, RELOAD_TIME)); // reloading
        }
    }
}

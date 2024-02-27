import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Human is an abstract class. Actors that are of species human should
 * extend this, so they would be instances of Human for Zombies to
 * chase. It doesn't anything else, really, as each subclass used
 * have slight differences in logic that I can't combine into one.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public abstract class Human extends Pedestrian
{
    /**
     * Act - do whatever the Human wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Human(int direction) {
        super(direction);
    }
    /**
     * Knocks the Human down, and infects them, turning them into a Zombie if not healed on time.
     */
    public void knockDownAndInfect() {
        super.knockDown();
        createEvent(new DelayedEvent(() -> turnIntoZombie(), 120));
    }
    /**
     * Replaces the Human with a Zombie, if they still are in the world and is not awake.
     * Used for knockDownAndInfect().
     */
    private void turnIntoZombie() {
        if(exists() && !isAwake()) { // If I still am in the world, and I am not awake,
                // Replace myself with a Zombie going in my direction.
                getWorld().addObject(new Zombie(direction), getX(), getY());
                getWorld().removeObject(this);
        }
    }
}

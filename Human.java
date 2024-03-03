import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Human is an abstract class. Humans can get infected by Zombies if they are
 * knocked down by them and they are not healed for a period of time.
 * I feel sorry for the poor Medics who would be in for quite a scare...
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
        // Turn into a zombie after 120 acts.
        createEvent(new DelayedEvent(() -> turnIntoZombie(), 120));
    }
    /**
     * Replaces the Human with a Zombie, if they still are in the world and is not awake.
     * Used for knockDownAndInfect(), put in a seperate method for readability.
     */
    private void turnIntoZombie() {
        if(exists() && !isAwake()) { // If I still am in the world, and I am not awake,
                // Replace myself with a Zombie going in my direction.
                getWorld().addObject(new Zombie(direction), getX(), getY());
                removeMe();
        }
    }
}

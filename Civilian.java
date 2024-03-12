import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Civilian has nothing special. It just wants to get the heck out of here,
 * either through getting on a bus or moving off screen. It has no means of defending
 * itself or helping others. However, it does have the ability to be immune from zombies (and only zombies)
 * for a short while after spawning. This is 100% a feature that is not a band aid solution
 * to another issue. But hey, Ambulances are more useful now!
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class Civilian extends Human
{
    /**
     * Act - do whatever the Grunt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private boolean immune;
    private static final int IMMUNITY_PERIOD = 20; // in acts
    public Civilian(int direction) {
        super(direction);
        immune = true;
    }
    public void addedToWorld(World w) {
        if (!initialAct) return;
        super.addedToWorld(w);
        createEvent(new DelayedEvent(() -> immune = false, IMMUNITY_PERIOD)); // After 20 acts, the civilian can be infected.
    }
    public void act() {
        if (awake){
            moveToOtherSide();
            if (atEdge()) removeMe();
        }
    }
    /**
     * Heals the Civilian normally and gives immunity period
     */
    public void healMe() {
        super.healMe();
        immune = true;
        createEvent(new DelayedEvent(() -> immune = false, IMMUNITY_PERIOD)); // After 20 acts, the civilian can be infected.
    }
    /**
     * Same as Human's knockDownAndInfect(), except that the Civilian can possibly be immune currently
     * and thus not get infected. If they are not immune, well sad for them.
     */
    public void knockDownAndInfect() {
        if (immune) return; // Immune. Nothing happens.
        super.knockDownAndInfect();
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Effects are visual effects for World... effects. Which are... events? Either way,
 * Effects basically do something, fade in, wait, fade out, do something else. That's pretty much
 * all there is. Don't ask me what I was thinking when doing this.
 * 
 * @author Freeman Wang
 * @version 2024-03-09
 */
public abstract class Effect extends Actor
{
    /**
     * Act - do whatever the Effect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int actsLived, eventDuration, fadeInTarget;
    private boolean fadingIn;
    protected boolean initialAct;
    protected VehicleWorld world;
    /**
     * Creates a effect that lasts for a specified amount of acts, and has a target transparency
     * throughout the effect's lifetime.
     * @param eventDuration how long the effect lasts.
     * @param fadeInTarget the target transparency to remain in
     */
    public Effect(int eventDuration, int fadeInTarget) {
        this.eventDuration = eventDuration;
        this.fadeInTarget = fadeInTarget;
        actsLived = 0;
        initialAct = true;
        fadingIn = true;
    }
    public void addedToWorld(World w){
        if (!initialAct) return;
        world = (VehicleWorld) w; // Store the world for convenience
        startEffect(); // Start the effect, whavever it does.
        initialAct = false;
    }
    public void act() {
        if (fadingIn && getImage().getTransparency() < fadeInTarget) { // If currently fading in, and not at target transparency,
            fadeIn(1);
        }
        else fadingIn = false; // Done fading in
        
        // Event has run its course, so time to fade out gracefully.
        if (++actsLived >= eventDuration) {
            fadeOut(1);
            if (getImage().getTransparency() <= 0) { // Gone completely, so
                stopEffect(); // Stop the effect.
                getWorld().removeObject(this);
            }
        }
    }
    /**
     * Increases the Effect's transparency by the given amount.
     * @param step how much transparency to gain.
     */
    protected void fadeIn(int step) {
        getImage().setTransparency(Math.min(getImage().getTransparency()+step, 255)); // Increase transparency, max 255
    }
    /**
     * Decreases the Effect's transparency by the given amount.
     * @param step how much transparency to lose.
     */
    protected void fadeOut(int step) {
        getImage().setTransparency(Math.max(getImage().getTransparency()-step, 0)); // Decrease transparency, min 0
    }
    /**
     * Code to run when the Effect becomes active.
     */
    public abstract void startEffect();
    /**
     * Code to run when the Effect has ended.
     */
    public abstract void stopEffect();
    
}

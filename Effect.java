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
        if (fadingIn && getImage().getTransparency() < fadeInTarget) {
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
    protected void fadeIn(int step) {
        getImage().setTransparency(Math.min(getImage().getTransparency()+step, 255));
    }
    protected void fadeOut(int step) {
        getImage().setTransparency(Math.max(getImage().getTransparency()-step, 0));
    }
    public abstract void startEffect();
    public abstract void stopEffect();
    
}

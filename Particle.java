import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Particles are images added to the World for special effects or whatever you want to do with it. Every act,
 * it would lose a set amount of transparancy. If the Particle is fully transparent, or too small, it will be removed from
 * the World.
 * 
 * @author Freeman Wang
 * @version 2024-02-29
 */
public abstract class Particle extends Actor
{
    /**
     * Act - do whatever the Particle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected GreenfootImage baseImage, currentImage;
    protected int currentLifespan, lifespan, step, actsPerStep;
    // public Particle(GreenfootImage baseImage, int lifespan) {
        // currentLifespan = 0;
        // step = 5; // Lose 5 transparency every action.
        // this.baseImage = baseImage;
        // currentImage = baseImage;
        // this.lifespan = lifespan;
        // actsPerStep = lifespan/(255/step); // get the amount of acts per step to make the particle have its target lifespan.
    // }
    /**
     * A basic Particle. Has an image, and will decrease by 5 transparency every step.
     * @param image The GreenfootImage the Particle shall use.
     */
    public Particle(GreenfootImage image){
        this(image, 5);
    }
    /**
     * A particle with customizable step in transparency decreases. Can be used as an arbitrary way to
     * increase or decrease the amount of time a Particle exists.
     * @param image The GreenfootImage the Particle shall use.
     * @param step The amount of transparency to lose every step.
     */
    public Particle(GreenfootImage image, int step) {
        this.step = step;
        baseImage = image;
        currentImage = image;
        currentLifespan = 0;
    }
    public void act()
    {
        currentLifespan++;
        setImage(currentImage);
        //if (timeToAct()) decayTransparency();
        decayTransparency();
        // Remove the Particle if it is transparent, or very small.
        if (currentImage.getTransparency() <= 0 || currentImage.getWidth() <= 15 || currentImage.getHeight() <= 15) {
            getWorld().removeObject(this);
        }
    }
    // /**
     // * Determine if it is time to do an action (e.g. decayTransparency).
     // * @return true if the Particle should act right now.
     // */
    // protected boolean timeToAct() {
        // return (currentLifespan % actsPerStep == 0);
    // }
    /**
     * Decays the transparancy of the Particle.
     */
    protected void decayTransparency() {
        currentImage.setTransparency(Math.max(0,currentImage.getTransparency()-step));
    }
    /**
     * Decays the particle's size by a given Factor. Using this *may* make the Particle decide to
     * remove itself earlier than your chosen lifespan... Not recommended to use, but *does* work.
     * 
     * @param scaleFactor The factor to scale the image by. Should be greater than 0, and less than 1.
     */
    protected void decaySize(double scaleFactor) {
        /*
        As it seems, scaling a scaled image leads to weird behaviours (circle becomes a square)
        Solution is to always scale from the base image, so each image is only scaled once, so any
        issues from compounding scale() actions will not snowball into visible issues with the image.
        */
        GreenfootImage newImage = new GreenfootImage(baseImage);
        newImage.scale(Math.max(1, Utility.round(currentImage.getWidth()*scaleFactor)), Math.max(1, Utility.round(currentImage.getHeight()*scaleFactor)));
        newImage.setTransparency(currentImage.getTransparency()); // To ensure transparency decay will carry over between images
        currentImage = newImage;
    }
    /**
     * Makes the Particle use a new provided image rather than the one given in the constructor.
     * @param img The new image to use.
     */
    protected void useNewImage(GreenfootImage img){
        setImage(img);
        baseImage = img;
        currentImage = img;
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Particles are images added to the World for special effects or whatever you want to do with it.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    public Particle(GreenfootImage image){
        this(image, 5);
    }
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
        if (currentImage.getTransparency() <= 0 || currentImage.getWidth() <= 15 || currentImage.getHeight() <= 15) {
            getWorld().removeObject(this);
        }
        
        // // I determine the Particle as completed its job when it is transparent,
        // // its size is too small to reasonably see anymore, or its lifespan
        // // has exceeded how long the Particle is expected to live.
        // if (currentImage.getTransparency() <= 0 || currentImage.getWidth() <= 1 || currentImage.getHeight() <= 1 || currentLifespan >= lifespan) {
            // getWorld().removeObject(this);
        // }
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
     * remove itself earlier than your chosen lifespan...
     * 
     * @param scaleFactor The factor to scale the image by. Should be greater than 0, and less than 1.
     */
    protected void decaySize(double scaleFactor) {
        // As it seems, scaling a scaled image leads to weird behaviours (circle becomes a square)
        // Solution is to always scale from the base image, so each image is only scaled once.
        GreenfootImage newImage = new GreenfootImage(baseImage);
        newImage.scale(Math.max(1, Utility.round(currentImage.getWidth()*scaleFactor)), Math.max(1, Utility.round(currentImage.getHeight()*scaleFactor)));
        newImage.setTransparency(currentImage.getTransparency()); // To ensure transparency decay will carry over between images
        currentImage = newImage;
    }
    protected void useNewImage(GreenfootImage img){
        setImage(img);
        baseImage = img;
        currentImage = img;
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Particle here.
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
    public Particle(GreenfootImage baseImage, int lifespan) {
        currentLifespan = 0;
        step = 5;
        this.baseImage = baseImage;
        currentImage = baseImage;
        this.lifespan = lifespan;
        actsPerStep = lifespan/(255/step);
    }
    public void act()
    {
        currentLifespan++;
        setImage(currentImage);
        if (currentImage.getTransparency() <= 0 || currentImage.getWidth() <= 1 || currentImage.getHeight() <= 1 || currentLifespan >= lifespan) {
            getWorld().removeObject(this);
        }
    }
    protected boolean timeToAct() {
        return (currentLifespan % actsPerStep == 0);
    }
    protected void decayTransparency() {
        currentImage.setTransparency(Math.max(0,currentImage.getTransparency()-step));
        //System.out.println(currentImage.getTransparency());
    }
    protected void decaySize(double scaleFactor) {
        // As it seems, scaling a scaled image leads to weird behaviours (circle becomes a square)
        // Solution is to always scale from the base image, so each image is only scaled once.
        GreenfootImage newImage = new GreenfootImage(baseImage);
        newImage.scale(Math.max(1, Utility.round(currentImage.getWidth()*scaleFactor)), Math.max(1, Utility.round(currentImage.getHeight()*scaleFactor)));
        newImage.setTransparency(currentImage.getTransparency()); // To ensure transparency decay will carry over between images
        currentImage = newImage;
    }
    public void useNewImage(GreenfootImage image) { 
        // Unfortunately, I can't create an image before passing into super constructor. This is the best solution, I guess.
        baseImage=image;
        currentImage = image;
    }
}

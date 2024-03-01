import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Explosions are a Particle of circular appearance and red in color. Used for, well, an explosion effect.
 * 
 * @author Freeman Wang
 * @version 2024-02-29
 */
public class Explosion extends Particle
{
    /**
     * Creates an Explosion effect. Nothing fancy.
     * @int radius The radius of the explosion image.
     */
    public Explosion(int radius) {
        super(new GreenfootImage(1,1), 4); // Pass in a TEMPOARY image to the superclass.
        
        // Construct the image of given radius.
        GreenfootImage image = new GreenfootImage(radius, radius);
        image.setColor(Color.RED);
        image.clear();
        image.fillOval(0,0,radius,radius);
        useNewImage(image); // Set the image for the Explosion as the new image.
    }
    /**
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        // In addition to losing transparency over time, also decrease in the size
        // after existing for a bit.
        if (currentLifespan > 30) decaySize(0.99);
    }
}

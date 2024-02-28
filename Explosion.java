import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Particle
{
    public Explosion(int radius) {
        super(new GreenfootImage(1,1), 4);
        GreenfootImage image = new GreenfootImage(radius, radius);
        image.setColor(Color.RED);
        image.clear();
        image.fillOval(0,0,radius,radius);
        useNewImage(image);
    }
    /**
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if (currentLifespan > 30) decaySize(0.99);
    }
}

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
        super(new GreenfootImage(1,1), 60*1);
        GreenfootImage image = new GreenfootImage(radius, radius);
        // for (int i = 0; i < 3; i++) {
            // if (i%2==0) image.setColor(Color.RED);
            // else image.setColor(Color.ORANGE);
            // image.fillOval(radius-radius/(i+1), radius-radius/(i+1),radius/(3-i), radius/(3-i));
        // }
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
        if (timeToAct()) {
            if (currentLifespan > 60/2) decaySize(0.95);
            decayTransparency();
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DeathParticle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DeathParticle extends Particle
{
    /**
     * Act - do whatever the DeathParticle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public DeathParticle() {
        super(new GreenfootImage("skull.png"), 60);
    }
    public void act()
    {
        super.act();
        if (timeToAct()) {
            decayTransparency();
        }
    }
}

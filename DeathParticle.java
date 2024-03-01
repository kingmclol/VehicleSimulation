import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The DeathParticle is literally a Particle with a skull image signifying that something has died.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class DeathParticle extends Particle
{
    /**
     * Act - do whatever the DeathParticle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    /**
     * Creates a :skull: Particle.
     */
    public DeathParticle() {
        super(new GreenfootImage("skull.png"));
    }
    public void act()
    {
        super.act();
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The ExplosiveTruck is a truck that is... explosive! Make sure no Pedestrians touch this
 * thing, as the driver wasn't careful enough to ensure their explosives were
 * disabled in their rush to get out of the city, making the slightest disturbance enough to
 * cause a bright fireworks show.
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public class ExplosiveTruck extends Vehicle
{
    private int explosionRadius; // The radius of the explosion.
    private SuperSound explosionSound;
    public ExplosiveTruck(VehicleSpawner o) {
        super(o);
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        yOffset = 12;
        explosionRadius = 150;
        
        // Randomly choose between Explosion1, Explosion2, Explosion3
        String explosionName = "Explosion" + (Greenfoot.getRandomNumber(3) + 1) + ".mp3";
        explosionSound = new SuperSound(explosionName);
    }
    /**
     * Act - do whatever the ExplosiveTruck wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    /**
     * Checks for a hit pedestrian. Returns true if one was hit, false if none were.
     * Explodes a pedestrian is hit.
     * @return Whether the ExplosiveTruck hit a pedestrian.
     */
    public boolean checkHitPedestrian() {
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);
        if (p!=null) { // Someone was hit!
            explode(); // Time to blow up.
            return true;
        }
        return false;
    }
    /**
     * Explodes the ExplosiveTruck, removing all unlucky vehicles or pedestrians from the world caught in the blast.
     */
    private void explode() { 
        explosionSound.play(60);
        // By checking for SuperActors, I will not accidetnally remove the VehicleSpawner,
        // any Events, or Particles.
        ArrayList<SuperActor> actors = (ArrayList<SuperActor>)getObjectsInRange(explosionRadius, SuperActor.class);
        for (SuperActor a : actors){ 
            if (a instanceof Pedestrian) ((Pedestrian)a).killMe(); // If a pedestrian, kill it.
            else if (a instanceof Bomb) continue; // Don't remove any bombs. Makes it more fun!
            else getWorld().removeObject(a); // Remove the object normally
        }
        // Add an explosion effect. Strangely, getObjectsInRange gives a larger radius than expected, so I 
        // resize the explosion effect to match.
        getWorld().addObject(new ExplosionParticle(explosionRadius+50), getX(), getY());
        getWorld().removeObject(this);
    }
    public double getSpeed() {
        return speed;
    }
}

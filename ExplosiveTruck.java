import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class ExplosiveTruck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplosiveTruck extends Vehicle
{
    private int explosionRadius; // The radius of the explosion.
    public ExplosiveTruck(VehicleSpawner o) {
        super(o);
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        yOffset = 12;
        explosionRadius = 150;
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
     * Explodes the ExplosiveTruck, removing all unlucky vehicles or pedestrians from the world.
     */
    private void explode() { 
        // By checking for SuperActors, I will not accidetnally remove the VehicleSpawner,
        // any Events, or Particles.
        ArrayList<SuperActor> actors = (ArrayList<SuperActor>)getObjectsInRange(explosionRadius, SuperActor.class);
        for (SuperActor a : actors){ 
            getWorld().removeObject(a); // Remove all nearby SuperActors.
        }
        getWorld().addObject(new Explosion(explosionRadius+50), getX(), getY()); // Add an explosion effect.
        getWorld().removeObject(this);
    }
    public double getSpeed() {
        return speed;
    }
}

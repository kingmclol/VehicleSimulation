import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class ExplosiveTruck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplosiveTruck extends Vehicle
{
    private int explosionRadius;
    public ExplosiveTruck(VehicleSpawner o) {
        super(o);
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        yOffset = 12;
        explosionRadius = 200;
    }
    /**
     * Act - do whatever the ExplosiveTruck wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    public boolean checkHitPedestrian() {
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);
        if (p!=null) {
            explode();
            return true;
        }
        return false;
    }
    private void explode() { // CLEAN UO LATER
        List<Actor> actors = (List<Actor>)getObjectsInRange(explosionRadius, Actor.class);
        for (Actor a : actors) {
            if (!(a instanceof VehicleSpawner || a instanceof Particle)) getWorld().removeObject(a);
        }
        getWorld().addObject(new Explosion(explosionRadius), getX(), getY());
        getWorld().removeObject(this);
    }
    public double getSpeed() {
        return speed;
    }
}

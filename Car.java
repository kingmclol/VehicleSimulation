import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The Car subclass
 */
public class Car extends Vehicle
{
    
    private int xcfsdf;
    private SuperActor target;
    public Car(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 30)/5);
        speed = maxSpeed;
        yOffset = 4;
        int z;
        followingDistance = 6;
    }

    public void act()
    {
        super.act();
        // if(getWorld()!= null) shoot();
    }
    // private void shoot() {
        // if (target==null || target.getWorld()==null) {
            // ArrayList<Zombie> zombies = (ArrayList<Zombie>)getObjectsInRange(200, Zombie.class);
            // if (zombies.size() != 0) target = zombies.get(0);
        // }
        // else {
            // getWorld().addObject(new Bullet(getPosition().distanceFrom(target.getPosition())), getX(), getY());
            // System.out.println("aa");
        // }
    // }
    /**
     * When a Car hit's a Pedestrian, it should knock it over
     */
    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null)
        {
            p.knockDown();
            return true;
        }
        return false;
        
    }
}

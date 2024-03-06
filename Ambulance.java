import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ambulances are a Vehicle that is very good at medicine, as it can heal any downed
 * Pedestrians that it touches, even curing any Zombies on contact!
 * Unfortunately, any cured Zombies wouldn't be happy to find themselves in the middle of a
 * bad situation...
 * 
 * @author Jordan Cohen
 * @author Freeman Wang
 * 
 * @version 2024-02-27
 */
public class Ambulance extends Vehicle
{
    public Ambulance(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        maxSpeed = 2.5 + Math.random()*1.5;
        speed = maxSpeed;
        yOffset = 10;
    }

    /**
     * Act - do whatever the Ambulance wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        
    }
    /**
     * Heals any downed Pedestrians that it touches. Humans wake up, while Zombies turn
     * into civilians.
     */
    public boolean checkHitPedestrian () {
        //Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);
        if (p!=null && !p.isAwake()) {
            p.healMe();
            return true;
        }
        return false;
    }
}

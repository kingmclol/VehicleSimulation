import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ambulances are a Vehicle that is very good at medicine, as it can heal any downed
 * Pedestrians that it touches, even curing Zombies on contact! Wow!
 * Unfortunately, cured Zombies wouldn't be happy to find themselves in the middle of a
 * bad situation... Nonetheless, they would serve as a good Zombie distraction for Soldiers to take
 * advantage of!
 * 
 * @author Jordan Cohen
 * @author Freeman Wang
 * 
 * @version 2024-02-27
 */
public class Ambulance extends Vehicle
{
    private HiddenBox topBox;
    private HiddenBox bottomBox;
    private HiddenBox frontBox;
    private final boolean SHOW_HEAL_BOXES = false;
    public Ambulance(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        maxSpeed = 2.5 + Math.random()*2;
        speed = maxSpeed;
        yOffset = 10;
        
        int height = getImage().getHeight();
        int width = getImage().getWidth();
        topBox = new HiddenBox(width, 5, SHOW_HEAL_BOXES, this, 0, -height/2-3);
        bottomBox = new HiddenBox(width, 5, SHOW_HEAL_BOXES, this, 0, height/2+3);
        frontBox = new HiddenBox(5, height, SHOW_HEAL_BOXES, this, width/2+(int)Math.round(speed), 0);
    }
    public void addedToWorld(World w) {
        if (!isNew) return;
        super.addedToWorld(w);
        w.addObject(topBox, getX(), getY());
        w.addObject(bottomBox, getX(), getY());
        w.addObject(frontBox, getX(), getY());
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
        Pedestrian p = (Pedestrian)frontBox.getOneIntersectingObject(Pedestrian.class);
        if (p == null) p = (Pedestrian)bottomBox.getOneIntersectingObject(Pedestrian.class);
        if (p == null) p = (Pedestrian)topBox.getOneIntersectingObject(Pedestrian.class);
        
        if (p!=null && !p.isAwake()) {
            p.healMe();
            return true;
        }
        return false;
    }
}

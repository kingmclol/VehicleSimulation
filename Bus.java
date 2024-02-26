import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Bus subclass
 */
public class Bus extends Vehicle
{
    private int passengers;
    private final int maxPassengers;
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        passengers = 0;
        maxPassengers = 7;
        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the Bus graphic is tall, offset it a up (this may result in some collision check issues)
        yOffset = 15;
    }

    /**
     * Act - do whatever the Bus wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
       super.act();
    }

    public boolean checkHitPedestrian () {
        Civilian c = (Civilian)getOneIntersectingObject(Civilian.class);
        // if (p!=null && p.isAwake() && passengers < maxPassengers) {
            // addPassenger();
            // getWorld().removeObject(p);
            // moving = false;
            // sleepFor(60);
            // return true;
            // // moving = false;
            // // p.getOnBus(this);
            // // return true;
        // }
        if (c!= null && c.isAwake() && passengers < maxPassengers) {
            addPassenger();
            getWorld().removeObject(c);
            moving = false;
            sleepFor(60);
            createEvent(new DelayedEvent(() -> moving=true, 60));
            return true;
        }
        return false;
    }
    private void addPassenger() {
        int offset = 17;
        GreenfootImage image = getImage();
        image.setColor(Color.BLACK);
        image.fillOval(image.getWidth()-48-(offset*passengers),image.getHeight()/2-8,12,12);
        setImage(image);
        passengers++;
    }
}

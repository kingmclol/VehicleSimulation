import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Bus is trying to get out of the city, but wouldn't mind picking up any Civilians it comes by.
 * Thankfully, it has quite a large max capacity for those panicked Civilians out there.
 * 
 * @author Jordan Cohen
 * @author Freeman Wang
 * @date 2024-02-27
 */
public class Bus extends Vehicle
{
    private int passengers;
    private final int maxPassengers = 7;
    private DelayedEvent pickedUpPassenger;
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        passengers = 0;
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
    /**
     * Checks if it has hit a civilian. Returns true if it did.
     * If the civilian can board the bus, then add it to the Bus as a passenger.
     * @return true if the Bus hit a civilian.
     */
    public boolean checkHitPedestrian () {
        Civilian c = (Civilian)getOneIntersectingObject(Civilian.class); // Check if a civilian is in contact w/ the bus
        // If the civilian exists, is awake, and the Bus still can take on more passengers,
        if (c!= null && c.isAwake() && passengers < maxPassengers) {
            addPassenger(c); // pick up the passenger.
            moving = false; // Stop the bus.
            // sleepFor(60); // Prevent this actor from acting for one second (only one Civilian can get on at a time).
            createEvent(new DelayedEvent(() -> moving = true, 60)); // set moving to true after 1 second.
            return true;
        }
        return false;
    }
    /**
     * Removes the civilian from the world, and draws it on the Bus' image.
     * @param c The civilian to pick up.
     */
    private void addPassenger(Civilian c) {
        //int offset = 17;
        c.getWorld().removeObject(c); // Remove the civilian (it is on the bus now)     
        GreenfootImage image = getImage();
        image.setColor(Color.DARK_GRAY);
        image.fillOval(image.getWidth()-48-(17*passengers),image.getHeight()/2-8,12,12);
        setImage(image);
        passengers++;
    }
}

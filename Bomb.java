import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * A Bomb is a bomb. It can EXPLODE! Destroys all Pedestrians and Vehicles around.
 * 
 * @author Freeman Wang
 * @version 2024-03-03
 */
public class Bomb extends SuperActor
{
    private int targetY; // The target Y position to go to (i.e. the lane)
    private double speed;
    private boolean aboutToExplode; // Whether I should explode soon.
    private int explosionRadius; // Explosion radius. Why am I commenting things that are self-explanatory?
    private GifImage bombImage = new GifImage("Bomb_v2.gif");
    private SuperSound explosionSound;
    public Bomb(int yPos){
        targetY = yPos;
        speed = 4.0;
        aboutToExplode = false;
        explosionRadius = 125;
        
        // Randomly choose between Explosion1, Explosion2, Explosion3
        String explosionName = "Explosion" + (Greenfoot.getRandomNumber(3) + 1) + ".mp3";
        explosionSound = new SuperSound(explosionName);
    }
    public void act()
    {
        if (!aboutToExplode) { // If still falling,
            moveTowards(new Vector(getX(), targetY), speed); // Converts the targetY to a position (vector) which I move to.
        }
        
        if (!aboutToExplode && getY() == targetY) {
            createEvent(new DelayedEvent(() -> explode(), 60)); // explode after 60 acts
            aboutToExplode = true; // currently in exploding phase
        }
        setImage(bombImage.getCurrentImage()); // set image based on current image of the gif
    }
    private void explode(){
        if (getWorld()!=null) { // If I am still in the World... Not an necessary check anymore but eh
            explosionSound.play(40);
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
    }
}

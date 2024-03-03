import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The DarkFilter is basically an image that is semi-transparent that should overlay itself over the entire World
 * for a "darkening" effect. Not the most effective.
 * 
 * @author Freeman Wang
 * @version 2024-03-01
 */
public class DarkFilter extends Actor
{
    /**
     * Act - do whatever the DarkFilter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int targetTransparency = 100;
    private final int step = 2;
    private boolean shouldExist; // Whether the DarkFilter should fade out or fade in.
    public DarkFilter(World w){
        shouldExist = false;
        GreenfootImage img = new GreenfootImage(w.getWidth(), w.getHeight());
        img.setColor(Color.BLACK);
        img.fill();
        img.setTransparency(0);
        setImage(img);
    }
    public void act(){
        int currentTransparency = getImage().getTransparency();
        if (shouldExist) { // I shouldn't remove myself!
            if (currentTransparency < targetTransparency) { // Make myself appear until I reach my target transparency
                getImage().setTransparency(Math.min(currentTransparency + step, targetTransparency));
            }
        }
        else { // I should remove myself :(
            if (currentTransparency > 0) { // Decrease transparency until invisible
                getImage().setTransparency(Math.max(currentTransparency - step, 0));
            }
            else { // I am invisible. Time to remove myself from the World so I don't mess with inspecting Actors.
                getWorld().removeObject(this);
            }
        }
    }
    /**
     * The DarkFilter should prepare to add itself.
     */
    public void timeToExist(){
        shouldExist = true;
    }
    /**
     * Tells the DarkFilter that it should REMOVE ITSELF... LATER!
     */
    public void timeToRemove(){
        shouldExist = false;
    }
}

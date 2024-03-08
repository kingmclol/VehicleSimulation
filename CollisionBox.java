import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The CollisionBox is literally a box that exists for collision detection.
 * 
 * @author Freeman Wang
 * @version 2024-03-06
 */
public class CollisionBox extends Actor
{
    private int height, width, yOffset;
    private Actor owner;
    /**
     * Creates a basic CollisionBox, which is basically a box. Should be used for temporary collision checking,
     * so having it visible or invisible does not matter.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     */
    public CollisionBox (int width, int height) {
        this(width, height, false, null, 0);
    }
    /**
     * Creates a CollisionBox that is able to exist for longer periods of time, tied to an owner.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     * @param visible Whether the CollisionBox should be visible.
     * @param owner The Actor that the CollisionBox should follow.
     */
    public CollisionBox(int width, int height, boolean visible, Actor owner){
        this(width, height, visible, owner, 0);
    }
    /**
     * Creates a CollisionBox that is exists for a longer period of time, tied to an owner, but with an yOffset.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     * @param visible Whether the CollisionBox should be visible.
     * @param owner The Actor that the CollisionBox should follow.
     * @param yOffset The y offset in pixels, for the CollisionBox's following.
     */
    public CollisionBox(int width, int height, boolean visible, Actor owner, int yOffset) {
        GreenfootImage img = new GreenfootImage(width, height);
        if (visible) {
            img.setColor(Color.RED);
            img.fill();
        }
        setImage(img);
        this.owner = owner;
        this.yOffset = yOffset;
    }
    public void act() {
        if (owner.getWorld() == null) { // Remove any orphaned CollisionBoxes. They should not exist long enough to act.
            getWorld().removeObject(this);
            return;
        }
        setLocation(owner.getX(), owner.getY()+yOffset); // Match with the owner, factoring in the yOffset.
    }
    /**
     * Returns whether this CollisionBox is intersecting an instance of the 
     * given class.
     * @param c The class to look for.
     * @return Whether the CollisionBox is touching an object of the class or not.
     */
    public boolean isIntersecting(Class c) {
        return isTouching(c);
    }
    /**
     * Makes the CollisionBox visible.
     */
    public void makeVisible() {
        getImage().setColor(Color.RED);
        getImage().fill();
    }
    /**
     * Makes the CollisionBox (probably) invisible.
     */
    public void makeInvisible() {
        getImage().clear();
    }
    /**
     * Returns an intersecting object of the given Class.
     * @param c The Class to look for.
     * @return The Actor being intersected. Returns null if none found.
     */
    public Actor getOneIntersectingObject(Class c) {
        return super.getOneIntersectingObject(c); // Haha, no recursion this time!
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The Box is literally a box that exists for collision detection. It can be visibile or invisible
 * depending on what you want. It also can follow an "owner" actor to act as a hitbox, or something
 * like an indicator for an attack's range.
 * 
 * <p>But in the end, it's just a box. I swear.</p>
 * 
 * @author Freeman Wang
 * @version 2024-03-08
 */
public abstract class Box extends Actor
{
    private int height, width, xOffset, yOffset;
    private Actor owner;
    protected Color color;
    private boolean visible;
    private boolean initialAct; // Combat z sorting always running addedToWorld();
    // /**
     // * Creates a basic Box, which is basically a box. Should be used for temporary collision checking,
     // * so having it visible or invisible does not matter.
     // * @param width The width of the CollisionBox.
     // * @param height The height of the CollisionBox.
     // */
    // public Box (int width, int height) {
        // this(width, height, false, null, 0, 0);
    // }
    // /**
     // * Creates a CollisionBox that is tied to an owner.
     // * @param width The width of the CollisionBox.
     // * @param height The height of the CollisionBox.
     // * @param visible Whether the CollisionBox should be visible.
     // * @param owner The Actor that the CollisionBox should follow.
     // */
    // public Box(int width, int height, boolean visible, Actor owner){
        // this(width, height, visible, owner, 0, 0);
    // }
    /**
     * Creates a CollisionBox that is tied to an owner, but with an offset.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     * @param visible Whether the CollisionBox should be visible.
     * @param owner The Actor that the CollisionBox should follow.
     * @param xOffset The x offset in pixels, for the CollisionBox's following.
     * @param yOffset The y offset in pixels, for the CollisionBox's following.
     */
    public Box(int width, int height, boolean visible, Actor owner, int xOffset, int yOffset) {
        setImage(new GreenfootImage(width, height));
        this.visible = visible;
        this.owner = owner;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        initialAct = true;
    }
    public void addedToWorld(World w){
        if (!initialAct) return;
        
        // Make the Box visible, if needed.
        if (visible) {
            getImage().setColor(color);
            getImage().fill();
        }
        
        initialAct = false;
    }
    public void act() {
        // Remove any orphaned Boxes. They should not exist to act without an owner. If used for instant
        // collision checks, they should've been removed anyways.
        if (owner.getWorld() == null) { 
            getWorld().removeObject(this);
            return;
        }
        setLocation(owner.getX()+xOffset, owner.getY()+yOffset); // Match with the owner, factoring in the yOffset.
    }
    /**
     * Returns whether this Box is intersecting an instance of the 
     * given Class.
     * @param c The Class to look for.
     * @return Whether the Box is touching an object of the Class or not.
     */
    public boolean isIntersecting(Class c) {
        return isTouching(c); // Check for intersetion normally.
    }
    /**
     * Makes the Box visible.
     */
    public void makeVisible() {
        getImage().setColor(color);
        getImage().fill();
        visible = true;
    }
    /**
     * Makes the Box (probably) invisible.
     */
    public void makeInvisible() {
        getImage().clear();
        visible = false;
    }
    /**
     * Returns an intersecting object of the given Class.
     * @param c The Class to look for.
     * @return The Actor being intersected. Returns null if none found.
     */
    public Actor getOneIntersectingObject(Class c) {
        return super.getOneIntersectingObject(c); // Haha, no recursion this time!
    }
    /**
     * Returns the owner of the Box. Returns null if the Box does not have one,
     * even though at that point the Box shouldn't be existing in the first place.
     */
    public Actor getOwner() {
        return owner;
    }
    /**
     * Returns whether the Box is visible.
     */
    public boolean getVisibility() {
        return visible;
    }
}

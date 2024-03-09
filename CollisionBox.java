import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * CollisionBoxes are Boxes meant for collision detection. They also can be seen as the "hitbox" for an
 * actor. They are not meant to be used as HiddenBoxes. An example of a proper application of a 
 * CollisionBox is to model the space a Vehicle takes up in a specific area.
 * 
 * <p>When visible, CollisionBoxes will be of the color <span style="color:red">red</span>.</p>
 * 
 * @author Freeman Wang
 * @version 2024-03-08
 */
public class CollisionBox extends Box
{
    /**
     * Creates a basic CollisionBox, which is basically a box. Should be used for temporary collision checking,
     * so having it visible or invisible does not matter.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     */
    public CollisionBox (int width, int height) {
        this(width, height, false, null, 0, 0);
    }
    /**
     * Creates a CollisionBox that is tied to an owner.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     * @param visible Whether the CollisionBox should be visible.
     * @param owner The Actor that the CollisionBox should follow.
     */
    public CollisionBox(int width, int height, boolean visible, Actor owner){
        this(width, height, visible, owner, 0, 0);
    }
    /**
     * Creates a CollisionBox that is tied to an owner, but with an offset.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     * @param visible Whether the CollisionBox should be visible.
     * @param owner The Actor that the CollisionBox should follow.
     * @param xOffset The x offset in pixels, for the CollisionBox's following.
     * @param yOffset The y offset in pixels, for the CollisionBox's following.
     */
    public CollisionBox(int width, int height, boolean visible, Actor owner, int xOffset, int yOffset) {
        super(width, height, visible, owner, xOffset, yOffset);
        color = Color.RED;
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * HiddenBoxes are a type of Box that should be used for detection, but are not to be used as
 * CollisionBoxes (e.g. <i>entity</i> hitboxes in a game). An applicable usage is to use a HiddenBox
 * as hit boxes for an attack, or to check if there is an Actor at a specific point before doing something.
 * When visible, HiddenBoxes will be of color <span style="color:blue">blue</span>.
 * 
 * @author Freeman Wang 
 * @version 2024-03-08
 */
public class HiddenBox extends Box
{
    /**
     * Creates a basic HiddenBox, which is basically a box. Should be used for temporary collision checking,
     * so having it visible or invisible does not matter.
     * @param width The width of the HiddenBox.
     * @param height The height of the HiddenBox.
     */
    public HiddenBox (int width, int height) {
        this(width, height, false, null, 0, 0);
    }
    /**
     * Creates a HiddenBox that is tied to an owner.
     * @param width The width of the HiddenBox.
     * @param height The height of the HiddenBox.
     * @param visible Whether the HiddenBox should be visible.
     * @param owner The Actor that the HiddenBox should follow.
     */
    public HiddenBox(int width, int height, boolean visible, Actor owner){
        this(width, height, visible, owner, 0, 0);
    }
    /**
     * Creates a HiddenBox that is tied to an owner, but with an offset.
     * @param width The width of the HiddenBox.
     * @param height The height of the HiddenBox.
     * @param visible Whether the HiddenBox should be visible.
     * @param owner The Actor that the HiddenBox should follow.
     * @param xOffset The x offset in pixels, for the HiddenBox's following.
     * @param yOffset The y offset in pixels, for the HiddenBox's following.
     */
    public HiddenBox(int width, int height, boolean visible, Actor owner, int xOffset, int yOffset) {
        super(width, height, visible, owner, xOffset, yOffset);
        color = Color.BLUE;
    }
}

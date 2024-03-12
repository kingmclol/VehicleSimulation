import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>AnnounceText is text that appears on the screen but quickly fades out. Does it count
 * as a Particle? I say it does, and I'm doing the coding. Haha!</p>
 * 
 * <p>Jokes aside, it does make sense, as you can do things like damage numbers with this.<p>
 * 
 * @author Freeman Wang
 * @version 2024-03-09
 */
public class AnnounceText extends Particle
{
    /**
     * Act - do whatever the AnnounceText wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String text;
    private SuperSound announceSound;
    public AnnounceText(String text) {
        this(text, 54, Color.WHITE, null, Color.DARK_GRAY, 2);
    }
    /**
     * The holy grail of AnnounceText constructors. Has everything configurable. Except font.
     * @param text The text to display.
     * @param size The font size of the text.
     * @param textColor The color of the text.
     * @param backgroundColor The color of the background. Use null for transparency.
     * @param outline The color of the text outline.
     * @param step How much transparency to lose per act.
     */
    public AnnounceText(String text, int size, Color textColor, Color backgroundColor, Color outline, int step) {
        super(new GreenfootImage(1,1), step);
        
        GreenfootImage img = new GreenfootImage(text, size, textColor, backgroundColor, outline);
        useNewImage(img);
        announceSound = new SuperSound("UI Announcement.mp3", 1, 50);
        announceSound.play();
    }
    public void act() {
        super.act();
    }
}

import greenfoot.*;
import java.util.HashMap;
/**
 * The SoundManager manages playing various sounds.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoundManager  
{
    private HashMap<String, String> sounds;
    /**
     * Constructor for objects of class SoundManager
     */
    public SoundManager(HashMap<String, String> soundFilenamePairs)
    {
        sounds = soundFilenamePairs;
    }
    public void playSound(String soundNameKey) {
        playSound(soundNameKey, 100);
    }
    public void playSound(String soundNameKey, int volume) {
        String fileName = sounds.get(soundNameKey);
        if (fileName == null) {
            System.out.println("Requested: " + soundNameKey + " whose corresponding file did not exist.");
            return;
        }
        if (volume < 0 || volume > 100) {
            System.out.println("Requested volume of " + volume + " is not within range [0, 100]");
        }
        GreenfootSound sound = new GreenfootSound(fileName);
        sound.setVolume(volume);
        sound.play();
    }
}

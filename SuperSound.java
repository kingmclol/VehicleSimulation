import greenfoot.*;
import java.util.HashMap;
/**
 * The SuperSound is basically another way to use GrenfootSounds, as it includes its own array
 * of GreenfootSounds. Doing so would remove the issue where sounds can only play themselves once at a time
 * as would be for a normal GreenfootSound.
 * 
 * @author Freeman Wang
 * @version 2024-03-11
 */
public class SuperSound  
{
    private GreenfootSound[] sounds;
    private int index;
    /**
     * Creates a single SuperSound.
     */
    public SuperSound(String filename) {
        this(filename, 1, 100);
    }
    /**
     * Creates a SuperSound with default volume.
     * @param filename The filename for your sound file.
     * @param n The number of instances to create.
     */
    public SuperSound(String filename, int n) {
        this(filename, n, 100);
    }
    /**
     * Creates a SuperSound with defined volume.
     * @param filename The filename of your sound file.
     * @param n The number of instances to create.
     * @param volume The volume to set the sounds to.
     */
    public SuperSound(String filename, int n, int volume) {
        sounds = new GreenfootSound[n];
        for (int i = 0; i < sounds.length; i++) {
            GreenfootSound sound = new GreenfootSound(filename);
            sound.setVolume(volume);
            sounds[i] = sound;
        }
        index = 0;
    }
    /**
     * Plays the SuperSound.
     */
    public void play() {
        sounds[index].play();
        getNextIndex();
    }
    /**
     * Temporarily overrides the original sound's volume to play it at the given volume.
     * @param volume The volume to play the SuperSound at.
     */
    public void play(int volume) {
        GreenfootSound sound = sounds[index];
        sound.setVolume(volume);
        sound.play();
        getNextIndex();
    }
    /**
     * Stops all instances of the GreenfootSound.
     */
    public void stop() {
        for (GreenfootSound sound : sounds) sound.stop();
    }
    /**
     * Plays the sound in a loop. Will only play the 0th sound.
     * Only meant to be used for SuperSounds with only one instance, otherwise you're doing 
     * something weird. Don't do that.
     */
    public void playLoop() {
        sounds[0].play();
    }
    /**
     * Get the next index for the next GreenfootSound to play.
     */
    private void getNextIndex() {
        index++;
        if (index >= sounds.length) { // Increment and compare with length of sounds array
            index = 0; // Would be out of bounds, reset to 0
        }
    }
    public int getVolume() {
        return sounds[0].getVolume();
    }
    public boolean isPlaying() {
        for (GreenfootSound sound: sounds) {
            if (sound.isPlaying()) return true;
        }
        return false;
    }
}

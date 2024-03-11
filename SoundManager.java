import greenfoot.*;
import java.util.HashMap;
/**
 * The SoundManager manages playing various sounds. Meant for use with multiple sounds, but deprecated for
 * using SuperSound instead (which only manages one sound at a time.)
 * 
 * @author Freeman Wang
 * @version 2024-03-11
 */
public class SoundManager  
{
    private HashMap<String, GreenfootSound[]> soundMap;
    private HashMap<String, Integer> iteratorMap;
    /**
     * Constructor for objects of class SoundManager
     */
    public SoundManager() {
        soundMap = new HashMap();
        iteratorMap = new HashMap();
    }
    public void init(String filename, int n) {
        GreenfootSound[] soundObjects = new GreenfootSound[n];
        for(int i = 0; i < n; i++) {
            soundObjects[i] = new GreenfootSound(filename);
        }
        soundMap.put(filename, soundObjects);
        iteratorMap.put(filename, 0);
    }
    public void play(String filename) {
        GreenfootSound[] sounds = soundMap.get(filename);
        int i = iteratorMap.get(filename);
        
        sounds[i].play();
        
        if(++i > sounds.length) {
            i = 0;
        }
        iteratorMap.put(filename, i);
    }
    public void stop(String filename){
        GreenfootSound[] sounds = soundMap.get(filename);
        for (GreenfootSound sound : sounds) {
            sound.stop();
        }
    }
}

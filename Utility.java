import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Utility here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Utility  
{
    public static int round(double val) {
        return (int) (val + 0.5);
    }
    public static int getSign(double val) {
        return (val < 0) ? -1 : 1;
    }
    // Moved to SuperActor
    // public static double getDistance(Vector a, Vector b) {
        // return a.distanceFrom(b).getMagnitude();
    // }
    // public static double getDistance(SuperActor a, SuperActor b) {
        // return a.getPosition().distanceFrom(b.getPosition()).getMagnitude();
    // }
}

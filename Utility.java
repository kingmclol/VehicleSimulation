/**
 * Random stuff.
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
    public static double roundToPrecision(double val, int decimalPoints) {
        return Math.round(val * Math.pow(10, decimalPoints)) / Math.pow(10, decimalPoints);
    }
}

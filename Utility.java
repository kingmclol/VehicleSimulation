/**
 * Random stuff, added as needed.
 * 
 * @author Freeman Wang
 * @version 2024-03-01
 */
public class Utility  
{
    public static int round(double val) {
        return (int) (val + 0.5);
    }
    public static double roundToPrecision(double val, int decimalPoints) {
        return Math.round(val * Math.pow(10, decimalPoints)) / Math.pow(10, decimalPoints);
    }
}

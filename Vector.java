/**
 * Write a description of class Vector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vector  
{
    // instance variables - replace the example below with your own
    private double x, y;

    /**
     * Constructor for objects of class Vector
     */
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    /** 
     * Vector subtraction, give a vector pointing TO the target
     */
    public Vector distanceFrom(Vector target) {
        return new Vector(target.getX()-x, target.getY()-y);
    }
    public Vector add(Vector other) {
        return new Vector(x+other.getX(), y+other.getY());
    }
    public Vector subtract(Vector other) {
        return new Vector(x-other.getX(), y-other.getY());
    }
    public String toString() {
        //return String.format("{%f, %f}", x, y); 
        return "{" + x + ", " + y + "}";
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    /**
     * Pythagorean
     */
    public double getMagnitude() {
        return Math.hypot(x, y);
    }
    /**
     * Converts a vector into one with a magnitude of 1
     */
    public Vector normalize() {
        double mag = getMagnitude();
        if (mag == 0) {
            //System.out.println("Cannot normalize Vector with magnitude 0.");
            return this;
        }
        return new Vector(x/mag, y/mag);
    }
    /**
     * Scales a vector based on the given scale
     */
    public Vector scale(double scalar) {
        return new Vector(x*scalar, y*scalar);
    }
    public Vector scaleTo(double magnitude){
        return this.normalize().scale(magnitude);
    }
    public Vector limitMagnitude(double magnitude) {
        if (this.getMagnitude() > magnitude) {
            return this.scaleTo(magnitude);
        }
        return this;
    }
    public Vector reverse(){
        return this.scale(-1);
    }
}

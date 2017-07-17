/*
 * PROJECT I: Circle.java
 *
 * This file contains a template for the class Circle. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Point class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class Circle {

    /*
     * Here, you should define private variables that represent the radius and
     * centre of this particular Circle. The radius should be of type double,
     * and the centre should be of type Point.
     */
    private double r;
    private Point A;
    // =========================
    // Constructors
    // =========================
    /**
     * Default constructor - performs no initialization.
     */
    public Circle() {
        // This method is complete.
    }
    
    /**
     * Alternative constructor, which sets the circle up with x and y
     * co-ordinates representing the centre, and a radius. Remember you should
     * not store these x and y co-ordinates explicitly, but instead create a
     * Point to hold them for you.
     *
     * @param xc   x-coordinate of the centre of the circle
     * @param yc   y-coordinate of the centre of the circle
     * @param rad  radius of the circle
     */
    public Circle(double xc, double yc, double rad) {
        setCenter(xc,yc);
        setRadius(rad);
    }

    /**
     * Alternative constructor, which sets the circle up with a Point
     * representing the centre, and a radius.
     *
     * @param center  Point representing the centre
     * @param rad     Radius of the circle
     */
    
    public Circle(Point center, double rad) {
        setCenter(center);
        setRadius(rad);
    }

    // =========================
    // Setters and Getters
    // =========================

    /**
     * Setter - sets the co-ordinates of the centre.
     *
     * @param xc  new x-coordinate of the centre
     * @param yc  new y-coordinate of the centre
     */   
    public void setCenter(double xc, double yc) {
        Point C= new Point(xc,yc);
        this.A=C;
        

    }

    /**
     * Setter - sets the centre of the circle to a new Point.
     *
     * @param C  A Point representing the new centre of the circle.
     */   
    public void setCenter(Point C) {
        this.A=C;
    }
    
    /**
     * Setter - change the radius of this circle.
     *
     * @param rad  New radius for the circle.
     */   
    public void setRadius(double rad) {
        this.r=rad;
    }
    
    /**
     * Getter - returns the centre of this circle.
     *
     * @return The centre of this circle (a Point).
     */   
    public Point getCenter(){
        return A;
    }

    /**
     * Getter - extract the radius of this circle.
     *
     * @return The radius of this circle.
     */   
    public double getRadius(){
        return r;
    }

    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Circle.
     *
     * @return A String of the form: "[Ax,Ay], r=Radius" where Ax and Ay are
     *         numerical values of the coordinates, and Radius is a numerical
     *         value of the radius.
     */
    public String toString() {
        return "[" + A.getX() + "," + A.getY() + "], r=" +r;
    }
    
    // ==========================
    // Service routines
    // ==========================
    
    /**
     * Similar to the equals() function in Point. Returns true if two Circles
     * are equal. By this we mean:
     * 
     * - They have the same radius (up to tolerance).
     * - They have the same centre (up to tolerance).
     * 
     * Remember that the second test is already done in the Point class!
     *
     * @return true if the two circles are equal.
     */
    public boolean equals(Circle c) {
        return (A.equals(c.A) &&
                Math.abs(r - c.r) <= Point.GEOMTOL);
    }
    
    // -----------------------------------------------------------------------
    // Do not change the method below! It is essential for marking the
    // project, and you may lose marks if it is changed.
    // -----------------------------------------------------------------------

    /**
     * Compare this Circle with some Object, using the test above.
     *
     * @param obj  The object to compare with.
     * @return true if the two objects are equal.
     */
    public boolean equals(Object obj) {
        // This method is complete.
        
        if (obj instanceof Circle) {
            boolean test = false;
            Circle C = (Circle)obj;
            
            test = this.equals(C);

            return test;
        } else {
            return false;
        }
    }

    // ======================================
    // Implementors
    // ======================================
    
    /**
     * Computes and returns the area of the circle.
     *
     * @return  Area of the circle
     */
    public double area() {
        return Math.PI*r*r;
    }

    /**
     * Tests whether this circle overlaps/touches/is disjoint with another
     * Circle, as set out in the project formulation.
     *
     * @return An integer describing the overlap with C:
     *         0 - disjoint; 1 - overlaps; 2 - touches; 3 - identical.
     */
    public int overlap(Circle C) {
        if (equals(C)) {return 3;}
        else if (A.Distance(C.A) >= r+C.r+Point.GEOMTOL) {return 0;}
        else if (A.Distance(C.A) <= r+C.r-Point.GEOMTOL) {return 1;}
        else if (r+C.r-Point.GEOMTOL < A.Distance(C.A) &&
                A.Distance(C.A)< r+C.r + Point.GEOMTOL) {return 2;}
        else {return -1;}
    }   


    // =======================================================
    // Tester - test methods defined in this class
    // =======================================================
    
    public static void main(String args[]) {
        //Test constructors.
        Circle B = new Circle(0.0,0.0,5.0);
        Point Z = new Point(0.0,0.0);
        Circle C = new Circle(Z,1.0);
        Circle D = new Circle(0.0,0.0,2.0);
        Point Y = new Point(2.0,0.0);
        Circle E = new Circle(Y,1.0);
        Circle F = new Circle(4.0,0.0,1.0);  
        Circle G = new Circle();
        //Test convertors.
        System.out.println("Constructor test:");
        System.out.println("Circle B = "+B.toString());
        System.out.println("Circle C = "+C.toString());
        //Test Setters.
        System.out.println("\nSetting centre of B to 3.5,2.5");
        B.setCenter(3.5,2.5); 
        System.out.println("Circle B = "+B.toString());
        System.out.println("\nSetting radius of B to 1.0");
        B.setRadius(1.0);
        System.out.println("Circle B = "+B.toString());
        System.out.println("\nSetting centre of B to 0.0 (point method)");
        B.setCenter(Z);
        System.out.println("Circle B = "+B.toString());
        //Test Getters.
        System.out.println("\nGetting centre of B");
        System.out.println("Centre of B = "+B.getCenter().toString());
        System.out.println("\nGetting radius of B");
        System.out.println("radius of B = "+B.getRadius());
        System.out.println("\nGetting x-coordinate of B");
        System.out.println("x-coordinate of B = "+B.A.getX());
        //Test equals.
        System.out.println("\nTesting circle equality function");
        System.out.println("B and B?"+B.equals(B));
        System.out.println("C and B?"+C.equals(B));
        System.out.println("B and D?"+B.equals(D));
        //Test area.
        System.out.println("\nTesting area");
        System.out.println("Area of B = "+B.area());
        System.out.println("Area of C = "+C.area());
        //Test overlap.
        System.out.println("\nTesting overlap function");
        System.out.println("B and C?"+B.overlap(C));
        System.out.println("C and B?"+C.overlap(B));
        System.out.println("B and D?"+B.overlap(D));
        System.out.println("B and E?"+B.overlap(E));
        System.out.println("B and F?"+B.overlap(F));
    }
}
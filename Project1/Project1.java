import java.util.Arrays;

/*
 * PROJECT I: Project1.java
 *
 * As in project 0, this file - and the others you downloaded - form a
 * template which should be modified to be fully functional.
 *
 * This file is the *last* file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation. It contains
 *    the descriptions of all of the functions and variables below.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The Results() method will perform the tasks
 *    laid out in the project formulation.
 */
public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names of the variables below! This is where you will
    // store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int    circleCounter; // Number of non-singular circles in the file.
    public int    posFirstLast;  // Indicates whether the first and last
                                 // circles overlap or not.
    public double maxArea;       // Area of the largest circle (by area).
    public double minArea;       // Area of the smallest circle (by area).
    public double averageArea;   // Average area of the circles.
    public double stdArea;       // Standard deviation of area of the circles.
    public double medArea;       // Median of the area.
    public int    stamp=189375;
    // -----------------------------------------------------------------------
    // You may implement - but *not* change the names or parameters of - the
    // functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
    }

    /**
     * Results function. It should open the file called FileName (using
     * MaInput), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */
    public void results(String fileName){
        // Defines an instance F1 of the input class MaInput which connects
        // the input stream to the file Project1.data (if you want to test it
        // with your own file, just change the name Project1.data into the
        // name of your file). The format of this file is simple: each line
        // contains four numbers: x y radius which represent (x,y) -
        // co-ordinates of the center, and rad is the radius.
        MaInput F1 = new MaInput(fileName);

        // Variables into which coordinates of points will be stored.
        double x, y, rad;
        
        // Double.MIN_VALUE is the smallest number a double can hold, and
        // Double.MAX_VALUE is the largest.
        double dmin = Double.MIN_VALUE;
        double dmax = Double.MAX_VALUE;
        
        // Counter for the number of entered points.
        //Initialise variables
        circleCounter = 0;
        posFirstLast = -1;
        double maxrad = dmin;
        double minrad = dmax;
        double sumarea=0;
        double sumsquare=0;
        Circle first = new Circle();
        Circle last = new Circle();
        
        // Read the file until the EOF (end-of-file) mark in the file is reached.
        while (!F1.atEOF()) {
            // Read the coordinates from one line in the file. We know that
            // there are 3 numbers on a line so the following commands read
            // all data on a line.
            x   = F1.readDouble();
            y   = F1.readDouble();
            rad = F1.readDouble();

            // Count the points entered.
            if (rad >= Point.GEOMTOL) 
            {circleCounter++;
            if (circleCounter == 1) 
            {
            first = new Circle(x,y,rad);
            }
            last = new Circle(x,y,rad);
            sumarea = sumarea + Math.PI*rad*rad;
            sumsquare = sumsquare + Math.pow(Math.PI*rad*rad, 2);
            if (rad > maxrad) {maxrad = rad;}
            if (rad < minrad) {minrad = rad;}
            }
        }
        averageArea=sumarea/((double)circleCounter);
        maxArea= Math.PI*maxrad*maxrad;
        minArea= Math.PI*minrad*minrad;
        stdArea= Math.sqrt((sumsquare/(double)circleCounter - Math.pow(averageArea,2)));
        posFirstLast = first.overlap(last);
        //Create array containing areas and sort to find median.
        double[] areas = new double[circleCounter];
        F1 = new MaInput(fileName);
        int i;
        for(i=0;i<circleCounter;i++) {
            x   = F1.readDouble();
            y   = F1.readDouble();
            rad = F1.readDouble(); 
            if (rad >= Point.GEOMTOL) {areas[i]=Math.PI*rad*rad;}
        }
        Arrays.sort(areas);
        if (circleCounter%2 == 0)
        {medArea = 0.5*(areas[(circleCounter/2)-1]+areas[(circleCounter/2)]);}
        else if (circleCounter%2 == 1)
        {medArea = areas[(circleCounter-1)/2];}
    }
  
    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        Project1 example = new Project1();
        example.results("Project1.data");
        System.out.println("Information about the data:");
        System.out.println("  \nNumber of points entered: " + example.circleCounter);
        System.out.println("  \nDo first and last circles overlap?: " + example.posFirstLast);
        System.out.println("  \nMaximum circle area: " + example.maxArea);
        System.out.println("  \nMinimum circle area: " + example.minArea);
        System.out.println("  \nAverage circle area: " + example.averageArea);
        System.out.println("  \nCircle area standard deviation: " + example.stdArea);
        System.out.println("  \nMeadian circle area: " + example.medArea);
        

    }
}
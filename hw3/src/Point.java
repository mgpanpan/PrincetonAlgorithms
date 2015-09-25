/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point>
{
    // compare points by slope
    // public final Comparator<Point> SLOPE_ORDER;       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y)
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    public Comparator<Point> slopeOrder()
    { return new SlopeComparator(); }

    private class SlopeComparator implements Comparator<Point>
    {
        public int compare(Point v, Point w)
        {
            double vSlope = slopeTo(v), wSlope = slopeTo(w);
            if (vSlope < wSlope) return -1;
            else if (vSlope > wSlope) return +1;
            else return 0;
        }
    }

    // plot this point to standard drawing
    public void draw()
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that)
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that)
    {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        if (this.y == that.y)
            return +0.0;
        else if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else
            return (double) (this.y - that.y) / (this.x - that.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that)
    {
        if ((this.y < that.y) || (this.y == that.y && this.x < that.x))
            return -1;
        else if (this.y == that.y && this.x == that.x)
            return 0;
        else 
            return +1;
    }

    // return string representation of this point
    public String toString()
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args)
    {
        /* YOUR CODE HERE */
    }
}

/**
 * Created by pmg on 2015/9/24.
 */

public class PointSET
{
    SET<Point2D> points;
    public PointSET()       // construct an empty set of points
    {
        points = new SET<Point2D>();
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
    }

    public boolean isEmpty()   // is the set empty?
    { return points.isEmpty(); }

    public int size()          // number of points in the set
    { return points.size(); }

    public void insert(Point2D p)   // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new NullPointerException();
        points.add(p);
    }

    public boolean contains(Point2D p)   // does the set contain point p?
    {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D point : points)
            point.draw();
    }

    public Iterable<Point2D> range(RectHV rect)     // all points that are inside the rectangle
    {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D point : points)
            if (rect.contains(point))
                q.enqueue(point);
        return q;
    }

    public Point2D nearest(Point2D p)     // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new NullPointerException();
        Point2D pNearest = null;
        double distanceNearest = Double.POSITIVE_INFINITY;
        for (Point2D point : points) {
            double distance = point.distanceTo(p);
            if (distance < distanceNearest) {
                pNearest = point;
                distanceNearest = distance;
            }
        }
        return pNearest;
    }

    public static void main(String[] args)          // unit testing of the methods (optional)
    {}
}

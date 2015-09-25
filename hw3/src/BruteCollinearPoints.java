import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class BruteCollinearPoints
{
    private Queue<LineSegment> q = new Queue<LineSegment>();
    private LineSegment[] ls;
    /**
     * finds all line segments containing 4 points
     * @param ps
     */
    public BruteCollinearPoints(Point[] ps)
    {
        int N = ps.length;
        Arrays.sort(ps);

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                double slope1 = ps[i].slopeTo(ps[j]);
                for (int k = j+1; k < N; k++) {
                    double slope2 = ps[i].slopeTo(ps[k]);
                    if (slope1 != slope2) continue;
                    for (int l = k+1; l < N; l++) {
                        double slope3 = ps[i].slopeTo(ps[l]);
                        if (slope1 == slope3)
                            q.enqueue(new LineSegment(ps[i], ps[l]));
                    }
                }
            }
        }
        ls = new LineSegment[q.size()];
        for (int i = 0; i < ls.length; i++)
            ls[i] = q.dequeue();
    }
    public int numberOfSegments()
    { return ls.length; }
    public LineSegment[] segments()
    { return ls; }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In("hw3/src/collinear_data/input40.txt");
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}

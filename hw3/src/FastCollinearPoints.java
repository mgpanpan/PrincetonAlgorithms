import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class FastCollinearPoints
{
    private Queue<LineSegment> q = new Queue<LineSegment>();

    /**
     * finds all line segments containing 4 points
     *
     * @param ps
     */
    public FastCollinearPoints(Point[] ps)
    {
        int N = ps.length;

        // throw exception when duplicate points
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (j != i && ps[i].compareTo(ps[j]) == 0)
                    throw new IllegalArgumentException();

        Point[] psRef = new Point[N];
        for (int i = 0; i < N; i++)
            psRef[i] = ps[i];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                ps[j] = psRef[j];

            Arrays.sort(ps, i, N, psRef[i].slopeOrder());

            for (int j = i + 1, k = j + 1; j < N - 2; j = k) {
                while (k < N && psRef[i].slopeOrder().compare(ps[j], ps[k]) == 0) k++;
                int num = k - j - 1;
                if (num >= 2) {
                    int totalnum = num + 2;
                    Point[] tmp = new Point[totalnum];
                    tmp[0] = psRef[i];
                    for (int m = 1; m < totalnum; m++)
                        tmp[m] = ps[j + m - 1];
                    Arrays.sort(tmp);
                    q.enqueue(new LineSegment(tmp[0], tmp[totalnum - 1]));
                }
            }
        }
    }

    public int numberOfSegments()
    { return q.size(); }

    public LineSegment[] segments()
    {
        LineSegment[] ls = new LineSegment[q.size()];
        int i = 0;
        for (LineSegment item : q) {
            ls[i] = item;
            i++;
        }
        return ls;
    }

    public static void main(String[] args) {
        // read the N points from a file
        // StdOut.println(System.getProperty("user.dir"));
        In in = new In("hw3/src/collinear_data/input80.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}

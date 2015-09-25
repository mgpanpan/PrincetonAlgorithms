/**
 * Created by pmg on 2015/9/24.
 */

public class KdTree
{
    /**
     * Note : this inner class is static
     */
    private static class Node
    {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        public Node(Point2D p, RectHV rect, Node lb, Node rt)
        { this.p = p; this.rect = rect; this.lb = lb; this.rt = rt; }
    }

    private Node root = null;
    private int N = 0;
    // instance variable distanceNearest and pNearest are used by the nearest method.
    private double distanceNearest = Double.POSITIVE_INFINITY;
    private Point2D pNearest = null;

    public KdTree()
    {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
    }

    /**
     * is the KdTree empty?
     * @return true if the KdTree is empty
     */
    public boolean isEmpty()
    { return root == null; }

    /**
     * size of the KdTree
     * @return number of points in the KdTree
     */
    public int size()
    { return N; }

    /**
     * insert the point to the KdTree (if it is not already in the KdTree)
     * @param p point to insert
     */
    public void insert(Point2D p)
    {
        if (p == null) throw new NullPointerException();
        root = put(root, p, new RectHV(-1.0, -1.0, 1.0, 1.0), true);
    }

    /**
     * helper function
     * @param node current processing node
     * @param p point to add
     * @param rect the rectangular correspond to current node
     * @param isHorizontal true表示用一条竖直线水平方向分割平面
     * @return
     */
    private Node put(Node node, Point2D p, RectHV rect, boolean isHorizontal)
    {
        if (node == null) { N++; return new Node(p, rect, null, null); }
        if (p.equals(node.p)) return node; // the point is already in the KdTree(a set).
        if (isHorizontal) {
            double cmp = p.x() - node.p.x();
            if (cmp < 0)
                node.lb = put(node.lb, p,
                              new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax()), // left
                              !isHorizontal);
            else
                node.rt = put(node.rt, p,
                              new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax()), // right
                              !isHorizontal);
        } else {
            double cmp = p.y() - node.p.y();
            if (cmp < 0)
                node.lb = put(node.lb, p, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y()), // down
                              !isHorizontal);
            else
                node.rt = put(node.rt, p, new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax()), // up
                              !isHorizontal);
        }
        return node;
    }

    /**
     * does the KdTree contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p)
    {
        if (p == null) throw new NullPointerException();
        return get(root, p, true) != null;
    }

    /**
     * helper function
     * @param node current processing node
     * @param p point to insert
     * @param isHorizontal true表示用一条竖直线水平方向分割平面
     * @return the Node containing the point, null if not find.
     */
    private Node get(Node node, Point2D p, boolean isHorizontal)
    {
        if (node == null) return null;
        if (p.equals(node.p)) return node;
        if (isHorizontal) {
            double cmp = p.x() - node.p.x();
            if (cmp < 0) return get(node.lb, p, !isHorizontal);
            else return get(node.rt, p, !isHorizontal);
        } else {
            double cmp = p.y() - node.p.y();
            if (cmp < 0) return get(node.lb, p, !isHorizontal);
            else return get(node.rt, p, !isHorizontal);
        }
    }

    /**
     * draw all points to standard draw
     */
    public void draw()
    { drawNode(root, true); }

    /**
     * helper function
     * @param x current processing node
     * @param isHorizontal true表示用一条竖直线水平方向分割平面
     */
    private void drawNode(Node x, boolean isHorizontal)
    {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        if (isHorizontal) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        drawNode(x.lb, !isHorizontal);
        drawNode(x.rt, !isHorizontal);
    }

    /**
     * range search
     * @param rect
     * @return all points that inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    private void range(Node x, RectHV rect, Queue q)
    {
        if (x == null) return;
        if (!rect.intersects(x.rect)) return;
        if (rect.contains(x.p)) q.enqueue(x.p);
        range(x.lb, rect, q);
        range(x.rt, rect, q);
    }

    /**
     * find the nearest point to the querying point.
     * @param p
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p)
    {
        if (p == null) throw new NullPointerException();
        distanceNearest = Double.POSITIVE_INFINITY;
        pNearest = null;
        if (p == null) throw new NullPointerException();
        nearest(root, p, true);
        return pNearest;
    }

    private void nearest(Node x, Point2D p, boolean isHorizontal)
    {
        if (x == null) return;
        if (x.rect.distanceTo(p) > distanceNearest) return;
        double distance = p.distanceTo(x.p);
        if (distance < distanceNearest) {
            distanceNearest = distance;
            pNearest = x.p;
        }
        if (isHorizontal) {
            if (p.x() < x.p.x()) {
                nearest(x.lb, p, !isHorizontal);
                nearest(x.rt, p, !isHorizontal);
            } else {
                nearest(x.rt, p, !isHorizontal);
                nearest(x.lb, p, !isHorizontal);
            }
        } else {
            if (p.y() < x.p.y()) {
                nearest(x.lb, p, !isHorizontal);
                nearest(x.rt, p, !isHorizontal);
            } else {
                nearest(x.rt, p, !isHorizontal);
                nearest(x.lb, p, !isHorizontal);
            }
        }
    }

    public static void main(String[] args)          // unit testing of the methods (optional)
    {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.7, 0.2));
        kdtree.draw();
        kdtree.insert(new Point2D(0.5, 0.4));
        kdtree.draw();
        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.draw();
        kdtree.insert(new Point2D(0.4, 0.7));
        kdtree.draw();
        kdtree.insert(new Point2D(0.9, 0.6));
        kdtree.draw();
        StdOut.println("size : " + kdtree.size());
    }
}

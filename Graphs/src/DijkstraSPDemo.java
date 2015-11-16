/**
 * Created by pmg on 2015/11/16.
 */
public class DijkstraSPDemo
{
    private double[] distance;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> unreached;
    private EuclideanEdgeWeightedDigraph EG;

    public DijkstraSPDemo(EuclideanEdgeWeightedDigraph EG, int s) {
        this.EG = EG;
        this.EG.draw();
        StdDraw.setPenColor(StdDraw.RED);

        EdgeWeightedDigraph G = EG.G();

        int V = G.V();
        distance = new double[V];
        edgeTo = new DirectedEdge[V];
        unreached = new IndexMinPQ<Double>(V);

        for (int i = 0; i < V; i++)
            distance[i] = Double.POSITIVE_INFINITY;

        distance[s] = 0.0;
        unreached.insert(s, 0.0);
        while (!unreached.isEmpty()) {
            int v = unreached.delMin();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.011);
            EG.drawPoint(EG.name(v));
            StdDraw.show(100);
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (distance[w] > distance[v] + edge.weight()) {
                distance[w] = distance[v] + edge.weight();
                edgeTo[w] = edge;
                if (unreached.contains(w)) unreached.changeKey(w, distance[w]);
                else                       unreached.insert(w, distance[w]);
                // 蓝色圆圈标出在priority queue中的点
                StdDraw.setPenRadius(0.003);
                StdDraw.setPenColor(StdDraw.BLUE);
                String[] xy = EG.name(w).split(",");
                double x = Double.parseDouble(xy[0]);
                double y = Double.parseDouble(xy[1]);
                StdDraw.circle(x, y, 0.010);
                StdDraw.show(100);
            }
        }
    }

    public double distTo(int v) {
        return distance[v];
    }

    public boolean hasPathTo(int v) {
        return distance[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> stk = new Stack<DirectedEdge>();
        DirectedEdge edge = edgeTo[v];
        for (; edge != null; edge = edgeTo[edge.from()])
            stk.push(edge);
        return stk;
    }

    public static void main(String[] args)
    {
        int N = 1000;
        RandomEuclideanEdgeWeightedDigraph REG = new RandomEuclideanEdgeWeightedDigraph(N);
        REG.setThreshold(0.06);
        REG.generate();
        EuclideanEdgeWeightedDigraph EG = REG.egOut();
        int s = 0;
        DijkstraSPDemo sp = new DijkstraSPDemo(EG, s);
        int target = StdRandom.uniform(0, N);
        StdOut.println("From point " + s + " (" + EG.name(s) + ")" + " to point " + target + " (" + EG.name(target) + ")." );
        StdDraw.setPenColor(StdDraw.CYAN);
        int i = 0;
        if (!sp.hasPathTo(target)) {
            StdOut.println("No path from " + s + " to " + target);
            return;
        }
        for (DirectedEdge edge : sp.pathTo(target))  // show the find path.(should be the shortest).
        {
            StdDraw.setPenRadius(0.012);
            int v = edge.from();
            int w = edge.to();
            EG.drawPoint(EG.name(v));
            if (v != s) {
                StdDraw.setPenRadius(0.004);
                EG.drawLine(EG.name(v), EG.name(w));
                StdOut.print(" -> " + w + "(" + EG.name(w) + ")");
            } else {
                StdOut.print(v + "(" + EG.name(v) + ")");
                StdOut.print(" -> " + w + "(" + EG.name(w) + ")");
            }
            StdDraw.show(500);
            if (i == 6) { StdOut.println(); i = 0; }
            i++;
        }
    }
}

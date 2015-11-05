/**
 * Created by pmg on 2015/11/5.
 */
public class LazyPrimMSTDemo
{
    private MinPQGeneric<Edge> pq;
    private boolean marked[];
    private Queue<Edge> mst;
    private EuclideanEdgeWeightedGraph EG;

    public LazyPrimMSTDemo(EuclideanEdgeWeightedGraph EG)
    {
        this.EG = EG;
        this.EG.draw();
        StdDraw.setPenColor(StdDraw.RED);

        EdgeWeightedGraph G = EG.G();
        pq = new MinPQGeneric<Edge>();
        marked = new boolean[G.V()];
        mst = new Queue<Edge>();
        visit(G, 0);
        while (!pq.isEmpty()) {
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(edge);
            StdDraw.setPenRadius(0.011);
            EG.drawPoint(EG.name(v));
            EG.drawPoint(EG.name(w));
            StdDraw.setPenRadius(0.003);
            EG.drawLine(EG.name(v), EG.name(w));
            StdDraw.show(100);
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v)
    {
        marked[v] = true;
        for (Edge edge : G.adj(v))
            if (!marked[edge.other(v)])
                pq.insert(edge);
    }

    public Iterable<Edge> edges()
    {
        return mst;
    }

    public double weight()
    {
        double weight = 0.0;
        for (Edge edge : mst)
            weight += edge.weight();
        return weight;
    }
    public static void main(String[] args)
    {
        int N = 600;
        RandomEuclideanEdgeWeightedGraph REG = new RandomEuclideanEdgeWeightedGraph(N);
        REG.setThreshold(0.06);
        REG.generate();
        EuclideanEdgeWeightedGraph EG = REG.egOut();
        LazyPrimMSTDemo prim = new LazyPrimMSTDemo(EG);
        int count = 0;
        for (Edge edge : prim.edges())
            if (count == 5) {
                count = 0;
                StdOut.println(edge);
            }
            else {
                StdOut.print(edge + " ");
                count++;
            }
        StdOut.println();
        StdOut.println("total weight : " + prim.weight());
    }
}

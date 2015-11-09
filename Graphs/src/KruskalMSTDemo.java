/**
 * Created by pmg on 2015/11/5.
 */
public class KruskalMSTDemo
{
    private Queue<Edge> mst;
    private MinPQGeneric<Edge> pq;
    private WeightedQuickUnionUF uf;
    private EuclideanEdgeWeightedGraph EG;

    public KruskalMSTDemo(EuclideanEdgeWeightedGraph EG)
    {
        this.EG = EG;
        this.EG.draw();
        StdDraw.setPenColor(StdDraw.RED);

        EdgeWeightedGraph G = EG.G();

        mst = new Queue<Edge>();
        pq = new MinPQGeneric<Edge>();
        uf = new WeightedQuickUnionUF(G.V());

        for (Edge edge : G.edges())
            pq.insert(edge);

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (uf.connected(v, w)) continue;
            uf.union(v, w);
            mst.enqueue(edge);

            StdDraw.setPenRadius(0.011);
            EG.drawPoint(EG.name(v));
            EG.drawPoint(EG.name(w));
            StdDraw.setPenRadius(0.003);
            EG.drawLine(EG.name(v), EG.name(w));
            StdDraw.show(100);
        }
    }

    public Iterable<Edge> edges()
    { return mst; }

    public double weight()
    {
        double weight = 0.0;
        for (Edge edge : edges())
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
        KruskalMSTDemo kruskal = new KruskalMSTDemo(EG);
        int count = 0;
        for (Edge edge : kruskal.edges())
            if (count == 5) {
                count = 0;
                StdOut.println(edge);
            }
            else {
                StdOut.print(edge + " ");
                count++;
            }
        StdOut.println();
        StdOut.println("total weight : " + kruskal.weight());
        // 从输出的顺序以及动态演示的结果能够看出，权值较小的edge（较短的）先加入MST
    }
}

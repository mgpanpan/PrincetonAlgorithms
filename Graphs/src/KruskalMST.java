/**
 * Created by pmg on 2015/11/9.
 */
public class KruskalMST
{
    private Queue<Edge> mst;
    private MinPQGeneric<Edge> pq;
    private WeightedQuickUnionUF uf;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        pq = new MinPQGeneric<Edge>();
        uf = new WeightedQuickUnionUF(G.V());

        for (Edge edge : G.edges())
            pq.insert(edge);

        // 当图本身不连通时会出现异常
        // for (int i = 0; i < G.V() - 1; ) {
        //     Edge edge = pq.delMin();
        //     int v = edge.either();
        //     int w = edge.other(v);
        //     if (uf.connected(v, w)) continue;
        //     uf.union(v, w);
        //     mst.enqueue(edge);
        //     i++;
        // }

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (uf.connected(v, w)) continue;
            uf.union(v, w);
            mst.enqueue(edge);
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
        In in = new In("tinyEWG.txt");
        PrimMST prim = new PrimMST(new EdgeWeightedGraph(in));
        int count = 0;
        StdOut.println("Edges in the MST: ");
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

/**
 * Created by pmg on 2015/11/5.
 */
public class LazyPrimMST
{
    private MinPQGeneric<Edge> pq;
    private boolean marked[];
    private Queue<Edge> mst;

    public LazyPrimMST(EdgeWeightedGraph G)
    {
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
        In in = new In("tinyEWG.txt");
        LazyPrimMST prim = new LazyPrimMST(new EdgeWeightedGraph(in));
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

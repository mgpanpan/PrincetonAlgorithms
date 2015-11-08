/**
 * Created by pmg on 2015/11/5.
 */
public class PrimMST
{
    private IndexMinPQ<Double> pq;
    private double[] distance;  // distance from the vertex to the MST
    private boolean[] marked;
    private Edge[] edgeTo;

    public PrimMST(EdgeWeightedGraph G)
    {
        pq = new IndexMinPQ<Double>(G.V());
        distance = new double[G.V()];
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];

        for (int v = 0; v < G.V(); v++)
            distance[v] = Double.POSITIVE_INFINITY;

        pq.insert(0, 0.0);
        distance[0] = 0.0;
        while (!pq.isEmpty())
            visit(G, pq.delMin());
    }

    private void visit(EdgeWeightedGraph G, int v)
    {
        marked[v] = true;
        for (Edge edge : G.adj(v)) {
            int w = edge.other(v);
            if (marked[w] || edge.weight() >= distance[w]) continue;
            edgeTo[w] = edge;
            distance[w] = edge.weight();
            if (pq.contains(w)) pq.changeKey(w, distance[w]);
            else pq.insert(w, distance[w]);
        }
    }

    public Iterable<Edge> edges()
    {
        Queue<Edge> edges = new Queue<Edge>();
        for (int i = 1; i < edgeTo.length; i++)
            edges.enqueue(edgeTo[i]);
        return edges;
    }

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

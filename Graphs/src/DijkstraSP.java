/**
 * Created by pmg on 2015/11/16.
 */
public class DijkstraSP
{
    private double[] distance;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> unreached;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        int V = G.V();
        distance = new double[V];
        edgeTo = new DirectedEdge[V];
        unreached = new IndexMinPQ<Double>(V);

        for (int i = 0; i < V; i++)
            distance[i] = Double.POSITIVE_INFINITY;

        distance[s] = 0.0;
        unreached.insert(s, 0.0);
        while (!unreached.isEmpty())
            relax(G, unreached.delMin());
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (distance[w] > distance[v] + edge.weight()) {
                distance[w] = distance[v] + edge.weight();
                edgeTo[w] = edge;
                if (unreached.contains(w)) unreached.changeKey(w, distance[w]);
                else                       unreached.insert(w, distance[w]);
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

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWDG.txt"));
        int s = 0;

        DijkstraSP sp = new DijkstraSP(G, s);

        for (int t = 0; t < G.V(); t++) {
            StdOut.print(s + " to " + t);
            StdOut.printf(" (%4.2f) : ", sp.distTo(t));
            if (sp.hasPathTo(t))
                for (DirectedEdge e : sp.pathTo(t))
                    StdOut.print(e + "  ");
            StdOut.println();
        }
    }
}

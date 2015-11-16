/**
 * Created by pmg on 2015/11/15.
 */
public class EdgeWeightedDigraph
{
    private int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    public EdgeWeightedDigraph(int V)
    {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    public EdgeWeightedDigraph(In in)
    {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }

    public void addEdge(DirectedEdge edge)
    {
        adj[edge.from()].add(edge);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v)
    { return adj[v]; }

    public Iterable<DirectedEdge> edges()
    {
        Bag<DirectedEdge> b = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge edge : adj[v])
            b.add(edge);
        return b;
    }

    public String toString()
    {
        String s = V + ", vertices, " + E + " edges.\n";
        for (int v = 0; v < V; v++)
        {
            s += v + ": ";
            for (DirectedEdge edge : adj(v))
                s += edge.to() + " ";
            s += "\n";
        }
        return s;
    }

    public int V()
    { return V; }

    public int E()
    { return E; }

    public static void main(String[] args)
    {
        In in = new In("tinyEWDG.txt");
        EdgeWeightedDigraph ewg = new EdgeWeightedDigraph(in);
        StdOut.println(ewg);
    }
}

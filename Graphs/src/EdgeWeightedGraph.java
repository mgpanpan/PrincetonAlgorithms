/**
 * Created by pmg on 2015/11/5.
 */
public class EdgeWeightedGraph
{
    int V;
    int E;
    Bag<Edge>[] adj;
    public EdgeWeightedGraph(int V)
    {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }

    public EdgeWeightedGraph(In in)
    {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public void addEdge(Edge edge)
    {
        int v = edge.either();
        int w = edge.other(v);
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    public Iterable<Edge> adj(int v)
    { return adj[v]; }

    public Iterable<Edge> edges()
    {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v++)
            for (Edge edge : adj[v])
                if (edge.other(v) > v) b.add(edge);
        return b;
    }

    public String toString()
    {
        String s = V + ", vertices, " + E + " edges.\n";
        for (int v = 0; v < V; v++)
        {
            s += v + ": ";
            for (Edge edge : adj(v))
                s += edge.other(v) + " ";
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
        In in = new In("tinyEWG.txt");
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(in);
        StdOut.println(ewg);
    }
}

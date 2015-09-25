/**
 * Created by pmg on 2015/9/7.
 */
public class Graph
{
    private final int V;     // number of vertices
    private int E;           // number of edges
    private Bag<Integer>[] adj; // adjacency lists

    public Graph(int V)
    {
        this.V = V; this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public Graph(In in)
    {
        this(in.readInt());     // Read V and construct this graph
        int E = in.readInt();
        for (int i = 0; i < E; i++)
        {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public void addEdge(int v, int w)
    {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public int V() {  return V;  }
    public int E() {  return E;  }

    public Iterable<Integer> adj(int v)
    { return adj[v]; }

    public int degree(int v)
    { return adj[v].size(); }

    public int maxDegree()
    {
        int max = 0;
        for (int v = 0; v < V; v++)
            if (degree(v) > max) max = degree(v);
        return max;
    }

    public int avgDegree()
    { return 2 * E() / V(); }

    public int numberOfSelfLoops()
    {
        int count = 0;
        for (int v = 0; v < V; v++)
            for (int w : adj[v])
                if (v == w) count++;
        return count / 2;
    }

    public String toString()
    {
        String s = V + ", vertices, " + E + " edges.\n";
        for (int v = 0; v < V; v++)
        {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args)
    {
        // Draw d = new Draw();
        // d.setCanvasSize(800, 600);
        // d.setXscale(0, 10);
        // d.line(0, 0, 0.5, 0.5);

        In in = new In(StdIn.readString());
        Graph g = new Graph(in);
        StdOut.println(g);
        StdOut.println("max degree: " + g.maxDegree());
        StdOut.println("average degree: " + g.avgDegree());
        StdOut.println("number of self loops: " + g.numberOfSelfLoops());
        g.addEdge(3, 3);
        StdOut.println("number of self loops: " + g.numberOfSelfLoops());
    }
}

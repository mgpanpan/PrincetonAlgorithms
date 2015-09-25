/**
 * Created by pmg on 2015/9/8.
 */
public class UFSearch
{
    // private boolean marked[];
    private int s;
    private WeightedQuickUnionUF uf;

    public UFSearch(Graph G, int s)
    {
        // marked = new boolean[G.V()];
        this.s = s;
        uf = new WeightedQuickUnionUF(G.V());
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                uf.union(v, w);
        /*
        for (int v = 0; v < G.V(); v++)
            if (uf.connected(s, v))
            { marked[v] = true; count++; }
        */
    }

    public boolean marked(int v)
    {
        return uf.connected(s, v);
    }

    public int count()
    {
        return uf.size(s); // add a sz method to the UF class. sz(s) = sz[find(s)]
    }

    public static void main(String[] args)
    {
        StdOut.print("Input a file name and a source vertex: ");
        In in = new In(StdIn.readString());
        Graph G = new Graph(in);
        UFSearch search = new UFSearch(G, StdIn.readInt());
        for (int v = 0; v < G.V(); v++)
            if (search.marked(v)) StdOut.print(v + " ");
        StdOut.println();
        if (search.count() != G.V()) StdOut.print("NOT ");
        StdOut.println("connected");

        StdOut.println("connect 6 and 7"); G.addEdge(6, 7);
        StdOut.print("Input a new source vertex: ");
        search = new UFSearch(G, StdIn.readInt());
        for (int v = 0; v < G.V(); v++)
            if (search.marked(v)) StdOut.print(v + " ");
        StdOut.println();
        if (search.count() != G.V()) StdOut.print("NOT ");
        StdOut.println("connected");

        StdOut.println("connect 7 and 9"); G.addEdge(7, 9);
        StdOut.print("Input a new source vertex: ");
        search = new UFSearch(G, StdIn.readInt());
        for (int v = 0; v < G.V(); v++)
            if (search.marked(v)) StdOut.print(v + " ");
        StdOut.println();
        if (search.count() != G.V()) StdOut.print("NOT ");
        StdOut.println("connected");
    }
}

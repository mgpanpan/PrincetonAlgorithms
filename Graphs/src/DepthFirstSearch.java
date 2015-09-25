/**
 * Created by pmg on 2015/9/8.
 */
public class DepthFirstSearch
{
    private boolean[] marked;
    private int count;        // number of marked vertices

    public DepthFirstSearch(Graph G, int s)
    {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public void dfs(Graph G, int v)
    {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean marked(int v)
    { return marked[v]; }

    public int count()
    { return count; }

    public static void main(String[] args)
    {
        StdOut.print("Input a file name and a source vertex: ");
        In in = new In(StdIn.readString());
        Graph G = new Graph(in);
        DepthFirstSearch search = new DepthFirstSearch(G, StdIn.readInt());
        for (int v = 0; v < G.V(); v++)
            if (search.marked(v)) StdOut.print(v + " ");
        StdOut.println();
        if (search.count() != G.V()) StdOut.print("NOT ");
        StdOut.println("connected");

        StdOut.println("connect 6 and 7"); G.addEdge(6, 7);
        StdOut.print("Input a new source vertex: ");
        search = new DepthFirstSearch(G, StdIn.readInt());
        for (int v = 0; v < G.V(); v++)
            if (search.marked(v)) StdOut.print(v + " ");
        StdOut.println();
        if (search.count() != G.V()) StdOut.print("NOT ");
        StdOut.println("connected");

        StdOut.println("connect 7 and 9"); G.addEdge(7, 9);
        StdOut.print("Input a new source vertex: ");
        search = new DepthFirstSearch(G, StdIn.readInt());
        for (int v = 0; v < G.V(); v++)
            if (search.marked(v)) StdOut.print(v + " ");
        StdOut.println();
        if (search.count() != G.V()) StdOut.print("NOT ");
        StdOut.println("connected");
    }
}

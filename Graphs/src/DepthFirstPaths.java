/**
 * Created by pmg on 2015/9/8.
 */

public class DepthFirstPaths
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    public DepthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
            {
                edgeTo[w] = v;
                dfs(G, w);
            }
    }

    public boolean hasPathTo(int v)
    { return marked[v]; }

    public Iterable<Integer> pathTo(int v)
    {
        if (!hasPathTo(v)) return null;
        Stack<Integer> stk = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            stk.push(x);
        stk.push(s);
        return stk;
    }

    public static void main(String[] args)
    {
        StdOut.print("Input a file name and a source vertex: ");
        In in = new In(StdIn.readString());
        Graph G = new Graph(in);
        int s = StdIn.readInt();
        DepthFirstPaths path = new DepthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++)
        {
            StdOut.print(s + " to " + v + ": ");
            if (!path.hasPathTo(v)) StdOut.println("No path");
            else
            {
                for (int w : path.pathTo(v))
                    if (w == s) StdOut.print(w);
                    else StdOut.print(" - " + w);
                StdOut.println();
            }
        }

        StdOut.println("connect 6 and 7"); G.addEdge(6, 7);
        StdOut.print("Input a new source vertex: ");
        s = StdIn.readInt();
        path = new DepthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++)
        {
            StdOut.print(s + " to " + v + ": ");
            if (!path.hasPathTo(v)) StdOut.println("No path");
            else
            {
                for (int w : path.pathTo(v))
                    if (w == s) StdOut.print(w);
                    else StdOut.print(" - " + w);
                StdOut.println();
            }
        }

        StdOut.println("connect 7 and 9"); G.addEdge(7, 9);
        StdOut.print("Input a new source vertex: ");
        s = StdIn.readInt();
        path = new DepthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++)
        {
            StdOut.print(s + " to " + v + ": ");
            if (!path.hasPathTo(v)) StdOut.println("No path");
            else
            {
                for (int w : path.pathTo(v))
                    if (w == s) StdOut.print(w);
                    else StdOut.print(" - " + w);
                StdOut.println();
            }
        }
    }
}


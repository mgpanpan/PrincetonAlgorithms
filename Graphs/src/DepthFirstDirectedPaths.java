/**
 * Created by pmg on 2015/9/8.
 */

public class DepthFirstDirectedPaths
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    public DepthFirstDirectedPaths(Digraph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Digraph G, int v)
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
        In in = new In(StdIn.readString()); // tinyDG.txt
        Digraph G = new Digraph(in);
        int s = StdIn.readInt();
        DepthFirstDirectedPaths path = new DepthFirstDirectedPaths(G, s);
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


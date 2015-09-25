/**
 * Created by pmg on 2015/9/9.
 */
public class BreadFirstPaths
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    public BreadFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int v)
    {
        marked[v] = true;
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(v);
        while (!queue.isEmpty())
        {
            int x = queue.dequeue();
            for (int w : G.adj(x))
                if (!marked[w])
                {
                    edgeTo[w] = x;
                    marked[w] = true;
                    queue.enqueue(w);
                }
        }

        /**
         * Exercise 4.1.14
         */
        /*
        Stack<Integer> queue = new Stack<Integer>();
        queue.push(v);
        while (!queue.isEmpty())
        {
            int x = queue.pop();
            for (int w : G.adj(x))
                if (!marked[w])
                {
                    edgeTo[w] = x;
                    marked[w] = true;
                    queue.push(w);
                }
        }
        */
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
        BreadFirstPaths path = new BreadFirstPaths(G, s);
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
        path = new BreadFirstPaths(G, s);
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
        path = new BreadFirstPaths(G, s);
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

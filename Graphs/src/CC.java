/**
 * Created by LENOVO on 2015/9/10.
 */
public class CC
{
    private int count;
    private boolean[] marked;
    private int[] id;

    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
            {
                dfs(G, v);
                count++;
            }
    }

    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public int count()
    { return count; }
    public int id(int v)
    { return id[v]; }
    public boolean connected(int v, int w)
    { return id[v] == id[w]; }

    public static void main(String[] args)
    {
        StdOut.print("Input a file name: ");
        In in = new In(StdIn.readString());
        Graph G = new Graph(in);
        CC cc = new CC(G);
        int M = cc.count();
        Bag<Integer>[] components = (Bag<Integer>[]) new Bag[M];
        for (int m = 0; m < M; m++)
            components[m] = new Bag<Integer>();
        for (int v = 0; v < G.V(); v++)
            components[cc.id(v)].add(v);

        for (int m = 0; m < M; m++)
        {
            StdOut.print(m + " :");
            for (int w : components[m])
                StdOut.print(w + " ");
            StdOut.println();
        }

        StdOut.println("connect 6 and 7"); G.addEdge(6, 7);
        cc = new CC(G);
        M = cc.count();
        for (int m = 0; m < M; m++)
            components[m] = new Bag<Integer>();
        for (int v = 0; v < G.V(); v++)
            components[cc.id(v)].add(v);

        for (int m = 0; m < M; m++)
        {
            StdOut.print(m + " :");
            for (int w : components[m])
                StdOut.print(w + " ");
            StdOut.println();
        }

        StdOut.println("connect 7 and 9"); G.addEdge(7, 9);
        cc = new CC(G);
        M = cc.count();
        for (int m = 0; m < M; m++)
            components[m] = new Bag<Integer>();
        for (int v = 0; v < G.V(); v++)
            components[cc.id(v)].add(v);

        for (int m = 0; m < M; m++)
        {
            StdOut.print(m + " :");
            for (int w : components[m])
                StdOut.print(w + " ");
            StdOut.println();
        }
    }
}

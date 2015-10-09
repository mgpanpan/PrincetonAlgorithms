/**
 * Created by pmg on 2015/9/25.
 */
public class DirectedDFS
{
    private boolean[] marked;

    public DirectedDFS(Digraph G, int s)
    {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> vs)
    {
        marked = new boolean[G.V()];
        for (int v : vs)
            if (!marked(v)) dfs(G, v);
    }

    private void dfs(Digraph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean marked(int v)
    { return marked[v]; }

    public static void main(String[] args)
    {
        Digraph G = new Digraph(new In("tinyDG.txt"));
        while (StdIn.hasNextLine())
        {
            Bag<Integer> sources = new Bag<Integer>();
            String line = StdIn.readLine();
            String[] elems = line.split(" ");
            for (int i = 0; i < elems.length; i++)
                sources.add(Integer.parseInt(elems[i]));
            DirectedDFS reachable = new DirectedDFS(G, sources);
            for (int v = 0; v < G.V(); v++)
                if (reachable.marked(v)) StdOut.print(v + " ");
            StdOut.println();
        }
    }
}

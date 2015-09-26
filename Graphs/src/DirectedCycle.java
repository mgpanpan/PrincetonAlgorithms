/**
 * Created by pmg on 2015/9/26.
 */
public class DirectedCycle
{
    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack;
    private Stack<Integer> cycle;

    public DirectedCycle(Digraph G)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v)
    {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v))
            if (this.hasCycle()) return;
            else if (!marked[w])
            { edgeTo[w] = v; dfs(G, w);}
            else if (onStack[w])
            {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        onStack[v] = false;
    }

    public boolean hasCycle()
    { return cycle != null; }

    public Iterable<Integer> cycle()
    { return cycle; }

    public static void main(String[] args)
    {
        Digraph G = new Digraph(new In("tinyDAG.txt"));
        DirectedCycle DC = new DirectedCycle(G);
        if (DC.hasCycle()) {
            StdOut.println("This Digraph is not a DAG, one of its cycle is: ");
            for (int v : DC.cycle)
                StdOut.print(v + " ");
        }
        else
            StdOut.print("No cycle, this Digraph is a DAG.");
        StdOut.println();
    }
}

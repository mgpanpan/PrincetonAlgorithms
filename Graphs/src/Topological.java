import java.lang.reflect.GenericArrayType;

/**
 * Created by pmg on 2015/9/26.
 */
public class Topological
{
    private Iterable<Integer> order;

    public Topological(Digraph G)
    {
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if (!cyclefinder.hasCycle())
        {
            DepthFirstOrder DFOrder = new DepthFirstOrder(G);
            order = DFOrder.reversePost();
        }
    }

    public boolean isDAG()
    { return order != null; }

    public Iterable<Integer> order()
    { return order; }

    public static void main(String[] args)
    {
        /*
        Digraph G = new Digraph(new In("tinyDG.txt"));
        Topological t = new Topological(G);
        if (!t.isDAG())
            StdOut.print("This Digraph can not be topological sorted");
        else
            for (int v : t.order())
                StdOut.print(v + " ");
        StdOut.println();
        */

        SymbolDigraph SG = new SymbolDigraph("jobs.txt", "/");
        Digraph G = SG.G();
        Topological t = new Topological(G);
        if (!t.isDAG())
            StdOut.print("This Digraph can not be topological sorted");
        else
            for (int v : t.order())
                StdOut.println(SG.name(v));
    }
}

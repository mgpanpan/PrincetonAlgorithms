/**
 * Created by pmg on 2015/9/27.
 */
public class KosarajuSCC
{
    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSCC(Digraph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        count = 0;
        DepthFirstOrder DFOrder = new DepthFirstOrder(G.reverse());
        for (int v : DFOrder.reversePost()) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public int count()
    { return count; }

    public boolean strongConnect(int v, int w)
    { return id[v] == id[w]; }

    public int id(int v)
    { return id[v]; }

    public static void main(String[] args)
    {
        Digraph G = new Digraph(new In("tinyDG.txt"));
        KosarajuSCC SCC = new KosarajuSCC(G);
        StdOut.println("There are " + SCC.count() + " strong connected component.");
        Bag<Integer>[] components = (Bag<Integer>[]) new Bag[SCC.count()];
        for (int c = 0; c < SCC.count(); c++)
            components[c] = new Bag<Integer>();
        for (int v = 0; v < G.V(); v++)
            components[SCC.id(v)].add(v);
        for (int c = 0; c < SCC.count(); c++) {
            StdOut.print("Component " + c + ": ");
            for (int w : components[c])
                StdOut.print(w + " ");
            StdOut.println();
        }
    }
}

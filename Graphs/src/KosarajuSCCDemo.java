import java.awt.*;

/**
 * Created by pmg on 2015/9/27.
 */
public class KosarajuSCCDemo
{
    private boolean[] marked;
    private int[] id;
    private int count;

    private final Color[] Colors = {Color.RED, Color.BLUE, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.PINK};
    private EuclideanDigraph EG;

    public KosarajuSCCDemo(EuclideanDigraph EG)
    {
        this.EG = EG;
        this.EG.draw();
        Digraph G = EG.G();
        marked = new boolean[G.V()];
        id = new int[G.V()];
        count = 0;
        DepthFirstOrder DFOrder = new DepthFirstOrder(G.reverse());
        for (int v : DFOrder.reversePost()) {
            if (!marked[v]) {
                StdDraw.setPenColor(Colors[count % Colors.length]);
                dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        StdDraw.setPenRadius(0.011);
        EG.drawPoint(EG.name(v));
        StdDraw.show(100);
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
        int N = 100;
        RandomEuclideanDigraph REG = new RandomEuclideanDigraph(N);
        REG.setThreshold(0.15);
        REG.generate();
        KosarajuSCCDemo SCC = new KosarajuSCCDemo(REG.egOut());
        StdOut.println("There are " + SCC.count() + " strong connected component.");
    }
}

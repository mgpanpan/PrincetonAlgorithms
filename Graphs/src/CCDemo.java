import java.awt.*;

/**
 * Created by LENOVO on 2015/9/10.
 */
public class CCDemo
{
    private int count;
    private boolean[] marked;
    private int[] id;

    private final Color[] Colors = {Color.RED, Color.BLUE, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.PINK};
    private EuclideanGraph EG;
    public CCDemo(EuclideanGraph EG)
    {
        this.EG = EG;
        this.EG.draw();
        Graph G = EG.G();
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
            {
                StdDraw.setPenColor(Colors[count % Colors.length]);
                dfs(G, v);
                count++;
            }
    }

    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        StdDraw.setPenRadius(0.011);
        EG.drawPoint(EG.name(v));
        StdDraw.show(100);
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
            {
                StdDraw.setPenRadius(0.003);
                EG.drawLine(EG.name(v), EG.name(w));
                StdDraw.show(100);
                dfs(G, w);
            }
    }

    public int count()
    { return count; }
    public int id(int v)
    { return id[v]; }
    public boolean connected(int v, int w)
    { return id[v] == id[w]; }

    public static void main(String[] args)
    {
        int N = 500;
        RandomEuclideanGraph REG = new RandomEuclideanGraph(N);
        REG.setThreshold(0.06);
        REG.generate();
        CCDemo cc = new CCDemo(REG.egOut());
        StdOut.println("Number of component: " + cc.count());
    }
}

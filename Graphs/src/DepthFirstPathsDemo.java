/**
 * Created by pmg on 2015/9/8.
 */

public class DepthFirstPathsDemo
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    private EuclideanGraph EG;
    public DepthFirstPathsDemo(EuclideanGraph EG, int s)
    {
        this.EG = EG;
        this.EG.draw();
        Graph G = EG.G();
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        StdDraw.setPenColor(StdDraw.RED);
        dfs(G, s);
    }

    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        StdDraw.setPenRadius(0.011);
        EG.drawPoint(EG.name(v));
        StdDraw.show(100);
        for (int w : G.adj(v))
            if (!marked[w])
            {
                edgeTo[w] = v;
                StdDraw.setPenRadius(0.003);
                EG.drawLine(EG.name(v), EG.name(w));
                StdDraw.show(100);
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
        int N = 1000;
        RandomEuclideanGraph REG = new RandomEuclideanGraph(N);
        REG.setThreshold(0.1);
        REG.generate();
        DepthFirstPathsDemo dfp = new DepthFirstPathsDemo(REG.egOut(), StdRandom.uniform(0, N));
    }
}


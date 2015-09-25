/**
 * Created by pmg on 2015/9/8.
 */

public class DepthFirstPathsDirectedDemo
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    private EuclideanDigraph EG;
    public DepthFirstPathsDirectedDemo(EuclideanDigraph EG, int s)
    {
        this.EG = EG;
        this.EG.draw();
        Digraph G = EG.G();
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        StdDraw.setPenColor(StdDraw.RED);
        dfs(G, s);
    }

    private void dfs(Digraph G, int v)
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
        int N = 100;
        RandomEuclideanDigraph REG = new RandomEuclideanDigraph(N);
        REG.setThreshold(0.2);
        REG.generate();

        EuclideanDigraph EG = REG.egOut();
        int source = StdRandom.uniform(0, N);
        DepthFirstPathsDirectedDemo dfp = new DepthFirstPathsDirectedDemo(REG.egOut(), source);
        int vPre = 0;
        int target = StdRandom.uniform(0, N);
        StdOut.println("From point " + source + " (" + EG.name(source) + ")" + " to point " + target + " (" + EG.name(target) + ")." );
        StdDraw.setPenColor(StdDraw.BLUE);
        int i = 0;
        if (!dfp.hasPathTo(target)) {
            StdOut.println("No path from " + source + " to " + target);
            return;
        }
        for (int v : dfp.pathTo(target))  // show the find path.(should be the shortest).
        {

            StdDraw.setPenRadius(0.012);
            EG.drawPoint(EG.name(v));
            if (v != source) {
                StdDraw.setPenRadius(0.004);
                EG.drawLine(EG.name(vPre), EG.name(v));
                StdOut.print(" - " + v + "(" + EG.name(v) + ")");
            } else StdOut.print(v + "(" + EG.name(v) + ")");
            StdDraw.show(500);
            vPre = v;
            if (i == 8) { StdOut.println(); i = 0; }
            i++;
        }
    }
}


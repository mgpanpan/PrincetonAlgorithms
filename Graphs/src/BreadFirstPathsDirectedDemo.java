/**
 * Created by pmg on 2015/9/9.
 */
public class BreadFirstPathsDirectedDemo
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    private EuclideanDigraph EG;
    public BreadFirstPathsDirectedDemo(EuclideanDigraph EG, int s)
    {
        this.EG = EG;
        this.EG.draw();
        Digraph G = EG.G();
        StdDraw.setPenColor(StdDraw.RED);
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Digraph G, int v)
    {
        marked[v] = true;
        StdDraw.setPenRadius(0.011);
        EG.drawPoint(EG.name(v));
        StdDraw.show(100);

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
                    StdDraw.setPenRadius(0.011);
                    EG.drawPoint(EG.name(w));
                    StdDraw.setPenRadius(0.003);
                    EG.drawLine(EG.name(x), EG.name(w));
                    StdDraw.show(100);
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
        int N = 100;
        RandomEuclideanDigraph REG = new RandomEuclideanDigraph(N);
        REG.setThreshold(0.21);
        REG.generate();
        EuclideanDigraph EG = REG.egOut();
        int source = StdRandom.uniform(0, N);
        BreadFirstPathsDirectedDemo bfp = new BreadFirstPathsDirectedDemo(EG, source);
        int vPre = 0;
        int target = StdRandom.uniform(0, N);
        StdOut.println("From point " + source + " (" + EG.name(source) + ")" + " to point " + target + " (" + EG.name(target) + ")." );
        StdDraw.setPenColor(StdDraw.BLUE);
        int i = 0;
        if (!bfp.hasPathTo(target)) {
            StdOut.println("No path from " + source + " to " + target);
            return;
        }
        for (int v : bfp.pathTo(target))  // show the find path.(should be the shortest).
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
            if (i == 6) { StdOut.println(); i = 0; }
            i++;
        }
    }
}

/**
 * Created by pmg on 2015/9/9.
 */
public class BreadFirstPathsDemo
{
    private boolean[] marked;
    private int[] edgeTo;     // *last* vertex on known path to this vertex
    private int s;
    private EuclideanGraph EG;
    public BreadFirstPathsDemo(EuclideanGraph EG, int s)
    {
        this.EG = EG;
        this.EG.draw();
        Graph G = EG.G();
        StdDraw.setPenColor(StdDraw.RED);
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int v)
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
                    EG.drawLine(EG.name(v), EG.name(w));
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
        int N = 200;
        RandomEuclideanGraph REG = new RandomEuclideanGraph(N);
        REG.setThreshold(0.06);
        REG.generate();
        BreadFirstPathsDemo bfp = new BreadFirstPathsDemo(REG.egOut(), StdRandom.uniform(0, N));
    }
}

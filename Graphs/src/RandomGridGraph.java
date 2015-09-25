/**
 * Created by pmg on 2015/9/21.
 */
public class RandomGridGraph
{
    private EuclideanGraph eg;
    private int N;

    public RandomGridGraph(int N)
    { this.N = N; }

    public void generate()
    {
        Out buff = new Out("buff.txt");
        // 将grid的点和坐标关联
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                double x = j;
                double y = N - 1 - i;
                buff.printf("%.4f, %.4f\n", x, y);
            }
        buff.close();

        eg = new EuclideanGraph("buff.txt", "/");
    }

    public EuclideanGraph graphOut()
    { return eg; }

    public static void main(String[] args)
    {
        int N = 10;
        RandomGridGraph gen = new RandomGridGraph(N);
        gen.generate();
        Graph G = gen.graphOut().G();
        gen.graphOut().draw();
        for (RandomGrid.Connection item : RandomGrid.generate(N))
        {
            CC cc = new CC(G);
            if (cc.count() == 1) { StdOut.println("Connected!!"); break; }
            G.addEdge(item.p, item.q);
            gen.graphOut().draw();
        }
    }
}

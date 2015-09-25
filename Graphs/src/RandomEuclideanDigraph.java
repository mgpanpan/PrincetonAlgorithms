/**
 * Created by pmg on 2015/9/20.
 */
public class RandomEuclideanDigraph
{
    private double xMin = 0.0;

    private double xMax = 1.0;
    private double yMin = 0.0;
    private double yMax = 1.0;
    private double threshold = 0.1;
    private EuclideanDigraph eg;
    private int V;

    public RandomEuclideanDigraph(int V)
    { this.V = V; }

    public void generate()
    {
        Out buff = new Out("buff.txt");
        for (int i = 0; i < V; i++)
        {
            double x = StdRandom.uniform() * (xMax - xMin) + xMin;
            double y = StdRandom.uniform() * (yMax - yMin) + yMin;
            buff.printf("%.4f, %.4f\n", x, y);
        }
        buff.close();
        eg = new EuclideanDigraph("buff.txt", "/");
        Digraph G = eg.G();
        /* method1, 某条边可能是双向的 */
        /*
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                if (i != j)
                    if (distance(eg.name(i), eg.name(j)) < threshold)
                        if (StdRandom.bernoulli())    // random orient
                            G.addEdge(i, j);
                            */
        /* method2 一定是单向的 */

        for (int i = 0; i < V; i++)
            for (int j = i + 1; j < V; j++)
                if (distance(eg.name(i), eg.name(j)) < threshold)
                        if (StdRandom.bernoulli())    // random orient
                            G.addEdge(i, j);
                        else G.addEdge(j, i);
    }

    private double distance(String start, String end)
    {
        String[] line1 = start.split(",");
        double x1 = Double.parseDouble(line1[0]), y1 = Double.parseDouble(line1[1]);
        String[] line2 = end.split(",");
        double x2 = Double.parseDouble(line2[0]), y2 = Double.parseDouble(line2[1]);
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public EuclideanDigraph egOut()
    { return eg; }

    public double getxMin()
    { return xMin; }
    public void setxMin(double xMin)
    { this.xMin = xMin; }

    public double getxMax()
    { return xMax; }
    public void setxMax(double xMax)
    { this.xMax = xMax; }

    public double getyMin()
    { return yMin; }
    public void setyMin(double yMin)
    { this.yMin = yMin; }

    public double getyMax()
    { return yMax; }
    public void setyMax(double yMax)
    { this.yMax = yMax; }

    public double getThreshold()
    { return threshold; }
    public void setThreshold(double threshold)
    { this.threshold = threshold; }

    public static void main(String[] args)
    {
        int V = 20;
        RandomEuclideanDigraph reg = new RandomEuclideanDigraph(V);
        reg.setThreshold(0.2);
        /*
        reg.setxMin(-5.0);
        reg.setxMax(5.0);
        reg.setyMin(-5.0);
        reg.setyMax(5.0);
         */
        reg.generate();
        reg.egOut().draw();
        /*CC cc = new CC(reg.egOut().G());
        if (cc.count() == 1) StdOut.println("Connected!!");
        else StdOut.println("Not Connected!!");*/
    }
}

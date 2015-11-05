/**
 * Created by pmg on 2015/9/20.
 */
public class RandomEuclideanEdgeWeightedGraph
{
    private double xMin = 0.0;

    private double xMax = 1.0;
    private double yMin = 0.0;
    private double yMax = 1.0;
    private double threshold = 0.1;
    private EuclideanEdgeWeightedGraph eg;
    private int V;

    public RandomEuclideanEdgeWeightedGraph(int V)
    { this.V = V; }

    public void generate()
    {
        ST<String, Integer> st = new ST<String, Integer>();
        String[] points = new String[V];
        for (int i = 0; i < V; i++)
        {
            double x = StdRandom.uniform() * (xMax - xMin) + xMin;
            double y = StdRandom.uniform() * (yMax - yMin) + yMin;
            points[i] = String.format("%.4f, %.4f", x, y);
        }
        Out out = new Out("buff.txt");
        for (int i = 0; i < V; i++)
            for (int j = i + 1; j < V; j++) {    // simple graph, not including self-loop and parallel edge
                double dis = distance(points[i], points[j]);
                if (dis < threshold) out.println(points[i] + "/" + points[j] + "/" + dis);
            }
        out.close();
        eg = new EuclideanEdgeWeightedGraph("buff.txt", "/");
    }

    private double distance(String start, String end)
    {
        String[] line1 = start.split(",");
        double x1 = Double.parseDouble(line1[0]), y1 = Double.parseDouble(line1[1]);
        String[] line2 = end.split(",");
        double x2 = Double.parseDouble(line2[0]), y2 = Double.parseDouble(line2[1]);
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public EuclideanEdgeWeightedGraph egOut()
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
        int V = 1000;
        RandomEuclideanEdgeWeightedGraph reg = new RandomEuclideanEdgeWeightedGraph(V);
        reg.setThreshold(0.05);
        /*
        reg.setxMin(-5.0);
        reg.setxMax(5.0);
        reg.setyMin(-5.0);
        reg.setyMax(5.0);
         */
        reg.generate();
        reg.egOut().draw();
    }
}

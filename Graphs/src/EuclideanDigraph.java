/**
 * Created by pmg on 2015/9/20.
 */
public class EuclideanDigraph extends SymbolDigraph
{
    // private Draw canvas;
    // 这里假设EuclideanGraph的每个vertex是逗号分隔的坐标
    public EuclideanDigraph(String filename, String delim)
    {
        super(filename, delim);
        // canvas = new Draw();
        StdDraw.setCanvasSize(600, 600);
    }

    public void draw()
    {
        StdDraw.clear();
        Digraph G = G();
        double xMin = Double.POSITIVE_INFINITY, yMin = Double.POSITIVE_INFINITY,
                xMax = Double.NEGATIVE_INFINITY, yMax = Double.NEGATIVE_INFINITY;
        double[] x = new double[G.V()];
        double[] y = new double[G.V()];
        // first pass, read the points and decide the x, y limit of the figure
        for (int v = 0; v < G.V(); v++)
        {
            String[] xy = (name(v)).split(",");
            x[v] = Double.parseDouble(xy[0]);
            y[v] = Double.parseDouble(xy[1]);
            if (x[v] < xMin) xMin = x[v];
            if (x[v] > xMax) xMax = x[v];
            if (y[v] < yMin) yMin = y[v];
            if (y[v] > yMax) yMax = y[v];
        }
        double border = 0.05;
        double borderX = (xMax - xMin) * border;
        double borderY = (yMax - yMin) * border;
        StdDraw.setXscale(xMin - borderX, xMax + borderX);
        StdDraw.setYscale(yMin - borderY, yMax + borderY);
        // second pass, draw
        for (int v = 0; v < G.V(); v++)
        {
            StdDraw.setPenRadius(0.01);
            drawPoint(name(v));
            // canvas.point(x[v], y[v]);
            StdDraw.setPenRadius();
            for (int w : G.adj(v))
                drawLine(name(v), name(w));
        }
        StdDraw.show(100);
    }

    public void drawPoint(String coordinate)
    {
        String[] xy = coordinate.split(",");
        double x = Double.parseDouble(xy[0]);
        double y = Double.parseDouble(xy[1]);
        StdDraw.point(x, y);
    }

    public void drawLine(String coordinate1, String coordinate2)
    {
        String[] xy1 = coordinate1.split(",");
        double x1 = Double.parseDouble(xy1[0]);
        double y1 = Double.parseDouble(xy1[1]);
        String[] xy2 = coordinate2.split(",");
        double x2 = Double.parseDouble(xy2[0]);
        double y2 = Double.parseDouble(xy2[1]);
        // StdDraw.arrow(x1, y1, x2, y2);
        StdDraw.filledArrow(x1, y1, x2, y2);
    }

    public void addEdge(int v, int w)
    {
        Digraph G = G();
        G.addEdge(v, w);
        // canvas.line();
    }

    public static void main(String[] args)
    {
        StdOut.print("Input a filename and its delimiter: ");
        String filename = StdIn.readLine();
        String delimiter = StdIn.readLine();
        EuclideanDigraph eg = new EuclideanDigraph(filename, delimiter);
        eg.draw();
    }
}

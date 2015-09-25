/**
 * Created by pmg on 2015/8/12.
 */
public class VisualAccumulator
{
    private double total = 0.0;
    private int N = 0;

    public VisualAccumulator(int trials, double max)
    {
        StdDraw.setXscale(0, trials);
        StdDraw.setYscale(0, max);
        StdDraw.setPenRadius(.005);
    }

    public void addDataValue(double val)
    {
        N++;
        total += val;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(N, val);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(N, total / N);
    }

    public double mean()
    { return total / N; }

    public String toString()
    { return "Mean (" + N + " values): "
            + String.format("%7.5f", mean()); }

    public static void main(String[] args)
    {
        int T = StdIn.readInt();
        VisualAccumulator a = new VisualAccumulator(T, 1.0);
        for (int t = 0; t < T; t++)
            a.addDataValue(StdRandom.random());
        StdOut.println(a);
    }
}

/**
 * Created by ph on 2015/8/12.
 */
public class Accumulator
{
    private double total = 0.0;
    private int N = 0;

    public void addDataValue(double val)
    {
        N++;
        total += val;
    }

    public double mean()
    { return total / N; }

    public String toString()
    { return "Mean (" + N + " values): "
            + String.format("%7.5f", mean()); }

    public static void main(String[] args)
    {
        int T = StdIn.readInt();
        Accumulator a = new Accumulator();
        for (int t = 0; t < T; t++)
            a.addDataValue(StdRandom.random());
        StdOut.println(a);
    }
}

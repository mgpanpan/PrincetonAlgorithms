/**
 * Created by pmg on 2015/9/10.
 */
public class SortRunningTimesPlot
{
    public static void main(String[] args)
    {
        StdOut.print("Input the sorting algorithm name: ");
        String alg = StdIn.readString();
        int T = 10; // experiment times
        double t = 0.0;
        double N;
        Draw d = new Draw();
        d.setXscale(1, 6);
        d.setYscale(0, 0.6);
        for (N = 1; N < 6; N += 0.1)
        {
            t = SortCompare.timeRandomInput(alg, (int)Math.round(Math.pow(10.0, N)), T);
            d.filledRectangle(N, t / 2, 0.04, t / 2);
        }
    }
}

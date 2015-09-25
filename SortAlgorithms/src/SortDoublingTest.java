/**
 * Created by pmg on 2015/9/10.
 */
public class SortDoublingTest
{
    public static void main(String[] args)
    {
        StdOut.print("Input the sorting algorithm name: ");
        String alg = StdIn.readString();
        double tPrev = 0.0;
        for (int N = 1000; true; N *= 2)
        {
            double t = SortCompare.timeRandomInput(alg, N, 1);
            StdOut.printf("N = %d, escaped time: %.6f, t/tPrev = %.6f\n", N, t, t / tPrev);
            tPrev = t;
        }
    }
}

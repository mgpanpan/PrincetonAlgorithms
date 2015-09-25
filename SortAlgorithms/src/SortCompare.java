/**
 * Created by pmg on 2015/9/8.
 */
public class SortCompare
{
    /**
     * time of executing the specific sorting algorithm
     * @param alg name of the sorting algorithm
     * @param a
     * @return time of executing the specific sorting algorithm
     */
    public static double time(String alg, Double[] a)
    {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Bubble"))    Bubble.sort(a);
        if (alg.equals("Selection")) Selection.sort(a);
        if (alg.equals("Insertion")) Insertion.sort(a);
        if (alg.equals("Merge"))     Merge.sort(a);
        if (alg.equals("Heap"))      HeapSort.sort(a);
        if (alg.equals("Shell"))     Shell.sort(a);
        if (alg.equals("InsertionWithoutExch")) InsertionWithoutExch.sort(a);

        return timer.elapsedTime();
    }

    /**
     * average time of sorting the randomly generated array using the specific algorithm
     * @param alg name of the sorting algorithm
     * @param N array size
     * @param T experiment times
     * @return average time of sorting the randomly generated array using the specific algorithm
     */
    public static double timeRandomInput(String alg, int N, int T)
    {
        Double[] a = new Double[N];
        double total = 0.0;
        for (int t = 0; t < T; t++)
        {
            for (int n = 0; n < N; n++)
                a[n] = StdRandom.uniform();
            total += time(alg, a);
        }
        return total / T;
    }

    public static void main(String[] args)
    {
        StdOut.print("Input algorithm numbers considered here: ");
        int algNum = StdIn.readInt();
        String[] alg = new String[algNum];
        for (int i = 0; i < algNum; i++)
        {
            StdOut.print("Input the sorting algorithm name: ");
            alg[i] = StdIn.readString();
        }
        StdOut.print("Input array size: ");
        int N = StdIn.readInt();
        StdOut.print("Input experiment times: ");
        int T = StdIn.readInt();
        double[] t = new double[algNum];
        for (int i = 0; i < algNum; i++)
            t[i] = timeRandomInput(alg[i], N, T);
        StdOut.println("For " + N + " random Doubles: ");

        for (int i = 0; i < algNum; i++)
            StdOut.printf("%s takes %.6f seconds\n", alg[i], t[i]);
    }
}

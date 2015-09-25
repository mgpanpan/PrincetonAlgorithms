/**
 * Created by pmg on 2015/7/14.
 */
public class HeapSort
{
    static int[] flag;
    static Draw d;

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        flag = new int[N];
        for (int k = 0; k < N; k++)
            flag[k] = 1;

        d = new Draw();
        d.setXscale(0, N);
        d.setYscale(0, 1);

        /* Sink-based Heap construction */
        show(a);
        for (int k = N / 2; k >= 1; k--)
        {
            for (int i = 0; i < a.length; i++)
                flag[i] = 0;
            sink(a, k, N);
        }
        d.text(0.5 * N, 1, "Heap Construction Completed");
        d.show(3000);

        /* sort down */
        while (N > 1)
        {
            for (int k = 0; k < a.length; k++)
                flag[k] = 0;
            exch(a, 1, N--);
            sink(a, 1, N);    // keep in heap order
        }
    }

    private static void show(Comparable[] a)
    {
        d.clear();
        for (int i = 0; i < a.length; i++)
        {
            if (flag[i] == 0)      d.setPenColor(Draw.GRAY);
            else if (flag[i] == 1) d.setPenColor(Draw.BLACK);
            else if (flag[i] == 2) d.setPenColor(Draw.RED);
            d.filledRectangle(i, (Double) a[i] / 2.0, 0.3, (Double)a[i] / 2.0);
        }
        d.show(1000);
    }

    private static void sink(Comparable[] a, int k, int N)
    {
        while (2 * k <= N)
        {
            flag[k-1] = 2;
            show(a);
            int j = 2 * k;
            if (j < N && less(a, j, j+1)) j++;
            flag[j-1] = 1;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            flag[k-1] = 1;
            flag[j-1] = 2;
            k = j;
        }
        show(a);
    }

    private static void exch(Comparable[] a, int i, int j)
    { Comparable tmp = a[i-1]; a[i-1] = a[j-1]; a[j-1] = tmp; }

    private static boolean less(Comparable[] a, int i, int j)
    { return a[i-1].compareTo(a[j-1]) < 0; }

    private static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
            if (less(a, i+1, i)) return false;
        return true;
    }

    public static void main(String[] args)
    {
        StdOut.print("Input the size of the array: ");
        int N = StdIn.readInt();
        Double[] a = new Double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform();
        sort(a);
    }
}

/**
 * Created by pmg on 2015/7/14.
 */
public class HeapSort
{
    static int times = 0; // count plot figures
    static int totalFigNum1; // number of figures in the progress of heap construction
    static int totalFigNum2; // number of figures in the progress of sorting down
    static int[] flag;
    static Draw d1;
    static Draw d2;

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        flag = new int[N];
        for (int k = 0; k < N; k++)
            flag[k] = 1;

        d1 = new Draw("Heap Construction Progress");
        d1.setCanvasSize(600, 650);
        totalFigNum1 = 1+ N / 2;
        d1.setYscale(0, 1.2 * totalFigNum1);
        d1.setXscale(0, N);

        /* Sink-based Heap construction */
        show(a, d1, times++, totalFigNum1, "input");

        for (int k = N / 2; k >= 1; k--)
        {
            for (int i = 0; i < a.length; i++)
                flag[i] = 0;
            sink(a, k, N);
            show(a, d1, times++, totalFigNum1, "sink " + k);
        }

        d2 = new Draw("Sort Down progress");
        d2.setCanvasSize(600, 650);
        totalFigNum2 = N;
        d2.setYscale(0, 1.2 * totalFigNum2);
        d2.setXscale(0, N);
        /* sort down */
        times = 0;
        for (int k = 0; k < a.length; k++)
            flag[k] = 0;
        show(a, d2, times++, totalFigNum2, "heap input");
        while (N > 1)
        {
            for (int k = 0; k < a.length; k++)
                flag[k] = 0;
            exch(a, 1, N--);
            sink(a, 1, N);    // keep in heap order
            show(a, d2, times++, totalFigNum2, "");
        }
    }

    private static void show(Comparable[] a, Draw d, int figNum, int totalFigNum, String text) // figNum : 0, 1, ...totalFigNum-1
    {
        int N = a.length;
        double yShift = (totalFigNum - 1 - figNum) * 1.2;
        d.line(0, yShift, N, yShift);   // draw x axis
        for (int i = 0; i < N; i++)
        {
            if (flag[i] == 0)     d.setPenColor(Draw.GRAY);
            else if (flag[i] == 1) d.setPenColor(Draw.BLACK);
            else if (flag[i] == 2) d.setPenColor(Draw.RED);
            d.filledRectangle(i, yShift + (Double)a[i] / 2.0, 0.3, (Double)a[i] / 2.0);
        }
        d.setPenColor();
        d.text(0, yShift + 1.0, text);
    }

    private static void sink(Comparable[] a, int k, int N)
    {
        while (2 * k <= N)
        {
            flag[k-1] = 2;
            int j = 2 * k;
            if (j < N && less(a, j, j+1)) j++;
            flag[j-1] = 1;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            flag[k-1] = 1;
            flag[j-1] = 2;
            k = j;
        }
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

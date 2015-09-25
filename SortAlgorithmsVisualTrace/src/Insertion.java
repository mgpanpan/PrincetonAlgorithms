/**
 * Created by pmg on 2015/9/7.
 */
public class Insertion
{
    static Draw d;
    static int[] flag;
    public static void sort(Comparable[] a)
    {
        int N = a.length;
        d = new Draw();
        d.setCanvasSize(400, 600);
        d.setXscale(0, N);
        d.setYscale(0, 1.2 * N); // generated random number are in [0, 1), so the highest of the histogram is 1.0, and keep a 0.2 width blank.

        for (int i = 0; i < N; i++)
        {
            flag = new int[N];  // 0 : untouched, 1 : touched, 2 : current element

            // do not need explicitly initialize flags to 0, because the default elements of new int array are all zeros
            // for (int k = 0; k < N; k++)
            //     flag[k] = 0;

            int j = i;
            for (; j > 0 && less(a[j], a[j-1]); j--)
            {
                exch(a, j, j - 1);
                flag[j] = 1;
            }
            flag[j] = 2;
            show(a, i, N);
        }
    }

    private static void show(Comparable[] a, int figNum, int totalFigNum) // figNum : 0, 1, ...totalFigNum-1
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
    }

    private static boolean less(Comparable a, Comparable b)
    { return a.compareTo(b) < 0; }

    private static void exch(Comparable[] a, int i, int j)
    { Comparable tmp = a[i]; a[i] = a[j]; a[j] = tmp; }

    private static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
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
        assert isSorted(a);
    }
}

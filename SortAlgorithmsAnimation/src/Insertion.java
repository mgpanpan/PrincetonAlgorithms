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
        d.setXscale(0, N);
        d.setYscale(0, 1);
        d.setPenRadius(0.2);

        for (int i = 0; i < N; i++)
        {
            flag = new int[N];  // 0 : untouched, 1 : touched, 2 : current item
            int j = i;
            for (; j > 0 && less(a[j], a[j - 1]); j--)
            { exch(a, j, j - 1); flag[j] = 1; }
            flag[j] = 2;
            show(a);
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
            d.filledRectangle(i, (Double)a[i] / 2.0, 0.3, (Double)a[i] / 2.0);
        }
        d.show(500); // turn on animation mode
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

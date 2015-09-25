/**
 * Created by pmg on 2015/9/7.
 */
public class Selection
{
    private static Draw d;
    private static int[] flag;

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        d = new Draw();
        d.setXscale(0, N);
        d.setYscale(0, 1);

        for (int i = 0; i < N; i++)
        {
            flag = new int[N];
            int min = i;
            flag[i] = 1;
            for (int j = i+1; j < N; j++)
            {
                flag[j] = 1;
                if (less(a[j], a[min])) min = j;
            }
            flag[min] = 2;
            show(a);
            exch(a, i, min);
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
    }
}

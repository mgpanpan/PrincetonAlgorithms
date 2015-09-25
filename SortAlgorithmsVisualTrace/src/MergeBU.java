/**
 * Created by pmg on 2015/9/12.
 */
public class MergeBU
{
    private static Draw d;
    private static int[] flag;
    public static void sort(Comparable[] a)
    {
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        d = new Draw();
        d.setXscale(0, N);
        d.setYscale(0, 1);
        flag = new int[N];

        int totalFigNum = (int)(Math.ceil(Math.log(N) / Math.log(2))) + 1;   // total figure number
        int k = 0;  // count plot figures
        d.setYscale(0, 1.2 * totalFigNum);
        d.setXscale(0, N);
        show(a, k, totalFigNum, "input");

        for (int sz = 1; sz < N; sz += sz)
        {
            k++;
            for (int j = 0; j < N - sz; j += sz + sz)
                merge(a, aux, j, j + sz - 1, Math.min(j + sz + sz - 1, N - 1));
            show(a, k, totalFigNum, "sz = " + sz);
        }
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
            if (i > mid)               a[k] = aux[j++];
            else if (j > hi)           a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else                       a[k] = aux[j++];
    }

    private static void show(Comparable[] a, int figNum, int totalFigNum, String text) // figNum : 0, 1, ...totalFigNum-1
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

    private static boolean less(Comparable a, Comparable b)
    {  return a.compareTo(b) < 0;  }

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

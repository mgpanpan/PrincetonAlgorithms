import javax.naming.CompositeName;

/**
 * Created by pmg on 2015/9/9.
 */
public class Shell
{
    private static Draw d;
    private static int[] flag;

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        d = new Draw();
        d.setCanvasSize(1000, 600);
        flag = new int[N];
        for (int i = 0; i < N; i++)
            flag[i] = 1;

        int h = 1;
        int k = 0; // count plot figures
        int totalFigNum = 2;   // total figure number, each value of h corresponds to one, plus the origin one.
        while (h < N / 3) { h = 3 * h + 1; totalFigNum++; }
        d.setYscale(0, 1.2 * totalFigNum);
        d.setXscale(0, N);
        show(a, k, totalFigNum, "input");
        while (h >= 1)
        {
            for (int i = h; i < N; i++)
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
            k++;
            show(a, k, totalFigNum, h + "-sorted");
            h = h / 3;
        }
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

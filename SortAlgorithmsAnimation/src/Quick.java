public class Quick
{
    static Integer[] flag;
    static Draw d;

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        flag = new Integer[N];
        d = new Draw();
        d.setXscale(0, N);
        d.setYscale(0, 1);
        StdRandom.shuffle(a);    // Eliminate dependence on input
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi)
    {
        if (lo >= hi) return;
        for (int i = lo ; i <= hi; i++)
            flag[i] = 0;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi)
    {
        Comparable v = a[lo];
        flag[lo] = 3;
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], v)) {
                flag[i] = 1; // left scan pointer
                show(a);
                if (i >= hi) break;
                flag[i] = 0; // reset
            }
            flag[i] = 1;
            show(a);
            while (less(v, a[--j])) {
                flag[j] = 2; // right scan pointer
                show(a);
                if (j <= lo) break;
                if (j != i) flag[j] = 0; // reset 不要覆盖i的标志色
            }
            flag[j] = 2;
            show(a);
            if (i >= j) break;
            exch(a, i, j);
            exch(flag, i, j);
            show(a);
        }
        exch(a, lo, j);
        exch(flag, lo, j);
        show(a);
        return j;
    }

    private static void show(Comparable[] a)
    {
        d.clear();
        for (int i = 0; i < a.length; i++)
        {
            if (flag[i] == 0)      d.setPenColor(Draw.GRAY);
            else if (flag[i] == 1) d.setPenColor(Draw.BLACK);
            else if (flag[i] == 2) d.setPenColor(Draw.BLUE);
            else if (flag[i] == 3) d.setPenColor(Draw.RED);
            d.filledRectangle(i, (Double)a[i] / 2.0, 0.3, (Double)a[i] / 2.0);
        }
        d.show(2000);
    }

    private static boolean less(Comparable a, Comparable b)
    {  return a.compareTo(b) < 0;  }

    private static void exch(Comparable[] a, int i, int j)
    { Comparable tmp = a[i]; a[i] = a[j]; a[j] = tmp; }

    private static boolean isSorted(double[] a)
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
        Double[] b = new Double[N];
        for (int i = 0; i < N; i++)
            b[i] = StdRandom.uniform();
        sort(b);
    }
}
public class Merge
{
    static int times = 0; // count plot figures
    static int totalFigNum; // total figure number
    private static Draw d;
    private static int[] flag;
    private static final int gate = 8; // only display merge operation that >= gate.

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        d = new Draw();
        flag = new int[N];

        totalFigNum = (int)Math.pow(2, Math.ceil(Math.log(N / gate) / Math.log(2)));  // total figure number
        d.setYscale(0, 1.2 * totalFigNum);
        d.setXscale(0, N);
        show(a, times++, totalFigNum, "input");

        sort(a, aux, 0, N-1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        for (int k = lo; k <= hi; k++)
            flag[k] = 1;

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid)                   a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
        if (hi - lo >= 8)
        {
            show(a, times, totalFigNum, "");
            times++;
        }
        for (int k = 0; k < a.length; k++) // reset
            flag[k] = 0;
    }

    private static boolean less(Comparable a, Comparable b)
    {  return a.compareTo(b) < 0;  }

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
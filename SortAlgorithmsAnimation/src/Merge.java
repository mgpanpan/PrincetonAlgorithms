public class Merge
{
    static int[] flag;
    static Draw d;

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        d = new Draw();
        d.setXscale(0, N);
        d.setYscale(0, 1);
        Comparable[] aux = new Comparable[N];
        sort(a, aux, 0, N-1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (lo >= hi) return;
        flag = new int[a.length]; // reset

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            flag[k] = 1;

        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid)                   a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
        show(a);
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
        d.show(500);
    }

    private static boolean less(Comparable a, Comparable b)
    {  return a.compareTo(b) < 0;  }

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
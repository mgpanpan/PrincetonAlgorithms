import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pmg on 2015/9/17.
 */
public class MaxPQ implements Iterable<Comparable>
{
    /// Note! the heap operation count the array from 1, but the items in the array
    /// are placed from index 0. The less and exch helper function used here is
    // sightly different from those in the sorting algorithms.
    private Comparable[] pq;
    private int N;

    public MaxPQ()
    {
        pq = new Comparable[1];
        N = 0;
    }

    public MaxPQ(int maxNum)
    {
        pq = new Comparable[maxNum];
        N = 0;
    }

    public MaxPQ(Comparable[] a)
    {
        int N = a.length;
        pq = new Comparable[N];
        for (int i = 0; i < N; i++)
            pq[i] = a[i];
        this.N = N;
        for (int i = N / 2; i >= 1; i--)
            sink(pq, i, N);
    }

    public Comparable delMax()
    {
        Comparable max = get(pq, 1);
        exch(pq, 1, N);
        put(pq, N, null);
        sink(pq, 1, --N);
        if (N > 0 && N == pq.length  / 4) resizing(pq.length / 2);
        return max;
    }

    public void insert(Comparable item)
    {
        if (N == pq.length) resizing(pq.length * 2);
        put(pq, ++N, item);
        swim(pq, N);
    }

    private int size()
    { return N; }

    private boolean isEmpty()
    {return N == 0; }

    private void swim(Comparable[] a, int k)
    {
        while (k > 1 && less(a, k / 2, k))
        {
            exch(a, k, k / 2);
            k = k / 2;
        }
    }

    private void sink(Comparable[] a, int k, int N)
    {
        while (k <= N / 2)
        {
            int j = k * 2;
            if (j < N && less(a, j, j+1)) j++;
            if (less(a, k, j)) { exch(a, k, j); k = j; }
            else return;
        }
    }

    private void resizing(int capacity)
    {
        Comparable[] copy = new Comparable[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = pq[i];
        pq = copy;
    }

    // using these helper functions, we can operate an array seems that it starts from index 1.
    private boolean less(Comparable[] a, int i, int j)
    { return a[i-1].compareTo(a[j-1]) < 0; }

    private void exch(Comparable[] a, int i, int j)
    { Comparable tmp = a[i-1]; a[i-1] = a[j-1]; a[j-1] = tmp; }

    private Comparable get(Comparable[] a, int i)
    { return a[i-1]; }

    private void put(Comparable[] a, int i, Comparable val)
    { a[i-1] = val; }

    public Iterator<Comparable> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Comparable>
    {
        private MaxPQ copy;

        public HeapIterator()
        {
            copy = new MaxPQ(size());
            for (int i = 0; i < N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()
        {
            return !copy.isEmpty();
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Comparable next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }

    public static void main(String[] args)
    {
        /// 测试方法：产生长度为N的随机数（模拟流），找出其中最小的M个数
        /// 使用长度为M+1的MaxPQ，将随机数依次Insert，当MaxPQ的长度超过
        /// M时，调用DelMax去掉其中最大的数，从而流通过后，MaxPQ中存放的
        /// 就是流中最小的M个数，再用DelMax，并逆序输出（用Stack），
        /// 得到最小M个数的升序输出，和直接排序的结果对比。
        // StdOut.print("Input the stream length and number: ");
        int N = 10000;
        int M = 20;

        MaxPQ pq = new MaxPQ(M + 1);
        Comparable[] randomInput = new Comparable[N];
        Comparable[] pqReversedOut = new Comparable[M];

        for (int i = 0; i < N; i++)
        {
            randomInput[i] = (int)(StdRandom.uniform() * N);
            pq.insert(randomInput[i]);
            if (pq.size() > M) pq.delMax();
        }

        StdOut.println("First 10 samples of input: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();

        StdOut.println("Test the iterator: ");
        for (Comparable item : pq)
            StdOut.print(item + " ");
        StdOut.println();

        Stack<Integer> stk = new Stack<Integer>();
        while (!pq.isEmpty()) stk.push((Integer)pq.delMax());

        int k = 0;
        for (int item : stk)
            pqReversedOut[k++] = item;
        StdOut.println("First 10 samples of M Maximum items in the stream collected by MaxPQ(by successively delete maximum): ");
        for (int i = 0; i < 10; i++)
            StdOut.print(pqReversedOut[i] + " ");
        StdOut.println();

        MergeBU.sort(randomInput);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();

        boolean flag = true;
        for (k = 0; k < M; k++)
            if (pqReversedOut[k].compareTo(randomInput[k]) != 0) { flag = false; break; }
        if (flag) StdOut.println("Random input test passed!");
        else StdOut.println("Random input test NOT passed!");
    }
}

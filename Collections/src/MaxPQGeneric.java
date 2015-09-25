import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pmg on 2015/7/14.
 */
public class MaxPQGeneric<Key extends Comparable<Key>> implements Iterable<Key>
{
    private Key[] pq;
    private int N;

    public MaxPQGeneric()
    {
        pq = (Key[]) new Comparable[1];
        N = 0;
    }

    public MaxPQGeneric(int maxNum)
    {
        pq = (Key[]) new Comparable[maxNum+1];
        N = 0;
    }

    public MaxPQGeneric(Key[] a)
    {
        N = a.length;
        pq = (Key[]) new Comparable[N+1];
        for (int i = 0; i < a.length; i++)
            pq[i+1] = a[i];
        for (int k = N/2; k >= 1; k--)
            sink(k);
    }

    public boolean isEmpty()
    { return N == 0; }

    public int size()
    { return N; }

    public void insert(Key key)
    {
        if (N+1 >= pq.length) resizing(2 * pq.length);
        pq[++N] = key;
        swim(N);
    }

    public Key delMax()
    {
        Key max = pq[1];
        exch(1, N--);
        pq[N+1] = null; // avoid loitering and help with gc
        sink(1);
        if (N > 0 && N == (pq.length - 1) / 4) resizing(pq.length / 2);
        return max;
    }

    public Key max()
    { return pq[1]; }

    private void sink(int k)
    {
        while (2 * k <= N)
        {
            int j = 2 * k;
            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private void swim(int k)
    {
        while (k > 1 && less(k/2, k))
        {
            exch(k, k/2);
            k = k / 2;
        }
    }

    private void resizing(int capacity)
    {
        Key[] copy = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++)
            copy[i] = pq[i];
        pq = copy;
    }

    private boolean less(int i, int j)
    { return pq[i].compareTo(pq[j]) < 0; }

    private void exch(int i, int j)
    { Key tmp = pq[i]; pq[i] = pq[j]; pq[j] = tmp; }

    public Iterator<Key> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Key>
    {
        private MaxPQGeneric<Key> copy;
        public HeapIterator()
        {
            copy = new MaxPQGeneric<Key>(size());
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()
        { return !copy.isEmpty(); }

        public void remove()
        { throw new UnsupportedOperationException(); }

        public Key next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }
    /**
     *  Unit tests
     */
    public static void main(String[] args)
    {
        /**
        MaxPQ<String> pq = new MaxPQ<String>();
        In in = new In("test.txt");
        while (!in.isEmpty())
        {
            String item = in.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
         */
        /// 测试方法：产生长度为N的随机数（模拟流），找出其中最小的M个数
        /// 使用长度为M+1的MaxPQ，将随机数依次Insert，当MaxPQ的长度超过
        /// M时，调用DelMax去掉其中最大的数，从而流通过后，MaxPQ中存放的
        /// 就是流中最小的M个数，再用DelMax，并逆序输出（用Stack），
        /// 得到最小M个数的升序输出，和直接排序的结果对比。
        // StdOut.print("Input the stream length and number: ");
        int N = 10000;
        int M = 10;

        MaxPQGeneric<Integer> pq = new MaxPQGeneric<Integer>(M + 1);
        Integer[] randomInput = new Integer[N];
        Integer[] pqReversedOut = new Integer[M];

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
        for (int item : pq)
            StdOut.print(item + " ");
        StdOut.println();

        Stack<Integer> stk = new Stack<Integer>();
        while (!pq.isEmpty()) stk.push((Integer)pq.delMax());

        int k = 0;
        for (int item : stk)
            pqReversedOut[k++] = item;
        StdOut.println("First 10 samples of M minimum items in the stream collected by MaxPQ(by successively delete maximum): ");
        for (int i = 0; i < 10; i++)
            StdOut.print(pqReversedOut[i] + " ");
        StdOut.println();

        MergeBU.sort(randomInput);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();

        Boolean flag = true;
        for (k = 0; k < M; k++)
            if (pqReversedOut[k].compareTo(randomInput[k]) != 0) { flag = false; break; }
        if (flag) StdOut.println("Random input test passed!");
        else StdOut.println("Random input test NOT passed!");
    }
}

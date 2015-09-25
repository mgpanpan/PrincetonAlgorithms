import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pmg on 2015/7/10.
 */
public class ResizingArrayQueue<Item> implements Iterable<Item>
{
    int N = 0;
    int head = 0, tail = 0;
    Item[] a = (Item[]) new Object[1];

    boolean isEmpty()
    {  return N == 0;  }

    int size()
    {  return N;  }

    private void resizing(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
        {
            copy[i] = a[head];
            head = (head + 1) % a.length;
        }
        a = copy;
        head = 0; tail = N;  // reset
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = a[head];
        a[head] = null;
        head = (head + 1) % a.length; N--;
        if (N > 0 && N == a.length / 4) resizing(a.length / 2);
        return item;
    }

    public void enqueue(Item item)
    {
        if (N == a.length) resizing(a.length * 2);
        a[tail] = item;
        tail = (tail + 1) % a.length; N++;
    }

    public Iterator<Item> iterator()
    {  return new SequenceIterator();  }

    private class SequenceIterator implements Iterator<Item>
    {
        int h = head, i = 0;
        public boolean hasNext()
        {  return i < N;  }
        public Item next()
        {
            Item item = a[h];
            h = (h + 1) % a.length;
            i++;
            return item;
        }
        public void remove() {}
    }

    public static void main(String[] args)
    {
        StdOut.println("------------ Test for Queue of String:");
        ResizingArrayQueue<String> queueStr = new ResizingArrayQueue<String>();
        In strfile = new In("str_test.txt");
        while (!strfile.isEmpty())
        {
            String item = strfile.readString();
            if (item.equals("-"))
                StdOut.print(queueStr.dequeue() + " ");
            else
                queueStr.enqueue(item);
        }
        StdOut.println();
        for (String s : queueStr)
            StdOut.print(s + " ");
        StdOut.println();

        StdOut.println("------------ Test for Queue of int:");
        ResizingArrayQueue<Integer> queueInt = new ResizingArrayQueue<Integer>();
        In intfile = new In("int_test.txt");
        while (!intfile.isEmpty())
        {
            int x = intfile.readInt();
            if (x == 0)
                StdOut.print(queueInt.dequeue() + " ");
            else
                queueInt.enqueue(x);
        }
        StdOut.println();

        Iterator<Integer> i = queueInt.iterator();
        while (i.hasNext())
        {
            StdOut.print(i.next() + " ");
        }
        StdOut.println();

        StdOut.println("------------ Test for Queue of Date:");
        ResizingArrayQueue<Date> queueDate = new ResizingArrayQueue<Date>();
        In datefile = new In("date_test.txt");
        while (!datefile.isEmpty())
        {
            String x = datefile.readString();
            queueDate.enqueue(new Date(x));
        }
        for (Date d : queueDate)
            StdOut.print(d + " ");
        StdOut.println();

        queueDate.dequeue();
        for (Date d : queueDate)
            StdOut.print(d + " ");
        StdOut.println();
    }
}

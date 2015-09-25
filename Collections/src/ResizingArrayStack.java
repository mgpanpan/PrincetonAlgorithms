import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pmg on 2015/7/10.
 */
public class ResizingArrayStack<Item> implements Iterable<Item>
{
    int N = 0;
    Item[] a = (Item[]) new Object[1];

    public boolean isEmpty()
    {  return N == 0;  }

    public int size()
    {  return N;  }

    private void resizing(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = a[i];
        a = copy;
    }

    public Item pop()
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = a[--N];
        a[N] = null;  // avoid loitering
        if (N > 0 && N == a.length / 4) resizing(a.length / 2);
        return item;
    }

    public void push(Item item)
    {
        if (N == a.length) resizing(a.length * 2);
        a[N++] = item;
    }

    public Iterator<Item> iterator()
    {  return new ReverseArrayIterator();  }

    private class ReverseArrayIterator implements Iterator<Item>
    {
        private int i = N;
        public boolean hasNext()
        {  return i > 0;  }
        public Item next()
        {  return a[--i];  }
        public void remove() {}
    }

    public static void main(String[] args)
    {
        StdOut.println("------------ Test for ResizingArrayStack of String:");
        ResizingArrayStack<String> stackStr = new ResizingArrayStack<String>();
        In strfile = new In("str_test.txt");
        while (!strfile.isEmpty())
        {
            String item = strfile.readString();
            if (item.equals("-"))
                StdOut.print(stackStr.pop() + " ");
            else
                stackStr.push(item);
        }
        StdOut.println();
        for (String s : stackStr)
            StdOut.print(s + " ");
        StdOut.println();

        StdOut.println("------------ Test for ResizingArrayStack of int:");
        ResizingArrayStack<Integer> stackInt = new ResizingArrayStack<Integer>();
        In intfile = new In("int_test.txt");
        while (!intfile.isEmpty())
        {
            int x = intfile.readInt();
            if (x == 0)
                StdOut.print(stackInt.pop() + " ");
            else
                stackInt.push(x);
        }
        StdOut.println();

        Iterator<Integer> i = stackInt.iterator();
        while (i.hasNext())
        {
            StdOut.print(i.next() + " ");
        }
        StdOut.println();

        StdOut.println("------------ Test for ResizingArrayStack of Date:");
        ResizingArrayStack<Date> stackDate = new ResizingArrayStack<Date>();
        In datefile = new In("date_test.txt");
        while (!datefile.isEmpty())
        {
            String x = datefile.readString();
            stackDate.push(new Date(x));
        }
        for (Date d : stackDate)
            StdOut.print(d + " ");
        StdOut.println();

        stackDate.pop();
        for (Date d : stackDate)
            StdOut.print(d + " ");
        StdOut.println();
    }
}

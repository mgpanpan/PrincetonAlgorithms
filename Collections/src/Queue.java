import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pmg on 2015/7/11.
 */
public class Queue<Item> implements Iterable<Item>
{
    private class Node
    {
        Item item;
        Node next;
    }

    Node first = null;
    Node last = null;
    int N = 0;

    public boolean isEmpty()
    { return N == 0; }

    public int size()
    { return N; }

    public void enqueue(Item item)
    {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        return item;
    }

    public Iterator<Item> iterator()
    { return new ListIterator(); }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        { return current != null; }

        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {}
    }

    public static void main(String[] args)
    {
        StdOut.println("------------ Test for Queue of String:");
        Queue<String> queueStr = new Queue<String>();
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
        Queue<Integer> queueInt = new Queue<Integer>();
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
        Queue<Date> queueDate = new Queue<Date>();
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

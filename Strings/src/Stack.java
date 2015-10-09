import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pmg on 2015/7/11.
 */
public class Stack<Item> implements Iterable<Item>
{
    private class Node
    {
        Item item;
        Node next;
    }

    Node top = null;
    int N = 0;

    public boolean isEmpty()
    { return N == 0; }

    public int size()
    { return N; }

    public void push(Item item)
    {
        Node newtop = new Node();
        newtop.item = item;
        newtop.next = top;
        top = newtop;
        N++;
    }

    public Item pop()
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = top.item;
        top = top.next;
        N--;
        return item;
    }

    public Iterator<Item> iterator()
    { return new ListIterator(); }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = top;

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
        StdOut.println("------------ Test for ResizingArrayStack of String:");
        Stack<String> stackStr = new Stack<String>();
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
        Stack<Integer> stackInt = new Stack<Integer>();
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
        /*
        Stack<Date> stackDate = new Stack<Date>();

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
         */
    }
}

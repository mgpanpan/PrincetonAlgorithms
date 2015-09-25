/**
 * Created by pmg on 2015/8/12.
 * unordered symbol table
 * java SequentialSearchLinkedListST
 *   tinyST.txt (ENTER)
 *   ...
 */

public class SequentialSearchLinkedListST<Key, Value>
{
    private Node first = null;
    private int N = 0;

    private int getNum = 0;  // Exercise 3.1.6, number of calls to get and put issued by FrequencyCounter
    private int putNum = 0;
    private int compareInPut = 0;

    public int getGetNum()
    { return getNum; }

    public int getPutNum()
    { return putNum; }

    public int getCompareInPut()
    { return compareInPut; }

    private class Node
    {   // linked-list node
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next)
        {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    int size()
    { return N; }

    boolean isEmpty()
    { return size() == 0; }

    boolean contains(Key key)
    { return get(key) != null; }

    public Value get(Key key)
    {
        getNum++;
        for (Node current = first; current != null; current = current.next)
            if (key.equals(current.key)) return current.val;
        return null;
    }

    public void put(Key key, Value val)
    {
        putNum++;
        compareInPut = 0;
        if (val == null) delete(key);
        for (Node current = first; current != null; current = current.next, compareInPut++)
            if (key.equals(current.key))
            { current.val = val; return; }
        first = new Node(key, val, first);
        N++;
    }

    public void delete(Key key)
    {
        if (isEmpty()) return;
        if (key.equals(first.key))
        { first = first.next; return; }

        for (Node head = first; head != null; head = head.next)
        {
            Node tail = head.next;
            if (tail == null) return;
            if (key.equals(tail.key))
            { head.next = tail.next; return; }
        }
    }

    public Iterable<Key> keys()
    {
        Queue<Key> q = new Queue<Key>();
        for (Node current = first; current != null; current = current.next)
            q.enqueue(current.key);
        return q;
    }

    public static void main(String[] args)
    {
        SequentialSearchLinkedListST<String, Integer> st = new SequentialSearchLinkedListST<String, Integer>();
        In in = new In(StdIn.readString()); // input file name

        for (int i = 0; !in.isEmpty(); i++)
        {
            String key = in.readString();
            st.put(key, i);
        }

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));

        /* test for delete */
        st.delete("H");
        StdOut.println("After deleting H");
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));

        st.delete("B"); // "B" is not in the ST
        StdOut.println("After deleting B");
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}

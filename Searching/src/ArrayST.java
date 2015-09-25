/**
 * Created by pmg on 2015/8/13.
 */
public class ArrayST<Key, Value>
{
    private Key[] keys = (Key[]) new Object[1];
    private Value[] vals = (Value[]) new Object[1];
    private int N = 0;

    private int numOfElementOperation = 0;

    public int getNumOfElementOperation()
    { return numOfElementOperation; }

    public void put(Key key, Value val)
    {
        numOfElementOperation = 0;
        if (val == null) { delete(key); return; }
        for (int i = 0; i < N; i++, numOfElementOperation++)
            if (key.equals(keys[i]))
            { vals[i] = val; return; }
        if (N == keys.length) resizing(2 * keys.length);
        keys[N] = key; vals[N] = val; N++;
    }

    public Value get(Key key)
    {
        for (int i = 0; i < N; i++)
            if (key.equals(keys[i]))
                return vals[i];
        return null;
    }

    public void delete(Key key)
    {
        for (int i = 0; i < N; i++)
            if (key.equals(keys[i]))
            {
                for (int j = i; j < N-1; j++)
                {
                    keys[j] = keys[j+1];
                    vals[j] = vals[j+1];
                }
                N--;
                if (N == keys.length / 4) resizing(keys.length / 2);
            }
    }

    private void resizing(int capacity)
    {
        Key[] keys_copy = (Key[]) new Object[capacity];
        Value[] vals_copy = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++)
        {
            keys_copy[i] = keys[i];
            vals_copy[i] = vals[i];
        }
        keys = keys_copy;
        vals = vals_copy;
    }

    boolean contains(Key key)
    { return get(key) != null; }

    boolean isEmpty()
    { return N == 0; }

    int size()
    { return N; }

    Iterable<Key> keys()
    {
        Queue<Key> q = new Queue<Key>();
        for (int i = 0; i < N; i++)
            q.enqueue(keys[i]);
        return q;
    }

    public static void main(String[] args)
    {
        ArrayST<String, Integer> st = new ArrayST<String, Integer>();
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

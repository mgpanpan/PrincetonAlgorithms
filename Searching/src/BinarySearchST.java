public class BinarySearchST<Key extends Comparable<Key>, Value>
{
    private Key[] keys;
    private Value[] vals;
    private int N;

    private int numOfElementOperation = 0;

    public int getNumOfElementOperation()
    { return numOfElementOperation; }

    public BinarySearchST()
    {
        keys = (Key[]) new Comparable[1];
        vals = (Value[]) new Object[1];
        N = 0;
    }

    public int size()
    {  return N;  }

    public boolean isEmpty()
    {  return size() == 0;  }

    public int rank(Key key)
    {
        int lo = 0, hi = N-1;
        while (lo <= hi)
        {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]); numOfElementOperation += 2;
            if      (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;    // key is in the symbol-table, return the position(array index) of the key
        }
        return lo;  // key not in the symbol-table, return the number of keys smaller than the key.
    }

    public Value get(Key key)
    {
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else                                      return null;
    }

    public void put(Key key, Value val)
    {
        numOfElementOperation = 0;
        if (val == null) { delete(key); return; }
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0)
        {  vals[i] = val; numOfElementOperation += 1; return;  }

        if (N == keys.length) resizing(N * 2);

        for (int j = N; j > i; j--)
        { keys[j] = keys[j-1]; vals[j] = vals[j-1]; numOfElementOperation += 1;}
        keys[i] = key; vals[i] = val; numOfElementOperation += 1;
        N++;
    }

    private void resizing(int capacity)
    {
        Key[] keys_copy = (Key[]) new Comparable[capacity];
        Value[] vals_copy = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++)
        {
            keys_copy[i] = keys[i];
            vals_copy[i] = vals[i];
        }
        keys = keys_copy;
        vals = vals_copy;
    }

    public Key min()
    {  return keys[0];  }

    public Key max()
    {  return keys[N-1];  }

    public Key floor(Key key)
    {
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0)
            return key;
        if (i == 0) return null;
        return keys[i-1];
    }

    public Key ceiling(Key key)
    {
        int i = rank(key);
        if (i == N) return null;
        return keys[i];
    }

    public Key select(int k)
    {
        /* the rank of the elements of the BST must be in range 0 ~ size()-1 */
        if (k < 0 || k >= size())  return null;
        return keys[k];
    }

    public void delete(Key key)
    {
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0)
        {
            for (int j = i; j < N-1; j++)
            {
                keys[j] = keys[j+1];
                vals[j] = vals[j+1];
            }
            N--;
            if (N == keys.length/4) resizing(keys.length/2);
        }
    }
    
    public void deleteMin()
    {  delete(min());  }

    public void deleteMax()
    {  delete(max());  }

    boolean contains(Key key)
    {  return get(key) != null;  }

    int size(Key lo, Key hi)
    {
        if (hi.compareTo(lo) < 0)
            return 0;
        else if (contains(hi))
            return rank(hi) - rank(lo) + 1;
        else
            return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> q = new Queue<Key>();
        for (int i = rank(lo); i < rank(hi); i++)
            q.enqueue(keys[i]);
        if (contains(hi))
            q.enqueue(keys[rank(hi)]);
        return q;
    }

    public Iterable<Key> keys()
    {
        return keys(min(), max());
    }

    public static void main(String[] args)
    {
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        In in = new In(StdIn.readString()); // input file name

        /* Construct the symbol table */
        for (int i = 0; !in.isEmpty(); i++)
        {
            String key = in.readString();
            st.put(key, i);
        }

        StdOut.println("Symbol table construct");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        /* Test of put */
        StdOut.println("Test of put operation: --------------------------------");
        st.put("A", 0); // A(0) C(4) E(12) H(5) L(11) M(9) P(10) R(3) S(0) X(7)
        StdOut.println("put 0 at position A");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.put("Y", 1); // A(0) C(4) E(12) H(5) L(11) M(9) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 1 at position Y");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.put("L", 13); // A(0) C(4) E(12) H(5) L(13) M(9) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 13 at position L");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.put("N", 15); // A(0) C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 15 at position N");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.put("A", null); // C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put null at position A (deleting A)");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.put("B", 10); // B(10) C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 10 at position B");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        /* Test of get, only need to test the situation when the key is not in the table */
        StdOut.println("Test of get operation: --------------------------------");
        StdOut.println("try to find key O in the table");
        if (st.get("O") == null) StdOut.println("O is not in the table");

        /* Test of delete */
        StdOut.println("Test of delete operation: --------------------------------");
        st.deleteMin(); // C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("delete minimum key");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.deleteMax(); // C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete maximum key");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.delete("L"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key L");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.delete("A"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key A");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.delete("Z"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key Z");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        st.delete("T"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key T");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println();

        /* Test of rank */
        StdOut.println("Test of rank operation: --------------------------------");
        StdOut.println("List of the ranks of the elements in the symbol table");
        for (String s : st.keys())
            StdOut.print(s + "(" + st.get(s) + ")" + "[" + st.rank(s) + "]" + " ");
        StdOut.println();
        StdOut.println("rank of B is " + st.rank("B")); // rank(B) = 0
        StdOut.println("rank of Y is " + st.rank("Y")); // rank(Y) = 9
        StdOut.println("rank of O is " + st.rank("O")); // rank(O) = 5

        /* Test of select */
        StdOut.println("Test of select operation: --------------------------------");
        StdOut.println("Test the invariant: key == select(rank(key)) for keys in the table");
        int count = 0;
        for (String s : st.keys())
            if (s == st.select(st.rank(s))) count++;
        if (count == st.size()) StdOut.println("invariant1 passed!!");

        StdOut.println("Test the invariant: i == rank(select(i)) for i = 0~size()-1");
        count = 0;
        for (int i = 0; i < st.size(); i++)
            if (i == st.rank(st.select(i))) count++;
        if (count == st.size()) StdOut.println("invariant2 passed!!");

        /* Test of floor and ceiling */
        StdOut.println("Test of floor and ceiling operation: --------------------------------");
        // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("floor of A is: " + st.floor("A")); // null
        StdOut.println("floor of C is: " + st.floor("C")); // C
        StdOut.println("floor of M is: " + st.floor("M")); // M
        StdOut.println("floor of O is: " + st.floor("O")); // N
        StdOut.println("floor of Y is: " + st.floor("Y")); // X

        StdOut.println("ceiling of A is: " + st.ceiling("A")); // C
        StdOut.println("ceiling of C is: " + st.ceiling("C")); // C
        StdOut.println("ceiling of M is: " + st.ceiling("M")); // M
        StdOut.println("ceiling of O is: " + st.ceiling("O")); // P
        StdOut.println("ceiling of Y is: " + st.ceiling("Y")); // null

        /* Test of keys(lo, hi) and size(lo, hi) */
        StdOut.println("Test of keys(lo, hi) and size(lo, hi) operation: --------------------------------");
        StdOut.println("Elements between C and X:");    // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        for (String s : st.keys("C", "X"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("C", "X"));

        StdOut.println("Elements between B and X:");    // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        for (String s : st.keys("B", "X"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("B", "X"));

        StdOut.println("Elements between B and Y:");    // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        for (String s : st.keys("B", "Y"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("B", "Y"));

        StdOut.println("Elements between D and T:");    // E(12) H(5) M(9) N(15) P(10) R(3) S(0)
        for (String s : st.keys("D", "T"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("D", "T"));

        StdOut.println("Elements between H and P:");    // H(5) M(9) N(15) P(10)
        for (String s : st.keys("H", "P"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("H", "P"));

        StdOut.println("Elements between H and O:");    // H(5) M(9) N(15)
        for (String s : st.keys("H", "O"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("H", "O"));

        StdOut.println("Elements between K and N:");    // M(9) N(15)
        for (String s : st.keys("K", "N"))
            StdOut.print(s + "(" + st.get(s) + ")" + " ");
        StdOut.println(", size of these elements: " + st.size("K", "N"));
    }
}

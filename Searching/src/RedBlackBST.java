/**
 * Created by pmg on 2015/8/16.
 */
public class RedBlackBST<Key extends Comparable<Key>, Value>
{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    Node root = null;

    private class Node
    {
        Key key;
        Value val;
        Node left, right;
        boolean color;
        int N;

        public Node(Key key, Value val, boolean color, int N)
        {
            this.key = key;
            this.val = val;
            this.color = color;
            this.N = N;
        }
    }

    private int numOfElementOperation = 0;

    public int getNumOfElementOperation()
    { return numOfElementOperation; }

    private boolean isRed(Node x)
    {
        if (x == null) return BLACK;
        else return x.color == RED;
    }

    public int size()
    {
        return size(root);
    }

    private int size(Node x)
    {
        if (x == null) return 0;  // the main different between size and x.N
        else return x.N;
    }

    public int size(Key lo, Key hi)
    {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public int rank(Key key)
    {
        return rank(root, key);
    }

    private int rank(Node x, Key key)
    {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return rank(x.right, key) + size(x.left) + 1;
        else return size(x.left);
    }

    public Key select(int k)
    {
        if (k < 0 || k >= size()) return null;
        return select(root, k);
    }

    private Key select(Node x, int k)
    {
        if (x == null) return null;
        int sizeOfLeft = size(x.left);
        if (k < sizeOfLeft) return select(x.left, k);
        else if (k > sizeOfLeft) return select(x.right, k - sizeOfLeft - 1);
        else return x.key;
    }

    public boolean contains(Key key)
    {
        return get(key) != null;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public Key min()
    {
        if (isEmpty()) return null;
        else           return min(root).key;
    }

    private Node min(Node x)
    {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max()
    {
        if (isEmpty()) return null;
        else           return max(root).key;
    }

    private Node max(Node x)
    {
        if (x.right == null) return x;
        else return max(x.right);
    }

    public Key floor(Key key)
    {
        return floor(root, key);
    }

    private Key floor(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return floor(x.left, key);
        else if (cmp == 0) return x.key;
        else
        {
            Key t = floor(x.right, key);
            if (t == null) return x.key;
            else return t;
        }
    }

    public Key ceiling(Key key)
    {
        return ceiling(root, key);
    }

    private Key ceiling(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp > 0) return ceiling(x.right, key);
        else if (cmp == 0) return x.key;
        else
        {
            Key t = ceiling(x.left, key);
            if (t == null) return x.key;
            else return t;
        }
    }

    public Value get(Key key)
    {
        return get(root, key);
    }

    private Value get(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    // insert helper functions
    private Node rotateLeft(Node h)
    {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h)
    {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(Node h)
    {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    public void put(Key key, Value val)
    {
        numOfElementOperation = 0;
        // if (val == null) { delete(key); return; }
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value val)
    {
        if (x == null) return new Node(key, val, RED, 1);
        int cmp = key.compareTo(x.key); numOfElementOperation++;
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;

        if (isRed(x.right) && !isRed(x.left)) { x = rotateLeft(x); numOfElementOperation++; }
        if (isRed(x.left) && isRed(x.left.left))
        {
            x = rotateRight(x); // if x.left=null, then isRed(x.left) is false, the second predicate will not be evaluated, then avoid the run-time error.
            numOfElementOperation++;
        }
        if (isRed(x.left) && isRed(x.right)) { flipColors(x); numOfElementOperation++;}

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys()
    { return keys(min(), max()); }

    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> q = new Queue<Key>();
        keys(root, q, lo, hi);
        return q;
    }

    private void keys(Node x, Queue<Key> q, Key lo, Key hi)
    {
        if (lo.compareTo(hi) > 0) return;
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, q, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) q.enqueue(x.key);
        if (cmphi > 0) keys(x.right, q, lo, hi);
    }

    public Iterable<Key> keysLevelOrder()
    {
        Queue<Key> qKey = new Queue<Key>();
        Queue<Node> qNode = new Queue<Node>();

        if (root == null) return qKey;
        qNode.enqueue(root);
        while (!qNode.isEmpty())
        {
            Node x = qNode.dequeue();
            qKey.enqueue(x.key);
            if (x.left != null) qNode.enqueue(x.left);
            if (x.right != null) qNode.enqueue(x.right);
        }

        return qKey;
    }

    public int height()
    { return height(root); }

    private int height(Node x)
    {
        if (x == null) return 0;
        else           return Math.max(height(x.left), height(x.right)) + 1;
    }

    /***************************************************************************
     *  Red-black tree deletion.
     ***************************************************************************/
    // delete the key-value pair with the minimum key
    public void deleteMin() {
        if (isEmpty()) return;
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }


    // delete the key-value pair with the maximum key
    public void deleteMax() {
        if (isEmpty()) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) {
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    // delete the key-value pair with the given key
    public void delete(Key key) {
        if (!contains(key)) {
            System.err.println("symbol table does not contain " + key);
            return;
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) {
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public void print()
    {
        if (root.right != null)
            printTree(root.right, true, "");
        printNodeValue(root);
        if (root.left != null)
            printTree(root.left, false, "");
        StdOut.println();
    }

    private void printNodeValue(Node x)
    {
        if (x == null) return;
        else StdOut.print("(" + x.key.toString() + "," + x.val.toString() + ") " + x.N);
        StdOut.println();
    }

    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(Node x, boolean isRight, String indent)
    {
        if (x.right != null)
            printTree(x.right, true, indent + (isRight ? "        " : " |      "));

        StdOut.print(indent);
        if (isRight) StdOut.print(" /");
        else StdOut.print(" \\");

        StdOut.print("----- ");
        printNodeValue(x);
        if (x.left != null)
            printTree(x.left, false, indent + (isRight ? " |      " : "        "));
    }

    public static void main(String[] args)
    {
        RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
        In in = new In(StdIn.readString()); // input file name

        /* Construct the symbol table */
        for (int i = 0; !in.isEmpty(); i++)
        {
            String key = in.readString();
            st.put(key, i);
        }

        StdOut.println("Symbol table construct");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        /* Test of put */
        StdOut.println("Test of put operation: --------------------------------");
        st.put("A", 0); // A(0) C(4) E(12) H(5) L(11) M(9) P(10) R(3) S(0) X(7)
        StdOut.println("put 0 at position A");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.put("Y", 1); // A(0) C(4) E(12) H(5) L(11) M(9) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 1 at position Y");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.put("L", 13); // A(0) C(4) E(12) H(5) L(13) M(9) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 13 at position L");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.put("N", 15); // A(0) C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 15 at position N");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.put("A", null); // C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)

        StdOut.println("put null at position A (deleting A)");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.put("B", 10); // B(10) C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("put 10 at position B");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        /* Test of get, only need to test the situation when the key is not in the table */
        StdOut.println("Test of get operation: --------------------------------");
        StdOut.println("try to find key O in the table");
        if (st.get("O") == null) StdOut.println("O is not in the table");

        /* Test of delete */
        StdOut.println("Test of delete operation: --------------------------------");
        st.deleteMin(); // C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7) Y(1)
        StdOut.println("delete minimum key");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.deleteMax(); // C(4) E(12) H(5) L(13) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete maximum key");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.delete("L"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key L");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.delete("A"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key A");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.delete("Z"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key Z");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.delete("T"); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key T");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.delete("E"); // C(4) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("delete key E");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
        StdOut.println();

        st.put("E", 12); // C(4) E(12) H(5) M(9) N(15) P(10) R(3) S(0) X(7)
        StdOut.println("put 12 at position E");
        st.print();
        StdOut.print("Size of this BST: ");
        StdOut.println(st.size());
        StdOut.print("Height of this BST: ");
        StdOut.println(st.height());
        StdOut.println("--------------------- Inorder traversal: ------------------- ");
        for (String s : st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        StdOut.println("-------------------- Level order traversal: ----------------- ");
        for (String s : st.keysLevelOrder())
            StdOut.print(s + " ");
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

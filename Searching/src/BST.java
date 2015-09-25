public class BST<Key extends Comparable<Key>, Value>
{
    private Node root;
    private class Node
    {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;

        /* Add x and y coordinate for BST drawing */
        private double x_coordinate, y_coordinate;

        /*
         * when using this constructor, returns an isolated point, whose
         * left and right links are null.
         */
        public Node(Key key, Value val, int N)
        {  this.key = key; this.val = val; this.N = N;  }
    }

    private int numOfElementOperation = 0;

    public int getNumOfElementOperation()
    { return numOfElementOperation; }

    public Value get(Key key)
    {  return get(root, key);  }

    private Value get(Node x, Key key)
    {
        if (x == null)    return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)      return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }
    
    public void put(Key key, Value val)
    {
        numOfElementOperation = 0;
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val)
    {
        if (x == null)    return new Node(key, val, 1);
        int cmp = key.compareTo(x.key); numOfElementOperation++;
        if (cmp < 0)      x.left  = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public int size()
    {  return size(root);  }

    private int size(Node x)
    {
        if (x == null)  return 0;
        else            return x.N;
    }

    public int size(Key lo, Key hi)
    {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public boolean contains(Key key)
    {  return get(key) != null;  }

    public boolean isEmpty()
    {  return size() == 0;  }

    /* iterative version */
    /*
    public Key min()
    {
        Node x = root;
        while (x.left != null)
            x = x.left;
        return x.key;
    }
    */

    /* iterative version */
    /*
    public Key max()
    {
        Node x = root;
        while (x.right != null)
            x = x.right;
        return x.key;
    }
    */

    public Key min()
    {
        if (isEmpty()) return null;
        return min(root).key;
    }

    private Node min(Node x)
    {
        if (x.left == null) return x;
        else                return min(x.left);
    }

    public Key max()
    {
        if (isEmpty()) return null;
        return max(root).key;
    }

    public Node max(Node x)
    {
        if (x.right == null) return x;
        else                 return max(x.right);
    }

    public Key floor(Key key)
    {
        Node x = floor(root, key);
        if (x == null) return null;
        else           return x.key;
    }

    private Node floor(Node x, Key key)
    {
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)   return x;
        if (cmp < 0)    return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t == null)  return x;
        else            return t;
    }

    public Key ceiling(Key key)
    {
        Node x = ceiling(root, key);
        if (x == null) return null;
        else           return x.key;
    }

    private Node ceiling(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)  return x;
        if (cmp > 0)   return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t == null) return x;
        else           return t;
    }

    public Key select(int k)
    {
        /* the rank of the elements of the BST must be in range 0 ~ size()-1 */
        if (k < 0 || k >= size())  return null;
        return select(root, k).key;
    }

    private Node select(Node x, int k)
    {
        if (x == null)  return null;
        int t = size(x.left);
        if      (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else            return x;
    }

    public int rank(Key key)
    {
        return rank(root, key);
    }

    private int rank(Node x, Key key)
    {
        if (x == null)    return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return size(x.left) + 1 + rank(x.right, key);
        else              return size(x.left);
    }

    public void deleteMin()
    {  root = deleteMin(root);  }

    private Node deleteMin(Node x)
    {
        if (x == null)      return null;
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax()
    {  root = deleteMax(root);  }

    private Node deleteMax(Node x)
    {
        if (x == null)       return null;
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key)
    {  root = delete(root, key);  }

    private Node delete(Node x, Key key)
    {
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   x.left = delete(x.left, key);
        else if (cmp > 0)  x.right = delete(x.right, key);
        else
        {
            if (x.right == null) return x.left;
            if (x.left == null)  return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys()
    {  return keys(min(), max());  }

    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null)  return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        // v1
        // if (cmplo < 0) keys(x.left, queue, lo, hi);
        // if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        // if (cmphi > 0) keys(x.right, queue, lo, hi);
        // v2
        if (lo.compareTo(hi) > 0) return;
        else if (cmplo > 0) keys(x.right, queue, lo, hi);
        else if (cmphi < 0) keys(x.left, queue, lo, hi);
        else
        {
            keys(x.left, queue, lo, hi);
            queue.enqueue(x.key);
            keys(x.right, queue, lo, hi);
        }
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

    public void draw()
    {
        Draw canvas = new Draw("BST");
        int canvas_radio = 2;
        canvas.setCanvasSize(500*canvas_radio, 500);
        double x_min = -1.0, x_max = 1.0, y_min = 0.0, y_max = 2.0;
        double x_range = x_max - x_min;
        double y_range = y_max - y_min;
        canvas.setXscale(x_min - x_range / 5, x_max + x_range / 5);
        canvas.setYscale(y_min - y_range / 5, y_max + y_range / 5);
        setCoordinate(x_min, x_max, y_min, y_max);

        int h = height();
        double x_num = Math.pow(2, h);
        /* the x interval of the lowest layer */
        double x_interval_lowest = x_range / (x_num - 1);
        double r1 = x_interval_lowest / 3;
        if (r1 > 0.05) r1 = r1 / 10;
        double r2 = r1 * canvas_radio;

        draw(canvas, r1, r2, root);
    }

    public void draw(Draw canvas, double r1, double r2, Node x)
    {
        if (x == null) return;
        canvas.filledEllipse(x.x_coordinate, x.y_coordinate, r1, r2);
        canvas.text(x.x_coordinate - r2, x.y_coordinate + r2 * 2, x.key.toString());
        canvas.setPenColor(canvas.RED);
        canvas.text(x.x_coordinate - r2, x.y_coordinate - r2 * 2, x.val.toString());
        canvas.setPenColor();
        canvas.text(x.x_coordinate - r2, x.y_coordinate - r2 * 5, Integer.toString(x.N));
        if (x.left != null)
            canvas.line(x.x_coordinate, x.y_coordinate, x.left.x_coordinate, x.left.y_coordinate);
        if (x.right != null)
            canvas.line(x.x_coordinate, x.y_coordinate, x.right.x_coordinate, x.right.y_coordinate);
        draw(canvas, r1, r2, x.left);
        draw(canvas, r1, r2, x.right);
    }

    private void setCoordinate(double x_min, double x_max, double y_min, double y_max)
    {
        double x_range = x_max - x_min;
        double y_range = y_max - y_min;
        int h = height();
        double x_num = Math.pow(2, h);
        /* the x interval of the lowest layer */
        double x_interval_lowest = x_range / (x_num - 1);
        double x_interval = x_interval_lowest * Math.pow(2, h-2);
        double y_interval = y_range / (h - 1);
        setCoordinate(root, (x_min + x_max) / 2, y_max, x_interval, y_interval);
    }

    private void setCoordinate(Node x, double x_cor, double y_cor, double x_interval, double y_interval)
    {
        if (x == null) return;
        x.x_coordinate = x_cor;
        x.y_coordinate = y_cor;
        setCoordinate(x.left, x_cor - x_interval / 2, y_cor - y_interval, x_interval / 2, y_interval);
        setCoordinate(x.right, x_cor + x_interval / 2, y_cor - y_interval, x_interval / 2, y_interval);
    }

    public int height()
    {
        return height(root);
    }

    private int height(Node x)
    {
        if (x == null) return 0;
        else return Math.max(height(x.left), height(x.right)) + 1;
    }

    /* Test Client v1 */

    // public static void main(String[] args)
    // {
    //     BST<String, Integer> st;
    //     st = new BST<String, Integer>();
    //     In in = new In(StdIn.readString());
    //     for (int i = 0; !in.isEmpty(); i++)
    //     {
    //         String key = in.readString();
    //         st.put(key, i);
    //         st.print();
    //         // st.draw();
    //     }
    //     st.put("Y", 1);
    //     st.print();
    //     StdOut.println("============================ Origin BST ========================");
    //     StdOut.println("----------------- Inorder traversal: ---------------------------");
    //     for (String s : st.keys())
    //         StdOut.print(s + " ");
    //     StdOut.println();
    //     StdOut.println("---------------- Level order traversal: ------------------------");
    //     for (String s : st.keysLevelOrder())
    //         StdOut.print(s + " ");
    //     StdOut.println();
//
//
    //     StdOut.print("Size of this BST: ");
    //     StdOut.println(st.size());
    //     StdOut.print("Height of this BST: ");
    //     StdOut.println(st.height());
//
    //     StdOut.println("Max key: " + st.max());
    //     StdOut.println("Min key: " + st.min());
//
    //     st.print();
    //     // st.draw();
//
    //     /* delete test */
    //     StdOut.println();
    //     StdOut.println("============================  Delete test: ===================== ");
    //     StdOut.println("================ Delete minimum ==================");
    //     st.deleteMin();
//
    //     StdOut.println("---------------------- Inorder traversal: -----------------------");
    //     for (String s : st.keys())
    //         StdOut.print(s + " ");
    //     StdOut.println();
    //     StdOut.println("----------------------- Level order traversal: ------------------");
    //     for (String s : st.keysLevelOrder())
    //         StdOut.print(s + " ");
    //     StdOut.println();
//
    //     StdOut.print("Size of this BST: ");
    //     StdOut.println(st.size());
    //     StdOut.print("Height of this BST: ");
    //     StdOut.println(st.height());
    //     // st.draw();
    //     st.print();
//
    //     StdOut.println("============== Delete Maximum ================== ");
    //     st.deleteMax();
//
    //     StdOut.println("--------------------- Inorder traversal: ------------------- ");
    //     for (String s : st.keys())
    //         StdOut.print(s + " ");
    //     StdOut.println();
    //     StdOut.println("-------------------- Level order traversal: ----------------- ");
    //     for (String s : st.keysLevelOrder())
    //         StdOut.print(s + " ");
    //     StdOut.println();
//
    //     StdOut.print("Size of this BST: ");
    //     StdOut.println(st.size());
    //     StdOut.print("Height of this BST: ");
    //     StdOut.println(st.height());
    //     // st.draw();
    //     st.print();
//
    //     StdOut.println("================== Delete Key 'M' =================");
    //     st.delete("M");
//
    //     StdOut.println("--------------------- Inorder traversal: ------------------- ");
    //     for (String s : st.keys())
    //         StdOut.print(s + " ");
    //     StdOut.println();
    //     StdOut.println("-------------------- Level order traversal: ----------------- ");
    //     for (String s : st.keysLevelOrder())
    //         StdOut.print(s + " ");
    //     StdOut.println();
//
    //     StdOut.print("Size of this BST: ");
    //     StdOut.println(st.size());
    //     StdOut.print("Height of this BST: ");
    //     StdOut.println(st.height());
    //     // st.draw();
    //     st.print();
//
    //     StdOut.println("============== Delete Key 'F'(not in the BST) =========");
    //     st.delete("F");
//
    //     StdOut.println("--------------------- Inorder traversal: ------------------- ");
    //     for (String s : st.keys())
    //         StdOut.print(s + " ");
    //     StdOut.println();
    //     StdOut.println("-------------------- Level order traversal: ----------------- ");
    //     for (String s : st.keysLevelOrder())
    //         StdOut.print(s + " ");
    //     StdOut.println();
//
    //     StdOut.print("Size of this BST: ");
    //     StdOut.println(st.size());
    //     StdOut.print("Height of this BST: ");
    //     StdOut.println(st.height());
    //     // st.draw();
    //     st.print();
//
    //     /* floor test */
    //     StdOut.println();
    //     StdOut.println("========================== floor test ====================== ");
    //     StdOut.println("floor of D: " + st.floor("D"));
    //     StdOut.println("floor of A: " + st.floor("A"));
    //     StdOut.println("floor of Y: " + st.floor("Y"));
    //     StdOut.println("floor of T: " + st.floor("T"));
//
    //     /* ceiling test */
    //     StdOut.println();
    //     StdOut.println("========================== ceiling test ====================== ");
    //     StdOut.println("ceiling of D: " + st.ceiling("D"));
    //     StdOut.println("ceiling of A: " + st.ceiling("A"));
    //     StdOut.println("ceiling of Y: " + st.ceiling("Y"));
    //     StdOut.println("ceiling of T: " + st.ceiling("T"));
//
    //     /* select test */
    //     StdOut.println();
    //     StdOut.println("========================== select test ====================== ");
    //     StdOut.println("select rank 0: " + st.select(0));
    //     StdOut.println("select rank 7: " + st.select(7));
    //     StdOut.println("select rank 6: " + st.select(6));
    //     StdOut.println("select rank 3: " + st.select(3));
//
    //     /* rank test */
    //     StdOut.println();
    //     StdOut.println("========================== rank test ====================== ");
    //     StdOut.println("rank of A: " + st.rank("A"));
    //     StdOut.println("rank of X: " + st.rank("X"));
    //     StdOut.println("rank of C: " + st.rank("C"));
    //     StdOut.println("rank of D: " + st.rank("D"));
//
    //     /* contains test */
    //     StdOut.println();
    //     StdOut.println("========================== contains test ====================");
    //     StdOut.println("whether contains D: " + st.contains("D"));
    //     StdOut.println("whether contains C: " + st.contains("C"));
//
    //     /* isEmpty test */
    //     StdOut.println();
    //     StdOut.println("========================== isEmpty test ====================");
    //     StdOut.println("whether empty: " + st.isEmpty());
    //     int N = st.size();
    //     for (int i = 0; i < N; i++)
    //         st.deleteMin();
    //     StdOut.println("whether empty: " + st.isEmpty());
//
    //     /* exercise3.2.1 */
    //     StdOut.println("Exercise 3.2.1 ");
    //     BST<String, Integer> st2;
    //     st2 = new BST<String, Integer>();
    //     String[] tmp = {"E", "A", "S", "Y", "Q", "U", "E", "S", "T", "I", "O", "N"};
    //     for (int i = 0; i < tmp.length; i++)
    //     {
    //         st2.put(tmp[i], i);
    //         st2.print();
    //         //    st2.draw();
    //     }
    //     st2.print();
    // }

    /* Test Client v2 */
    public static void main(String[] args)
    {
        BST<String, Integer> st = new BST<String, Integer>();
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

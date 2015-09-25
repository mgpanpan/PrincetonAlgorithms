/**
 * Created by ph on 2015/8/12.
 */
public class FrequencyCounter
{
    /* test of SequentialSearchLinkedListST */
    /*
    public static void main(String[] args)
    {
        In in = new In(StdIn.readString()); // input the file name
        int minlen = StdIn.readInt(); // input min length
        SequentialSearchLinkedListST<String, Integer> st = new SequentialSearchLinkedListST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(14350, 5500); // 14350 is the number of words, number of distinct words is 5131
        while (!in.isEmpty())
        {
            String word = in.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) st.put(word, 1);
            else                    st.put(word, st.get(word) + 1);
            va.addDataValue(st.getCompareInPut());
        }
        StdOut.println(va); // the result is the same as the figure on page 377

        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));

        StdOut.println("Number of calls to gets(): " + st.getGetNum());
        StdOut.println("Number of calls to puts(): " + st.getPutNum());
    }
    */

    /* test of BinarySearchST */
    /*
    public static void main(String[] args)
    {
        In in = new In(StdIn.readString()); // input the file name
        int minlen = StdIn.readInt(); // input min length
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(14350, 5500); // 14350 is the number of words, number of distinct words is 5131
        while (!in.isEmpty())
        {
            String word = in.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) st.put(word, 1);
            else                    st.put(word, st.get(word) + 1);
            va.addDataValue(st.getNumOfElementOperation());
        }
        StdOut.println(va); // the result is similar to the figure on page 384

        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));

    }
    */

    /* test of OrderedLinkedList */
    /*
    public static void main(String[] args)
    {
        In in = new In(StdIn.readString()); // input the file name
        int minlen = StdIn.readInt(); // input min length
        OrderedLinkedListST<String, Integer> st = new OrderedLinkedListST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(14350, 5500); // 14350 is the number of words, number of distinct words is 5131
        while (!in.isEmpty())
        {
            String word = in.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) st.put(word, 1);
            else                    st.put(word, st.get(word) + 1);
            va.addDataValue(st.getNumOfElementOperation());
        }
        StdOut.println(va);

        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));
    }
    */

    /* test of ArrayList */
    /*
    public static void main(String[] args)
    {
        In in = new In(StdIn.readString()); // input the file name
        int minlen = StdIn.readInt(); // input min length

        ArrayST<String, Integer> st = new ArrayST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(14350, 5500); // 14350 is the number of words, number of distinct words is 5131
        while (!in.isEmpty())
        {
            String word = in.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) st.put(word, 1);
            else                    st.put(word, st.get(word) + 1);
            va.addDataValue(st.getNumOfElementOperation());
        }
        StdOut.println(va);

        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));
    }
    */

    /* test of BST */
    /*
    public static void main(String[] args)
    {
        In in = new In(StdIn.readString()); // input the file name
        int minlen = StdIn.readInt(); // input min length

        BST<String, Integer> st = new BST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(14350, 20); // 14350 is the number of words, number of distinct words is 5131
        while (!in.isEmpty())
        {
            String word = in.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) st.put(word, 1);
            else                    st.put(word, st.get(word) + 1);
            va.addDataValue(st.getNumOfElementOperation());
        }
        StdOut.println(va);

        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));
    }
    */

    /* test of RedBlackBST */
    public static void main(String[] args)
    {
        In in = new In(StdIn.readString()); // input the file name
        int minlen = StdIn.readInt(); // input min length

        RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(14350, 20); // 14350 is the number of words, number of distinct words is 5131
        while (!in.isEmpty())
        {
            String word = in.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) st.put(word, 1);
            else                    st.put(word, st.get(word) + 1);
            va.addDataValue(st.getNumOfElementOperation());
        }
        StdOut.println(va);

        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));
    }
}

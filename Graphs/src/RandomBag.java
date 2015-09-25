import java.util.Iterator;

/**
 * Created by pmg on 2015/9/21.
 * Exercise 1.3.34
 */
public class RandomBag<Item> implements Iterable<Item>
{
    private int N = 0;
    private Item[] bag;

    public RandomBag()
    { bag = (Item[]) new Object[1]; }

    public int size()
    { return N; }

    public boolean isEmpty()
    { return size() == 0; }

    public void add(Item item)
    {
        if (N == bag.length) resizing(bag.length * 2);
        bag[N++] = item;
    }

    public Iterator<Item> iterator()
    { return new RandomBagIterator(); }

    private class RandomBagIterator implements Iterator<Item>
    {
        private int n = -1;
        private Item[] copy = (Item[]) new Object[N];
        public RandomBagIterator()
        {
            for (int i = 0; i < N; i++)
                copy[i] = bag[i];
            StdRandom.shuffle(copy);
        }
        public boolean hasNext()
        {  return n < N - 1;  }
        public void remove()
        {  throw new java.lang.UnsupportedOperationException();  }
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return copy[++n];
        }
    }

    private void resizing(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = bag[i];
        bag = copy;
    }

    public static void main(String[] args)
    {
        RandomBag<String> bag = new RandomBag<String>();
        int N = 10;
        for (int i = 0; i < N; i++)
            bag.add(i + "");
        for (String item : bag)
            StdOut.print(item + " ");
        StdOut.println();
    }
}

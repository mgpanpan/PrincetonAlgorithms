/* ----------------------------------------------------------------
 *  Author:        Pan Mengguan
 *  Written:       23/12/2014
 *  Last updated:  24/12/2014,
 *                 24/12/2014 afternoon, fixed submit1 bug.
 *
 *  Compilation:   javac RandomizedQueue.java
 *  Execution:     java RandomizedQueue
 *  Dependencies:  StdRandom.java, StdOut.java(test client)
 * 
 *  Implementation of Randomized Queue using resizing array.
 *
 *  % java RandomizedQueue
 *  3 4 2 1 
 *  4 2 1 3 
 *  3 2 4 1 
 *  2 4 1 3 
 *  % java RandomizedQueue
 *  2 1 4 3 
 *  3 2 4 1 
 *  1 2 3 4 
 *  1 3 4 2 

 * ---------------------------------------------------------------- */

import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] q;
    private int N = 0;

    /**
     *  Initializes an empty queue.
     */
    public RandomizedQueue()
    {
        q = (Item[]) new Object[1];
    }

    /**
     *  Is this queue empty?
     *  @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty()
    {  return N == 0;  }

    /**
     *  Returns the number of items in this queue.
     *  @return the number of items in this queue.
     */
    public int size()
    {  return N;  }

    /**
     *  Adds the item to the end of this queue.
     *  @param item the item to add
     *  @throws java.lang.NullPointerException if the param is null
     */
    public void enqueue(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (N == q.length) resizing(q.length * 2);
        q[N++] = item;
    }

    /**
     *  Removes and returns an item of random index in this queue.
     *  @return an item of random index in this queue
     *  @throws java.util.NoSuchElementException if the queue is empty
     */
    public Item dequeue()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int sel = StdRandom.uniform(N);
        Item item = q[sel];
        q[sel] = null;
        q[sel] = q[N-1];
        q[N-1] = null;
        N--;

        // add N > 0 && , fixed bug
        if (N > 0 && N == q.length / 4) resizing(q.length / 2);

        return item;
    }

    /**
     *  Return (but do not delete) a random item.
     *  @return an item of random index in this queue
     *  @throws java.util.NoSuchElementException if the queue is empty
     */
    public Item sample()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int sel = StdRandom.uniform(N);
        return q[sel];
    }

    /**
     *  Returns an iterator that iterates over the items in this queue
     *  in random order
     *  @return an iterator that iterates over the items in this queue
     *  in random order
     */
    public Iterator<Item> iterator()
    {  return new RandomizedArrayIterator();  }

    private class RandomizedArrayIterator implements Iterator<Item>
    {
        private Item[] randomizedSeq;
        private int n = N;

        public RandomizedArrayIterator()
        {
            randomizedSeq = (Item[]) new Object[n];
            for (int i = 0; i < n; i++)
                randomizedSeq[i] = q[i];
            StdRandom.shuffle(randomizedSeq);
        }
        
        public boolean hasNext()
        {  return n > 0;  }
        public void remove()
        {  throw new java.lang.UnsupportedOperationException();  }
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return randomizedSeq[--n];
        }
    }

    private void resizing(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = q[i];
        q = copy;
    }

    /**
     *  Unit test
     */
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 1; i <= 4; i++)
            rq.enqueue(i);
        for (int i = 0; i < 4; i++)
            StdOut.print(rq.dequeue() + " ");
        StdOut.println();
        
        for (int i = 1; i <= 4; i++)
            rq.enqueue(i);
        for (int i = 0; i < 4; i++)
            StdOut.print(rq.dequeue() + " ");
        StdOut.println();

        for (int i = 1; i <= 4; i++)
            rq.enqueue(i);

        for (int iter1 : rq)
            StdOut.print(iter1 + " ");
        StdOut.println();

        for (int iter1 : rq)
            StdOut.print(iter1 + " ");
        StdOut.println();

        StdOut.println("----- Debug of assessment 1 -----");
        rq.enqueue(10);
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.enqueue(1);
        rq.dequeue();
        rq.enqueue(1);
        /* the first submit version has problem running this series of code,
         the problem is in the dequeue method, add N > 0. */
    }
}

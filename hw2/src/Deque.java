/* ----------------------------------------------------------------
 *  Author:        Pan Mengguan
 *  Written:       23/12/2014
 *  Last updated:  24/12/2014
 *
 *  Compilation:   javac Deque.java
 *  Execution:     java Deque
 *  Dependencies:  StdOut.java(test client)
 *  Unit test suit: TestDeque.java
 *  Implementation of Deque using double-ended linked list.
 *
 *  % java Deque
 *  2
 *  1
 *  3
 * ---------------------------------------------------------------- */

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
    private class Node
    {
        Item item;
        Node next;
        Node prior;
    }
    
    private Node first = null;
    private Node last = null;
    private int num = 0;

    /**
     *  Is this queue empty?
     *  @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty()
    {
        return num == 0;
    }

    /**
     *  Returns the number of items in this queue.
     *  @return the number of items in this queue.
     */
    public int size()
    {  return num;  }

    /**
     *  Adds the item to the front of this queue.
     *  @param item the item to add
     *  @throws java.lang.NullPointerException if the param is null
     */
    public void addFirst(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldfirst = first;

        first = new Node();
        first.item = item;
        first.next = oldfirst; // first.prior is the default value: null
        if (isEmpty()) last = first;
        else oldfirst.prior = first;
        num++;
    }

    /**
     *  Adds the item to the end of this queue.
     *  @param item the item to add
     *  @throws java.lang.NullPointerException if the param is null
     */
    public void addLast(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prior = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        num++;
    }

    /**
     *  Removes and returns the item on the front of this queue.
     *  @return the item on the front of this queue.
     *  @throws java.util.NoSuchElementException if the queue is empty
     */
    public Item removeFirst()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        num--;
        if (isEmpty()) last = null;
        else first.prior = null;
        return item;
    }

    /**
     *  Removes and returns the item at the last of this queue.
     *  @return the item at the last of this queue.
     *  @throws java.util.NoSuchElementException if the queue is empty
     */
    public Item removeLast()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prior;
        num--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    /**
     *  Returns an iterator that iterates over the items in this queue
     *  in order from front to end.
     *  @return an iterator that iterates over the items in this queue
     *  in order from front to end
     */
    public Iterator<Item> iterator()
    {  return new LinkedListIterator();  }

    /* LinkedList Iterator */
    private class LinkedListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {  return current != null;  }
        public void remove()
        {  throw new java.lang.UnsupportedOperationException();  }
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     *  Simple Unit test of the <tt>Deque</tt> data type.
     *  More complete unit tests are in the TestDeque class.
     */
    public static void main(String[] args)
    {
        Deque<Integer> intDeque = new Deque<Integer>();
        intDeque.addFirst(1);
        intDeque.addFirst(2);
        Iterator<Integer> iter1 = intDeque.iterator();
        intDeque.addLast(3);
        StdOut.println(iter1.next());
        StdOut.println(iter1.next());
        StdOut.println(iter1.next());
    }
    
}

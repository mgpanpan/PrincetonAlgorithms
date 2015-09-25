/* ----------------------------------------------------------------
 *  Author:        Pan Mengguan
 *  Written:       24/12/2014
 *  Last updated:  24/12/2014
 *
 *  Compilation:   javac Subset.java
 *  Execution:     java Subset
 *  Dependencies:  RandomizedQueue.java, StdOut.java
 * 
 *  A client program takes a command-line integer k; reads in a sequence
 *  of N strings from standard; and prints out exactly k of them, uniformly
 *  at random. Each item from the sequence printed out at most once.
 *
 *  % echo A B C D E F G H I | java Subset 3 
 *  D
 *  F
 *  I
 *  
 *  % echo A B C D E F G H I | java Subset 3 
 *  F
 *  G
 *  E
 *  
 *  % java Subset 8 < subset_test.txt 
 *  BB
 *  BB
 *  AA
 *  BB
 *  CC
 *  BB
 *  BB
 *  CC
 *  
 *  % java Subset 8 < subset_test.txt 
 *  BB
 *  BB
 *  BB
 *  AA
 *  CC
 *  BB
 *  CC
 *  BB

 * ---------------------------------------------------------------- */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Subset
{
    
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            rq.enqueue(item);
        }

        Iterator<String> iter = rq.iterator();
        for (int i = 0; i < k; i++)
            StdOut.println(iter.next());
    }
}

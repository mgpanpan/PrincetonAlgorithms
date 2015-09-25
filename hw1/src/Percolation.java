/* ----------------------------------------------------------------
 *  Author:        Pan Mengguan
 *  Written:       20/12/2014
 *  Last updated:  21/12/2014 change style
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *  Dependencies:  WeightedQuickUnionUF.java, StdOut.java(test client)
 *
 *  Implementation of Percolation class.
 *
 *  % java Percolation
 *    false
 *    false
 *    false
 *    true
 *    true
 *    true
 * 
 * ---------------------------------------------------------------- */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private boolean[][] sites;                // 2-dimensional grid
    private WeightedQuickUnionUF connections; // model connectivity of sites
    
    /**
     *  Map from a 2-dimensional (row, column) pair to a 1-dimensional
     *  union find object index.
     */
    private int xyTo1D(int i, int j)
    {
        return (i-1) * sites.length + (j-1) + 1;
    }

    /**
     *  Validating wheter the indexes are in a resonable bound. When not,
     *  throwing an exception.
     */
    private void indicesValidating(int i, int j)
    {
        if (i <= 0 || i > sites.length)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > sites.length)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    /**
     *  Create N-by-N grid, with all sites blocked.
     */
    public Percolation(int N)
    {
        if (N <= 0) throw new IllegalArgumentException();
        
        /* including virtual top site and virtual bottom site. */
        connections = new WeightedQuickUnionUF(N*N+2);

        sites = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                sites[i][j] = false;

        /* connect the virtual sites. */
        for (int j = 0; j < N; j++)
        {
            /* connect virtual top site to all the top sites. */
            connections.union(0, xyTo1D(1, j+1));
            /* connect virtual bottom site to all the bottom sites. */
            connections.union(N*N+1, xyTo1D(N, j+1));
        }
    }

    /**
     *  Open site (row i, column j) if it is not opened.
     */
    public void open(int i, int j)
    {
        indicesValidating(i, j);
        sites[i-1][j-1] = true;
        
        int N = sites.length;
        if (i-1 >= 1 && this.isOpen(i-1, j))
            connections.union(xyTo1D(i, j), xyTo1D(i-1, j));
        if (i+1 <= N && this.isOpen(i+1, j))
            connections.union(xyTo1D(i, j), xyTo1D(i+1, j));
        if (j-1 >= 1 && this.isOpen(i, j-1))
            connections.union(xyTo1D(i, j), xyTo1D(i, j-1));
        if (j+1 <= N && this.isOpen(i, j+1))
            connections.union(xyTo1D(i, j), xyTo1D(i, j+1));        
    }

    /**
     *  Is the site at (i, j) opened?
     */
    public boolean isOpen(int i, int j)
    {
        indicesValidating(i, j);
        return sites[i-1][j-1];
    }

    /**
     *  Is the site at (i, j) full?
     */
    public boolean isFull(int i, int j)
    {
        indicesValidating(i, j);
        return sites[i-1][j-1] && connections.connected(0, xyTo1D(i, j));
    }

    /**
     *  Does the system percolate?
     */
    public boolean percolates()
    {
        int N = sites.length;
        return connections.connected(0, N*N+1);
    }

    /**
     *  Test client. Using a 3-by-3 example.
     */
    public static void main(String[] args)
    {
        Percolation perc = new Percolation(3);

        StdOut.println(perc.percolates());

        StdOut.println(perc.isOpen(1,1));
        StdOut.println(perc.isFull(1,1));

        perc.open(1,1);
        StdOut.println(perc.isOpen(1,1));
        StdOut.println(perc.isFull(1,1));

        perc.open(2,1);
        perc.open(3,1);
        StdOut.println(perc.percolates());
    }
}

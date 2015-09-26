/**
 * Created by pmg on 2015/8/25.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] blocks;
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {                                      // (where blocks[i][j] = block in row i, column j)
        // assume that the constructor receives an N-by-N array containing the N2 integers between 0 and N2 − 1, where 0 represents the blank square.
        int N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.blocks[i][j] = blocks[i][j];
    }
    public int dimension()                 // board dimension N
    { return blocks.length; }
    public int hamming()                   // number of blocks out of place
    {
        int hammingPriority = 0;
        int N = dimension();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (blocks[i][j] != 0 && blocks[i][j] != i*N+j+1) hammingPriority++;
        return hammingPriority;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhattanPriority = 0;
        int N = dimension();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (blocks[i][j] == 0) continue;
                int goalRow = (blocks[i][j] - 1) / N;
                int goalCol = (blocks[i][j] - 1) % N;
                manhattanPriority += Math.abs(goalRow - i) + Math.abs(goalCol - j);
            }
        return manhattanPriority;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int N = dimension();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (blocks[i][j] != 0 && blocks[i][j] != i*N+j+1) return false;
        return true;
    }

    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    {
        int N = dimension();
        int[] index = new int[2];
        int i;    // exchange the elements in row 0
        for (i = 0; i < N; i++)
        {
            int count = 0;
            for (int j = 0; j < N; j++)
                if (count == 2) break;
                else if (blocks[i][j] != 0)
                    index[count++] = j;
            if (count == 2) break;
        }
        int[][] copy = new int[N][N];
        for (int ii = 0; ii < N; ii++)
            for (int jj = 0; jj < N; jj++)
                copy[ii][jj] = blocks[ii][jj];
        int tmp = copy[i][index[0]];
        copy[i][index[0]] = copy[i][index[1]];
        copy[i][index[1]] = tmp;
        return new Board(copy);
    }

    public boolean equals(Object x) // does this board equal y?
    {
        if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        Board that = (Board) x;
        if (this.blocks.length != that.blocks.length) return false;
        if (this.blocks[0].length != that.blocks[0].length) return false;
        for (int i = 0; i < this.blocks.length; i++)
            for (int j = 0; j < this.blocks[0].length; j++)
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> q = new Queue<Board>();
        int N = dimension();
        int i = 0, j = 0;
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
                if (blocks[i][j] == 0) break;
            if (j < N && blocks[i][j] == 0) break;
        }
        int[][] copy = new int[N][N];
        makeCopy(copy, blocks);
        if (i > 0) { copy[i][j] = copy[i-1][j]; copy[i-1][j] = 0; q.enqueue(new Board(copy));}
        makeCopy(copy, blocks);
        if (i < N-1) { copy[i][j] = copy[i+1][j]; copy[i+1][j] = 0; q.enqueue(new Board(copy));}
        makeCopy(copy, blocks);
        if (j > 0) { copy[i][j] = copy[i][j-1]; copy[i][j-1] = 0; q.enqueue(new Board(copy));}
        makeCopy(copy, blocks);
        if (j < N-1) { copy[i][j] = copy[i][j+1]; copy[i][j+1] = 0; q.enqueue(new Board(copy));}
        return q;
    }

    private void makeCopy(int[][] copy, int[][] src)
    {
        for (int ii = 0; ii < src.length; ii++)
            for (int jj = 0; jj < src.length; jj++)
                copy[ii][jj] = src[ii][jj];
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuffer s = new StringBuffer();
        int N = blocks.length;
        s.append(N + "\n");
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                s.append(String.format("%2d", blocks[i][j]));
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * for the animation
     * @param row
     * @param col
     * @return
     */
    public int tileAt(int row, int col)
    { return blocks[row][col]; }

    public static void main(String[] args)// unit tests (not graded)
    {
        // create initial board from file
        // StdOut.print("Input a file name: ");
        // In in = new In(StdIn.readString());
        In in = new In("hw4/src/testdata/puzzle.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.isGoal());
        Board twin1 = initial.twin();
        Board twin2 = initial.twin();
        StdOut.println(twin1);
        StdOut.println(twin2);
        StdOut.println(initial.equals(twin1));
        StdOut.println(initial.equals(twin2));
        StdOut.println(initial.equals(initial));
        Board initial2 = new Board(blocks);
        StdOut.println(initial.equals(initial2));
        for (Board neighbour : initial.neighbors())
            StdOut.print(neighbour);
    }
}
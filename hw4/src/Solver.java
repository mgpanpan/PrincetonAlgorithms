import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by pmg on 2015/9/23.
 */

public class Solver
{
    private class SearchNode implements Comparable<SearchNode>
    {
        private Board board;
        private int moves;
        private SearchNode pre;
        public SearchNode(Board board, int moves, SearchNode pre)
        {
            this.board = board;
            this.moves = moves;
            this.pre = pre;
        }
        public int compareTo(SearchNode that)
        {
            int thisPriority = this.board.manhattan() + this.moves;
            int thatPriority = that.board.manhattan() + that.moves;
            if      (thisPriority < thatPriority) return -1;
            else if (thisPriority > thatPriority) return +1;
            else                                return  0;
        }
    }

    private boolean isSolvable;
    private int moves;
    private SearchNode goalNode;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new NullPointerException();
        Board twin = initial.twin();
        MinPQ<SearchNode> initialPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        initialPQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(twin, 0, null));
        SearchNode initialCurrent = initialPQ.delMin();
        SearchNode twinCurrent = twinPQ.delMin();
        while (!initialCurrent.board.isGoal() && !twinCurrent.board.isGoal())
        {
            for (Board neighbour : initialCurrent.board.neighbors())
                if (initialCurrent.pre == null || !neighbour.equals(initialCurrent.pre.board))
                    initialPQ.insert(new SearchNode(neighbour, initialCurrent.moves+1, initialCurrent));
            initialCurrent = initialPQ.delMin();
            for (Board neighbour : twinCurrent.board.neighbors())
                if (twinCurrent.pre == null || !neighbour.equals(twinCurrent.pre.board))
                    twinPQ.insert(new SearchNode(neighbour, twinCurrent.moves+1, twinCurrent));
            twinCurrent = twinPQ.delMin();
        }
        if (twinCurrent.board.isGoal())
        {
            isSolvable = false;
            moves = -1;
            goalNode = null;
        }
        else
        {
            isSolvable = true;
            moves = initialCurrent.moves;
            goalNode = initialCurrent;
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    { return isSolvable; }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    { return moves; }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable()) return null;
        Stack<Board> stk = new Stack<Board>();
        SearchNode nodeBack = goalNode;
        while (nodeBack != null)
        {
            stk.push(nodeBack.board);
            nodeBack = nodeBack.pre;
        }
        return stk;
    }

    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In("hw4/src/testdata/puzzle.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

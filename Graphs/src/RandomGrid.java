/**
 * Created by pmg on 2015/9/21.
 */
public class RandomGrid
{
    public static class Connection
    {
        int p;
        int q;
        public Connection(int p, int q)
        { this.p = p; this.q = q; }
    }

    public static Connection[] generate(int N)
    {
        RandomBag<Connection> connectionBag = new RandomBag<Connection>();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (i > 0) connectionBag.add(new Connection(index(i, j, N), index(i - 1, j, N)));
                if (i < N - 1) connectionBag.add(new Connection(index(i, j, N), index(i + 1, j, N)));
                if (j > 0) connectionBag.add(new Connection(index(i, j, N), index(i, j - 1, N)));
                if (j < N - 1) connectionBag.add(new Connection(index(i, j, N), index(i, j + 1, N)));
            }
        Connection[] connections = new Connection[connectionBag.size()];
        int i = 0;
        for (Connection item : connectionBag)
            connections[i++] = item;
        return connections;
    }

    public static int index(int row, int col, int N) // row, col are counted from 0.
    { return row * N + col; }

    public static void main(String[] args)
    {
        int N = 4;
        Connection[] connections = generate(N);
        StdOut.println("full connections number: " + connections.length);
        for (int i = 0; i < connections.length; i++)
            StdOut.println(connections[i].p + " " + connections[i].q);
    }
}

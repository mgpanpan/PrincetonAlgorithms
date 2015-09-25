/**
 * Created by pmg on 2015/9/21.
 */
public class ErdosRenyi
{
    public static int count(int N)
    {
        int edges = 0;
        QuickUnionImproveUF uf = new QuickUnionImproveUF(N);
        while (uf.count() > 1)
        {
            int p = StdRandom.uniform(0, N);
            int q = StdRandom.uniform(0, N);
            uf.union(p, q);
            edges++;
        }
        return edges;
    }

    public static void main(String[] args)
    {
        int N = Integer.parseInt(StdIn.readString());     // number of vertices
        int T = Integer.parseInt(StdIn.readString());     // number of trials
        int[] edges = new int[T];

        // repeat the experiment T times
        for (int t = 0; t < T; t++)
            edges[t] = count(N);

        // report statistics
        StdOut.println("1/2 N ln N = " + 0.5 * N * Math.log(N));
        StdOut.println("mean       = " + StdStats.mean(edges));
        StdOut.println("stddev     = " + StdStats.stddev(edges));
    }
}

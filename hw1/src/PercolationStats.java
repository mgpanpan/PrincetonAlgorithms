/* ----------------------------------------------------------------
 *  Author:        Pan Mengguan
 *  Written:       20/12/2014
 *  Last updated:  21/12/2014 change style
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats
 *  Dependencies:  Percolation.java, StdRandom.java, StdStats.java,
 *                 StdOut.java
 * 
 *  Monte Carlo simulation for the estimating of percolation threshold.
 *
 *  % java PercolationStats 200 100
 *    mean                      = 0.592314
 *    stddev                    = 0.0097444334
 *    95% confidence interval   = 0.5904038411, 0.5942236589
 * 
 * ---------------------------------------------------------------- */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats
{
    private double[] expResult;  // stores the T times experiment result

    /**
     *  running Monte Carlo simulation for N-by-N percolation problem T times.
     */
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();

        expResult = new double[T];

        for (int k = 0; k < T; k++)
        {
            int opened = 0;  // the number of opened sites
            Percolation prec = new Percolation(N);
            while (!prec.percolates())
            {
                int row = StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                if (!prec.isOpen(row, col))
                {
                    prec.open(row, col);
                    opened++;
                }
            }
            expResult[k] = (double) opened / (N*N);
        }
    }

    /**
     *  sample mean of percolation threshold
     */
    public double mean()
    {
        return StdStats.mean(expResult);
    }

    /**
     *  sample standard deviation of percolation threshold
     */
    public double stddev()
    {
        if (expResult.length == 1)
            return Double.NaN;
        else
            return StdStats.stddev(expResult);
    }

    /**
     *  low endpoint of 95% confidence interval
     */
    public double confidenceLo()
    {
        return mean() - 1.96 * stddev() / Math.sqrt(expResult.length);
    }

    /**
     *  high endpoint of 95% confidence interval
     */
    public double confidenceHi()
    {
        return mean() + 1.96 * stddev() / Math.sqrt(expResult.length);
    }

    /**
     *  test client, takes two command-line arguments N and T, performs
     *  T independent computational experiments on a N-by-N grid, and
     *  prints out the mean, standard deviation, and the 95% confidence
     *  interval for the percolation threshold.
     */
    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats perc_stats = new PercolationStats(N, T);

        StdOut.printf("%-25s = %.6f\n", "mean", perc_stats.mean());
        StdOut.printf("%-25s = %.10f\n", "stddev", perc_stats.stddev());
        StdOut.printf("%-25s = %.10f, %.10f\n", "95% confidence interval",
                      perc_stats.confidenceLo(), perc_stats.confidenceHi());
    }

}

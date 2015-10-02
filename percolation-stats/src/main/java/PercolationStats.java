import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private final int gridSize;
    private final int numberOfExperiments;

    private double mean;
    private double quadStdDev;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Params should be positive");
        }
        gridSize = N;
        numberOfExperiments = T;

        for (int i = 1; i <= T; i++) {
            Percolation percolation = new Percolation(N);
            int openSites = 0;
            while (!percolation.percolates()) {
                int row = next();
                int col = next();
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSites++;
                }
            }
            double threshold = openSites / (N*N);
            mean = threshold / i + ((mean * (i - 1)) / i);
            quadStdDev = (threshold - mean)*(threshold - mean) / i + quadStdDev * (i - 1) / i;
        }
    }

    // test client (described below)
    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
        System.out.println("elapsed time " + stopwatch.elapsedTime());

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return Math.sqrt(quadStdDev);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - diff();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + diff();
    }

    private double diff() {
        return 1.96 * stddev() / Math.sqrt(numberOfExperiments);
    }

    private int next() {
        return StdRandom.uniform(gridSize) + 1;
    }
}

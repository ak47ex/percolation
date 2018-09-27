import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double NORMAL_DISTRIBUTION_DEVIATION = 1.96d;

    private final int n;

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.n = n;

        double[] results = new double[trials];

        for (int i = 0; i < trials; ++i) {
            results[i] = getOpenSitesRatio();
        }

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        double sqrtT = Math.sqrt(trials);
        confidenceLo = mean - (NORMAL_DISTRIBUTION_DEVIATION * stddev)/sqrtT;
        confidenceHi = mean + (NORMAL_DISTRIBUTION_DEVIATION * stddev)/sqrtT;
    }

    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }


    private double getOpenSitesRatio() {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            if (perc.isOpen(row, col)) continue;

            perc.open(row, col);
        }
        return 1.0d * perc.numberOfOpenSites() / (n * n);
    }

    public static void main(String[] args) {
        int n;
        int trials;

        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } else {
            n = StdIn.readInt();
            trials = StdIn.readInt();
        }
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        double time = stopwatch.elapsedTime();
        System.out.println(String.format("Time is %s\nHas %sx%s grid and %s trials\nMean: %s\nStdDev: %s\nConfidence: low - %s, hi - %s",
                time, n, n, trials, stats.mean(), stats.stddev(), stats.confidenceLo(), stats.confidenceHi()));

    }
}

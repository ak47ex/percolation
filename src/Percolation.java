import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;

    private final int n;

    private final boolean[][] cells;

    private final int topIndex;
    private final int botIndex;
    private int count;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be bigger than 0");

        this.n = n;
        topIndex = n * n;
        botIndex = n * n + 1;

        cells = new boolean[n][n];

        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        checkArgs(row, col);

        int i = row - 1;
        int j = col - 1;

        if (cells[i][j]) return;
        cells[i][j] = true;
        count++;

        int cellIndex = xyTo1D(i, j);

        if (i - 1 >= 0) {
            int index = xyTo1D(i - 1, j);
            if (isOpen(row - 1, col)) uf.union(cellIndex, index);
        } else if (i - 1 == -1) {
            uf.union(cellIndex, topIndex);
        }
        if (i + 1 < n) {
            int index = xyTo1D(i + 1, j);
            if (isOpen(row + 1, col)) uf.union(cellIndex, index);
        } else if (i + 1 == n) {
            uf.union(cellIndex, botIndex);
        }
        if (j - 1 >= 0) {
            int index = xyTo1D(i, j - 1);
            if (isOpen(row, col - 1)) uf.union(cellIndex, index);
        }
        if (j + 1 < n) {
            int index = xyTo1D(i, j + 1);
            if (isOpen(row, col + 1)) uf.union(cellIndex, index);
        }



    }

    public boolean isOpen(int row, int col) {
        checkArgs(row, col);
        return cells[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkArgs(row, col);
        return isOpen(row, col) && uf.connected(topIndex, xyTo1D(row - 1, col - 1));
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return uf.connected(topIndex, botIndex);
    }

    private int xyTo1D(int i, int j) {
        return j * n + i;
    }

    private void checkArgs(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) throw new IllegalArgumentException();
    }

}

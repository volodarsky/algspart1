import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final String ROW_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1 = "Row index should be greater or equals 1";
    private static final String COLUMN_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1 = "Column index should be greater or equals 1";

    private final WeightedQuickUnionUF quickUnionUF;
    private final boolean[][] grid;
    private final int gridSize;

    /**
     * create N-by-N grid, with all sites blocked
     *
     * @param N - size of grid
     */
    public Percolation(int N) {
        requirePositive(N, "Grid size should be positive");
        gridSize = N;
        grid = new boolean[N][N];
        quickUnionUF = new WeightedQuickUnionUF(N * N + 2);
        //consider (0) as virtualTopSite, (pow(N,2) - 1) as virtualBottomSite
        for (int i = 1; i <= N; i++) {
            quickUnionUF.union(0, i);
            quickUnionUF.union(N * N + 1, N * N + 1 - i);
        }
    }


    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        requirePositiveIndex(i, ROW_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1);
        requirePositiveIndex(j, COLUMN_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1);

        grid[i - 1][j - 1] = true;
        int N = gridSize;
        int target = N * (i - 1) + j;
        //site above
        if (target > N && isOpen(i - 1, j) && !quickUnionUF.connected(target, target - N)) {
            quickUnionUF.union(target, target - N);
        }
        //site below
        if (target < N * (N - 1) && isOpen(i + 1, j) && !quickUnionUF.connected(target, target + N)) {
            quickUnionUF.union(target, target + N);
        }
        //site left
        if ((target - 1) % N != 0 && isOpen(i, j - 1) && !quickUnionUF.connected(target, target - 1)) {
            quickUnionUF.union(target, target - 1);
        }
        //site right
        if (target % N != 0 && isOpen(i, j + 1) && !quickUnionUF.connected(target, target + 1)) {
            quickUnionUF.union(target, target + 1);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        requirePositiveIndex(i, ROW_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1);
        requirePositiveIndex(j, COLUMN_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1);
        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        requirePositiveIndex(i, ROW_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1);
        requirePositiveIndex(j, COLUMN_INDEX_SHOULD_BE_GREATER_OR_EQUALS_1);

        return isOpen(i, j) && quickUnionUF.connected(gridSize * (i - 1) + j, 0);
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(0, gridSize * gridSize + 1);
    }

    private static void requirePositiveIndex(int value, String message) {
        if (value <= 0) {
            throw new IndexOutOfBoundsException("Value [" + value + "]. " + message);
        }
    }

    private static void requirePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value [" + value + "]. " + message);
        }
    }

    public static void main(String[] args) {
    }

}

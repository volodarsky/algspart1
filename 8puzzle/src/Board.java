import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.*;

/**
 * @author vladimir.romanov
 * @version 1.0
 * @since UP...
 */
public class Board {

    private int[] elements;
    private int size;
    private Board goalBoard = null;
    private int hamming = -1;
    private int manhattan = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {

        if(blocks == null) throw new NullPointerException();

        assert blocks.length == blocks[0].length;
        size = blocks.length;

        int k = 1;
        elements = new int[size * size + 1];
        for (int[] block : blocks) {
            for (int j = 0; j < blocks.length; j++) {
                elements[k++] = block[j];
            }
        }
        assert countNulls() == 2;
    }

    private int countNulls() {
        int nulls = 0;
        for (int element : elements) {
            if (element == 0) nulls++;
        }
        return nulls;
    }

    // board dimension N
    public int dimension() {
        return size;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming == -1) {
            hamming = 0;
            for (int i = 1; i < elements.length; i++) {
                int element = elements[i];
                if(element != 0 && element != i){
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan == -1) {
            manhattan = 0;
            for (int i = 1; i < elements.length; i++) {
                int element = elements[i];
                if(element != 0){
                    int curr_row = i % size == 0 ? i / size : i / size + 1;
                    int curr_col = i % size;
                    if(curr_col == 0) curr_col = size;

                    int tag_row = element % size == 0 ? element / size : element / size + 1;
                    int tag_col = element % size;
                    if(tag_col == 0) tag_col = size;

                    manhattan = manhattan + (abs(curr_row - tag_row) + abs(curr_col - tag_col));
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {

        if (goalBoard == null) {
            goalBoard = cloneBoard();
            makeGoal(goalBoard);
        }
        return this.equals(goalBoard);
    }

    private void makeGoal(Board board) {
        final int[] elements = board.elements;
        // first is maximum
        elements[0] = Integer.MAX_VALUE;
        // zero is first but should be last
        Arrays.sort(elements);
        // empty should present
        assert elements[0] == 0;
        elements[size * size] = elements[0];
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        final Board board = cloneBoard();
        final int[] elements = board.elements;

        int j = 0, k = 0;
        for (int i = 1; i < elements.length; i = i + size) {
            j = k = i;
            while (elements[j] == 0) j++;
            while (elements[k] == 0 || j == k) k++;

            if (abs(k - j) == 1 && (j - 1)/size == (k - 1)/ size) break;
        }
        swap(elements, j, k);
        return board;
    }


    private Board cloneBoard() {
        final int[] copy = Arrays.copyOf(elements, elements.length);
        final int[][] blocks = new int[size][size];

        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(elements, i * size + 1, blocks[i], 0, size);
        }
        return new Board(blocks);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return Objects.equals(size, board.size) && Arrays.equals(elements, board.elements);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        final ArrayList<Board> neighbors = new ArrayList<>();
        int empty = getEmptyIndex();
        assert empty != 0;
        // has right
        if (empty % size != 0) {
            final Board board = cloneBoard();
            swap(board.elements, empty, empty + 1);
            neighbors.add(board);
        }
        //has left
        if (empty % size != 1) {
            final Board board = cloneBoard();
            swap(board.elements, empty, empty - 1);
            neighbors.add(board);
        }
        // has top
        if (empty > size) {
            final Board board = cloneBoard();
            swap(board.elements, empty, empty - size);
            neighbors.add(board);
        }
        //has bottom
        if (empty + size <= size * size) {
            final Board board = cloneBoard();
            swap(board.elements, empty, empty + size);
            neighbors.add(board);
        }
        return neighbors;
    }

    private int getEmptyIndex() {
        int empty = 0;
        for (int i = 1; i < elements.length; i++) {
            if (elements[i] == 0) {
                empty = i;
            }
        }
        return empty;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(size).append('\n');
        for (int i = 1; i < elements.length; i++) {
            builder.append(elements[i]).append(' ');
            if (i % size == 0) builder.append('\n');
        }
        return builder.toString();
    }

    private void swap(int[] a, int i, int j) {
        assert i < a.length && i < a.length;
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }

    // unit tests (not graded)
    public static void main(String[] args) {

        //final int[][] grid = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        final int[][] grid = {{0, 2}, {3, 1}};

        final Board original = new Board(grid);

        System.out.println(original.toString());

        final boolean goal = original.isGoal();
        //assert goal;
        //System.out.println(original.goalBoard);
        System.out.println(original.twin());

        final Iterable<Board> neighbors = original.neighbors();
        for (Board neighbor : neighbors) {
            //System.out.println(neighbor);
        }

        System.out.println(original.manhattan());
    }

    private static void testForCorner(){
        final int[][] grid = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        final Board original = new Board(grid);
    }

    private static void testForCenter(){
        final int[][] grid = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};

        final Board original = new Board(grid);
        final Iterable<Board> neighbors = original.neighbors();
        for (Board neighbor : neighbors) {

        }
    }

}

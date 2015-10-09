import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author vladimir.romanov
 * @version 1.0
 * @since UP...
 */
public class Solver {

    private List<Board> solution = new LinkedList<>();

    private final BoardNodeComparator comparator = new BoardNodeComparator();
    private TreeSet<Board> leafs = new TreeSet<>(new Comparator<Board>() {

        @Override
        public int compare(Board b1, Board b2) {
            final int i = b1.manhattan() - b2.manhattan();
            return i != 0 ? i : b1.equals(b2) ? 0 : -1;
        }

    });

    private boolean isSolvable;
    private Board goal;
    private Board goalTwin;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        final long start = System.currentTimeMillis();

        if (initial == null) {
            throw new NullPointerException();
        }
        if (initial.isGoal()) {
            isSolvable = true;
            solution.add(initial);
            return;
        }
        if (initial.twin().isGoal()) {
            isSolvable = false;
            return;
        }
        //System.out.println("Initial : " + initial);
        //initial = initial.twin();
        // System.out.println("Initial twin: " + initial);
        initGoal(initial.dimension());

        initial = initial.twin();

        int moves = 0;

        final MinPQ<BoardComparable> boards = new MinPQ<>();
        BoardComparable boardComparable = new BoardComparable(null, initial, moves);

        boards.insert(boardComparable);
        BoardComparable min = boards.delMin();
        Board currSearchNode = min.getBoard();
        leafs.add(currSearchNode);
        do {
            moves++;

            final Iterable<Board> neighbors = currSearchNode.neighbors();
            for (Board neighbor : neighbors) {

                final BoardComparable comparable = new BoardComparable(boardComparable, neighbor, moves);

                if (!leafs.contains(neighbor)) {
                    boards.insert(comparable);
                    leafs.add(neighbor);
                }
            }
            try {
                boardComparable = boards.delMin();
            } catch (Exception e) {
                e.printStackTrace();
            }
            currSearchNode = boardComparable.getBoard();

            System.out.print("Move [" + moves + "] Mantattan : [" + currSearchNode.manhattan() + "] Next min: " + currSearchNode);

            if (currSearchNode.equals(goalTwin)) {
                isSolvable = false;
                return;
            }
            if(System.currentTimeMillis() - start > 9000){
                isSolvable = false;
                return;
            }
        } while (!currSearchNode.equals(goal));

        // assert solveBoard.value.board.equals(currSearchNode);

        while (boardComparable != null) {
            solution.add(boardComparable.board);
            boardComparable = boardComparable.parent;
        }
        Collections.reverse(solution);

        isSolvable = true;
    }

    private void initGoal(int dimension) {
        int N = dimension;
        int k = 1;
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = k++;
            }
        }
        tiles[N - 1][N -1] = 0;
        goal = new Board(tiles);
        System.out.println(goal);

        swap(tiles, 0, 0, 0, 1);
        goalTwin = new Board(tiles);
        System.out.println(goalTwin);
    }

    private boolean exists(MinPQ<BoardComparable> boards, Board neighbor) {

        final Iterator<BoardComparable> iterator = boards.iterator();
        while (iterator.hasNext()) {
            BoardComparable next = iterator.next();
            if (next.getBoard().equals(neighbor)) {
                return true;
            }
        }
        return false;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable ? solution.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable ? solution : null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
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
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static void swap(int[][] a, int i, int j, int k, int l) {
        int temp = a[i][j];
        a[i][j] = a[k][l];
        a[k][l] = temp;
    }

    private static class BoardComparable implements Comparable<BoardComparable> {

        final BoardComparable parent;
        final Board board;
        final int move;
        final int weight;

        public BoardComparable(BoardComparable parent, Board board, int move) {
            this.parent = parent;
            this.board = board;
            this.move = move;
            this.weight = this.board.manhattan() + this.move;
        }

        public BoardComparable getParent() {
            return parent;
        }

        public Board getBoard() {
            return board;
        }

        public int getMove() {
            return move;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(BoardComparable that) {
            return getWeight() - that.getWeight();
        }

        public int manhattan(){
            return board.manhattan();
        }


    }


    private static class BoardNodeComparator implements Comparator<BoardComparable> {
        @Override
        public int compare(BoardComparable o1, BoardComparable o2) {
            final int compareTo = o1.compareTo(o2);
            return compareTo == 0 ? o1.equals(o2) ? 0 : -1 : compareTo;
        }
    }

}

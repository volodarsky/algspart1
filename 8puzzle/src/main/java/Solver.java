import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * @author vladimir.romanov
 * @version 1.0
 * @since UP...
 */
public class Solver {

    private List<Board> solution = new LinkedList<>();

    private final BoardNodeComparator comparator = new BoardNodeComparator();
    ArrayList<BoardComparable> leafs = new ArrayList<>();
    private boolean isSolvable;

    private static class BoardComparable implements Comparable<BoardComparable> {

        final BoardComparable parent;
        final Board board;
        final int move;

        public BoardComparable(BoardComparable parent, Board board, int move) {
            this.parent = parent;
            this.board = board;
            this.move = move;
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

        @Override
        public int compareTo(BoardComparable that) {
            return (this.board.manhattan() + this.move) - (that.getBoard().manhattan() + that.getMove());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BoardComparable))
                return false;
            BoardComparable that = (BoardComparable) o;
            return Objects.equals(board, that.board);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new NullPointerException();
        }
        if (initial.isGoal()) {
            isSolvable = true;
        }

        System.out.println(initial);

        final Board goalBoard = initial.goalBoard;
        final Board twin = goalBoard.cloneBoard();
        Board.swap(twin.elements, 1, 2);
        int moves = 0;

        final MinPQ<BoardComparable> boards = new MinPQ<>();
        final BoardComparable boardComparable = new BoardComparable(null, initial, moves);

        boards.insert(boardComparable);
        BoardComparable min = boards.delMin();
        Board currSearchNode = min.getBoard();

        //BoardNode<BoardComparable> solveBoard = new BoardNode<>(null, new BoardComparable(currSearchNode, moves));
        BoardTree boardTree = new BoardTree();

        do {
            moves++;

            final Iterable<Board> neighbors = currSearchNode.neighbors();
            for (Board neighbor : neighbors) {

                final BoardComparable comparable = new BoardComparable(neighbor, moves);
                final BoardNode<BoardComparable> boardNode = new BoardNode<>(solveBoard, comparable);

                if (!boardTree.hasNode(boardNode.value) && !neighbor.equals(initial)) {
                    boards.insert(comparable);
                    boardTree.addLeaf(boardNode.value);
                }
            }


            final BoardComparable comparable = boards.delMin();
            currSearchNode = comparable.getBoard();

            solveBoard = boardTree.searchNode(comparable);
            System.out.print("Next min: " + currSearchNode);
            if (solveBoard != null) {
                System.out.println("Next solve: " + solveBoard.value.board);
            }else{
                System.out.println();
            }


            if (solveBoard == null) {
                System.out.println();
            }

            if (currSearchNode.equals(twin)) {
                isSolvable = false;
                return;
            }
        } while (!currSearchNode.isGoal());

        // assert solveBoard.value.board.equals(currSearchNode);



        while (solveBoard.parent != null) {
            solution.add(solveBoard.value.board);
            solveBoard = solveBoard.parent;
        }
        Collections.reverse(solution);
        System.out.println("Solution size : " + solution.size());
        isSolvable = true;
    }

    private static class BoardTree {

        private final BoardNodeComparator comparator = new BoardNodeComparator();
        ArrayList<BoardComparable> leafs = new ArrayList<>();

        void addLeaf(BoardComparable boardComparable) {
            leafs.add(boardComparable);
            Collections.sort(leafs,comparator);
        }

        boolean hasNode(BoardComparable boardComparable) {
            final int binarySearch = Collections.binarySearch(leafs, boardComparable, comparator);
            if(binarySearch >= 0){
                final BoardComparable boardNode = leafs.get(binarySearch);
                if(boardNode.board.equals(boardComparable.board)){
                    return true;
                }
            }
            return false;
        }

        BoardComparable searchNode(BoardComparable board) {
            final int binarySearch = Collections.binarySearch(leafs, board, comparator);
            if(binarySearch >= 0){
                final BoardComparable node = leafs.get(binarySearch);
                if(node.board.equals(board.board)){
                    return node;
                }
            }
            return null;
        }
    }

    private static class BoardNodeComparator implements Comparator<BoardComparable> {

        @Override
        public int compare(BoardComparable o1, BoardComparable o2) {
            return o1.compareTo(o2);
        }
    }

    private static class BoardNode<T> {
        final BoardNode<T> parent;
        final T value;

        public BoardNode(BoardNode<T> parent, T value) {
            this.parent = parent;
            this.value = value;
        }
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
        return isSolvable ? solution.size() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable ? solution : null;
    }

    private static boolean isNeibours(Board first, Board second) {

        final Iterable<Board> neighbors = first.neighbors();
        for (Board neighbor : neighbors) {
            if (second.equals(neighbor)) {
                return true;
            }
        }
        return false;
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
}

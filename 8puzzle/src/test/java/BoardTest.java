import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author vladimir.romanov
 * @version 1.0
 */
public class BoardTest {

    @Test
    public void testManhattan() {
        Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        Assert.assertTrue(board.manhattan() == 0);

        board = new Board(new int[][] { { 0, 2, 3 }, { 4, 5, 6 }, { 7, 8, 1 } });
        Assert.assertTrue(board.manhattan() == 4);

        board = new Board(new int[][] { { 0, 3, 2 }, { 4, 5, 6 }, { 7, 8, 1 } });
        Assert.assertTrue(board.manhattan() == 6);

        board = new Board(new int[][] { { 0, 3, 2 }, { 5, 4, 6 }, { 7, 8, 1 } });
        Assert.assertTrue(board.manhattan() == 8);

        board = new Board(new int[][] { { 0, 4, 2 }, { 5, 3, 6 }, { 7, 8, 1 } });
        Assert.assertTrue(board.manhattan() == 10);
    }

    @Test
    public void testNeibours() {

        Board initial = new Board(new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } });

        Board n1 = new Board(new int[][] { { 1, 0, 3 }, { 4, 2, 6 }, { 7, 8, 5 } });
        Board n11 = new Board(new int[][] { { 1, 0, 3 }, { 4, 2, 6 }, { 7, 8, 5 } });
        Assert.assertEquals(n1, n11);
        Assert.assertEquals(n1, n1.cloneBoard());

        Board n2 = new Board(new int[][] { { 1, 2, 3 }, { 4, 8, 6 }, { 7, 0, 5 } });
        Board n22 = new Board(new int[][] { { 1, 2, 3 }, { 4, 8, 6 }, { 7, 0, 5 } });
        Assert.assertEquals(n2, n22);
        Assert.assertEquals(n2, n2.cloneBoard());

        Board n3 = new Board(new int[][] { { 1, 2, 3 }, { 0, 4, 6 }, { 7, 8, 5 } });
        Board n33 = new Board(new int[][] { { 1, 2, 3 }, { 0, 4, 6 }, { 7, 8, 5 } });
        Assert.assertEquals(n3, n33);
        Assert.assertEquals(n3, n3.cloneBoard());

        Board n4 = new Board(new int[][] { { 1, 2, 3 }, { 4, 6, 0 }, { 7, 8, 5 } });
        Board n44 = new Board(new int[][] { { 1, 2, 3 }, { 4, 6, 0 }, { 7, 8, 5 } });
        Assert.assertEquals(n4, n44);
        Assert.assertEquals(n4, n4.cloneBoard());

        final List<Board> boards = Arrays.asList(n1, n2, n3, n4);
        final Iterable<Board> neighbors = initial.neighbors();
        for (Board neighbor : neighbors) {
            Assert.assertTrue(contains(boards, neighbor));
        }
    }

    private boolean contains(List<Board> boards, Board neighbor) {
        for (Board board : boards) {
            if (neighbor.equals(board)) {
                return true;
            }
        }
        return false;
    }

}
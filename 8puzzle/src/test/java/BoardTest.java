import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

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

        Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } });

        Board n1 = new Board(new int[][] { { 1, 0, 3 }, { 4, 2, 6 }, { 7, 8, 5 } });
        Board n2 = new Board(new int[][] { { 1, 2, 3 }, { 4, 8, 6 }, { 7, 0, 5 } });
        Board n3 = new Board(new int[][] { { 1, 2, 3 }, { 0, 4, 6 }, { 7, 8, 5 } });
        Board n4 = new Board(new int[][] { { 1, 2, 3 }, { 4, 6, 0 }, { 7, 8, 5 } });

        final HashSet<Board> boardHashSet = new HashSet<>(Arrays.asList(new Board[] { n1, n2, n3, n4 }));
        final Iterable<Board> neighbors = board.neighbors();
        for (Board neighbor : neighbors) {
            System.out.println(neighbor);
            //Assert.assertTrue(boardHashSet.contains(neighbor));
        }

    }

}
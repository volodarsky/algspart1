import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author vladimir.romanov
 * @version 1.0
 */
public class BoardTest {

    @Test
    public void testManhattan() {

        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assert board.manhattan() == 0;

        board = new Board(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 8, 1}});
        assert board.manhattan() == 4;

        board = new Board(new int[][]{{0, 3, 2}, {4, 5, 6}, {7, 8, 1}});
        assert board.manhattan() == 6;

        board = new Board(new int[][]{{0, 3, 2}, {5, 4, 6}, {7, 8, 1}});
        assert board.manhattan() == 8;

        board = new Board(new int[][]{{0, 4, 2}, {5, 3, 6}, {7, 8, 1}});
        assert board.manhattan() == 10;
    }



}
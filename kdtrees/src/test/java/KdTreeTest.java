import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.*;

/**
 * @author vladimir.romanov
 * @version 1.0
 */
public class KdTreeTest {

    @Test
    public void testInsert() throws Exception {

        final KdTree kdTree = new KdTree();
        Assert.assertTrue(kdTree.isEmpty());
        Assert.assertEquals(0, kdTree.size());

        kdTree.insert(new Point2D(0.5, 0.5));
        Assert.assertEquals(1, kdTree.size());
        Assert.assertFalse(kdTree.isEmpty());

        kdTree.insert(new Point2D(0.5, 0.5));
        Assert.assertEquals(1, kdTree.size());
        Assert.assertFalse(kdTree.isEmpty());

        kdTree.insert(new Point2D(0.75, 0.5));
        Assert.assertEquals(2, kdTree.size());
        Assert.assertFalse(kdTree.isEmpty());

        kdTree.insert(new Point2D(0.25, 0.5));
        Assert.assertEquals(3, kdTree.size());
        Assert.assertFalse(kdTree.isEmpty());

        kdTree.insert(new Point2D(0.25, 0.25));
        Assert.assertEquals(4, kdTree.size());
        Assert.assertFalse(kdTree.isEmpty());

        kdTree.insert(new Point2D(0.25, 0.75));
        Assert.assertEquals(5, kdTree.size());
        Assert.assertFalse(kdTree.isEmpty());

        kdTree.insert(new Point2D(0.75, 0.25));
        Assert.assertEquals(6, kdTree.size());

        kdTree.insert(new Point2D(0.75, 0.75));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.75, 0.75));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.25, 0.25));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.5, 0.5));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.5, 0.5));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.75, 0.5));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.25, 0.5));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.25, 0.25));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.25, 0.75));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.75, 0.25));
        Assert.assertEquals(7, kdTree.size());

        kdTree.insert(new Point2D(0.75, 0.75));
        Assert.assertEquals(7, kdTree.size());
    }


   /*
   @Test
   public void testFloorAndCeiling() {

        final KdTree kdTree = new KdTree();
        final Point2D center = new Point2D(0.5, 0.5);
        kdTree.insert(center);

        Point2D tested = new Point2D(0.6, 0.5);
        Point2D floor = kdTree.root.floor(tested);
        Assert.assertEquals(center, floor);

        floor = kdTree.root.floor(tested, true);
        Assert.assertNull(floor);

        Point2D ceiling = kdTree.root.ceiling(tested);
        Assert.assertNull(ceiling);

        ceiling = kdTree.root.ceiling(tested, true);
        Assert.assertEquals(center, ceiling);

        tested = new Point2D(0.5, 0.5);
        floor = kdTree.root.floor(tested);
        Assert.assertEquals(center, floor);

        floor = kdTree.root.floor(tested, true);
        Assert.assertEquals(center, floor);

        ceiling = kdTree.root.ceiling(tested);
        Assert.assertEquals(center, ceiling);

        ceiling = kdTree.root.ceiling(tested, true);
        Assert.assertEquals(center, ceiling);

        tested = new Point2D(0.3, 0.5);
        floor = kdTree.root.floor(tested);
        Assert.assertNull(floor);

        floor = kdTree.root.floor(tested, true);
        Assert.assertEquals(center, floor);

        ceiling = kdTree.root.ceiling(tested);
        Assert.assertEquals(center, ceiling);

        ceiling = kdTree.root.ceiling(tested, true);
        Assert.assertNull(ceiling);

        Point2D left = new Point2D(0.25, 0.5);
        kdTree.insert(left);

        tested = new Point2D(0.5, 0.6);
        floor = kdTree.root.floor(tested);
        Assert.assertEquals(left, floor);

        floor = kdTree.root.floor(tested, true);
        Assert.assertEquals(center, floor);

        tested = new Point2D(0.5, 0.3);
        floor = kdTree.root.floor(tested);
        Assert.assertNull(floor);

        floor = kdTree.root.floor(tested, true);
        Assert.assertEquals(left, floor);

        Point2D right = new Point2D(0.25, 0.25);
        kdTree.insert(right);

        tested = new Point2D(0.2, 0.3);
        floor = kdTree.root.floor(tested);
        Assert.assertNull(floor);

        tested = new Point2D(0.4, 0.3);
        floor = kdTree.root.floor(tested);
        Assert.assertEquals(right, floor);
    }*/


    @Test
    public void testSubTree(){

        final KdTree kdTree = new KdTree();
        final Point2D center = new Point2D(0.5, 0.5);
        kdTree.insert(center);

        final Point2D west = new Point2D(0.25, 0.5);
        kdTree.insert(west);

        final Point2D east = new Point2D(0.75, 0.5);
        kdTree.insert(east);

        final Point2D southwest = new Point2D(0.25, 0.25);
        kdTree.insert(southwest);

        final Point2D northwest = new Point2D(0.25, 0.75);
        kdTree.insert(northwest);

        final Point2D southeast = new Point2D(0.75, 0.25);
        kdTree.insert(southeast);

        final Point2D northeast = new Point2D(0.75, 0.75);
        kdTree.insert(northeast);

        Assert.assertTrue(kdTree.contains(center));
        Assert.assertTrue(kdTree.contains(west));
        Assert.assertTrue(kdTree.contains(east));
        Assert.assertTrue(kdTree.contains(northeast));
        Assert.assertTrue(kdTree.contains(northwest));
        Assert.assertTrue(kdTree.contains(southeast));
        Assert.assertTrue(kdTree.contains(southwest));

        final Iterable<Point2D> range = kdTree.range(new RectHV(0.0, 0.0, 1.0, 1.0));
        for (Point2D point2D : range) {
            System.out.println(point2D);
        }



    }

}
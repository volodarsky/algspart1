import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

/**
 * @author vladimir.romanov
 * @version 1.0
 */
public class KdTree {

    private boolean isEmpty;
    private int size;
    private TreeSet<Point2D> set = new TreeSet<>();
    private TreeNode root = null;

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;

    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        requireNonNull(p);

        if (root == null) {
            initRoot(p);
        } else {
            TreeNode curr = root;
            if (curr.even) {
                if (p.x() < curr.value.x()) {
                    TreeNode left = new TreeNode();
                    left.value = p;
                    left.even = !curr.even;
                    left.rect = new RectHV(curr.rect.xmin(), curr.rect.ymin(), curr.value.x(), curr.rect.ymax());
                    curr.left = left;
                } else {
                    TreeNode right = new TreeNode();
                    right.value = p;
                    right.even = !curr.even;
                    right.rect = new RectHV(curr.value.x(), curr.rect.ymin(), curr.rect.ymax(), curr.rect.ymax());
                    curr.right = right;
                }
            }else{

            }


        }


        set.add(p);
    }

    private void initRoot(Point2D p) {
        root = new TreeNode();
        root.value = p;
        root.even = true;
        root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        requireNonNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : set) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        requireNonNull(rect);
        final Point2D lower = new Point2D(rect.xmin(), rect.ymin());
        final Point2D higher = new Point2D(rect.xmax(), rect.ymax());
        return set.subSet(lower, higher);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        requireNonNull(p);
        if (set.isEmpty()) {
            return null;
        }

        Point2D floor = set.floor(p);
        Point2D ceiling = set.ceiling(p);
        if (floor == null) {
            return ceiling;
        }
        if (ceiling == null) {
            return floor;
        }

        if (floor.equals(p)) floor = ceiling;
        if (ceiling.equals(p)) ceiling = floor;

        return p.distanceTo(floor) <= p.distanceTo(ceiling) ? floor : ceiling;
    }

    private static void requireNonNull(Object o) {
        if (o == null) throw new NullPointerException();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }


    private static class TreeNode {

        RectHV rect;
        Point2D value;
        TreeNode left;
        TreeNode right;
        boolean even;


    }
}

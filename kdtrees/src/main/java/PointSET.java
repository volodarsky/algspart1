import edu.princeton.cs.algs4.*;

import java.util.*;

public class PointSET {

    private boolean isEmpty;
    private int size;
    private TreeSet<Point2D> set = new TreeSet<>();

    // private SET<Point2D> set = new SET<>();

    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();

    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        requireNonNull(p);
        set.add(p);
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
        final ArrayList<Point2D> result = new ArrayList<>();
        if (set.isEmpty()) {
            return result;
        }
        for (Point2D point2D : set) {
            if(rect.contains(point2D)){
                result.add(point2D);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        requireNonNull(p);
        if (set.isEmpty()) {
            return null;
        }
        Point2D result = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D point2D : set) {
            if(p.equals(point2D)){
                return p;
            }
            final double distanceTo = p.distanceTo(point2D);
            if(distanceTo < minDistance){
                minDistance = distanceTo;
                result = point2D;
            }
        }
        return result;
    }

    private static void requireNonNull(Object o) {
        if (o == null)
            throw new NullPointerException();
    }
}
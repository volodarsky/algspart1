import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

public class PointSET {

    private boolean isEmpty;
    private int size;
    private TreeSet<Point2D> set = new TreeSet<>();

    //private SET<Point2D> set = new SET<>();

    // construct an empty set of points
    public PointSET(){
    }

    // is the set empty?
    public boolean isEmpty(){
        return set.isEmpty();

    }

    // number of points in the set
    public int size(){
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public  void insert(Point2D p){
        requireNonNull(p);
        set.add(p);
    }

    // does the set contain point p?
    public  boolean contains(Point2D p){
        requireNonNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw(){
        for (Point2D point2D : set) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        requireNonNull(rect);
        final Point2D lower = new Point2D(rect.xmin(), rect.ymin());
        final Point2D higher = new Point2D(rect.xmax(), rect.ymax());
        return set.subSet(lower,higher);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        requireNonNull(p);
        if(set.isEmpty()){
            return null;
        }

        final Point2D floor = set.floor(p);
        final Point2D ceiling = set.ceiling(p);
        if(floor == null){
            return ceiling;
        }
        if(ceiling == null){
            return floor;
        }

        return p.distanceTo(floor) <= p.distanceTo(ceiling) ? floor : ceiling;
    }

    private static void requireNonNull(Object o){
        if(o == null) throw new NullPointerException();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args){
    }
}
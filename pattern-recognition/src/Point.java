import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    private final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            if(p1 == null || p2 == null) throw new NullPointerException();

            int compare = Double.compare(slopeTo(p1), slopeTo(p2));
            //System.out.println("(" + x + ", " + y + ")" + " - " + p1 + " - " + p2 + " : " + slopeTo(p1) + " : " + slopeTo(p2) + " : " + compare);
            return compare;
        }
    };

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Comparator<Point> slopeOrder(){
        return SLOPE_ORDER;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        double ydiff = this.y - that.y;
        double xdiff = this.x - that.x;

        if (ydiff == 0.0 && xdiff == 0.0) {
            return Double.NEGATIVE_INFINITY;
        } else if (ydiff == 0.0) {
            return 0.0;
        } else if (xdiff == 0.0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return ydiff / xdiff;
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
    public int compareTo(Point that) {
        return this.y - that.y != 0 ? this.y - that.y : this.x - that.x;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {

        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);

        Point p1 = new Point(5, 6);
        Point p2 = new Point(0, 3);
        Point p3 = new Point(4, 0);

        p1.draw();
        p2.draw();
        p3.draw();
        p1.drawTo(p2);
        p2.drawTo(p3);
        p3.drawTo(p1);
    }


}

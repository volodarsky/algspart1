import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class BruteCollinearPoints {

    private final Point[] points;
    private int numberOfSegments;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        final Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        checkEquals(sortedPoints);
        this.points = sortedPoints;
        calculate();
    }

    private void checkEquals(Point[] points) {
        Arrays.sort(Arrays.copyOf(points,points.length));
        for (int i = 0; i < points.length - 1; i++) {
            if(points[i] != null && points[i + 1] != null && points[i].compareTo(points[i + 1]) == 0){
                throw new IllegalArgumentException("Input contains equals points");
            }
        }
    }

    private void calculate() {

        final ArrayList<LineSegment> segments = new ArrayList<>();
        int N = points.length;
        Point[] fourPoints = new Point[4];
        for (int i = 0; i < N; i++) {

            fourPoints[0] = points[i];
            for (int j = i + 1; j < N; j++) {

                fourPoints[1] = points[j];
                for (int k = j + 1; k < N; k++) {

                    fourPoints[2] = points[k];
                    if (!fourPoints[0].equals(fourPoints[1]) && !fourPoints[1].equals(fourPoints[2])) {

                        if (fourPoints[0].slopeTo(fourPoints[1]) == fourPoints[1].slopeTo(fourPoints[2])) {
                            for (int l = k + 1; l < N; l++) {

                                fourPoints[3] = points[l];
                                if (!fourPoints[0].equals(fourPoints[3])) {
                                    if (fourPoints[0].slopeTo(fourPoints[1]) == fourPoints[0].slopeTo(fourPoints[2]) && fourPoints[0].slopeTo(fourPoints[1]) == fourPoints[0].slopeTo(fourPoints[3])) {

                                        Point min = getMinPoint(fourPoints);
                                        Point max = getMaxPoint(fourPoints);

                                        segments.add(new LineSegment(min, max));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final int size = segments.size();
        numberOfSegments = size;
        lineSegments = segments.toArray(new LineSegment[size]);
    }

    private Point getMaxPoint(Point[] points) {
        Point max = null;
        for (Point point : points) {
            if (max == null) {
                max = point;
                continue;
            }
            if (max.compareTo(point) <= 0) {
                max = point;
            }
        }
        return max;
    }

    private Point getMinPoint(Point[] points) {
        Point min = null;
        for (Point point : points) {
            if (min == null) {
                min = point;
                continue;
            }
            if (min.compareTo(point) >= 0) {
                min = point;
            }
        }
        return min;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(this.lineSegments, lineSegments.length);
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        In in = new In(args[0]);

        int N = in.readInt();
        int[][] ints = new int[N][2];
        for (int i = 0; i < N; i++) {
            ints[i][0] = in.readInt();
            ints[i][1] = in.readInt();
        }

        //HashSet found = new HashSet();

        for (int i = 0; i < N; i++) {
            Point p = new Point(ints[i][0], ints[i][1]);

            for (int j = i + 1; j < N; j++) {
                Point q = new Point(ints[j][0], ints[j][1]);

                for (int k = j + 1; k < N; k++) {
                    Point r = new Point(ints[k][0], ints[k][1]);

                    if (!p.equals(q) && !p.equals(r)) {
                        if (p.slopeTo(q) == p.slopeTo(r)) {

                            for (int l = k + 1; l < N; l++) {
                                Point s = new Point(ints[l][0], ints[l][1]);

                                if (!p.equals(s)) {
                                    if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {

                                        // if (!found.contains(p)) {

                                        // found.add(p);
                                        // found.add(q);
                                        // found.add(r);
                                        // found.add(s);

                                        Point[] sorted = { p, q, r, s };
                                        Arrays.sort(sorted, new Comparator<Point>() {
                                            @Override
                                            public int compare(Point o1, Point o2) {
                                                return o1.compareTo(o2);
                                            }
                                        });

                                        StdOut.println(sorted[0] + " -> " + sorted[1] + " -> " + sorted[2] + " -> "
                                                + sorted[3]);
                                        sorted[0].draw();
                                        sorted[1].draw();
                                        sorted[2].draw();
                                        sorted[3].draw();

                                        sorted[0].drawTo(sorted[3]);

                                        // sorted[0].drawTo(sorted[1]);
                                        // sorted[1].drawTo(sorted[2]);
                                        // sorted[2].drawTo(sorted[3]);
                                    }
                                    // }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

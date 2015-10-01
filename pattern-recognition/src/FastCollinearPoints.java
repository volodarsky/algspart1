import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final Point[] points;
    private int numberOfSegments;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        final Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        checkEquals(sortedPoints);
        this.points = sortedPoints;
        calculate();
    }

    private void checkEquals(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if(points[i] != null && points[i + 1] != null && points[i].compareTo(points[i + 1]) == 0){
                throw new IllegalArgumentException("Input contains equals points");
            }
        }
    }

    private void calculate() {

        final ArrayList<LineSegment> segments = new ArrayList<>();
        Point[] sorted = Arrays.copyOf(points, points.length);
        TreeSet<Point> collinears = new TreeSet<>();
        allCollinears = new TreeSet<>();

        outer : for (int i = 0; i < points.length; i++) {
            final Point original = points[i];

            Arrays.sort(sorted, original.slopeOrder());
            boolean found = false;
            for (int j = 1; j < sorted.length - 2; j++) {
                Point point = sorted[j];

                if (original.slopeTo(sorted[j]) == sorted[j].slopeTo(sorted[j + 1]) && sorted[j].slopeTo(sorted[j + 1]) == sorted[j + 1].slopeTo(sorted[j + 2])) {

                    if (allCollinears.contains(original) && allCollinears.contains(sorted[j]) && allCollinears.contains(sorted[j + 1]) && allCollinears.contains(sorted[j + 2])) {
                        continue outer;
                    }

                    if (!found) {
                        collinears.add(original);
                        found = true;
                    }

                    collinears.add(sorted[j]);
                    collinears.add(sorted[j + 1]);
                    collinears.add(sorted[j + 2]);
                } else if (found) {
                    collinears.add(sorted[j + 1]);
                    break;
                }
            }
            if (collinears.size() >= 4) {
                Point first = collinears.first();
                Point last = collinears.last();

                segments.add(new LineSegment(first, last));
                allCollinears.addAll(collinears);
            }
            collinears.clear();
        }

        final int size = segments.size();
        numberOfSegments = size;
        lineSegments = segments.toArray(new LineSegment[size]);
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(this.lineSegments, lineSegments.length);
    }

    private static TreeSet<Object> allCollinears;

    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        //System.out.println(Double.compare(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        //System.out.println(Double.compare(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));

        In in = new In(args[0]);
        int N = in.readInt();

        Point[] points = new Point[N];

        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        find(points);
        //System.out.println("The end");
    }

    private static void find(Point[] points) {

        Point[] sorted = Arrays.copyOf(points, points.length);
        TreeSet<Point> collinears = new TreeSet<>();
        allCollinears = new TreeSet<>();

        outer : for (int i = 0; i < points.length; i++) {
            final Point original = points[i];


            Arrays.sort(sorted, original.slopeOrder());

            //StdOut.println(original);
            //StdOut.println(Arrays.toString(sorted));
            //StdOut.println(Arrays.toString(points));

            boolean found = false;
            for (int j = 1; j < sorted.length - 2; j++) {
                Point point = sorted[j];

                if (original.slopeTo(sorted[j]) == sorted[j].slopeTo(sorted[j + 1]) && sorted[j].slopeTo(sorted[j + 1]) == sorted[j + 1].slopeTo(sorted[j + 2])) {

                    if (allCollinears.contains(original) && allCollinears.contains(sorted[j]) && allCollinears.contains(sorted[j + 1]) && allCollinears.contains(sorted[j + 2])) {
                        continue outer;
                    }

                    if (!found) {
                        collinears.add(original);
                        found = true;
                    }

                    collinears.add(sorted[j]);
                    collinears.add(sorted[j + 1]);
                    collinears.add(sorted[j + 2]);
                } else if (found) {
                    collinears.add(sorted[j + 1]);
                    break;
                }
            }
            print(collinears);
        }
    }

    private static void print(TreeSet<Point> collinears) {
        if (collinears.size() >= 4) {

            Point first = collinears.first();
            Point last = collinears.last();
            Iterator<Point> iterator = collinears.iterator();
            while (iterator.hasNext()) {
                Point point = iterator.next();
                point.draw();

                if (last.equals(point)) {
                    StdOut.print(point);
                    first.drawTo(last);
                } else {
                    StdOut.print(point + " -> ");
                }
            }
            StdOut.println();
            allCollinears.addAll(collinears);
        }
        collinears.clear();
    }
}

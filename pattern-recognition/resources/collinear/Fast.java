package collinear;

import java.util.*;

public class Fast {

    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        In in = new In(args[0]);
        int N = in.readInt();

        Point[] points = new Point[N];

        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        find(points, 0);
        //System.out.println("The end");
    }

    private static void find(Point[] points, int low) {

        //StdOut.println("low [" + low + "]");
        if (low >= points.length - 1) return;
        int N = points.length;

        Point original = points[low];
        Arrays.sort(points, new PointComparator(original.SLOPE_ORDER));

        StdOut.println(original);
        StdOut.println(Arrays.toString(points));
        StdOut.println();

        int from = low + 1;
        Double slopeTo = null;
        List<Point> sorted = new ArrayList<>();
        Set<Double> slopeTos = new HashSet<>();

        for (int i = from; i < N - 1; i++) {

            if (!original.equals(points[i])) {
                double slopeTo1 = original.slopeTo(points[i]);
                double slopeTo2 = points[i].slopeTo(points[i + 1]);
                if (slopeTo1 == slopeTo2) {
                        if (slopeTos.contains(slopeTo1)) {
                            continue;
                        }

                    if (slopeTo == null) {
                        sorted.add(original);
                        sorted.add(points[i]);
                    }
                    sorted.add(points[i + 1]);
                    slopeTo = slopeTo1;

                } else if (slopeTo1 != slopeTo2) {
                    print(sorted);
                    slopeTos.add(slopeTo);
                }
            }
        }



        //if (from == low + 1) return;

        find(points, low + 1);
    }

    private static void print(List<Point> points) {
        if (points.size() >= 4) {
            Collections.sort(points, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    return o1.compareTo(o2);
                }
            });

            Point first = null;
            Point last;
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                point.draw();

                if (i == 0) {
                    first = point;
                }
                if (i + 1 == points.size()) {
                    last = point;
                    StdOut.print(point);
                    first.drawTo(last);
                }else {
                    StdOut.print(point + " -> ");
                }
            }
            StdOut.println();
            points.clear();
        }
    }

    private static class PointComparator implements Comparator<Point> {

        Comparator<Point> comparator;

        public PointComparator(Comparator<Point> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(Point o1, Point o2) {
            if (o1 == o2 || o1.equals(o2)) return 0;
            return comparator.compare(o1, o2) == 0 ? -1 : 1;
        }
    }
}

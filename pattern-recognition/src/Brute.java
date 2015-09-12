import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class Brute {
    /*
6
19000  10000
18000  10000
32000  10000
21000  10000
 1234   5678
14000  10000
     */
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

        HashSet found = new HashSet();

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

                                        //if (!found.contains(p)) {

                                        //found.add(p);
                                        //found.add(q);
                                        //found.add(r);
                                        //found.add(s);

                                        Point[] sorted = {p, q, r, s};
                                        Arrays.sort(sorted, new Comparator<Point>() {
                                            @Override
                                            public int compare(Point o1, Point o2) {
                                                return o1.compareTo(o2);
                                            }
                                        });


                                        StdOut.println(sorted[0] + " -> " + sorted[1] + " -> " + sorted[2] + " -> " + sorted[3]);
                                        sorted[0].draw();
                                        sorted[1].draw();
                                        sorted[2].draw();
                                        sorted[3].draw();

                                        sorted[0].drawTo(sorted[3]);

                                        //sorted[0].drawTo(sorted[1]);
                                        //sorted[1].drawTo(sorted[2]);
                                        //sorted[2].drawTo(sorted[3]);
                                    }
                                    //}
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author vladimir.romanov
 * @version 1.0
 */
public class KdTree {

    private static final Point2D LOWER = new Point2D(0.0, 0.0);
    private static final Point2D HIGHER = new Point2D(1.0, 1.0);
    private int size;
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
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        requireNonNull(p);
        size++;
        if (root == null) {
            initRoot(p);
        } else {
            TreeNode curr = root;
            while (true) {
                if (curr.even) {
                    if (p.x() < curr.value.x()) {
                        if (curr.left == null) {
                            TreeNode left = newNode(p, curr);
                            left.rect = new RectHV(curr.rect.xmin(), curr.rect.ymin(), curr.value.x(),
                                    curr.rect.ymax());
                            curr.left = left;
                            break;
                        } else {
                            curr = curr.left;
                        }
                    } else {
                        if (curr.right == null) {
                            TreeNode right = newNode(p, curr);
                            right.rect = new RectHV(curr.value.x(), curr.rect.ymin(), curr.rect.xmax(),
                                    curr.rect.ymax());
                            curr.right = right;
                            break;
                        } else {
                            curr = curr.right;
                        }
                    }
                } else {
                    if (p.y() < curr.value.y()) {
                        if (curr.left == null) {
                            TreeNode left = newNode(p, curr);
                            left.rect = new RectHV(curr.rect.xmin(), curr.rect.ymin(), curr.rect.xmax(),
                                    curr.value.y());
                            curr.left = left;
                            break;
                        } else {
                            curr = curr.left;
                        }
                    } else {
                        if (curr.right == null) {
                            TreeNode right = newNode(p, curr);
                            right.rect = new RectHV(curr.rect.xmin(), curr.value.y(), curr.rect.xmax(),
                                    curr.rect.ymax());
                            curr.right = right;
                            break;
                        } else {
                            curr = curr.right;
                        }
                    }
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        requireNonNull(p);
        return root != null && root.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : root.subSet(LOWER, HIGHER)) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        requireNonNull(rect);
        final Point2D lower = new Point2D(rect.xmin(), rect.ymin());
        final Point2D higher = new Point2D(rect.xmax(), rect.ymax());
        return root.subSet(lower, higher);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        requireNonNull(p);
        if (isEmpty()) {
            return null;
        }

        Point2D floor = root.floor(p);
        Point2D ceiling = root.ceiling(p);
        if (floor == null) {
            return ceiling;
        }
        if (ceiling == null) {
            return floor;
        }

        if (floor.equals(p))
            floor = ceiling;
        if (ceiling.equals(p))
            ceiling = floor;
        return p.distanceTo(floor) <= p.distanceTo(ceiling) ? floor : ceiling;
    }

    private TreeNode newNode(Point2D p, TreeNode curr) {
        TreeNode node = new TreeNode();
        node.value = p;
        node.even = !curr.even;
        return node;
    }

    private void initRoot(Point2D p) {
        root = new TreeNode();
        root.value = p;
        root.even = true;
        root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    }

    private static void requireNonNull(Object o) {
        if (o == null)
            throw new NullPointerException();
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

        public boolean contains(Point2D p) {
            if (p.equals(value)) {
                return true;
            } else if (p.compareTo(value) < 0) {
                return left != null && left.contains(p);
            } else {
                return right != null && right.contains(p);
            }
        }

        public Point2D floor(Point2D p) {
            requireNonNull(p);
            if (value.compareTo(p) == 0) {
                return value;
            } else if (value.compareTo(p) < 0) {
                return left.value.compareTo(p) > 0 ? left.value : left.floor(p);
            } else {
                return right.value.compareTo(p) < 0 ? value : right.floor(p);
            }
        }

        public Point2D ceiling(Point2D p) {
            requireNonNull(p);
            if (value.compareTo(p) == 0) {
                return value;
            } else if (value.compareTo(p) < 0) {
                return left.value.compareTo(p) > 0 ? value : left.ceiling(p);
            } else {
                return right.value.compareTo(p) < 0 ? right.value : right.ceiling(p);
            }
        }

        private static List<Point2D> orderedFrom(TreeNode node, Point2D min, Point2D max, List<Point2D> list) {
            if (node == null)
                return list;
            if (node.left != null && node.left.value.compareTo(min) >= 0) {
                list.addAll(orderedFrom(node.left, min, max, list));
            }
            if (node.value.compareTo(min) >= 0 && node.value.compareTo(max) <= 0) {
                list.add(node.value);
            }
            if (node.right != null && node.right.value.compareTo(max) <= 0) {
                list.addAll(orderedFrom(node.right, min, max, list));
            }
            return list;
        }

        public Iterable<Point2D> subSet(Point2D lower, Point2D higher) {
            final Point2D ceiling = ceiling(lower);
            final Point2D floor = floor(higher);

            return orderedFrom(this, ceiling, floor, new ArrayList<Point2D>());
        }
    }
}

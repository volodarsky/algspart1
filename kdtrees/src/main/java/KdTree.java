import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

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
        if (isEmpty()) {
            initRoot(p);
            size++;
        } else {
            if (!contains(p)) {
                final boolean insert = root.insert(p);
                if (insert) {
                    size++;
                }
                assert insert;
            }
        }
    }

    private void insert1(Point2D p) {
        if (contains(p))
            return;
        boolean newAdded = false;
        if (root == null) {
            initRoot(p);
        } else {
            TreeNode curr = root;
            while (true) {
                if (TreeNode.slightlyEquals(p, curr.value)) {
                    return;
                }
                if (curr.even) {
                    if (p.x() <= curr.value.x()) {
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
        size++;
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
        if (root == null) {
            return new ArrayList<>();
        }
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
        return root.nearest(p, root.value);
    }

    private double distanceTo(Point2D p1, Point2D p2) {
        if (p2 == null)
            return Double.MAX_VALUE;
        return p1.distanceTo(p2);
    }

    private static TreeNode newNode(Point2D p, TreeNode curr) {
        TreeNode node = new TreeNode();
        node.value = p;
        node.even = !curr.even;
        return node;
    }

    private void initRoot(Point2D p) {
        root = new TreeNode();
        root.value = p;
        root.even = true;
        root.rect = new RectHV(0.000000, 0.000000, 1.000000, 1.000000);
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
            } else if (onLeft(p)) {
                return left != null && left.contains(p);
            } else if (onRight(p)) {
                return right != null && right.contains(p);
            } else {
                System.out.println("Impossible!");
                return false;
            }
        }

        public boolean insert(Point2D p) {
            if (p.equals(value)) {
                return false;
            }
            if (onLeft(p)) {
                if (left == null) {
                    final TreeNode treeNode = newNode(p, this);
                    treeNode.rect = even ? new RectHV(rect.xmin(), rect.ymin(), value.x(), rect.ymax()) :
                            new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), value.y());
                    left = treeNode;
                    return true;
                } else {
                    return left.insert(p);
                }
            } else if (onRight(p)) {
                if (right == null) {
                    TreeNode treeNode = newNode(p, this);
                    treeNode.rect = even ? new RectHV(rect.xmin(), value.y(), rect.xmax(), rect.ymax()) :
                            new RectHV(rect.xmin(), value.y(), rect.xmax(),rect.ymax());
                    right = treeNode;
                    return true;
                } else {
                    return right.insert(p);
                }
            }
            return false;
        }

        public Iterable<Point2D> range(RectHV rect) {
            final ArrayList<Point2D> list = new ArrayList<>();
            range(rect, list);
            return list;
        }

        public void range(RectHV rect, List<Point2D> list) {
            if (left != null && left.rect.intersects(rect)) {
                left.range(rect, list);
            }
            if (rect.contains(value)) {
                list.add(value);
            }
            if (right != null && right.rect.intersects(rect)) {
                right.range(rect, list);
            }
        }

        public Point2D nearest(Point2D p, Point2D nearest) {

            if (slightlyEquals(p, nearest))
                return nearest;
            double minDistance = p.distanceSquaredTo(nearest);
            if (left != null) {
                final double distanceTo = p.distanceTo(left.value);
                if (distanceTo <= minDistance) {
                    minDistance = distanceTo;
                    nearest = left.value;
                }
            }
            if (right != null) {
                final double distanceTo = p.distanceTo(right.value);
                if (distanceTo <= minDistance) {
                    minDistance = distanceTo;
                    nearest = right.value;
                }
            }
            Point2D lnearest = nearest;
            if (left != null && left.rect.distanceTo(nearest) < minDistance) {
                lnearest = left.nearest(p, nearest);
            }
            Point2D rnearest = nearest;
            if (right != null && right.rect.distanceTo(nearest) < minDistance) {
                rnearest = right.nearest(p, nearest);
            }

            final double ldist = p.distanceTo(lnearest);
            final double rdist = p.distanceTo(rnearest);
            if (ldist < minDistance) {
                nearest = lnearest;
                minDistance = ldist;
            }
            if (rdist < minDistance) {
                nearest = rnearest;
                minDistance = rdist;
            }
            return nearest;
        }

        public Point2D floor(Point2D p) {
            return floor(p, false);
        }

        public Point2D floor(Point2D p, boolean inclusive) {
            requireNonNull(p);
            if (value.equals(p)) {
                return value;
            } else if (onLeft(p)) {
                if (left != null && left.onRight(p)) {
                    return inclusive ? value : left.value;
                } else if (left != null && left.onLeft(p)) {
                    return left.floor(p, inclusive);
                } else {
                    return inclusive ? value : null;
                }
            } else if (onRight(p)) {
                return right == null ? inclusive ? null : value : right.floor(p, inclusive);
            } else {
                return inclusive ? value : null;
            }
        }

        private static boolean slightlyEquals(Point2D p1, Point2D p2) {
            return Math.abs(p1.x() - p2.x()) < 0.000001 && Math.abs(p1.y() - p2.y()) < 0.000001;
        }

        public Point2D ceiling(Point2D p) {
            return ceiling(p, false);
        }

        public Point2D ceiling(Point2D p, boolean inclusive) {
            requireNonNull(p);
            if (value.equals(p)) {
                return value;
            } else if (onRight(p)) {
                if (right != null && right.onLeft(p)) {
                    return inclusive ? value : right.value;
                } else if (right != null && right.onRight(p)) {
                    return right.ceiling(p, inclusive);
                } else {
                    return inclusive ? value : null;
                }
            } else if (onLeft(p)) {
                return left == null ? inclusive ? null : value : left.ceiling(p, inclusive);
            } else {
                return inclusive ? value : null;
            }
        }

        private boolean onLeft(final Point2D p) {
            return (even && (p.x() <= value.x())) || (!even && (p.y() <= value.y()));
        }

        private boolean onRight(final Point2D p) {
            return (even && (p.x() > value.x())) || (!even && (p.y() > value.y()));
        }

        private static List<Point2D> orderedFrom(TreeNode node, Point2D min, Point2D max, List<Point2D> list) {
            if (node == null) {
                return list;
            }
            if (node.left != null && node.left.onRight(min)) {
                list.addAll(orderedFrom(node.left, min, max, list));
            }
            if (node.onRight(min) && node.onLeft(max)) {
                list.add(node.value);
            }
            if (node.right != null && node.right.onLeft(max)) {
                list.addAll(orderedFrom(node.right, min, max, list));
            }
            return list;
        }

        public Iterable<Point2D> subSet(Point2D lower, Point2D higher) {
            final Point2D ceiling = ceiling(lower);
            final Point2D floor = floor(higher);
            if (ceiling == null || floor == null) {
                return new ArrayList<>();
            }
            return orderedFrom(this, lower, higher, new ArrayList<Point2D>());
        }
    }
}

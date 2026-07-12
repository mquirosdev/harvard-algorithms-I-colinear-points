import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    private class SlopeComparator implements Comparator<Point> {
        private Point ref;

        public SlopeComparator(Point ref) {
            this.ref = ref;
        }

        public int compare(Point p1, Point p2) {
            double slopeToP1 = ref.slopeTo(p1);
            double slopeToP2 = ref.slopeTo(p2);
            return Double.compare(slopeToP1, slopeToP2);
        }
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        double deltaY = (double) (that.y - this.y);
        double deltaX = (double) (that.x - this.x);

        if (deltaX == 0 && deltaY == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (deltaY == 0) {
            return 0.0;
        } else {
            return deltaY / deltaX;
        }
    }

    public int compareTo(Point that) {
        int comparison = Integer.compare(this.y, that.y);

        if (comparison != 0) {
            return comparison;
        }

        return Integer.compare(this.x, that.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator(this);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000)
        };
    }
}

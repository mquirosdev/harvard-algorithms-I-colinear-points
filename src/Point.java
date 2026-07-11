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

        if(deltaX == 0 && deltaY == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
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
        Point p = new Point(1,1);

//        System.out.println("compare (1,1) vs (1,1): " + p.compareTo(new Point(1,1))); // 0
//        System.out.println("compare (1,1) vs (2,1): " + p.compareTo(new Point(2,1))); // -1
//        System.out.println("compare (1,1) vs (0,1): " + p.compareTo(new Point(0,1))); // 1
//        System.out.println("compare (1,1) vs (0,2): " + p.compareTo(new Point(0,2))); // -1
//        System.out.println("compare (1,1) vs (2,0): " + p.compareTo(new Point(2,0))); // 1

//        System.out.println(
//                "ref: (1,1) -> (2,2) vs (2,4)"
//                        + p.slopeOrder().compare(new Point(2,2), new Point(2,4))
//        ); // -1
//
//        System.out.println(
//                "ref: (1,1) -> (2,4) vs (2,2)"
//                        + p.slopeOrder().compare(new Point(2,4), new Point(2,2))
//        ); // 1
//
//        System.out.println(
//                "ref: (1,1) -> (2,2) vs (3,3)"
//                        + p.slopeOrder().compare(new Point(2,2), new Point(3,3))
//        ); // 0

//        System.out.println("(1,1)-(2,2) " + p.slopeTo(new Point(2,2))); // 1
//        System.out.println("(1,1)-(2,0) " + p.slopeTo(new Point(2,0))); // -1
//        System.out.println("(1,1)-(2,1) " + p.slopeTo(new Point(2,1))); // 0
//        System.out.println("(1,1)-(1,1) " + p.slopeTo(new Point(1,1))); // -Infinity
//        System.out.println("(1,1)-(1,2) " + p.slopeTo(new Point(1,2))); // +Infinity
    }
}

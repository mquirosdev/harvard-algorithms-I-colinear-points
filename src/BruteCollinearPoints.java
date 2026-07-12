import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument can not be null");
        }

        this.points = points.clone();
        validatePointsPresence();
        validatePointsUniqueness();

        ArrayList<LineSegment> list = computeSegmentList();
        this.segments = list.toArray(new LineSegment[list.size()]);
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    private ArrayList<LineSegment> computeSegmentList() {
        ArrayList<LineSegment> list = new ArrayList<LineSegment>();

        if (points.length < 4) {
            return list;
        }

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];

                        boolean collinear = p.slopeTo(q) == p.slopeTo(r) &&
                                p.slopeTo(q) == p.slopeTo(s) &&
                                p.slopeTo(r) == p.slopeTo(s);


                        if (collinear) {
                            LineSegment lineSegment = new LineSegment(p, s);
                            list.add(lineSegment);
                        }
                    }
                }
            }
        }

        return list;
    }

    private void validatePointsPresence() {
        for (Point currentPoint : points) {
            if (currentPoint == null) {
                throw new IllegalArgumentException("Null points not allowed");
            }
        }
    }

    private void validatePointsUniqueness() {
        Arrays.sort(this.points);

        for (int i = 0; i < points.length - 1; i++) {
            Point currentPoint = points[i];
            Point nextPoint = points[i + 1];

            if (currentPoint.compareTo(nextPoint) == 0) {
                throw new IllegalArgumentException("Repeated points not allowed");
            }
        }
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

        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        for (LineSegment ls : bf.segments()) {
            System.out.println(ls);
        }
    }
}

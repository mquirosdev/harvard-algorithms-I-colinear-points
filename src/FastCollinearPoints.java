import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
    private final LineSegment[] segments;

    private class SegmentCandidate {
        private Point min;
        private Point max;
        private final double slope;

        public SegmentCandidate(Point min, Point max) {
            this.min = min;
            this.max = max;
            this.slope = min.slopeTo(max);
        }

        public Point getMin() {
            return this.min;
        }

        public double getSlope() {
            return this.slope;
        }

        public Point getMax() {
            return this.max;
        }

        public boolean pointFits(Point point) {
            double slope = point.slopeTo(min);
            return slope == Double.NEGATIVE_INFINITY || slope == this.slope;
        }

        public void addPoint(Point point) {
            if (!pointFits(point)) return;
            if (point.compareTo(min) == -1) this.min = point;
            if (point.compareTo(max) == 1) this.max = point;
        }
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument can not be null");
        }

        this.points = points.clone();
        validatePointsPresence();
        validatePointsUniqueness(); // this method sorts the points

        this.segments = computeLineSegments();
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    private LineSegment[] computeLineSegments() {
        LineSegment[] lineSegments = new LineSegment[0];
        ArrayList<SegmentCandidate> segmentCandidates = new ArrayList<SegmentCandidate>();

        if (points.length < 4) {
            return lineSegments;
        }

        // Populates segmentCandidates
        for (Point origin : points) {
            computeSegmentCandidates(origin, segmentCandidates);
        }

        lineSegments = new LineSegment[segmentCandidates.size()];
        for (int i = 0; i < segmentCandidates.size(); i++) {
            lineSegments[i] = new LineSegment(
                    segmentCandidates.get(i).getMin(),
                    segmentCandidates.get(i).getMax()
            );
        }

        return lineSegments;
    }

    private void computeSegmentCandidates(Point origin, ArrayList<SegmentCandidate> segmentCandidates) {
        Point[] reorderedPoints = points.clone();
        Arrays.sort(reorderedPoints, origin.slopeOrder()); // Organize points by slope

        int i = 0;
        for (int j = 0; j <= reorderedPoints.length; j++) {
            // Process ranges with the same slope -> points in the same segment
            if (j == reorderedPoints.length || origin.slopeTo(reorderedPoints[j]) != origin.slopeTo(reorderedPoints[i])) {
                // Only process as a segment those ranges with more than 4 points
                // 4 points -> origin and 3+ points in batch
                if (j - i >= 3) {
                    Point minPoint = reorderedPoints[i];
                    Point maxPoint = reorderedPoints[j - 1];
                    double slope = minPoint.slopeTo(maxPoint);

                    // Traverse points in the range
                    for (int k = i; k < j; k++) {
                        Point currentPoint = reorderedPoints[k];
                        boolean belongsToOtherCandidate = false;

                        for (SegmentCandidate candidate : segmentCandidates) {
                            if (candidate.getSlope() == slope && candidate.pointFits(currentPoint)) {
                                belongsToOtherCandidate = true;
                                candidate.addPoint(currentPoint);
                            }
                        }

                        if (!belongsToOtherCandidate) {
                            SegmentCandidate candidate = new SegmentCandidate(minPoint, maxPoint);
                            segmentCandidates.add(candidate);
                        }
                    }
                }
                i = j;
            }
        }
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
        int n = StdIn.readInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }
    }
}

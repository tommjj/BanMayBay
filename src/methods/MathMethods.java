package methods;

import java.awt.Point;

public class MathMethods {

    public static Point.Double getPointTL(double ox, double oy, double x, double y, double length) {
        double a = ox - x;
        double b = oy - y;
        double scale = length / Math.sqrt(a * a + b * b);
        return new Point.Double(ox + (-a * scale), oy + (-b * scale));
    }

    public static Point.Double getVetorWithLength(double ox, double oy, double x, double y, double length) {
        double a = ox - x;
        double b = oy - y;
        double scale = length / Math.sqrt(a * a + b * b);
        return new Point.Double((double) -a * scale, (double) -b * scale);
    }

    public static double getAngle(double ox, double oy, double x, double y) {
        double a = ox - x;
        double b = oy - y;
        double corner = (int) (Math.atan2(-a, b) * 180 / Math.PI);
        return corner < 0 ? 360 + corner : corner;
    }
    
    public static double getLength(double ox, double oy, double x, double y) {
        double a = ox - x;
        double b = oy - y;
        return Math.sqrt(a * a + b * b);
    }

    public static Point.Double turnCenter(double a, double b, double x, double y, double o) {
        double tX = a + (x - a) * Math.cos(o) - (y - b) * Math.sin(o);
        double tY = b + (x - a) * Math.sin(o) + (y - b) * Math.cos(o);
        return new Point.Double(tX, tY);
    }

    public static Point.Double turnCenter(double x, double y, double o) {
        double tX = x * Math.cos(o) - y * Math.sin(o);
        double tY = x * Math.sin(o) + y * Math.cos(o);
        return new Point.Double(tX, tY);
    }

    static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)
                && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) {
            return true;
        }

        return false;
    }
    
    static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y);

        if (val == 0) {
            return 0; 
        }
        return (val > 0) ? 1 : 2; 
    }

    static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        return o4 == 0 && onSegment(p2, q1, q2); 
    }
}

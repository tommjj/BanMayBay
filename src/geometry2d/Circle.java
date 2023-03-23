package geometry2d;

import java.awt.Rectangle;

public class Circle {

    public int x, y;
    public int r;

    public Circle(int oX, int oY, int r) {
        this.x = oX;
        this.y = oY;
        this.r = r;
    }

    public boolean intersect(Circle circle) {
        double val = Math.sqrt((x - circle.x) * (x - circle.x) + (y - circle.y) * (y - circle.y));
        return val <= r + circle.r;
    }

    public boolean intersect(int x, int y) {
        double val = Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
        return val <= r;
    }

    public boolean intersect(Rectangle ret) {
        if (intersect(ret.x, ret.y)) {
            return true;
        }
        if (intersect(ret.x + ret.width, ret.y)) {
            return true;
        }
        if (intersect(ret.x, ret.y + ret.height)) {
            return true;
        }
        if (intersect(ret.x + ret.width, ret.y + ret.height)) {
            return true;
        }
        if (ret.contains(x, y)) {
            return true;
        }
        if (ret.contains(x + r, y)) {
            return true;
        }
        if (ret.contains(x, y + r)) {
            return true;
        }
        if (ret.contains(x - r, y)) {
            return true;
        }
        if (ret.contains(x, y - r)) {
            return true;
        }
        return false;
    }  
}

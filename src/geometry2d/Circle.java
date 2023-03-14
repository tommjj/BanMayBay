package geometry2d;

import java.awt.Rectangle;

public class Circle {

    public int oX, oY;
    public int r;

    public Circle(int oX, int oY, int r) {
        this.oX = oX;
        this.oY = oY;
        this.r = r;
    }

    public boolean intersect(Circle circle) {
        double val = Math.sqrt((oX - circle.oX) * (oX - circle.oX) + (oY - circle.oY) * (oY - circle.oY));
        return val <= r + circle.r;
    }

    public boolean intersect(int x, int y) {
        double val = Math.sqrt((oX - x) * (oX - x) + (oY - y) * (oY - y));
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
        if (ret.contains(oX, oY)) {
            return true;
        }
        if (ret.contains(oX + r, oY)) {
            return true;
        }
        if (ret.contains(oX, oY + r)) {
            return true;
        }
        if (ret.contains(oX - r, oY)) {
            return true;
        }
        if (ret.contains(oX, oY - r)) {
            return true;
        }
        return false;
    }  
}

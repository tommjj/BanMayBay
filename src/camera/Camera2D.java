package camera;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Camera2D {
    
    private double x, y;
    private int wight, height;
    private float scale = 1.0f;
    private CameraDraw draw;

    public Camera2D(double x, double y, int wight, int height, CameraDraw draw) {
        this.x = x;
        this.y = y;
        this.wight = wight;
        this.height = height;
        this.draw = draw;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWight() {
        return wight;
    }

    public void setWight(int wight) {
        this.wight = wight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public CameraDraw getDraw() {
        return draw;
    }

    public void setDraw(CameraDraw draw) {
        this.draw = draw;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create((int)-x, (int)-y, wight, height);
        draw.Draw(g2d);
        g2d.dispose();
    }
    public interface CameraDraw {
        public void Draw(Graphics2D g);          
    }
}

package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import methods.MathMethods;


public class Bullet {
    private double x, y;
    private double veterX, veterY;
    private Rectangle hitbox;
    private int updateTime = 0;
    
    public Bullet(double x, double y, double mX, double mY) {
        this.x = x;
        this.y = y;
        Point.Double p = MathMethods.getVetorWithLength(x, y, mX, mY, 10);
        veterX = p.x;
        veterY = p.y;
        hitbox = new Rectangle((int)x,(int) y, 5, 5);
    }
    
    private void updateHitBox() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    
    public void update() {
        x += veterX;
        y += veterY;
        updateHitBox();
        updateTime++;
       
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
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

    public int getUpdateTime() {
        return updateTime;
    }
    
    
}

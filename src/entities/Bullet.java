package entities;

import geometry2d.Circle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import methods.MathMethods;

public class Bullet extends RangedWeapon {

    private Circle hitbox;
    private int updateTime = 0;
    private static float speed = 10;

    public Bullet(double x, double y, double mX, double mY) {
        this.x = x;
        this.y = y;
        Point.Double p = MathMethods.getVetorWithLength(x, y, mX, mY, speed);
        vectorX = p.x;
        vectorY = p.y;
        hitbox = new Circle((int) x, (int) y, 2);
    }

    private void updateHitBox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public void update() {
        x += vectorX;
        y += vectorY;
        updateHitBox();
        updateTime++;

    }

    public void draw(Graphics g, BufferedImage img) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(hitbox.x, hitbox.y);
        g2d.rotate(Math.toRadians(MathMethods.getAngle(x, y, x+vectorX, y+vectorY)));

        g2d.drawImage(img, (int) (-2), (int) (-3),null);
        g2d.dispose();

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

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float speed) {
        Bullet.speed = speed;
    }
}

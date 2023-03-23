package entities;

import gameStates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import methods.MathMethods;
import utiliz.Constants.WindowConstants;

public class Enemy {

    private double x, y;
    private Rectangle hitbox;
    private Random rand = new Random();
    private double speed = 2;
    private int nextSpawn = 0;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x, y, 15, 15);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void resetSpeed() {
        speed = 1;
    }

    public void reset() {
        switch (nextSpawn) {
            case 0:
                x = rand.nextInt(WindowConstants.WIDTH_SIZE);
                y = 0;
                break;
            case 1:
                x = 0;
                y = rand.nextInt(WindowConstants.HEIGHT_SIZE);
                break;
            case 2:
                x = rand.nextInt(WindowConstants.WIDTH_SIZE);
                y = WindowConstants.HEIGHT_SIZE;
                break;
            case 3:
                x = WindowConstants.WIDTH_SIZE;
                y = rand.nextInt(WindowConstants.HEIGHT_SIZE);
                break;
            default:
                throw new AssertionError();
        }
        nextSpawn = rand.nextInt(4);
        if (speed < 4) {
            speed += 0.1f;
        } else if (speed < 6) {
            speed += 0.05f;
        } else if (speed < 8) {
            speed += 0.02f;
        } else if (speed < 10) {
            speed += 0.01f;
        }
    }

    public void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public void update(Playing playing) {
        Point.Double p = MathMethods.getVetorWithLength(x, y, (int) playing.getPlayer().getoX(), (int) playing.getPlayer().getoY(), speed);
        x += p.x;
        y += p.y;
        updateHitbox();
    }

    public void draw(Graphics g) {
        
        g.setColor(Color.WHITE);
        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public int getNextSpawn() {
        return nextSpawn;
    }
}

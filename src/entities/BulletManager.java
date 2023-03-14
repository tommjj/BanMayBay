package entities;

import gameStates.Playing;
import java.awt.Graphics;
import java.util.ArrayList;

public class BulletManager {

    private ArrayList<Bullet> bullets;
    private ArrayList<Rocket> rockets;
    private Playing playing;

    public BulletManager(Playing playing) {
        bullets = new ArrayList<>();
        rockets = new ArrayList<>();
        
        this.playing = playing;
    }

    public void update() {
        updateBullets();

        updateRockets();
    }

    public void updateBullets() {
        int n = bullets.size();
        for (int i = n - 1; i >= 0; i--) {
            if (bullets.get(i).getUpdateTime() < 300) {
                if (playing.getEnemy().getHitbox().intersects(bullets.get(i).getHitbox())) {
                    playing.getEnemy().reset();
                    bullets.remove(i);
                    playing.setScore(playing.getScore() + 1);
                    return;
                } else {
                    bullets.get(i).update();
                }
            } else {
                bullets.remove(i);
            }
        }
    }

    private void updateRockets() {
        int n = rockets.size();
        for (int i = n - 1; i >= 0; i--) {
            if (rockets.get(i).getUpdateTime() < 800) {
                if (playing.getEnemy().getHitbox().intersects(rockets.get(i).getHitbox())) {
                    playing.getEnemy().reset();
                    rockets.remove(i);
                    playing.setScore(playing.getScore() + 1);
                    return;
                } else {
                    rockets.get(i).update(playing.getEnemy().getHitbox().x, playing.getEnemy().getHitbox().y);
                }
            } else {
                rockets.remove(i);
            }
        }
    }

    public void draw(Graphics g) {
        drawBullets(g);

        drawRockets(g);
    }

    public void drawRockets(Graphics g) {
        for (int i = rockets.size() - 1; i >= 0; i--) {
            if (i < rockets.size()) {    
                   rockets.get(i).draw(g);             
            }
        }
    }

    public void drawBullets(Graphics g) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            if (i < bullets.size()) {
                bullets.get(i).draw(g);
            }
        }
    }

    public void addBullet(double x, double y, double mX, double mY) {
        bullets.add(new Bullet(x, y, mX, mY));
    }

    public void addRocket(double x, double y, double mX, double mY) {
        rockets.add(new Rocket(x, y, mX, mY));
    }

    public void reset() {
        bullets.removeAll(bullets);
    }
}

package entities;

import gameStates.Playing;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utiliz.LoadSave;

public class WeaponsManager {

    private ArrayList<Bullet> bullets;
    private ArrayList<Rocket> rockets;
    private Playing playing;

    private int maxBullet = 120;
    private int currentBullet = maxBullet;
    private int reloadSpeed = 3, reloadTick = 0;

    private int attackTick = 0, attackSpeadMachineGun = 7, attackSpeadRocketGun = 15;

    public static final int MACHINE_GUN = 0;
    public static final int ROCKET_GUN = 1;
    
    private BufferedImage bulletImg;

    public WeaponsManager(Playing playing) {
        bullets = new ArrayList<>();
        rockets = new ArrayList<>();
        Rocket.setRadarRange(500);
        Rocket.setMinSpeed(6);
        Rocket.setSpeedUp(0.07f);
        Rocket.setMaxSteeringAngle(2);
        this.playing = playing;
        loadImg();
    }
    
    private void loadImg() {
        bulletImg = LoadSave.getImage(LoadSave.BULLET);
    }

    public void update() {
        updateBullets();

        updateRockets();
        attackTick++;
    }

    public void fire(double x1, double y1, double x2, double y2, int type) {
        if (attackTick >= attackSpeadMachineGun && currentBullet > 0 && type == MACHINE_GUN) {
            addBullet(x1, y1, x2, y2);
            attackTick = 0;
            reloadTick = 0;
            currentBullet--;
        } else if (attackTick >= attackSpeadRocketGun && currentBullet > 4 && type == ROCKET_GUN) {
            addRocket(x1, y1, x2, y2);
            attackTick = 0;
            reloadTick = 0;
            currentBullet -= 5;
        } 
    }

    public void reload() {
        if (reloadTick >= reloadSpeed && currentBullet < maxBullet) {
            currentBullet++;
            reloadTick = 0;
        }
        
        reloadTick++;
    }

    public void updateBullets() {
        int n = bullets.size();
        for (int i = n - 1; i >= 0; i--) {
            if (bullets.get(i).getUpdateTime() < 300) {
                if (bullets.get(i).getHitbox().intersect(playing.getEnemy().getHitbox())) {
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
                if (rockets.get(i).getHitbox().intersect(playing.getEnemy().getHitbox())) {
                    playing.getEnemy().reset();
                    rockets.remove(i);
                    playing.setScore(playing.getScore() + 1);
                    return;
                } else {
                    rockets.get(i).update(playing.getEnemy().getHitbox().x + 7, playing.getEnemy().getHitbox().y + 7);
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
                if (rockets.get(i) != null) {
                    rockets.get(i).draw(g);
                }
            }
        }
    }

    public void drawBullets(Graphics g) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            if (i < bullets.size()) {
                if (bullets.get(i) != null) {
                    bullets.get(i).draw(g, bulletImg);
                }
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
        bullets.clear();
        rockets.clear();
    }

    public int getCurrentBullet() {
        return currentBullet;
    }

    public void setCurrentBullet(int currentBullet) {
        this.currentBullet = currentBullet;
    }

    public int getMaxBullet() {
        return maxBullet;
    }

    public void setMaxBullet(int maxBullet) {
        this.maxBullet = maxBullet;
    }
    
    
}

package entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javafx.scene.shape.Line;

import gameStates.Playing;
import gameStates.Statemethods;
import geometry2d.Circle;
import methods.MathMethods;
import utiliz.Constants;
import utiliz.DrawMethods;
import utiliz.LoadSave;

public class Player implements Statemethods {

    private Playing playing;

    private double oX = Constants.GameConstants.WIDTH_SIZE / 2;
    private double oY = Constants.GameConstants.HEIGHT_SIZE / 2;

    private boolean checkMousePressed = false;
    private int currentMouseX, currentMouseY;
    private int vectorX, vectorY;
    private int checkBotton;
    private final Circle hitbox;

    private boolean left = false, right = false, up = false, down = false;
    private final int speed = 5;

    private int dashTick = 0;
    private final int dashTime = 10;
    private boolean dleft = false, dright = false, dup = false, ddown = false;
    private boolean dash = true, dashFirst = false;
    private final ArrayList<Line> lastVetor;

    private int maxLifeP = 3;
    private int lifeP = maxLifeP;

    private final BufferedImage img;

    private final WeaponsManager weaponsManager;

    public Player(Playing playing) {
        this.lastVetor = new ArrayList<>();
        this.playing = playing;
        hitbox = new Circle((int) oX, (int) oY, 10);
        weaponsManager = new WeaponsManager(playing);
        img = LoadSave.getImage(LoadSave.m);
    }

    public void updateHitbox() {
        hitbox.x = (int) oX;
        hitbox.y = (int) oY;
    }

    public void chageX(double value) {
        if (oX + value < 0) {
            oX = 0;
            return;
        }
        if (oX + value > Constants.GameConstants.WIDTH_SIZE) {
            oX = Constants.GameConstants.WIDTH_SIZE;
            return;
        }
        oX += value;
    }

    public void chageY(double value) {
        if (oY + value < 0) {
            oY = 0;
            return;
        }
        if (oY + value > Constants.GameConstants.HEIGHT_SIZE) {
            oY = Constants.GameConstants.HEIGHT_SIZE;
            return;
        }
        oY += value;
    }

    public void updateVetor() {
        Point.Double p = MathMethods.getPointTL((int) oX, (int) oY, currentMouseX, currentMouseY, 500);

        vectorX = (int) p.x;
        vectorY = (int) p.y;
    }

    @Override
    public void update() {
        if (dash) {
            if (!(dashFirst)) {
                dleft = left;
                dright = right;
                dup = up;
                ddown = down;
                dashFirst = true;
                lastVetor.clear();
            }
            if (dashTick % 2 == 0) {
                lastVetor.add(new Line(oX, oY, vectorX, vectorY));
            }
            if (!dleft && dright) {
                chageX(20);
            }
            if (dleft && !dright) {
                chageX(-20);
            }
            if (!dup && ddown) {
                chageY(20);
            }
            if (dup && !ddown) {
                chageY(-20);
            }
            dashTick++;
            if (dashTick >= dashTime) {
                dash = false;
                dleft = false;
                dright = false;
                dup = false;
                ddown = false;
                dashTick = 0;
                dash = false;
                dashFirst = false;
            }
        } else {
            if (!left && right) {
                chageX(speed);
            }
            if (left && !right) {
                chageX(-speed);
            }
            if (!up && down) {
                chageY(speed);
            }
            if (up && !down) {
                chageY(-speed);
            }
        }

        weaponsManager.update();

        updateVetor();

        updateHitbox();

        if (hitbox.intersect(playing.getEnemy().getHitbox())) {
            lifeP--;
            playing.getEnemy().reset();
        }
        if (lifeP <= 0) {
            playing.setGameover(true);
        }

        if (checkMousePressed) {
            if (checkBotton == MouseEvent.BUTTON1) {
                weaponsManager.fire(oX, oY, vectorX, vectorY, WeaponsManager.MACHINE_GUN);
            } else {
                weaponsManager.fire(oX, oY, vectorX, vectorY, WeaponsManager.ROCKET_GUN);
            }
        } else {
            weaponsManager.reload();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) oX - 2, (int) oY - 2, 4, 4);

        if (dash && dashFirst) {
            float a = 0.6f / lastVetor.size();
            float ap = 0;
            for (int i = 0; i < lastVetor.size(); i++) {
                Graphics2D g2dt = (Graphics2D) g.create();
                ap += a;
                AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ap > 1 ? 1 : ap);
                g2dt.setComposite(alcom);
                g2dt.translate(lastVetor.get(i).getStartX(), lastVetor.get(i).getStartY());
                g2dt.rotate(Math.toRadians(MathMethods.getAngle((int) lastVetor.get(i).getStartX(), (int) lastVetor.get(i).getStartY(), (int) lastVetor.get(i).getEndX(), (int) lastVetor.get(i).getEndY())));
                g2dt.drawImage(img, (int) (-45 * 0.4f), (int) (-40 * 0.4f), (int) (90 * 0.4f), (int) (80 * 0.4f), null);
                g2dt.dispose();
            }
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(oX, oY);
        g2d.rotate(Math.toRadians(MathMethods.getAngle((int) oX, (int) oY, vectorX, vectorY)));

        g2d.drawImage(img, (int) (-45 * 0.4f), (int) (-40 * 0.4f), (int) (90 * 0.4f), (int) (80 * 0.4f), null);

        DrawMethods.drawAmmo(g, weaponsManager.getMaxBullet(), weaponsManager.getCurrentBullet(), (int) oX - 25, (int) oY + 22, 50, 2, 2, Color.WHITE);
        g2d.dispose();

        g.setColor(Color.WHITE);
        g.fillRect(currentMouseX - 7, currentMouseY - 2, 14, 4);
        g.fillRect(currentMouseX - 2, currentMouseY - 7, 4, 14);

        weaponsManager.draw(g);

    }

    public void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        Font font = new Font("Serif", Font.BOLD, 45);
        g.setFont(font);
        g.drawString("Score: " + playing.getScore(), 5, 80);

        //draw health
        DrawMethods.drawHealth(g, maxLifeP, lifeP, 0, 0, 450, 20, 10, Color.WHITE);

        //currentBullet
        DrawMethods.drawAmmo(g, weaponsManager.getMaxBullet(), weaponsManager.getCurrentBullet(), 0, 22, 435, 8, 5, Color.WHITE);
    }

    

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        checkMousePressed = true;

        checkBotton = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        checkMousePressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setCurrentMouse(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setCurrentMouse(e);
    }

    public void setCurrentMouse(int x, int y) {
        currentMouseX = x;
        currentMouseY = y;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_SPACE:
                dash = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_CONTROL:
                break;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public double getoX() {
        return oX;
    }

    public double getoY() {
        return oY;
    }

    public void resetBool() {
        checkMousePressed = false;
        left = false;
        right = false;
        up = false;
        down = false;
        dleft = false;
        dright = false;
        dup = false;
        ddown = false;
    }

    public void reset() {
        lifeP = maxLifeP;
        oX = Constants.WindowConstants.WIDTH_SIZE / 2;
        oY = Constants.WindowConstants.HEIGHT_SIZE / 2;

        resetBool();
        weaponsManager.reset();
    }

    private void setCurrentMouse(MouseEvent e) {
        currentMouseX = e.getX();
        currentMouseY = e.getY();
    }

    public void setoX(double oX) {
        this.oX = oX;
    }

    public void setoY(double oY) {
        this.oY = oY;
    }

    public void setMaxLifeP(int maxLifeP) {
        this.maxLifeP = maxLifeP;
    }

    public int getVectorX() {
        return vectorX;
    }

    public void setVectorX(int vectorX) {
        this.vectorX = vectorX;
    }

    public int getVectorY() {
        return vectorY;
    }

    public void setVectorY(int vectorY) {
        this.vectorY = vectorY;
    }
}

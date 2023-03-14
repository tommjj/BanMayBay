package entities;

import gameStates.Playing;
import gameStates.Statemethods;
import geometry2d.Circle;
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
import methods.MathMethods;
import utiliz.Constants;
import utiliz.LoadSave;

public class Player implements Statemethods {

    private Playing playing;

    private double oX = Constants.WindowConstants.WIDTH_SIZE / 2;
    private double oY = Constants.WindowConstants.HEIGHT_SIZE / 2;

    private boolean checkMousePressed = false;
    private boolean checkCtrlDown = false;
    private int currentMouseX, currentMouseY;
    private int vetorX, vetorY;
    private int checkBotton;

    private boolean left = false, right = false, up = false, down = false;

    private int attackTick = 0, attackSpead = 7;

    private int speed = 4, dashTick = 0, dashTime = 10;
    private boolean dleft = false, dright = false, dup = false, ddown = false;
    private boolean dash = true, dashFirst = false;
    private ArrayList<Line> lastVetor = new ArrayList<>();

    private int maxLifeP = 6;
    private int lifeP = maxLifeP;

    private int maxBullet = 120;
    private int currentBullet = maxBullet;
    private Circle hitbox;

    private BufferedImage img;

    private BulletManager bulletManager;

    public Player(Playing playing) {
        this.playing = playing;
        hitbox = new Circle((int) oX, (int) oY, 10);
        bulletManager = new BulletManager(playing);
        img = LoadSave.getImage(LoadSave.m);
    }

    public void updateHitbox() {
        hitbox.oX = (int) oX;
        hitbox.oY = (int) oY;
    }

    public void chageX(double value) {
        if (oX + value < 0) {
            oX = 0;
            return;
        }
        if (oX + value > Constants.WindowConstants.WIDTH_SIZE) {
            oX = Constants.WindowConstants.WIDTH_SIZE;
            return;
        }
        oX += value;
    }

    public void chageY(double value) {
        if (oY + value < 0) {
            oY = 0;
            return;
        }
        if (oY + value > Constants.WindowConstants.HEIGHT_SIZE) {
            oY = Constants.WindowConstants.HEIGHT_SIZE;
            return;
        }
        oY += value;
    }
    
    public void updateVetor() {
        Point.Double p = MathMethods.getPointTL((int) oX, (int) oY, currentMouseX, currentMouseY, 500);

        vetorX = (int) p.x;
        vetorY = (int) p.y;
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
                lastVetor.add(new Line(oX, oY, vetorX, vetorY));
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

        bulletManager.update();

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
            if (attackTick >= attackSpead && currentBullet > 0 && checkBotton == MouseEvent.BUTTON1) {
                bulletManager.addBullet(oX, oY, vetorX, vetorY);
                attackTick = 0;
                currentBullet--;
            } else if (attackTick >= attackSpead && currentBullet > 2) {
                Point.Double p1 = MathMethods.turnCenter(oX, oY, vetorX, vetorY, Math.toRadians(-1.5));
                Point.Double p2 = MathMethods.turnCenter(oX, oY, vetorX, vetorY, Math.toRadians(1.5));

//                bulletManager.addBullet(oX, oY, vetorX, vetorY);
//                bulletManager.addBullet(oX, oY, p1.x, p1.y);
//                bulletManager.addBullet(oX, oY, p2.x, p2.y);
                bulletManager.addRocket(oX, oY, vetorX, vetorY);
                attackTick = 0;
                currentBullet -= 3;
            } else if (attackTick >= attackSpead && currentBullet < maxBullet) {
                currentBullet++;
                attackTick = 0;
            }
            attackTick++;
        } else {
            if (attackTick >= attackSpead && currentBullet < maxBullet) {
                currentBullet++;
                attackTick = 0;
            }
            attackTick++;
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
        g2d.rotate(Math.toRadians(MathMethods.getAngle((int) oX, (int) oY, vetorX, vetorY)));

        g2d.drawImage(img, (int) (-45 * 0.4f), (int) (-40 * 0.4f), (int) (90 * 0.4f), (int) (80 * 0.4f), null);
        g2d.dispose();

        g.fillRect(currentMouseX - 7, currentMouseY - 2, 14, 4);
        g.fillRect(currentMouseX - 2, currentMouseY - 7, 4, 14);

        bulletManager.draw(g);

        g.setColor(Color.black);
        Font font = new Font("Serif", Font.BOLD, 45);
        g.setFont(font);
        g.drawString("Score: " + playing.getScore(), 5, 80);

        //draw health
        g.setColor(Color.red);
        for (int i = 0; i < lifeP; i++) {
            g.fillRect(i * 50, 0, 49, 20);
        }

        //currentBullet
        g.setColor(Color.red);

        g.fillRect(0, 25, currentBullet * 3, 20);
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
                checkCtrlDown = false;
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
        checkCtrlDown = false;
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
        bulletManager.reset();
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

    public void setMaxBullet(int maxBullet) {
        this.maxBullet = maxBullet;
    }

    public int getVetorX() {
        return vetorX;
    }

    public void setVetorX(int vetorX) {
        this.vetorX = vetorX;
    }

    public int getVetorY() {
        return vetorY;
    }

    public void setVetorY(int vetorY) {
        this.vetorY = vetorY;
    }

    public int getAttackSpead() {
        return attackSpead;
    }

    public void setAttackSpead(int attackSpead) {
        this.attackSpead = attackSpead;
    }
}

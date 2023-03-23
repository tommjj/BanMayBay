package entities;

import geometry2d.Circle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import methods.MathMethods;
import utiliz.LoadSave;

public class Rocket extends RangedWeapon{

    private Circle hitbox;
    private int updateTime = 0;
    private static double minSpeed = 5,maxSpeed = 11, radarRange = 350;
    private double speed = minSpeed;
 
    private static float maxSteeringAngle = 3f;
    private static float speedUp = 0.15f;
    
    private BufferedImage img;

    public Rocket(double x, double y, double mX, double mY) {
        this.x = x;
        this.y = y;
        Point.Double p = MathMethods.getVetorWithLength(x, y, mX, mY, speed);
        vectorX = p.x;
        vectorY = p.y;
        hitbox = new Circle((int) x, (int) y, 4);
        loadImg();
    }
    
    private void loadImg() {
        img = LoadSave.getImage(LoadSave.ROCKET);
    }

    private void updateHitBox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public void update(double x, double y) {

        if (MathMethods.getLength(this.x, this.y, x, y) <= radarRange) {
            double agEnemy = MathMethods.getAngle(this.x, this.y, x, y);
            double agRoket = MathMethods.getAngle(0, 0, vectorX, vectorY);
            double temp = agEnemy - agRoket;
            temp = Math.abs(temp) < 180 ? temp : -temp;
            temp = Math.abs(temp) <= maxSteeringAngle ? temp : temp > 0 ? maxSteeringAngle : -maxSteeringAngle;

            Point.Double newVector = MathMethods.turnCenter(vectorX, vectorY, Math.toRadians(temp));
            if(temp >= maxSteeringAngle && speed > minSpeed) {
                speed -= speedUp;
            } else if(speed <= maxSpeed){
                speed += speedUp;
            }       
            newVector = MathMethods.getVetorWithLength(0, 0, newVector.x, newVector.y, speed);
            vectorX = newVector.x;
            vectorY = newVector.y;
        }
        this.x += vectorX;
        this.y += vectorY;
        updateHitBox();
        updateTime++;

    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(hitbox.x, hitbox.y);
        g2d.rotate(Math.toRadians(MathMethods.getAngle(x, y, x+vectorX, y+vectorY)));

        g2d.drawImage(img, (-4), (-4), 6, 12, null);
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public static double getMaxSpeed() {
        return maxSpeed;
    }

    public static void setMaxSpeed(double maxSpeed) {
        Rocket.maxSpeed = maxSpeed;
    }

    public static float getMaxSteeringAngle() {
        return maxSteeringAngle;
    }

    public static void setMaxSteeringAngle(float maxSteeringAngle) {
        Rocket.maxSteeringAngle = maxSteeringAngle;
    }

    public static double getMinSpeed() {
        return minSpeed;
    }

    public static void setMinSpeed(double minSpeed) {
        Rocket.minSpeed = minSpeed;
    }

    public static double getRadarRange() {
        return radarRange;
    }

    public static void setRadarRange(double radarRange) {
        Rocket.radarRange = radarRange;
    }

    public static float getSpeedUp() {
        return speedUp;
    }

    public static void setSpeedUp(float speedUp) {
        Rocket.speedUp = speedUp;
    }
    
    

}

package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import methods.MathMethods;

public class Rocket {

    private double x, y;
    private double vetorX, vetorY;
    private Rectangle hitbox;
    private int updateTime = 0;
    private static double minSpeed = 5,maxSpeed = 11, radarRange = 350;
    private double speed = minSpeed;
 
    private static float maxSteeringAngle = 3f;
    private static float speedUp = 0.15f;

    public Rocket(double x, double y, double mX, double mY) {
        this.x = x;
        this.y = y;
        Point.Double p = MathMethods.getVetorWithLength(x, y, mX, mY, speed);
        vetorX = p.x;
        vetorY = p.y;
        hitbox = new Rectangle((int) x, (int) y, 7, 7);
    }

    private void updateHitBox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void update(double x, double y) {

        if (MathMethods.getLength(this.x, this.y, x, y) <= radarRange) {
            double agEnemy = MathMethods.getAngle(this.x, this.y, x, y);
            double agRoket = MathMethods.getAngle(0, 0, vetorX, vetorY);
            double temp = agEnemy - agRoket;
            temp = Math.abs(temp) < 180 ? temp : -temp;
            temp = Math.abs(temp) <= maxSteeringAngle ? temp : temp > 0 ? maxSteeringAngle : -maxSteeringAngle;

            Point.Double newVetor = MathMethods.turnCenter(vetorX, vetorY, Math.toRadians(temp));
            if(temp >= maxSteeringAngle && speed > minSpeed) {
                speed -= speedUp;
            } else if(speed <= maxSpeed){
                speed += speedUp;
            }       
            newVetor = MathMethods.getVetorWithLength(0, 0, newVetor.x, newVetor.y, speed);
            vetorX = newVetor.x;
            vetorY = newVetor.y;
        }
        this.x += vetorX;
        this.y += vetorY;
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
        maxSpeed = maxSpeed;
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

package hellofx;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class PlayerShip extends ShipSprite {
    private static double[] shipPoints = {
        25.0, 0.0,
        0.0, 50.0,
        25.0, 25.0,
        25.0, 0.0,
        25.0, 25.0,
        50.0, 50.0,
        25.0, 0.0,
    };
    private double angleFactor = 4;
    private double currentAngle;
    private boolean invincible = false;
    long deathtime = 0;
    Point2D speed;


    PlayerShip(int x, int y) {
        super(PlayerShip.shipPoints);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setFill(Color.WHITE);
        this.speed = new Point2D(0, 0);
    }

    public void setInvincible(boolean bool) {
        this.invincible = bool;
    }

    public boolean getInvincible() {
        return this.invincible;
    }

    public void death() {
        this.setTranslateX(500);
        this.setTranslateY(450);
        this.invincible();
        this.setRotate(0);
        this.speed = new Point2D(0, 0);
        this.deathtime = System.currentTimeMillis();
    }

    public void invincible() {
        this.setInvincible(true);
        this.setFill(Color.GREEN);
    }

    public void notinvincible() {
        this.setInvincible(false);
        this.setFill(Color.WHITE);
    }
    
    public void hyperspace(int width, int height) {
        double x = Math.random() * width;
        double y = Math.random() * height;
        this.setTranslateX(x);
        this.setTranslateY(y); 
    }

    public void changeAngle(String direction) {
        currentAngle = getRotate();
        if (direction == "left") setRotate(currentAngle - angleFactor);
        else if (direction == "right") setRotate(currentAngle + angleFactor);
    }

    public double getBoundsCenterX() {
        double halfWidth = this.getLayoutBounds().getWidth()/2;
        double squareCenterX = this.getTranslateX() + halfWidth;
        return squareCenterX;
    }

    public double getBoundsCenterY() {
        double halfWidth = this.getLayoutBounds().getWidth()/2;
        double squareCenterY = this.getTranslateY() + halfWidth;
        return squareCenterY;
    }

    public void thrust() {
        double currentVelocityX = Math.sin(Math.toRadians(this.getRotate()));
        double currentVelocityY = -Math.cos(Math.toRadians(this.getRotate()));
        currentVelocityX *= 0.06;
        currentVelocityY *= 0.06;
        this.speed = this.speed.add(currentVelocityX, currentVelocityY);
     }

     public void move() {
        this.setTranslateX(this.getTranslateX() + this.speed.getX());
        this.setTranslateY(this.getTranslateY() + this.speed.getY());
        this.wrap();
     }
}

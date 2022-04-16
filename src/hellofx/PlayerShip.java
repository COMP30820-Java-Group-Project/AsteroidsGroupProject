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
        // this.currentVelocityX = 0;
        // this.currentVelocityY = 0;
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
        this.setSpeed(0);
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

    // public void changeSpeed(double speedChange) {
    //     this.speed += speedChange;
    //      if (this.getSpeed() < 0) {
    //         this.speed = 0;
    //     }
    //     // setting arbitrary max speed - feel free to change
    //     if (this.getSpeed() > 6) {
    //         this.speed = 6;
    //     }
    // }

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
        //this.changeSpeed(0.1);
        //this.currentAngle = Math.toRadians(this.getRotate());
        //double objectAngle = this.getRotate();
        // double currentX = this.getTranslateX();
        // double currentY = this.getTranslateY();
        double currentVelocityX = Math.sin(Math.toRadians(this.getRotate()));
        double currentVelocityY = -Math.cos(Math.toRadians(this.getRotate()));
        currentVelocityX *= 0.05;
        currentVelocityY *= 0.05;
        // double changeX = Math.cos(Math.toRadians(this.getRotate()));
        // double changeY = Math.sin(Math.toRadians(this.getRotate()));
        // this.setTranslateX(this.getTranslateX() + currentVelocityX);
        // this.setTranslateY(this.getTranslateY() + currentVelocityY);
        this.speed = this.speed.add(currentVelocityX, currentVelocityY);
     }

     public void move() {
        // double cvx = currentVelocityX + Math.sin(currentAngle);
        // double cvy = currentVelocityX + -Math.cos(currentAngle);
        this.setTranslateX(this.getTranslateX() + this.speed.getX());
        this.setTranslateY(this.getTranslateY() + this.speed.getY());
        this.wrap();
     }
}

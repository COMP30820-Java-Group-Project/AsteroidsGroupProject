package hellofx;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class Alien extends ShipSprite {

    private static double[] alienCoordinates = { 
        // New coords
        30.0, 0.0,
        40.0, 20.0,
        45.0, 10.0,
        60.0, 25.0,
        55.0, 30.0,
        60.0, 35.0,
        45.0, 50.0,
        40.0, 40.0,
        30.0, 60.0,
        20.0, 40.0,
        15.0, 50.0,
        0.0, 35.0,
        5.0, 30.0,
        0.0, 25.0,
        15.0, 10.0,
        20.0, 20.0,
        30.0, 0.0,
        };
    
    double directionTime;
    long changeTime;
    double spawnTime;
    boolean onScreen;
    
    double objectAngle = 90;
        
    Alien() {
        super(Alien.alienCoordinates);
        super.speed = 4;
        this.setRotate(90);
        this.directionTime = 200 + Math.random() * 3000;
        this.changeTime = System.currentTimeMillis();
        this.spawnTime = System.currentTimeMillis() + 10000 + Math.random() * 6000;
        this.onScreen = false;
        this.setFill(Color.WHITE);
    }
    
    public double[] getLocation(){
        double xCord = this.getTranslateX();
        double yCord = this.getTranslateY();
        double[] coordinates = {xCord, yCord};
        return coordinates;
    }

    public double getAngleToTraverse(double playerX, double playerY) {
        double opposite = playerX - this.getTranslateX();
        double adjacent = playerY - this.getTranslateY();
        double angleToTraverse = Math.toDegrees(Math.atan(opposite/adjacent));
        return angleToTraverse;
    }

    public void pointToPlayer(double playerX, double playerY) {
        double alienX = this.getNoseX();
        double alienY = this.getNoseY();
        double coordY = playerY - alienY;
        double coordX = playerX - alienX;
        double angleOfDiff = Math.toDegrees(Math.atan2(coordY, coordX));

        // Getting ranNum from -10 to 10, makes alien shoot behind and ahead of player at times
        Random alienTestRan = new Random();
        int angleChange = alienTestRan.nextInt(40-0) + 0;
        angleChange -= 20;
        this.setRotate(angleOfDiff+90.0+angleChange);
    }

    public void spawn() {
        this.onScreen = true;
        this.setTranslateX(Main.SCREENWIDTH * Math.random());
        this.setTranslateY(Main.SCREENHEIGHT * Math.random());
    }

    public void isHit() {
        this.onScreen = false;
        this.spawnTime = System.currentTimeMillis() + 10000 + Math.random() * 6000;
    }

    public void changeDirection() {
        this.directionTime = 500 + Math.random()* 3500;
        this.changeTime = System.currentTimeMillis();
        if (this.objectAngle==90) {
            this.objectAngle = 270;
        }
        else {
            this.objectAngle = 90;
        }
    }

    public boolean checkOnScreen() {
        if (this.onScreen) {
            if (System.currentTimeMillis() - this.changeTime > this.directionTime) {
                this.changeDirection();
            }
            return true;
        }
        return false;
    }
   
    public void changeSpeed(int speedChange) {
        this.speed += speedChange;
    }

    // new move method for alien since getRotate is used for pointing towards ship
    public void move() {
        double currentX = this.getTranslateX();
        double currentY = this.getTranslateY();
        double currentVelocityX = this.speed * Math.sin(Math.toRadians(this.objectAngle));
        double currentVelocityY = -this.speed * Math.cos(Math.toRadians(this.objectAngle));
        this.setTranslateX(currentX + currentVelocityX);
        this.setTranslateY(currentY + currentVelocityY);
        this.wrap();
     }
}
package hellofx;

import javafx.scene.shape.Polyline;

public class PlayerShip extends Polyline {
    private static double[] shipPoints = {
        20.0, 0.0,
        0.0, 60.0,
        20.0, 40.0,
        20.0, 0.0,
        20.0, 40.0,
        40.0, 60.0,
        20.0, 0.0,
    };
    private int speed = 0;
    private double angleFactor = 14.4;

    PlayerShip(int x, int y) {
        super(PlayerShip.shipPoints);
        setTranslateX(x);
        setTranslateY(y);
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getNoseX() {
        //update
        return this.speed;
    }

    public int getNoseY() {
        //update
        return this.speed;
    }

    public void changeSpeed(int speedChange) {
        this.speed += speedChange;
    }

    public void changeAngle(String direction) {
        double currentAngle = getRotate();
        if (direction == "left") setRotate(currentAngle - angleFactor);
        else if (direction == "right") setRotate(currentAngle + angleFactor);
    }

    public void move() {
        double playerAngle = this.getRotate();
        int playerSpeed = this.getSpeed();
        double currentX = this.getTranslateX();
        double currentY = this.getTranslateY();
        double currentVelocityX = playerSpeed * Math.sin(Math.toRadians(playerAngle));
        double currentVelocityY = -playerSpeed * Math.cos(Math.toRadians(playerAngle));
        this.setTranslateX(currentX + currentVelocityX);
        this.setTranslateY(currentY + currentVelocityY);
    }
}

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

    public void changeSpeed(int speedChange) {
        this.speed += speedChange;
    }

    public void changeAngle(String direction) {
        double currentAngle = getRotate();
        if (direction == "left") setRotate(currentAngle - angleFactor);
        else if (direction == "right") setRotate(currentAngle + angleFactor);
    }
}

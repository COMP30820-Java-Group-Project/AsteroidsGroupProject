package hellofx;

import javafx.scene.shape.Polygon;

public class PlayerShip extends Polygon {
    private static double[] shipPoints = {
        0.0, 50.0,
        25.0, 0.0,
        50.0, 50.0
    };
    private int speed = 0;
    private double angleFactor = 24.0;

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

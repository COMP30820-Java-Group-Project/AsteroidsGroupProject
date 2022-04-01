package hellofx;

import javafx.scene.shape.Polygon;

public class PlayerShip extends Polygon {
    private static double[] shipPoints = {
        0.0, 50.0,
        25.0, 0.0,
        50.0, 50.0
    };

    private int speed = 0;

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
}

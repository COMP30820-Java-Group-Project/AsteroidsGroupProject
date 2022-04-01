package hellofx;

import javafx.scene.shape.Polygon;

public class PlayerShip extends Polygon {
    private static double[] shipPoints = {
        0.0, 50.0,
        25.0, 0.0,
        50.0, 50.0
    };

    private int speedFactor = 0;
    private boolean shipActivated = false;

    PlayerShip(int x, int y) {
        super(PlayerShip.shipPoints);
        setTranslateX(x);
        setTranslateY(y);
    }

    public void increaseSpeedFactor() {
        if (this.shipActivated == false) {
            this.speedFactor += 1;
            this.activateShip();
        } else {
            this.speedFactor += 1;
        }
    }

    private void activateShip() {
        this.shipActivated = true;
        while (true) {
            double currentY = getTranslateY();
            setTranslateY(currentY + (-5 * this.speedFactor));
        }
    }
}

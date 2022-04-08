package hellofx;

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

    PlayerShip(int x, int y) {
        super(PlayerShip.shipPoints);
        setTranslateX(x);
        setTranslateY(y);
    }

    public double getSpeed() {
        return this.speed;
    
    }
    
    public void hyperspace(int width, int height) {
        double x = Math.random() * width;
        double y = Math.random() * height;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void changeSpeed(double speedChange) {
        this.speed += speedChange;
         if (this.getSpeed() < 0) {
            this.speed = 0;
        }
        // setting arbitrary max speed - feel free to change
        if (this.getSpeed() > 6) {
            this.speed = 6;
        }
    }

    public void changeAngle(String direction) {
        double currentAngle = getRotate();
        if (direction == "left") setRotate(currentAngle - angleFactor);
        else if (direction == "right") setRotate(currentAngle + angleFactor);
    }
}

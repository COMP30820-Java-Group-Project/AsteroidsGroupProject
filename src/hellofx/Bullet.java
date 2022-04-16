package hellofx;

import javafx.scene.paint.Color;

public class Bullet extends Sprite {

    // using enums for bullet type
    private BulletType bType;
    private Color colour;

    // make size a constant
    final static int B_WIDTH = 2;

    private static double[] bulletPoints = {
        B_WIDTH, -B_WIDTH, B_WIDTH, B_WIDTH, -B_WIDTH, B_WIDTH, -B_WIDTH, -B_WIDTH, B_WIDTH, -B_WIDTH
    };
    long startTime = System.currentTimeMillis();
    
    public Bullet(double x1, double y1, double r1, double s, BulletType bType) {
        // x1 and y1 to be nose position of ship that fired (either alien or space ship)
        super(Bullet.bulletPoints);
        this.setTranslateX(x1);
        this.setTranslateY(y1);
        this.setRotate(r1);
        // setting to 6 so bullet travels at reasonable speed regardless
        // bullet speed dependent on ship speed
        this.speed = 6.0 + s;

        this.bType = bType;
        this.colour = getColour(this.bType);
        this.setFill(this.colour);
        
    }

    // final as it is used in constructor
    private final Color getColour(BulletType bt) {
        Color c;
        switch (bt) {
            case PLAYER:
                c = Color.GREEN;
                break;              
            case ALIEN:
                c = Color.RED;
                break;
            default:
            c = Color.WHITE;
            break;
        }
        return c;
    }
}

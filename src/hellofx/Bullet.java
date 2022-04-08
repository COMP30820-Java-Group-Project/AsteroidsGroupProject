package hellofx;

public class Bullet extends Sprite {
    private static double[] bulletPoints = {
        2, -2, 2, 2, -2, 2, -2, -2, 2, -2
    };
    private double x, y, rotate;
    private double life;
    //private Rectangle size;
    long startTime = System.currentTimeMillis();
    public static final int BULLET_WIDTH = 2;
    
    public Bullet(double x1, double y1, double r1, double s) {
        // x1 and y1 to be nose position of ship that fired (either alien or space ship)
        super(Bullet.bulletPoints);
        this.setTranslateX(x1);
        this.setTranslateY(y1);
        this.setRotate(r1);
        // setting to 6 so bullet travels at reasonable speed regardless
        // bullet speed dependent on ship speed
        this.speed = 6.0 + s;

    }

    public void timeout() {
    }

    public void damageable() {
        // bullet will need to disappear if it collides with alien/asteroid
        // should call addPoints()
    }

    public int addPoints() {
        // should set the points counter to 1 more than current value
        // pointsSet() = pointsGet() + 1;
        return 1;
    }
}

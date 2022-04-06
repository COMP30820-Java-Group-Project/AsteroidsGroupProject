package hellofx;


import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class Bullet extends Polyline {
    private static double[] bulletPoints = {
        2, -2, 2, 2, -2, 2, -2, -2
    };
    private double x, y, rotate;
    private double life;
    //private Rectangle size;
    private int speed = 0;
    long startTime = System.currentTimeMillis();
    public static final int BULLET_WIDTH = 2;
    
public Bullet(double x1, double y1, double r1, int s) {
    // x1 and y1 to be nose position of ship that fired (either alien or space ship)
    super(Bullet.bulletPoints);
    this.setTranslateX(x1);
    this.setTranslateY(y1);
    this.setRotate(r1);
    // setting to 6 so bullet travels at reasonable speed regardless
    // bullet speed dependent on ship speed
    this.speed = 6 + s;

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
// method updates coordinates if either goes off screen
public void wrap() {
      //Rectangle2D screenBounds = Screen.getPrimary().getBounds();
     if (this.getTranslateX() + this.getLayoutBounds().getWidth()/2<0)
     this.setTranslateX(1000+this.getLayoutBounds().getWidth()/2);
     if (this.getTranslateX() > 1000 + this.getLayoutBounds().getWidth()/2)
     this.setTranslateX(-this.getLayoutBounds().getWidth()/2);
     if (this.getTranslateY() + this.getLayoutBounds().getHeight()/2<0)
     this.setTranslateY(900+this.getLayoutBounds().getHeight()/2);
     if (this.getTranslateY() > 900 + this.getLayoutBounds().getHeight()/2)
     this.setTranslateY(-this.getLayoutBounds().getHeight()/2);
 }

public void move() {
	double playerAngle = this.getRotate();
    int bulletSpeed = this.speed;
    double currentX = this.getTranslateX();
    double currentY = this.getTranslateY();
    double currentVelocityX = bulletSpeed * Math.sin(Math.toRadians(playerAngle));
    double currentVelocityY = -bulletSpeed * Math.cos(Math.toRadians(playerAngle));
    this.setTranslateX(currentX + currentVelocityX);
    this.setTranslateY(currentY + currentVelocityY);
    this.wrap();
    

}

}

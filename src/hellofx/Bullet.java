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
    private int speed = 6;
    long startTime = System.currentTimeMillis();
    public static final int BULLET_WIDTH = 2;
    
public Bullet(double x1, double y1, double r1) {
    // x1 and y1 to be nose position of ship that fired (either alien or space ship)
    super(Bullet.bulletPoints);
    setTranslateX(x1);
    setTranslateY(y1);
    setRotate(r1);
    // this.x = x1;
    // this.y = y1;
    // this.life = 0;
    // new Polygon(2, -2, 2, 2, -2, 2, -2, -2, this.x, this.y);
    //this.damage = d;
    
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
public void wrap() {
    //Rectangle2D screenBounds = Screen.getPrimary().getBounds();
     if (this.getTranslateX() + this.getLayoutBounds().getWidth()/2<0)
     this.setTranslateX(1000+this.getLayoutBounds().getWidth()/2);
     if (this.getTranslateX() > 1000 + this.getLayoutBounds().getWidth()/2)
     this.setTranslateX(-this.getLayoutBounds().getWidth()/2);
     if (this.getTranslateY() + this.getLayoutBounds().getHeight()/2<0)
     this.setTranslateY(900+this.getLayoutBounds().getWidth()/2);
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

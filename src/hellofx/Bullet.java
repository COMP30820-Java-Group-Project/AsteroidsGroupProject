package hellofx;


import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class Bullet extends Polyline {
    private double x, y;
    private double life;
    //private Rectangle size;
    public static final int BULLET_WIDTH = 2;
    
public Bullet(double x1, double y1) {
    // x1 and y1 to be nose position of ship that fired (either alien or space ship)
    
    this.x = x1;
    this.y = y1;
    this.life = 0;
    new Polygon(2, -2, 2, 2, -2, 2, -2, -2, this.x, this.y);
    //this.damage = d;
    
}


public void timeout() {
	// assuming we are using AnimationTimer class
    // delete after 2 seconds
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

public void move() {
	// should inherit this from a Movable class
}

}

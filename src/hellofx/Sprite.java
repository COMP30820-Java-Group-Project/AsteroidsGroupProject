package hellofx;
import javafx.scene.shape.Polyline;

public abstract class Sprite extends Polyline {
    protected double speed = 0;
    
    // For everything else
    public Sprite(double[] points){
        super(points);
    }

    // For asteroids
    public Sprite() {
        super();
    }
    
    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double s) {
        this.speed = s;
    }

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
       double objectAngle = this.getRotate();
       double currentX = this.getTranslateX();
       double currentY = this.getTranslateY();
       double currentVelocityX = this.speed * Math.sin(Math.toRadians(objectAngle));
       double currentVelocityY = -this.speed * Math.cos(Math.toRadians(objectAngle));
       this.setTranslateX(currentX + currentVelocityX);
       this.setTranslateY(currentY + currentVelocityY);
       this.wrap();
    }
}

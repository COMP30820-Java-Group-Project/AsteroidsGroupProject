package hellofx;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.*;
import java.util.List;

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
       this.setTranslateX(Controller.SCREENWIDTH+this.getLayoutBounds().getWidth()/2);
       if (this.getTranslateX() > Controller.SCREENWIDTH + this.getLayoutBounds().getWidth()/2)
       this.setTranslateX(-this.getLayoutBounds().getWidth()/2);
       if (this.getTranslateY() + this.getLayoutBounds().getHeight()/2<0)
       this.setTranslateY(Controller.SCREENHEIGHT-100+this.getLayoutBounds().getHeight()/2);
       if (this.getTranslateY() > Controller.SCREENHEIGHT-100 + this.getLayoutBounds().getHeight()/2)
       this.setTranslateY(-this.getLayoutBounds().getHeight()/2);
   }

   public void move() {
       double objectAngle = this.getRotate();
       double currentX = this.getTranslateX();
       double currentY = this.getTranslateY();
       double currentVelocityX = this.speed * Math.cos(Math.toRadians(objectAngle));
       double currentVelocityY = this.speed * Math.sin(Math.toRadians(objectAngle));
       this.setTranslateX(currentX + currentVelocityX);
       this.setTranslateY(currentY + currentVelocityY);
       this.wrap();
    }
    public static boolean shapesHaveIntersection(Shape s1, Shape s2) {
        Shape sharedArea = Shape.intersect(s1, s2);
        // If sharedArea bounds > 0 then true
        return sharedArea.getBoundsInLocal().getWidth() > 0;
    }

    public static boolean listHasIntersection(List<Sprite> L, Shape s) {
        for (int i = 0; i < L.size(); i++) {
            Sprite b = L.get(i);    
            Shape area = Shape.intersect(b, s);
            if (area.getBoundsInLocal().getWidth() > 0) return true;
        }
        return false;
    }

}

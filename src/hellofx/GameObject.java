package hellofx;
import javafx.scene.shape.Polyline;

public abstract class GameObject extends Polyline {
    public GameObject(double[] points){
        super(points);
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
}

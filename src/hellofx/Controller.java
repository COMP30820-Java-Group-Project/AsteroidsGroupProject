package hellofx;
import javafx.scene.shape.*;

public class Controller {
    public static boolean shapesHaveIntersection(Shape s1, Shape s2) {
        Shape sharedArea = Shape.intersect(s1, s2);
        // If sharedArea bounds > 0 then true
        return sharedArea.getBoundsInLocal().getWidth() > 0 ? true : false;
    }
}
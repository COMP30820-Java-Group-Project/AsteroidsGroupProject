package hellofx;
import javafx.scene.shape.*;
import java.util.List;


public class Controller {
    public static boolean shapesHaveIntersection(Shape s1, Shape s2) {
        Shape sharedArea = Shape.intersect(s1, s2);
        // If sharedArea bounds > 0 then true
        return sharedArea.getBoundsInLocal().getWidth() > 0 ? true : false;
    }

    public static boolean listHasIntersection(List<Sprite> L, Shape s) {
        for (int i = 0; i < L.size(); i++) {
            Sprite b = L.get(i);    
            Shape area = Shape.intersect(b, s);
            if (area.getBoundsInLocal().getWidth() > 0) {
                return true;
            }
        }
        return false;
        }
}
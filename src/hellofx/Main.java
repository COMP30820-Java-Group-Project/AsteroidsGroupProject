package hellofx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {

    // Lets us add children
    private Pane root = new Pane();

    private Parent createContent() {
        root.setPrefSize(1000, 900);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello Asteroids");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

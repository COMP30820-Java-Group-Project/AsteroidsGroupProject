package hellofx;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {

    // Lets us add children
    private Pane root = new Pane();

    private PlayerShip player = new PlayerShip(475, 600);

    private Parent createContent() {
        root.setPrefSize(1000, 900);

        root.getChildren().add(player);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello Asteroids");
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case W:
                    player.increaseSpeedFactor();
                    break;
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
